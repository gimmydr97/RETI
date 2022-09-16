import java.io.*;
import java.nio.file.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.Scanner;
import java.util.Random;
import org.json.simple.JSONArray;

	public class creaFileCCJ{
			public  static void main(String[] args) throws IOException {
				
				Scanner in1;
				Scanner in2;
				Random random = new Random();
				
				System.out.println("inserire il numero di conti correnti presenti nella banca");
				in1 = new Scanner(System.in);
				int ncc = in1.nextInt();
				
				
				JSONArray listOfCC = new JSONArray(); 

				for(int i=0 ; i < ncc; i++ ) {
				
					System.out.println("inserire il nome del " + i + " correntista" );
					in2 = new Scanner(System.in); 
					String name = in2.next();
					contoCorrenteJ cc = new contoCorrenteJ(name);
					
					int nm = random.nextInt(10)+10;
					
					for(int c=0 ; c <= nm; c++) {
						movimentoJ mov = new movimentoJ();
						cc.addMovimento(mov);
					}
					listOfCC.add(cc.getJSON());
					
				}
				ByteBuffer buf = ByteBuffer.allocate(1024*1024);
				
				buf.asCharBuffer().put(listOfCC.toJSONString());
				
				FileChannel outChannel = FileChannel.open(Paths.get("CC.db"),StandardOpenOption.WRITE);
				
				while(buf.hasRemaining()) {
					outChannel.write(buf);
				}
				
				buf.clear();
				outChannel.close();
				
				in1.close();
				System.out.println("File creato con successo");
		}
		
	}

