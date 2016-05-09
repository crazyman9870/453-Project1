package parser;

public class ResultSet {
	
	private Node root;
	private int size;

	public ResultSet() {
		root = null;
		size = 0;
	}
	
	public void addNode(String path, double score) {
		if(root == null) {
			root = new Node(path, score);
			size++;
		}
		
		Node temp = root;
		while(temp.next != null) {
			temp = temp.next;
		}
		temp.next = new Node(path, score);
		size++; 
	}
	
	public boolean containsKey(String path) {
		if(root == null)
			return false;
		Node temp = root;
		while(temp != null) {
			if(temp.filePath.equals(path)) {
				return true;
			}
			temp = temp.next;
		}
		return false;
	}
	
	public void updateScore(String path, double score) {
		if(root == null)
			return;
		
		Node temp = root;
		while(temp != null) {
			if(temp.filePath.equals(path)) {
				temp.score += score;
			}
			temp = temp.next;
		}
	}
	
	public void printTopTenResults() {
		if(root == null)
			return;
		for(int i = 0; i < 10; i++) {
			Node topResult = root;
			Node temp = root;
			while(temp != null) {
				if(temp.score > topResult.score) {
					topResult = temp;
				}
				temp = temp.next;
			}
			System.out.println("File path = " + topResult.filePath);
			System.out.println("Score = " + topResult.score);
			topResult.score = -1000;
		}
	}
	
	public int getSize() {
		return this.size;
	}
	
	private class Node {
		
		public Node next;
		public String filePath;
		public double score;
		
		Node() {
			next = null;
			filePath = "";
			score = 0.0;
		}
		Node(String filePath, double score) {
			this.next = null;
			this.filePath = filePath;
			this.score = score;
		}
	}
}