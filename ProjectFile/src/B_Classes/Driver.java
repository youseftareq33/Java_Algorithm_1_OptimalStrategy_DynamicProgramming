package B_Classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		
		try {
			File f_sample=new File("src\\sample.txt");
			Scanner scan=new Scanner(f_sample);
			while(scan.hasNextLine()) {
				String s=scan.nextLine();
				System.out.println(s);
			}
			
			scan.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("there is no file");
		}
	}

}
