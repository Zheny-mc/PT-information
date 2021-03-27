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
	int middleLen;
	
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
		middleLen = 0;
	}
	
	public void compress() {
		//проверка на кратность при делении binaryMessage.size() / len 
		//исправление в случае ошибки
		correctionNoDivisionSizeMessage();
		
		//разбиение сообщения на блоки
		splitingArray();
		//рассортировка букв в  alphabet из файла по len-длине блока
		countingRelativeFrequencyLetters();
		
		buildingBinaryTree();
		
		buildingTableCode(root);
		
		compressingBinaryMessage();
		
		calcMiddleLenBlock();
	}
	
	
	public boolean isCheckDivisionSizeMessageOnLen() {
		//проверка на кратность при делении binaryMessage.size() / len
		return (binaryMessage.length() % len != 0)? true : false;
	}
	
	public void correctionNoDivisionSizeMessage() {
		//проверка на кратность при делении binaryMessage.size() / len 
		//исправление в случае ошибки
		if (isCheckDivisionSizeMessageOnLen()) {
			int mod = binaryMessage.length() % len;
			for (int i = 0; i < mod; i++) {
				StringBuilder strBuilder = new StringBuilder(binaryMessage);
				binaryMessage = strBuilder.insert(0, '0').toString();
			}	
		}
	}
	
	public void calcMiddleLenBlock() {
		for (String i: blockMessage) {
			middleLen += tableCode.get(i).length();
		}
		middleLen /= blockMessage.size();
	}
	
	public void toResultCompress() {
		//результат уменьшения информации
		System.out.println("Message = " + binaryMessage);
		System.out.println("alphabet = " + countAlphabet);
		
		System.out.println("Tree: ");
		print(root, 0);
		
		System.out.println("ListCode: ");
		System.out.println(tableCode);
		
		System.out.println("CodeString = " + compressBinaryMessage);
		
		int binMessLen = binaryMessage.length();
		int comprBinMessLen = compressBinaryMessage.length();
		
		System.out.printf("n0 / n = %d / %d = %.2f\n", binMessLen, 
				comprBinMessLen, (double)binMessLen / comprBinMessLen);
		//изменение длины блока после кодирования
		System.out.println("len block = " + len);
		System.out.println("middle len block = " + middleLen);
		System.out.printf("len block / middle len block = %.2f\n", (double)len / middleLen);
	}
	
	private void splitingArray() {
		//разбиение на блоки длинной len
		
		for (int i = 0; i < binaryMessage.length() / len; i++) {
			char[] arr = new char[len];
			binaryMessage.getChars(i*len, i*len + len, arr, 0);
			
			blockMessage.add(new String(arr) );
			
		}
	}
	
	//подсчет относ частоты букв
	public void countingRelativeFrequencyLetters() {
		
		for (int i = 0; i < blockMessage.size(); i++) {
			Integer count = countAlphabet.get(blockMessage.get(i));
			if (count == null) count = 0;
			countAlphabet.put(blockMessage.get(i), count +1);
		}
		
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
