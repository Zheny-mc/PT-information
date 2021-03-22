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
	private Vector<Boolean> binaryMessage;
	private Vector<Vector<Boolean> > blockMessage;
	
	
	//выход
	private String compressBinaryMessage;
	
	//для работы алгоритма
	private Map<Vector<Boolean>, Integer> countAlphabet;
	private Node root;
	private Map<String, String> codeLetter;

	public Haffman(Vector<Boolean> binaryMessage, int len) {
		this.len = len;
		this.binaryMessage = binaryMessage;
		blockMessage = new Vector<Vector<Boolean> >();
		countAlphabet = new HashMap<Vector<Boolean>, Integer>();
		
		splitingArray();
		
		countingRelativeFrequencyLetters();
		//рассортировка букв в  alphabet из файла по len-длине блока
	}
	
	private void splitingArray() {
		//разбиение на блоки длинной len
		for (int i = 0; i < binaryMessage.size() / len; i++) {
			blockMessage.add(new Vector<Boolean>());
			
			for (int j = 0; j < len; j++) {
				blockMessage.get(i).add( binaryMessage.get(i*len + j) );
			}
		}
		
		if (binaryMessage.size() % len != 0) {
			int numBlock = blockMessage.size(); 
			blockMessage.add(new Vector<Boolean>());
			
			for (int i = numBlock * len; i < binaryMessage.size(); i++) {
				blockMessage.get(numBlock).add( binaryMessage.get(i) );
			}
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
