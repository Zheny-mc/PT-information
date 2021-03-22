import java.io.File;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Vector;
import java.util.Map.Entry;

public class Haffman {
	//вход для алгоритма
	private int len;
	private String binaryMessage;
	private Vector<String> blockMessage;
	
	
	//выход
	private String compressBinaryMessage;
	
	//для работы алгоритма
	private Map<String, Integer> countAlphabet;
	private Node root;
	private Map<String, String> codeLetter;

	public Haffman(String binaryMessage, int len) {
		this.len = len;
		this.binaryMessage = binaryMessage;
		blockMessage = new Vector<String>();
		countAlphabet = new HashMap<String, Integer>();
		
		splitingArray();
		
		countingRelativeFrequencyLetters();
		//рассортировка букв в  alphabet из файла по len-длине блока
	}
	
	private void splitingArray() {
		//разбиение на блоки длинной len
		
		for (int i = 0; i < binaryMessage.length() / len; i++) {
			char[] arr = new char[len];
			binaryMessage.getChars(i*len, i*len + len, arr, 0);
			
			blockMessage.add(new String(arr) );
			
		}
		
		int mod = binaryMessage.length() % len;
		if (mod != 0) {
			int numBlock = blockMessage.size();
			
			char[] arr = new char[len];
			for (int i = 0; i < len; i++) 
				arr[i] = '0';
			
			//заполнять блок от len-mod индекса
			binaryMessage.getChars(numBlock*len, binaryMessage.length(), arr, len-mod);
			
			blockMessage.add(new String(arr));
		}
	}
	
	//подсчет относ частоты букв
	public void countingRelativeFrequencyLetters() {
		
		for (int i = 0; i < blockMessage.size(); i++) {
			Integer count = countAlphabet.get(blockMessage.get(i));
			if (count == null) count = 0;
			countAlphabet.put(blockMessage.get(i), count +1);
		}
		System.out.println(countAlphabet);
	}
	
	
	//постоение бинарного дерева
	public void buildingBinaryTree() {
		
	}
	
	//получение результов(кодов символов)
	public void compressingBinaryMessage() {
		
	}
}
