import java.io.File;
import java.util.Scanner;
import java.util.Vector;

public class Main {
	static String message; 
	static int[] lenBlock = {2, 3, 4};
	static Haffman haffman;
	static Shennon shennon;
	
	public static void readFile(String fileName) {

		File file = null;
		Scanner scanner = null;
		
		try {
			file = new File(fileName);
			scanner = new Scanner(file);
			
			if (file.canRead()) {
				message = scanner.next();
			}
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
		} finally {
			scanner.close();
		}
	}
	
	public static void compressHaffman() {
		System.out.println("---------------Haffman-------------------");
		
		for (int i: lenBlock) {
			System.out.println("-----------------------------------------");
			System.out.println("LenBlock = " + i);
			System.out.println("-----------------------------------------");
			
			haffman = new Haffman(message, i);
			haffman.compress();
			haffman.toResultCompress();
		}
	}
	
	public static void compressShennon() {
		System.out.println("\n---------------Shennon-------------------");
		
		for (int i: lenBlock) {
			System.out.println("-----------------------------------------");
			System.out.println("LenBlock = " + i);
			System.out.println("-----------------------------------------");
			
			shennon = new Shennon(message, i);
			shennon.compress();
			shennon.toResultCompress();
		}
	}
	
	public static void main(String[] args) {
		readFile("E:\\test\\compression of information\\src\\message.txt");
		
		compressHaffman();
		compressShennon();
		
	}

}
