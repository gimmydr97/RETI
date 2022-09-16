import java.util.Scanner;
import java.util.List;
import java.util.LinkedList;

public class Main {
	public static void main(String[] args){
		int stud;
		int tes;
		int prof;
		List<Computer> pc = new LinkedList<Computer>();
		
		for(int i= 0; i<20 ; i++) {
			pc.add(new Computer(i));
		}
		
		Tutor tutor = new Tutor(pc);
		
		System.out.println("inserire il numero di studenti che devono usare il laboratorio");
		Scanner in1 = new Scanner(System.in);
		stud= in1.nextInt();
	
	
		System.out.println("inserire il numero di tesisti che devono usare il laboratorio");
		Scanner in2 = new Scanner(System.in);
		tes= in2.nextInt();
	
	
		System.out.println("inserire il numero di professori che devono usare il laboratorio");
		Scanner in3 = new Scanner(System.in);
		prof= in3.nextInt();
	
		for(int i = 0; i < stud ; i++) {
			Studente s = new Studente(tutor,i);
			s.setPriority(Thread.MIN_PRIORITY);
			s.start();
		} 
		
		for(int i = 0; i < tes ; i++ ) {
			Tesista t = new Tesista(tutor, i);
			t.setPriority(Thread.NORM_PRIORITY);
			t.start();
		}
		
		for(int i = 0 ; i< prof ; i++) {
			Professore p = new Professore(tutor, i);
			p.setPriority(Thread.MAX_PRIORITY);
			p.start();
		}
	
	
	in1.close();
	in2.close();
	in3.close();
	
	}
}