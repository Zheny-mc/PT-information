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
			System.out.println("Длина Блока = " + i);
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
			System.out.println("Длина Блока = " + i);
			System.out.println("-----------------------------------------");
			
			shennon = new Shennon(message, i);
			shennon.compress();
			shennon.toResultCompress();
		}
	}
	
	public static void getHarasteristicsInputData() {
		int numOne = 0;
		
		for (int i = 0; i < message.length(); i++) {
			if (message.charAt(i) == '1')
				numOne++;
		}
		double probabilityOne = (double)numOne / (message.length());
		System.out.printf("Вероятность 1: p = %.3f\n", probabilityOne);
		double probabilityZero = 1-probabilityOne;
		System.out.printf("Вероятность 0: q = 1 - p = %.3f\n", probabilityZero);
		double entropySystem = 0.996;
		System.out.printf("Энтропия системы = %.3f\n", entropySystem);
		double redundancyRatio = 1 - entropySystem;
		System.out.printf("Коэффициент избыточности = %.3f\n", redundancyRatio);
	}
	
	public static void main(String[] args) {
		readFile("E:\\test\\compression of information\\src\\message.txt");
		getHarasteristicsInputData();
		
		compressHaffman();
		//compressShennon();
		
	}

}
