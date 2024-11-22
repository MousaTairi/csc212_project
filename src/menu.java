import java.util.InputMismatchException;
import java.util.Scanner;

public class menu {
	public static void main(String[] args) {

		DocumentProcessor documentProcessor = new DocumentProcessor(
				"");
		documentProcessor.LoadDocuments("");
		documentProcessor.BuildInvertedIndex();

		InvertedIndexBST invertedIndex = documentProcessor.getInvertedIndex();
		BasicIndex basicIndex = documentProcessor.getBasicIndex();
		QueryProcessor queryProcessor = new QueryProcessor(basicIndex, documentProcessor);
		Scanner scanner = new Scanner(System.in);
		while (true) {

			System.out.println("*** Menu ***");
			System.out.println("1. Boolean Retrieval");
			System.out.println("2. Ranked Retrieval");
			System.out.println("3. Indexed Documents");
			System.out.println("4. Indexed Tokens");
			System.out.println("5. Exit");
			System.out.print("Enter your choice: ");

			char choice =scanner.next().charAt(0);
			
			while(Character.isLetter(choice)) {
			System.out.println("Inter an Integer! ");
			choice =scanner.next().charAt(0);
			scanner.nextLine();
			}

			
			switch (choice) {
			case '1':
				System.out.print("Enter Boolean query: ");
				String query = scanner.nextLine().trim();
				DocumentIDNode booleanResults = queryProcessor.ProcessANDOR(query);

				if (booleanResults == null) {
					System.out.println("No matching documents found.");
				} else {
					System.out.print("Matching Document IDs: ");
					while (booleanResults != null) {
						System.out.print(booleanResults.getDocID() + " ");
						booleanResults = booleanResults.getNext();
					}
					System.out.println();
				}
				break;

			case '2':
				System.out.print("Enter Ranked Retrieval query: ");
				String rankedQuery = scanner.nextLine().trim();
				NumNode rankedResults = queryProcessor.RankedRetrieval(rankedQuery);

				if (rankedResults == null) {
					System.out.println("No matching documents found.");
				} else {
					System.out.println("Ranked Results:");
					while (rankedResults != null) {
						System.out.println(
								"Document ID: " + rankedResults.getDocID() + " - Score: " + rankedResults.getScore());
						rankedResults = rankedResults.getNext();
					}
				}
				break;

			case '3':
			    System.out.println("\n*** Basic Index documents ***");
			    documentProcessor.getBasicIndex().displayAllDocuments();
			    break;


			case '4':
			    System.out.println("\n*** Words in Basic Index ***");
			    BasicIndex basicIndex1 = documentProcessor.getBasicIndex();

			   
			    WordDocumentLL wordToDocuments = new WordDocumentLL();

			   
			    DocumentNode currentDoc = basicIndex1.getHead(); 
			    while (currentDoc != null) {
			        int docID = currentDoc.getDocument().getDocumentId();
			        String[] words = currentDoc.getDocument().getContent().split("\\s+");

			      
			        for (String word : words) {
			            wordToDocuments.AddWord(word, docID);
			        }

			        currentDoc = currentDoc.getNext();
			    }

			 
			    WordDocumentNode currentWordNode = wordToDocuments.getHead();
			    while (currentWordNode != null) {
			        System.out.print("Word: " + currentWordNode.getWord() + " -> Documents: ");
			        DocumentIDNode docIDs = currentWordNode.getDocumentIDs();
			        while (docIDs != null) {
			            System.out.print(docIDs.getDocID() + " ");
			            docIDs = docIDs.getNext();
			        }
			        System.out.println();
			        currentWordNode = currentWordNode.getNext();
			    }
			    break;

			case '5':
				System.out.println("Exiting the program");
				return;

			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}
}
