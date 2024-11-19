public class InnerBST <T> {
	BSTNode<T> root, current;
	public int size;
	public InnerBST() {
		root = current = null;
		
	}
	
	public boolean empty() {
		return root == null;
	}
	
	public boolean full() {
		return false;
	}
	
	public T retrieve () {
		return current.getData();
	}
	public boolean findkey(int tkey) {
		BSTNode<T> p = root, q = root;	
		if(empty())
			return false;
		
		while(p != null) {
			q=p;
			if(p.getKey() == tkey) {
				current = p;
				return true;
			}
			else if(tkey < p.getKey())
				p = p.getLeft();
			else
				p = p.getRight();
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
			size++;
			return true;
		}
		else {
			if (k < current.getKey())
				current.setLeft(p);
			else
				current.setRight(p);
			current = p;
			size++;
			return true;
		}
	}
	public boolean removeKey(int k) {
		int k1 = k;
		BSTNode<T> p = root;
		BSTNode<T> q = null;
		while (p != null) {
			if (k1 < p.getKey()) {
				q=p;
				p=p.getLeft();
			}else if(k1<p.getKey()) {
				q=p;
				p=p.getLeft();
			}else {
				if ((p.getLeft() != null) && (p.getRight() != null)) {
					BSTNode<T> min = p.getRight();
					q=p;
					while (min.getLeft() != null) {
						q = min;
						min=min.getLeft();
					}
					p.setKey(min.getKey());
					p.setData(min.getData());
					k1=min.getKey();
					p=min;
				}
				if(p.getLeft()!=null)
					p=p.getLeft();
				else
					p=p.getRight();
				if(q==null)
					root=p;
				else 
					if(k1<q.getKey())
						q.setLeft(p);
					else
						q.setRight(p);
				
				current=root;
				size--;
				return true;
			}
		}
		return false;
					
			}
		
	

	public boolean update(int key, T data){
			removeKey(current.getKey());
			return insert(key, data);
		}
	public void deleteSub(){
		if(current == root){
			current = root = null;
		}
		else {
			BSTNode<T> p = current;
			findparent(p,root);
			if(current.getLeft() == p)
				current.setLeft(null);
			else 
				current.setRight(null);
			current = root;
		}
	}
	private BSTNode<T> findparent(BSTNode<T> p, BSTNode<T> t) {
		if(t == null)
			return null;	
		
		if(t.getRight() == null && t.getLeft() == null)
			return null;
		else if(t.getRight() == p || t.getLeft() == p)
			return t;	
		else {
			BSTNode<T> q = findparent(p, t.getLeft());
			if (q != null)
				return q;
			else
				return findparent(p, t.getRight());
		}
	}
	public void traverse(Order ord,BSTNode<T> root) {
		if(root==null)
			return; 
		if(ord.getTraverseType().equalsIgnoreCase("preOrder")) {
			System.out.println(root.getData()+" ,");
			traverse(ord,root.getLeft());
			traverse(ord,root.getRight());
		}
		else if(ord.getTraverseType().equalsIgnoreCase("inOrder")) {
			traverse(ord,root.getLeft());
			System.out.println(root.getData()+" ,");
			traverse(ord,root.getRight());
		}
		else if(ord.getTraverseType().equalsIgnoreCase("postOrder")) {
			traverse(ord,root.getLeft());
			traverse(ord,root.getRight());
			System.out.println(root.getData()+" ,");
		}
		else
			System.out.println("Wrong traverse type");
	}
}


	



	

