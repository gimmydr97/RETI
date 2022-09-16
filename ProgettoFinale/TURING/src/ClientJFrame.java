
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ClientJFrame extends JFrame implements ActionListener {
	
	HelpClient help ;
	
	//variabili utili alla formazione dell'interfaccia
    LogJFrame LoggedFrame;
	JPanel nordPnl;
	JPanel centroPnl;
	JPanel sudPnl;
	JLabel infoLbl1;
	JLabel infoLbl2;
	JTextField nome;
	JTextField psw;
	JButton reg_but;
	JButton log_but;
	
	private static final long serialVersionUID = 1L;
	
	//MAIN PRINCIPALE DEL CLIENT
	public static void main(String[] args)throws Exception {
		  new ClientJFrame();
	}
		
	public ClientJFrame() {
		
		super("turing");
		//istanzio tutti gli oggetti
		help = new HelpClient();
		nordPnl = new JPanel();
	    centroPnl = new JPanel();
		sudPnl = new JPanel();
		infoLbl1 = new JLabel(" nome");
		infoLbl2 = new JLabel(" password");
		nome = new JTextField(15);
		psw = new JTextField(15);
		reg_but = new JButton("register");
		log_but= new JButton("login"); 
		// li aggiungo ai vari pannelli
		nordPnl.setLayout(new GridLayout(1,2));
		nordPnl.add(infoLbl1);
		nordPnl.add(infoLbl2);
		centroPnl.add(nome);
		centroPnl.add(psw);
		sudPnl.add(reg_but);
		sudPnl.add(log_but);
		//setto la disposizione degli ogetti nell'interfaccia e la sua dimensione
		getContentPane().add(nordPnl,BorderLayout.NORTH);
		getContentPane().add(centroPnl,BorderLayout.CENTER);
		getContentPane().add(sudPnl,BorderLayout.SOUTH);
		pack();
		Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((int)(dim.getWidth()-this.getWidth())/2,
					(int)(dim.getHeight()-this.getHeight())/2);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		reg_but.addActionListener(this);
		log_but.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String operation = e.getActionCommand();
		
		if(operation.equals("register")) {
			try {
				JOptionPane.showMessageDialog(null,help.connWithServer("register"+"1"+nome.getText()+"2"+psw.getText()+"3"));
				nome.setText("");
				psw.setText("");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}else if (operation.equals(("login"))) {
			try {
				String mexReceived=help.connWithServer("login"+"1"+nome.getText()+"2"+psw.getText()+"3");
				
				JOptionPane.showMessageDialog(null,mexReceived);
				//se l'operazione di login va a buon fine apro la seconda finestra che compone l'interfaccia e chiudo questa
				if(mexReceived.compareTo("registrati oppure nome o password errata ")!=0) {
					LoggedFrame= new LogJFrame(nome.getText(),help);
					LoggedFrame.setVisible(true);
					WindowEvent close = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
					this.dispatchEvent(close);
				}
				nome.setText("");
				psw.setText("");
					
			} catch (HeadlessException | IOException e1) {
				e1.printStackTrace();
			}
		}
	
		
	}
}
