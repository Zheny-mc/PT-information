import java.util.Random;
import java.util.Vector;

public class Main {

	public static void main(String[] args) {
		Vector<Boolean> fileMessage = new Vector<Boolean>();
		int lenBlock = 2;
		
		Random randomGenerator = new Random(); 
		for (int i = 0; i < 20; i++) {
			fileMessage.add(randomGenerator.nextBoolean());
		}
		
		Haffman haffman = new Haffman(fileMessage, lenBlock);
		
		
	}

}
