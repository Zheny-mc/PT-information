
public class Node implements Comparable<Node>{
	private String nameBlock;
	private int number;
	private Node left;
	private Node right;
	
	public Node(Node left, Node right) {
		super();
		this.left = left;
		this.right = right;
		nameBlock = null;
		this.number = left.getNumber() + right.getNumber();
	}

	public Node(String nameBlock, int number) {
		super();
		this.nameBlock = nameBlock;
		this.number = number;
		this.left = null;
		this.right = null;
	}

	public Node(String nameBlock, int number, Node left, Node right) {
		super();
		this.nameBlock = nameBlock;
		this.number = number;
		this.left = left;
		this.right = right;
	}
	
	public String getNameBlock() {
		return nameBlock;
	}

	public void setNameBlock(String nameBlock) {
		this.nameBlock = nameBlock;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}
	
	@Override
	public int compareTo(Node obj) {
        if (this.number == obj.number) {
            return 0;
        } else if (this.number < obj.number) {
            return -1;
        } else {
            return 1;
        }
	}

	@Override
	public String toString() {
		return "Node [nameBlock=" + nameBlock + ", number=" + number + "]";
	}
	
	
}
