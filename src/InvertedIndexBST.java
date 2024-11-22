public class InvertedIndexBST {
	private BSTNode Root;
	/*uses recurseve way of searching for all methods*/

	public InvertedIndexBST() {
		Root = null;
	}
        //Big-O: O(log(n))
	public void AddWord(String word, int docID) {
		Root = AddWordBST(Root, word, docID);
	}

	//Big-O: O(log(n))
	private BSTNode AddWordBST(BSTNode current, String word, int docID) {
		if (current == null) {
			BSTNode newNode = new BSTNode(word);
			newNode.addDocumentID(docID);
			return newNode;
		}

		if (word.compareTo(current.getWord()) < 0) {
			current.setLeft(AddWordBST(current.getLeft(), word, docID));
		} else if (word.compareTo(current.getWord()) > 0) {
			current.setRight(AddWordBST(current.getRight(), word, docID));
		} else {

			current.addDocumentID(docID);
		}

		return current;
	}

	//Big-O: O(log(n))
	public DocumentIDNode getDocuments(String word) {
		return GetDocumentsBST(Root, word);
	}

	//Big-O: O(log(n))
	private DocumentIDNode GetDocumentsBST(BSTNode current, String word) {
		if (current == null) {
			return null;
		}

		if (word.equals(current.getWord())) {
			return current.getDocumentIDs();
		}

		if (word.compareTo(current.getWord()) < 0) {
			return GetDocumentsBST(current.getLeft(), word);
		} else {
			return GetDocumentsBST(current.getRight(), word);
		}
	}

	//Big-O: O(n + m)
	public void TraverseInOrder(BSTNode node) {
		if (node != null) {
			TraverseInOrder(node.getLeft());
			System.out.print("Word: " + node.getWord() + " -> Document IDs: ");
			DocumentIDNode docIDs = node.getDocumentIDs();
			while (docIDs != null) {
				System.out.print(docIDs.getDocID() + " ");
				docIDs = docIDs.getNext();
			}
			System.out.println();
			TraverseInOrder(node.getRight());
		}
	}

	public BSTNode getRoot() {
		return Root;
	}
}
