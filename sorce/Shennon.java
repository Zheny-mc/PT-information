import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.Map.Entry;

public class Shennon {
	/*
		//compress
		//*********************************************
		mapCountAlphabetToLinkedList();
		System.out.println(listsTree);
		//**********************************************
		
		Node root = new Node(null, listsTree.size());
		buildingBinaryTree(listsTree, root);
		print(root, 0);
	*/
	
	//���� ��� ���������
	private int len;
	private String binaryMessage;
	private Vector<String> blockMessage;
	
	//��� ������ ���������
	private Map<String, Integer> countAlphabet;
	private Node root;
	private LinkedList<Node> listsTree;
	
	private Vector<Character> code;
	private Map<String, String> tableCode;
	
	//�����
	private String compressBinaryMessage;
	int middleLen;
	
	public Shennon(String binaryMessage, int len) {
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
		//�������� �� ��������� ��� ������� binaryMessage.size() / len 
		//����������� � ������ ������
		correctionNoDivisionSizeMessage();
		
		//��������� ��������� �� �����
		splitingArray();
		//������������� ���� �  alphabet �� ����� �� len-����� �����
		countingRelativeFrequencyLetters();
		
		mapCountAlphabetToLinkedList();
		System.out.println(listsTree);
		root = new Node(null, listsTree.size());
		buildingBinaryTree(listsTree, root);
		
		buildingTableCode(root);
		
		compressingBinaryMessage();
		
		calcMiddleLenBlock();
	}
	
	public boolean isCheckDivisionSizeMessageOnLen() {
		//�������� �� ��������� ��� ������� binaryMessage.size() / len
		return (binaryMessage.length() % len != 0)? true : false;
	}
	
	public void correctionNoDivisionSizeMessage() {
		//�������� �� ��������� ��� ������� binaryMessage.size() / len 
		//����������� � ������ ������
		if (isCheckDivisionSizeMessageOnLen()) {
			int mod = binaryMessage.length() % len;
			for (int i = 0; i < mod; i++) {
				StringBuilder strBuilder = new StringBuilder(binaryMessage);
				binaryMessage = strBuilder.insert(0, '0').toString();
			}	
		}
	}
	
	private void splitingArray() {
		//��������� �� ����� ������� len
		for (int i = 0; i < binaryMessage.length() / len; i++) {
			char[] arr = new char[len];
			binaryMessage.getChars(i*len, i*len + len, arr, 0);
			
			blockMessage.add(new String(arr) );
		}
	}
	
	//������� ����� ������� ����
	public void countingRelativeFrequencyLetters() {
		
		for (int i = 0; i < blockMessage.size(); i++) {
			Integer count = countAlphabet.get(blockMessage.get(i));
			if (count == null) count = 0;
			countAlphabet.put(blockMessage.get(i), count +1);
		}
		
	}
	
	public void mapCountAlphabetToLinkedList() {
		Iterator itr = countAlphabet.entrySet().iterator();
		
		while (itr.hasNext()) {
			Entry entry = (Entry) itr.next();
			
			String key = (String)entry.getKey();
			Integer value = (Integer)entry.getValue();
			
			listsTree.add(new Node(key, value));
			
			System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
		}
		
		
		listsTree.sort(null);
	}
	
	int findMiddleArr(LinkedList<Node> �ountAlphabet) {
		Integer partSumma=0;
		Integer summa=0;
		
		Iterator<Node> itr = �ountAlphabet.iterator();
		while (itr.hasNext()) {
			partSumma += itr.next().getNumber();
		}	
		
		partSumma /= 2;
		
		itr = �ountAlphabet.iterator();
	
		int i = -1;
		while (itr.hasNext()) { 
			Integer freqI = itr.next().getNumber();
					
			if (Math.abs(summa+freqI) > partSumma) {
				break;
			}
			
		    summa += freqI;
		    i++;
		}
		
		return i;
	}
	
	
	public void buildingBinaryTree(LinkedList<Node> �ountAlphabet, Node node) {
		if (�ountAlphabet.size() == 1) {
			String str = �ountAlphabet.iterator().next().getNameBlock();
			node.setNameBlock(str);
			node.setNumber(�ountAlphabet.size());
			return;
		}
		
		//������� map ������� � �������� map1, map2
		int indMiddleLen = findMiddleArr(�ountAlphabet);
		LinkedList<Node> alphabetLeft = new LinkedList<Node>();
		LinkedList<Node> alphabetRight = new LinkedList<Node>();
		
		//********************************
		//indMiddleLen, �ountAlphabet, ind0, ind1
		Iterator<Node> itr = �ountAlphabet.iterator();
		//mapLeft
		for (int i = 0; itr.hasNext() && i <= indMiddleLen; i++) {
			Node tmpNode = itr.next();
			
			String key = tmpNode.getNameBlock(); 
			Integer value = tmpNode.getNumber();
			
			alphabetLeft.add(new Node(key, value));
		}
		//mapRight
		while (itr.hasNext()){
			Node tmpNode = itr.next();
			
			String key = tmpNode.getNameBlock(); 
			Integer value = tmpNode.getNumber();
			
			alphabetRight.add(new Node(key, value));
		}
		//***************************************
		
		node.setLeft (new Node( null, alphabetLeft.size() ));
		node.setRight(new Node( null, alphabetRight.size() ));
		
		
		buildingBinaryTree(alphabetLeft, node.getLeft());
		buildingBinaryTree(alphabetRight, node.getRight());
	}
	
	//��������� ���������(����� ��������)
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
			print(node.getLeft(), k+3);
			
			for (int i = 0; i < k; i++)
				System.out.print(' ');
			
			if (node.getNameBlock() != null)
				System.out.println(node.getNumber() + "(" + node.getNameBlock() + ")");
			else
				System.out.println(node.getNumber());
			
			print(node.getRight(), k+3);
		}
	}
	
	public void calcMiddleLenBlock() {
		for (String i: blockMessage) {
			middleLen += tableCode.get(i).length();
		}
		middleLen /= blockMessage.size();
	}
	
	public void toResultCompress() {
		//��������� ���������� ����������
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
		//��������� ����� ����� ����� �����������
		System.out.println("len block = " + len);
		System.out.println("middle len block = " + middleLen);
		System.out.printf("len block / middle len block = %.2f\n", (double)len / middleLen);
	}
}
