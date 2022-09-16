import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class EditJFrame extends JFrame implements ActionListener {
	//oggetti utili alla costruzione dell'interfaccia
	JScrollPane scrolltextarea;
	JTextArea textarea;
	JTextField messaggio;
	JButton button;
	JButton endEdit;
	
	String nomeDoc;
	String n_sez;
	String utente;
	String indMulti;
	
	HelpClient help ;
	
	//thread da attivare
	ExecutorService server;
	ExecutorService receiver;
	ExecutorService invito;
	
	
	private static final long serialVersionUID = 1L;
	private final int margine = 5;
	
	public  EditJFrame(String utente, String nomeDoc,String n_sez, HelpClient h) throws IOException {
		//formo l'interfaccia
		super("Turing:"+utente);
		this.utente = utente;
		this.nomeDoc = nomeDoc;
		this.n_sez = n_sez;
		this.help = h;
		
		//istanzio invito e reciver
		 invito = Executors.newSingleThreadExecutor();
		 receiver = Executors.newSingleThreadExecutor();
		 
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setPreferredSize(new Dimension(250,150));
		
		//istanzio le varie parti dell'interfaccia
		textarea = new JTextArea("");
		messaggio = new JTextField();
		button = new JButton("invia");
		endEdit = new JButton("endEdit");
		scrolltextarea = new JScrollPane(textarea);
		
		//le aggiungo a vari pannelli che andranno a formare l'interfaccia 
		SpringLayout layout = new SpringLayout();
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		panel1.add(scrolltextarea);
		panel1.add(messaggio);
		panel1.add(panel2);
		panel2.add(endEdit);
		panel2.add( new JLabel(""));
		panel2.add(button);
		
		//inposto le varie misure dell'interfaccia
		panel1.setLayout(layout);
		panel2.setLayout(new GridLayout(3,1));
		
		layout.putConstraint(SpringLayout.WEST,scrolltextarea,margine,SpringLayout.WEST,panel1);
		layout.putConstraint(SpringLayout.NORTH,scrolltextarea,margine,SpringLayout.NORTH,panel1);
		layout.putConstraint(SpringLayout.EAST,scrolltextarea,-margine,SpringLayout.EAST,panel1);
		layout.putConstraint(SpringLayout.SOUTH,scrolltextarea,-35,SpringLayout.NORTH,messaggio);
		
		layout.putConstraint(SpringLayout.WEST,messaggio,margine,SpringLayout.WEST,panel1);
		layout.putConstraint(SpringLayout.EAST,messaggio,-margine,SpringLayout.WEST,panel2);
		layout.putConstraint(SpringLayout.SOUTH,messaggio,-margine,SpringLayout.SOUTH,panel1);
		
		layout.putConstraint(SpringLayout.NORTH,panel2,margine,SpringLayout.SOUTH,scrolltextarea);
		layout.putConstraint(SpringLayout.EAST,panel2,-margine,SpringLayout.EAST,panel1);
		layout.putConstraint(SpringLayout.SOUTH,panel2,-margine,SpringLayout.SOUTH,panel1);
		
		
		this.add(panel1);
		this.pack();
		this.setVisible(true);
		
	
		//se la chat per questo documento non e' ancora stata creata la creo istanzindo e mandando in esecuzione "server"
		if(help.connWithServer("exists"+"1"+this.nomeDoc).compareTo("Server non creato")==0)
			{	server = Executors.newSingleThreadExecutor();
				server.submit(new ChatRoomServer(this.nomeDoc));}
		
		//controllo utile ad aspettare che il thread che si occupa di creare la chat sia pronto e operativo
		while(help.connWithServer("exists"+"1"+this.nomeDoc).compareTo("Server non creato")==0) {}
			
		//incremento gli utenti collegati alla chat di questo documento e mando in esecuzione il thread che si occupa di ricevere i messaggi della chat
		help.connWithServer("incCollegati"+"1"+this.nomeDoc);
		indMulti = help.connWithServer("exists"+"1"+this.nomeDoc);
		receiver.submit(new Receiver (textarea,indMulti));
		
		//azziono il thread che si occupa di ricevere gli inviti 
		invito.submit(new Invito(help,utente));	
		
		//mando in funzione i bottoni	
		button.addActionListener(this);
		endEdit.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String operation = e.getActionCommand();
		
		switch(operation) {
		
		case ("invia"):
			
			try(DatagramSocket s= new DatagramSocket()){
				
				InetAddress  address= InetAddress.getByName("127.0.0.1");
				messaggio.setText(utente+":"+messaggio.getText());
				DatagramPacket p= new DatagramPacket(messaggio.getText().getBytes("UTF-8"),0,messaggio.getText().getBytes("UTF-8").length,address,2000);
				s.send(p);
				messaggio.setText("");
				
			} catch (IOException e1) {e1.printStackTrace();}
			break;
			
		case("endEdit"):
			
			try {
				//carico la sezione aggiornata
				FileChannel channel1 = FileChannel.open(Paths.get(nomeDoc+"_"+n_sez), StandardOpenOption.READ);
   				ByteBuffer buf = ByteBuffer.allocate(1024*1024);
				boolean stop = false;
				String contFile ="";
				while(!stop) {
					int bytesRead = channel1.read(buf);
					if(bytesRead == -1) {stop = true;}
					buf.flip();
    				while(buf.hasRemaining()) {contFile+=(char)buf.get();}
    				buf.clear();
    			}
    			channel1.close();
    			
    			//cancello la sezione per l'utente
    			Files.delete(Paths.get(nomeDoc+"_"+n_sez));
    			JOptionPane.showMessageDialog(null,help.connWithServer("end-edit"+"1"+utente+"2"+nomeDoc+"3"+n_sez+"."+contFile));
    			
    			//decremento gli utenti collegati alla chat
				help.connWithServer("decCollegati"+"1"+nomeDoc);
				
				//termino i threads dell'invito e del reciver e apro una nuova finestra di tipo logjframe
				invito.shutdownNow();
				receiver.shutdownNow();
				LogJFrame LogFrame = new LogJFrame(utente,help);
				LogFrame.setVisible(true);
				WindowEvent close = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
				this.dispatchEvent(close);
				
			} catch (IOException e1) {e1.printStackTrace();}
			break;		
		}
	}

}
