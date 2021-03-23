import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
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
	private LinkedList<Node> listsTree;
	
	private Vector<Character> code;
	private Map<String, String> tableCode;
	
	public Haffman(String binaryMessage, int len) {
		this.len = len;
		this.binaryMessage = binaryMessage;
		blockMessage = new Vector<String>();
		countAlphabet = new TreeMap<String, Integer>();
		listsTree = new LinkedList<Node>();
		
		code = new Vector<Character>();
		tableCode = new TreeMap<String, String>();
		compressBinaryMessage = new String();
		//разбиение сообщения на блоки
		splitingArray();
		//рассортировка букв в  alphabet из файла по len-длине блока
		countingRelativeFrequencyLetters();
		buildingBinaryTree();
		print(root, 0);
		buildingTableCode(root);
		System.out.println(tableCode);
		compressingBinaryMessage();
		//результат уменьшения информации
		System.out.printf("n0 / n = %d / %d = %.2f\n", binaryMessage.length(), 
			compressBinaryMessage.length(), (double)binaryMessage.length() / compressBinaryMessage.length());
		
		
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
		Iterator it = countAlphabet.entrySet().iterator();
		
		while (it.hasNext()) {
		    Entry entry = (Entry) it.next();
		    //получить ключ
		    String key = (String) entry.getKey();
		    //получить значение
		    Integer value = (Integer) entry.getValue();
			//добавлние листа
		    listsTree.add(new Node(key, value));
		}
		
		//Iterator<Node> i = listsTree.iterator();
		//while (i.hasNext()) {
		//	System.out.println(i.next() + " ");
		//}
		
		while (listsTree.size() != 1) {
			listsTree.sort(null);
			//System.out.println("sort = " + listsTree);
			
			Node sonL = listsTree.pollFirst(); 
			Node sonR = listsTree.pollFirst();
			//System.out.println("delete = " + listsTree);
			
			Node parent = new Node(sonL, sonR);
			listsTree.addLast(parent);
			//System.out.println("add Parent = " + listsTree);
		}
		
		root = listsTree.getFirst();
		//System.out.println(root);
	}
	
	//получение результов(кодов символов)
	public void compressingBinaryMessage() {
		for (String i: blockMessage) {
			compressBinaryMessage += tableCode.get(i);
		}
		System.out.println("CodeString = " + compressBinaryMessage);
	}
	
	public void buildingTableCode(Node node) {
		if (node.getLeft() != null) {
			code.add('0');
			buildingTableCode(node.getLeft());
		}
		
		if (node.getRight() != null) {
			code.add('1');
			buildingTableCode(node.getRight());
		}
		
		if (node.getNameBlock() != null) {
			String str = "";
			for (Character chr: code)
				str += chr;
			
			tableCode.put(node.getNameBlock(), str);
		}
			
		
		if (code.size() > 0)
			code.remove(code.size()-1);
	}
	
	public void print(Node node, int k) {
		if (node != null) {
			print(node.getLeft(), k+len);
			
			for (int i = 0; i < k; i++)
				System.out.print(' ');
			
			if (node.getNameBlock() != null)
				System.out.println(node.getNumber() + "(" + node.getNameBlock() + ")");
			else
				System.out.println(node.getNumber());
			
			print(node.getRight(), k+len);
		}
	}
}
