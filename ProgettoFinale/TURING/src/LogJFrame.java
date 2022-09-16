import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LogJFrame extends JFrame implements ActionListener{
	
	static HelpClient help ;
	private EditJFrame EditFrame;
	private ClientJFrame ClientJF;
	static String utente;
	
	ExecutorService invito;
	//creoo le variabili per l'interfaccia
	JPanel centroPnl;
	
	Hashtable<Integer,JLabel> infoLblnd;
	Hashtable<Integer,JLabel> infoLblns;
	Hashtable<Integer,JLabel> vuoto;
	JLabel infoLblc ;

	Hashtable<Integer,JTextField> nomeDoc;
	Hashtable<Integer,JTextField> nSez;
	JTextField nomeC;
	
	JButton create_but;
	JButton share_but;
	JButton show_but;
	JButton showd_but;
	JButton edit_but;
	JButton list_but;
	JButton logout_but;
	
	private static final long serialVersionUID = 1L;
		
	public LogJFrame(String nomeUtente,HelpClient h) {
		//le istanzio
		 super("Turing:"+nomeUtente);
		 utente = nomeUtente;
		 help = h;
		 //istanzio il thread che si occuperà della ricezione degli inviti
		 invito = Executors.newSingleThreadExecutor();
		 
		 centroPnl = new JPanel();
		 
		 infoLblnd = new Hashtable<Integer,JLabel>();
		 infoLblns = new Hashtable<Integer,JLabel>();
		 vuoto = new Hashtable<Integer,JLabel>();
		 
		 infoLblc = new JLabel(" collaboratore");
		 
		 nomeDoc = new Hashtable<Integer,JTextField>();
		 nSez = new Hashtable<Integer,JTextField>();
		 
		 nomeC = new JTextField(15);
		 
		 create_but = new JButton("create");
		 share_but= new JButton("share");
		 show_but = new JButton("show");
		 showd_but= new JButton("showd");
		 edit_but = new JButton("edit");
		 list_but= new JButton("list");
		 logout_but = new JButton("logout");
				 
		 for(int i = 0 ; i<= 4; i++) {
			 JLabel infoLb = new JLabel(" nome documento");
			infoLblnd.put(i,infoLb);	
		 }
		 
		 for(int i = 0 ; i<= 2; i++) {
			 JLabel infoLb = new JLabel(" n.ro sez.");
			infoLblns.put(i,infoLb);	
		 }
		 
		 for(int i = 0 ; i<= 16; i++) {
			 JLabel vuotoLb = new JLabel("");
			 vuoto.put(i,vuotoLb);	
		 }
		 
		 for(int i = 0 ; i<= 4; i++) {
			 JTextField nomeD = new JTextField(15);
			 nomeDoc.put(i,nomeD);	
		 }
		 
		 for(int i = 0 ; i<= 2; i++) {
			 JTextField nS = new JTextField(15);
			 nSez.put(i,nS);	
		 }

		//le aggiungo all'interfaccia così da impostarne le sembianze
		centroPnl.add(infoLblnd.get(0));
		centroPnl.add(infoLblns.get(0));
		centroPnl.add(vuoto.get(0));
		centroPnl.add(nomeDoc.get(0));
		centroPnl.add(nSez.get(0));
		centroPnl.add(create_but);
		
		centroPnl.add(infoLblnd.get(1));
		centroPnl.add(infoLblc);
		centroPnl.add(vuoto.get(1));
		centroPnl.add(nomeDoc.get(1));
		centroPnl.add(nomeC);
		centroPnl.add(share_but);
		
		
		centroPnl.add(infoLblnd.get(2));
		centroPnl.add(infoLblns.get(1));
		centroPnl.add(vuoto.get(2));
		centroPnl.add(nomeDoc.get(2));
		centroPnl.add(nSez.get(1));
		centroPnl.add(show_but);
		
		centroPnl.add(infoLblnd.get(3));
		centroPnl.add(vuoto.get(3));
		centroPnl.add(vuoto.get(4));
		centroPnl.add(nomeDoc.get(3));
		centroPnl.add(vuoto.get(5));
		centroPnl.add(showd_but);
		
		centroPnl.add(infoLblnd.get(4));
		centroPnl.add(infoLblns.get(2));
		centroPnl.add(vuoto.get(6));
		centroPnl.add(nomeDoc.get(4));
		centroPnl.add(nSez.get(2));
		centroPnl.add(edit_but);
		
		centroPnl.add(vuoto.get(7));
		centroPnl.add(vuoto.get(8));
		centroPnl.add(vuoto.get(9));
		centroPnl.add(vuoto.get(10));
		centroPnl.add(vuoto.get(11));
		centroPnl.add(list_but);
		
		centroPnl.add(vuoto.get(12));
		centroPnl.add(vuoto.get(13));
		centroPnl.add(vuoto.get(14));
		centroPnl.add(vuoto.get(15));
		centroPnl.add(vuoto.get(16));
		centroPnl.add(logout_but);
		//setto le misure dell'interfaccia 
		centroPnl.setLayout(new GridLayout(14,3));
		getContentPane().add(centroPnl,BorderLayout.CENTER);
		pack();
		
		
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//mando in esecuzione il thread che si occupa della ricezione di inviti
		invito.submit(new Invito(help,utente));
		
		create_but.addActionListener(this);
		share_but.addActionListener(this);
		show_but.addActionListener(this);
		showd_but.addActionListener(this);
		edit_but.addActionListener(this);
		list_but.addActionListener(this);
		logout_but.addActionListener(this);
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String operation = e.getActionCommand();
		String receivedString = "";
		
		switch(operation) {
			
		case("create"):
			try {
				JOptionPane.showMessageDialog(null,help.connWithServer(operation+"1"+utente+"2"+nomeDoc.get(0).getText()+"3"+nSez.get(0).getText()));
				nomeDoc.get(0).setText("");
				nSez.get(0).setText("");
			} catch (HeadlessException | IOException e1) {e1.printStackTrace();}
			break;
		
		case("share"):
			try {
				JOptionPane.showMessageDialog(null,help.connWithServer(operation+"1"+utente+"2"+nomeDoc.get(1).getText()+"3"+nomeC.getText()));
				nomeDoc.get(1).setText("");
				nomeC.setText("");
			} catch (HeadlessException | IOException e1) {e1.printStackTrace();}
			break;
			
		case("show"):
			
			
			try {
				receivedString = help.connWithServer(operation+"1"+utente+"2"+nomeDoc.get(2).getText()+"3"+nSez.get(1).getText());
				if(receivedString.contains(nomeDoc.get(2).getText()+"_"+nSez.get(1).getText())) {
					//imposto il nome e il contenuto della sezione
					String nameFile = receivedString.substring(0,receivedString.indexOf(":"));
		    		String contenutoFile = receivedString.substring(receivedString.indexOf(":")+1);
		    		//creo un nuovo file di testo per contenerla
		    		Path path = Paths.get(nameFile);
		    		Files.createFile(path);
		    		//apro un canale
		    		FileChannel channel = FileChannel.open(Paths.get(nameFile), StandardOpenOption.WRITE);
		    		ByteBuffer buffer = ByteBuffer.wrap(contenutoFile.getBytes());
		    		//scrivo il contenuto della sezione scaricata nel nuovo file di testo creato
		    		while(buffer.hasRemaining()) { channel.write(buffer); }
		    		channel.close();
		    		buffer.clear();
		    		JOptionPane.showMessageDialog(null,"Sezione " +nSez.get(1).getText()+ " scaricata con successo");
		    	}
				else JOptionPane.showMessageDialog(null,receivedString);
				
				nomeDoc.get(2).setText("");
				nSez.get(1).setText("");
				
			} catch (IOException e1) {e1.printStackTrace();}
			break;
	    
		case("showd"):
			//creo una cartella con il nome del documento da scaricare
			Path pathdir = Paths.get(utente);
			try {  Files.createDirectory(pathdir); } catch (IOException e1) {e1.printStackTrace();}
			int n = 1;
			receivedString = "";
	        //faccio la stessa cosa di sopra ma per tutte le sezioni di un documento
			while(receivedString.compareTo("nel documento "+nomeDoc.get(3).getText()+" non e' presente la sezione "+(n-1)) != 0) {
			
				try {
					receivedString = help.connWithServer("show"+"1"+utente+"2"+nomeDoc.get(3).getText()+"3"+n);
				
				
					if(receivedString.contains(nomeDoc.get(3).getText()+"_"+n)) {
						String nameFile = receivedString.substring(receivedString.indexOf("_")+1,receivedString.indexOf(":"));
						String contenutoFile = receivedString.substring(receivedString.indexOf(":")+1);
						Path path = Paths.get(nomeDoc.get(3).getText()+"Agg"+"/"+nameFile);
			    		
			    			
							Files.createFile(path);
							FileChannel channel;
						
							channel = FileChannel.open(path, StandardOpenOption.WRITE);
							ByteBuffer buffer = ByteBuffer.wrap(contenutoFile.getBytes());
							while(buffer.hasRemaining()) { channel.write(buffer); }
							channel.close();
							buffer.clear();
						
					}
					n++;
				} catch (IOException e1) {e1.printStackTrace();}
				
			}
			JOptionPane.showMessageDialog(null,"Documento "+nomeDoc.get(3).getText()+" scaricato con successo");
			nomeDoc.get(3).setText("");
			break;
		
		case("list"):
			
			try {
				
				JOptionPane.showMessageDialog(null,help.connWithServer(operation+"1"+utente+"2"));
				
			} catch (HeadlessException | IOException e1) {e1.printStackTrace();}
			break;
		
		case("edit"):
			
			try {
				receivedString = help.connWithServer(operation+"1"+utente+"2"+nomeDoc.get(4).getText()+"3"+nSez.get(2).getText());
				
				if(receivedString.contains(nomeDoc.get(4).getText()+"_"+nSez.get(2).getText()+":")) {
					
					String nameFile = receivedString.substring(0,receivedString.indexOf(":"));
					String contenutoFile = receivedString.substring(receivedString.indexOf(":")+1);
					//creo anche qui un nuovo file che conterrà il contenuto della sezione scaricata
					Path path = Paths.get(nameFile);
					Files.createFile(path);
	    		
					FileChannel channel = FileChannel.open(path, StandardOpenOption.WRITE);
					ByteBuffer buffer = ByteBuffer.wrap(contenutoFile.getBytes());
					
					while(buffer.hasRemaining()) { channel.write(buffer); }
					channel.close();
					buffer.clear();
					
					JOptionPane.showMessageDialog(null,"Sezione " + nSez.get(2).getText() + " del documento "+nomeDoc.get(4).getText()+" scaricata con successo");
					//termino il thread che si occupa di ricevere i messaggi
					invito.shutdownNow();
					//apro la terza finestra che compone l'interfaccia e chiudo questa
					EditFrame= new EditJFrame(utente,nomeDoc.get(4).getText(),nSez.get(2).getText(),help);
					EditFrame.setVisible(true);
					WindowEvent close = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
					this.dispatchEvent(close);
				}
				else JOptionPane.showMessageDialog(null,receivedString);
				
				nSez.get(2).setText("");
				nomeDoc.get(4).setText("");
				
			} catch (IOException e1) { e1.printStackTrace();}
			
			break;
			
		case("logout"):
			try {
				
				JOptionPane.showMessageDialog(null,help.connWithServer(operation+"1"));
				//anche in questo caso termino il thread che si occupa di ricevere i messaggi
				invito.shutdownNow();
				//apro la prima finastra che compone l'interfaccia e chiudo questa
				ClientJF= new ClientJFrame();
				ClientJF.setVisible(true);
				WindowEvent close = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
				this.dispatchEvent(close);
				
			} catch (HeadlessException | IOException e1) {e1.printStackTrace();}
				
			
		
			
		}
		
	
		
	}
}
