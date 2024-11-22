import java.util.Scanner;

public class menu {
    public static void main(String[] args) {
        DocumentProcessor documentProcessor = new DocumentProcessor("here the path to the stop words");
        documentProcessor.LoadDocuments("here the path to the cvs file");
        documentProcessor.BuildInvertedIndex();
        /*for bst change the below line to this 
        InvertedIndexBST invertedIndex = documentProcessor.getInvertedIndex();
        */
        InvertedIndex invertedIndex = documentProcessor.getInvertedIndex();
        QueryProcessor queryProcessor = new QueryProcessor(invertedIndex, documentProcessor);
        
        Scanner scanner = new Scanner(System.in);
        while (true) {
           
            System.out.println("*** Menu ***");
            System.out.println("1. Boolean Retrieval");
            System.out.println("2. Ranked Retrieval");
            System.out.println("3. Indexed Documents");
            System.out.println("4. Indexed Tokens");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 
            switch (choice) {
                case 1: 
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
                case 2: 
                    System.out.print("Enter Ranked Retrieval query: ");
                    String rankedQuery = scanner.nextLine().trim();
                    NumNode rankedResults = queryProcessor.RankedRetrieval(rankedQuery);
                    if (rankedResults == null) {
                        System.out.println("No matching documents found.");
                    } else {
                        System.out.println("Ranked Results:");
                        while (rankedResults != null) {
                            System.out.println("Document ID: " + rankedResults.getDocID() + " - Score: " + rankedResults.getScore());
                            rankedResults = rankedResults.getNext();
                        }
                    }
                    break;
                case 3: 
                    System.out.println("\n*** Indexed Documents ***");
                    DocumentLL documentList = documentProcessor.getDocumentList();
                    for (int i = 0; i < documentList.getSize(); i++) {
                        Document doc = documentList.getDocument(i);
                        System.out.println("Document ID: " + doc.getDocumentId());
                        System.out.println("Content: " + doc.getContent());
                        System.out.println();
                    }
                    break;
                /*for bst change case 4 to this
                case 4:
				System.out.println("\n*** Indexed Tokens ***");
				invertedIndex.TraverseInOrder(invertedIndex.getRoot());
				break; 
                */    
                    
                case 4:
                    System.out.println("\n*** Indexed Tokens ***");
                    WordNode current = invertedIndex.getHead();
                    while (current != null) {
                        System.out.print("Word: " + current.getWord() + " -> Document IDs: ");
                        DocumentIDNode docIDs = current.getDocumentIDs();
                        while (docIDs != null) {
                            System.out.print(docIDs.getDocID() + " ");
                            docIDs = docIDs.getNext();
                        }
                        System.out.println();
                        current = current.getNext();
                    }
                    break;
                    
                    
                case 5: 
                    System.out.println("Exiting the program");
                    return;
                default: 
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
