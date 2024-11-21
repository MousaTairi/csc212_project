public class BSTNode {
	private String Word;
	private DocumentIDNode DocumentIDs;
	private BSTNode Left;
	private BSTNode Right;
	/*since i changed a lot of the code i just decided to make your old code a comment down below for whenever you want to look at it 
 and i switched it from generic because it would mean we need to convert our whole code to generics<T> 
 and that isnt asked for us and would take a long time
 
         */
	public BSTNode(String word) {
		this.Word = word;
		this.DocumentIDs = null;
		this.Left = null;
		this.Right = null;
	}

	public String getWord() {
		return Word;
	}

	public DocumentIDNode getDocumentIDs() {
		return DocumentIDs;
	}

	public void addDocumentID(int docID) {
		if (DocumentIDs == null) {
			DocumentIDs = new DocumentIDNode(docID);
			DocumentIDs.incrementNumberOfTimes();
		} else {
			DocumentIDNode current = DocumentIDs;
			while (current != null) {
				if (current.getDocID() == docID) {
					current.incrementNumberOfTimes();
					return;
				}
				if (current.getNext() == null)
					break;
				current = current.getNext();
			}
			DocumentIDNode newNode = new DocumentIDNode(docID);
			newNode.incrementNumberOfTimes();
			current.setNext(newNode);
		}
	}

	public BSTNode getLeft() {
		return Left;
	}

	public void setLeft(BSTNode left) {
		this.Left = left;
	}

	public BSTNode getRight() {
		return Right;
	}

	public void setRight(BSTNode right) {
		this.Right = right;
	}
}
/*
public class BSTNode <T> {
	private String key;
	private T data;
	private BSTNode<T> left, right;
	
	public BSTNode(String k, T val) {
		setKey(k);
		setData(val);
	}
	
	public BSTNode(String k, T val, BSTNode<T> l, BSTNode<T> r) {
		setKey(k);
		setData(val);
		left = l;
		setRight(r);
	}
	public String getKey() {
		return key;
	}
	public T getData() {
		return data;
	}
	public BSTNode<T> getRight(){
		return right;
	}
	public BSTNode<T> getLeft(){
		return left;
	}

	public void setRight(BSTNode<T> right) {
		this.right = right;
	}

	public void setLeft(BSTNode<T> left) {
		this.left = left;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setData(T data) {
		this.data = data;
	}
	
}*/
