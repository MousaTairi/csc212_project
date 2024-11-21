/*this isnt really needed because most methods are not used
for example in linked lists we dont need to implement a delete method because 
we wont use it anywhere in the project code
public class BST <T> {
	BSTNode<T> root, current;
	public int size;
	public InnerBST ib;
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
		return current.getData();
	}
	public boolean findkey(String tkey) {
		BSTNode<T> p = root, q = root;	
		if(empty())
			return false;
		
		while(p != null) {
			q=p;
			if(p.getKey().equalsIgnoreCase(tkey)) {
				current = p;
				return true;
			}
			else if(p.getKey().compareTo(tkey)<0)
				p = p.getRight();
			else
				p = p.getLeft();
		}
		current = q;
		return false;
	}
	public boolean insert(String k, T val) {
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
			if (current.getKey().compareTo(k)<0)
				current.setRight(p);
			else
				current.setLeft(p);
			current = p;
			size++;
			return true;
		}
	}
	public boolean removeKey(String k) {
		String k1 = k;
		BSTNode<T> p = root;
		BSTNode<T> q = null;
		while (p != null) {
			if (p.getKey().compareTo(k1)>0) {
				q=p;
				p=p.getLeft();
			}else if(p.getKey().compareTo(k1)<0) {
				q=p;
				p=p.getRight();
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
					if(p.getKey().compareTo(k1)>0)
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
		
	

	public boolean update(String key, T data){
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

*/
	



	

