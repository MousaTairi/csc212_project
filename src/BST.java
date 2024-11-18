public class BST <T> {
	BSTNode<T> root, current;
	
	public BST() {
		root = current = null;
	}
	
	public boolean empty() {
		return root == null;
	}
	
	public boolean full() {
		return false;
	}
	
	public T retrieve () {
		return current.data;
	}
	public boolean findkey(int tkey) {
		BSTNode<T> p = root, q = root;	
		if(empty())
			return false;
		
		while(p != null) {
			q=p;
			if(p.key == tkey) {
				current = p;
				return true;
			}
			else if(tkey < p.key)
				p = p.left;
			else
				p = p.right;
		}
		
		current = q;
		return false;
	}
	public boolean insert(int k, T val) {
		BSTNode<T> p, q = current;
		
		if(findkey(k)) {
			current = q;  
			return false; 
		}
		
		p = new BSTNode<T>(k, val);
		if (empty()) {
			root = current = p;
			return true;
		}
		else {
			if (k < current.key)
				current.left = p;
			else
				current.right = p;
			current = p;
			return true;
		}
	}
	public boolean removeKey(int k) {
		int k1 = k;
		BSTNode<T> p = root;
		BSTNode<T> q = null;
		while (p != null) {
			if (k1 < p.key) {
				q=p;
				p=p.left;
			}else if(k1<p.key) {
				q=p;
				p=p.left;
			}else {
				if ((p.left != null) && (p.right != null)) {
					BSTNode<T> min = p.right;
					q=p;
					while (min.left != null) {
						q = min;
						min=min.left;
					}
					p.key=min.key;
					p.data=min.data;
					k1=min.key;
					p=min;
				}
				if(p.left!=null)
					p=p.left;
				else
					p=p.right;
				if(q==null)
					root=p;
				else 
					if(k1<q.key)
						q.left=p;
					else
						q.right=p;
				
				current=root;
				return true;
			}
		}
		return false;
					
			}
		
	

	public boolean update(int key, T data){
			removeKey(current.key);
			return insert(key, data);
		}
	public void deleteSub(){
		if(current == root){
			current = root = null;
		}
		else {
			BSTNode<T> p = current;
			findparent(p,root);
			if(current.left == p)
				current.left = null;
			else 
				current.right = null;
			current = root;
		}
	}
	private BSTNode<T> findparent(BSTNode<T> p, BSTNode<T> t) {
		if(t == null)
			return null;	
		
		if(t.right == null && t.left == null)
			return null;
		else if(t.right == p || t.left == p)
			return t;	
		else {
			BSTNode<T> q = findparent(p, t.left);
			if (q != null)
				return q;
			else
				return findparent(p, t.right);
		}
	}
	public void traverse(Order ord,BSTNode<T> root) {
		if(root==null)
			return; 
		if(ord.getTraverseType().equalsIgnoreCase("preOrder")) {
			System.out.println(root.data+" ,");
			traverse(ord,root.left);
			traverse(ord,root.right);
		}
		else if(ord.getTraverseType().equalsIgnoreCase("inOrder")) {
			traverse(ord,root.left);
			System.out.println(root.data+" ,");
			traverse(ord,root.right);
		}
		else if(ord.getTraverseType().equalsIgnoreCase("postOrder")) {
			traverse(ord,root.left);
			traverse(ord,root.right);
			System.out.println(root.data+" ,");
		}
		else
			System.out.println("Wrong traverse type");

		
		
		
	}
}


	



	

