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
	
}
