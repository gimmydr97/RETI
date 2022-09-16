import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class webLogMonoT {
	
	public static void main(String args[])throws IOException {
		
		String webL ="web.log.txt";
		File 		file = new  File(webL);//file per leggere web.log.txt
		FileWriter     w = new FileWriter("web2.log.txt");//scrittore delnuovo file da creare
    	BufferedWriter b = new BufferedWriter (w);//buffer dello scrittore del nuovo file
		try(FileReader fr = new FileReader(file)){
			BufferedReader br = new BufferedReader(fr);
			boolean flag = false;
			while (!flag) {
				String tmp = br.readLine();
				if(tmp == null) {flag= true;}
				if(tmp != null) {
					try{
						InetAddress host= InetAddress.getByName(getHost(tmp));
						String newline = sostituisci(tmp,host.getHostName());
						System.out.println(newline);
						b.write(newline+"\n");
					}catch(UnknownHostException e) {}
				}
		
		  }
		  b.flush();
		  b.close();
		}
		catch(FileNotFoundException e) {e.printStackTrace();}
		catch(IOException e1) {e1.printStackTrace();}
		
		
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
