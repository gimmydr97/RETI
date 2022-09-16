import java.net.InetAddress;

import java.net.UnknownHostException;
import java.io.*;
import java.util.concurrent.locks.*;
public class Task implements Runnable {
	private BufferedWriter b;
	private String line;
	private final Lock lock;
	public Task(BufferedWriter b,String line,Lock lock) {//il task riceve come parametri il bufferW  per scrivere sul nuovo file
		this.b=b;										 //la linea da tradurre e la lock per gestire l'accesso atomico al file per scriverci
		this.line=line;
		this.lock=lock;
		
	}
	public void run(){
		try{
			InetAddress host= InetAddress.getByName(getHost(line));
			String newline = sostituisci(line,host.getHostName());
			System.out.println(newline);
			 lock.lock();//acquisisco la lock per atomicizare la scrittura
			 b.write(newline+"\n");
			 lock.unlock();//rilascio la lock quando ha finito di scrivere
		}
		catch(UnknownHostException e) {}
		catch(IOException e1) {System.out.println("linea non scritta su file");}
	}
	private static String getHost(String line) {
		String h = line.substring(0,line.indexOf(" "));
		return h;
	}
	private static String sostituisci(String line, String nameHost) {
		String h = line.substring(0,line.indexOf(" "));
		String newline = line.replace(h,nameHost);
		return newline;
		
	}
}
