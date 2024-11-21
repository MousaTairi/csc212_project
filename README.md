i will put the entire code for every class that needs to use basic index
the classes present in here(the program works completly with them just copy and paste)
BasicIndex
Document
DocumentNode
WordDocumentNode
DocumentIDNode
QueryProcessor
DocumentProcessor
StopWordsLL
NumNode
menu
-----------------------------------------------------------------------------------------------
public class BasicIndex {
    private DocumentNode Head; 
    private WordDocumentNode WordHead; 

    public BasicIndex() {
        Head = null;
        WordHead = null;
    }

    public DocumentNode getHead() {
        return Head;
    }

   
    public void AddDocument(int docID, String[] words) {
        DocumentNode NewDoc = new DocumentNode(new Document(docID, String.join(" ", words)));

        
        for (String word : words) {
            WordDocumentNode WordNode = AddWordNode(word); 
            WordNode.addDocumentID(docID);
        }

       
        if (Head == null) {
            Head = NewDoc;
        } else {
            DocumentNode current = Head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(NewDoc);
        }
    }

    
    private WordDocumentNode AddWordNode(String word) {
        WordDocumentNode Current = WordHead; 
        while (Current != null) {
            if (Current.getWord().equals(word)) {
                return Current; 
            }
            if (Current.getNext() == null) break;
            Current = Current.getNext();
        }

     
        WordDocumentNode NewNode = new WordDocumentNode(word);
        if (Current == null) {
            WordHead = NewNode; 
        } else {
            Current.setNext(NewNode); 
        }
        return NewNode;
    }

 
    public String[] GetWordsinDocument(int docID) {
        DocumentNode Current = Head;
        while (Current != null) {
            if (Current.getDocument().getDocumentId() == docID) {
                return Current.getDocument().getContent().split("\\s+");
            }
            Current = Current.getNext();
        }
        return null; 
    }

    
    public DocumentIDNode GetDocuments(String word) {
        WordDocumentNode current = WordHead; 
        while (current != null) {
            if (current.getWord().equals(word)) {
                return current.getDocumentIDs(); 
            }
            current = current.getNext();
        }
        return null; 
    }


    
    public void displayAllDocuments() {
        DocumentNode current = Head;
        while (current != null) {
            System.out.print("Document ID: " + current.getDocument().getDocumentId());
            System.out.println(" Content: " + current.getDocument().getContent());
            current = current.getNext();
        }
    }
}
--------------------------------------------------------------------------------------------------------------------
public class Document {
    private int DocumentId;
    private String Content;
    /*
     * this will contain the string and the id number
     * of every single line in the cvs file
     */

    public Document(int documentId, String content) {
        this.DocumentId = documentId;
        this.Content = content;
    }

    public int getDocumentId() {
        return DocumentId;
    }

    public String getContent() {
        return Content;
    }
}
--------------------------------------------------------------------------------------------------------------------
public class WordDocumentNode {
    private String Word;
    private DocumentIDNode DocumentIDs;
    private WordDocumentNode Next;

    public WordDocumentNode(String word) {
        this.Word = word;
        this.DocumentIDs = null;
        this.Next = null;
    }

    public String getWord() {
        return Word;
    }

    public DocumentIDNode getDocumentIDs() {
        return DocumentIDs;
    }

    public void AddDocumentID(int docID) {
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
                if (current.getNext() == null) break;
                current = current.getNext();
            }
            DocumentIDNode NewNode = new DocumentIDNode(docID);
            NewNode.incrementNumberOfTimes(); 
            current.setNext(NewNode);
        }
    }


    public WordDocumentNode getNext() {
        return Next;
    }

    public void setNext(WordDocumentNode next) {
        this.Next = next;
    }
}
--------------------------------------------------------------------------------------------------------------------------
public class DocumentIDNode {
    private int DocID; 
    private DocumentIDNode Next; 
    private int NumberOfTimes;
    /*
     *this is a node class for a method in the 
     *InvertedIndex class 
     *and it has a NumberOfTimes variable which 
     *we will use later 
     */

    public DocumentIDNode(int docID) {
        this.DocID = docID;
        this.Next = null;
    }

    public int getDocID() {
        return DocID;
    }

    public DocumentIDNode getNext() {
        return Next;
    }

    public void setNext(DocumentIDNode next) {
        this.Next = next;
    }
    public int getNumberOfTimes() {
		return NumberOfTimes;
	}

	public void incrementNumberOfTimes() {
		this.NumberOfTimes++;
	}
}
-----------------------------------------------------------------------------------------------------------------------
public class QueryProcessor {

	private BasicIndex  BasicIndex;
	private DocumentProcessor DocumentProcessor;
	/*
	 * good luck trying to understand this mess 
	 * i barely got this working after days of trying 
	 * i will try explaining to the best of my ability
	 */

	public QueryProcessor(BasicIndex basicIndex, DocumentProcessor documentProcessor) {
	    this.BasicIndex = basicIndex; // Initialize BasicIndex
	    this.DocumentProcessor = documentProcessor;
	}

	

	/*
	 * this takes a sentence from the user and splits it if it has 
	 * more than one word and also uses the ANDORPRESEDNECE method
	 */
	public DocumentIDNode ProcessANDOR(String query) {
		
		String[] words = query.split("\\s+");

		
		return ANDORPRESEDENCE(words);
	}

	/*
	 * notice that we use a tail here
	 * because its way better as its big O(1) for adding at the end of the list
	 * and uses the addtoresult method below 
	 */
	private DocumentIDNode GetAllDocuments() {
		DocumentIDNode resultHead = null, resultTail = null;
		for (int i = 0; i < DocumentProcessor.getDocumentList().getSize(); i++) {
			int docID = DocumentProcessor.getDocumentList().getDocument(i).getDocumentId();
			resultHead = AddToResult(resultHead, resultTail, docID);
			if (resultTail == null) {
				resultTail = resultHead; 
			} else {
				resultTail = resultTail.getNext();
			}
		}
		return resultHead;
	}
	

	/*
	 *checks if a its not excluded then adds it 
	 */
	private DocumentIDNode NOT(DocumentIDNode TotalDocs, DocumentIDNode ExcludedDocs) {
		DocumentIDNode resultHead = null, resultTail = null;
		DocumentIDNode currentTotal = TotalDocs;
		while (currentTotal != null) {
			boolean Excluded = false;
			DocumentIDNode CurrentExcluded = ExcludedDocs;
			while (CurrentExcluded != null) {
				if (currentTotal.getDocID() == CurrentExcluded.getDocID()) {
					Excluded = true;
					break;
				}
				CurrentExcluded = CurrentExcluded.getNext();
			}
			if (!Excluded) {
				resultHead = AddToResult(resultHead, resultTail, currentTotal.getDocID());
				if (resultTail == null) {
					resultTail = resultHead;
				} else {
					resultTail = resultTail.getNext();
				}
			}
			currentTotal = currentTotal.getNext();
		}
		return resultHead;
	}

	/*
	 * Basically makes the AND part of a query always processed first 
	 */
	private DocumentIDNode ANDORPRESEDENCE(String[] query) {
		DocumentIDNode Result = null; 
		DocumentIDNode ANDResult = null;
		boolean WaitingOr = false; 

		for (int i = 0; i < query.length; i++) {
			String term = query[i];

			if (!term.equals("AND") && !term.equals("OR") && !term.equals("NOT"))
			//just checks if the term is not a part of the processing queries and just a normal word
			{

				DocumentIDNode CurrentList = GetDocumentsIDForWord(term);

				
				if (i > 0 && query[i-1].equals("NOT")) {
					DocumentIDNode AllDocs = GetAllDocuments();
					CurrentList = NOT(AllDocs, CurrentList);
				}

				if (ANDResult == null) {
				
					ANDResult = CurrentList;
				} else if (i > 0 && query[i-1].equals("AND")) {
				
					ANDResult = Intersection(ANDResult, CurrentList);
				}

		
				if (i > 0 && query[i-1].equals("OR")) {
					if (Result == null) {
						Result = ANDResult; 
					} else {
						Result = Union(Result, ANDResult); 
					}
					ANDResult = CurrentList; 
					WaitingOr = true;
				}
			}
		}

	
		if (ANDResult != null) {
			if (Result == null || !WaitingOr) {
				Result = ANDResult;
			} else {
				Result = Union(Result, ANDResult);
			}
		}

		return Result;
	}

	private DocumentIDNode GetDocumentsIDForWord(String word) {
		 return BasicIndex.GetDocuments(word);
	}

	private DocumentIDNode Intersection(DocumentIDNode list1, DocumentIDNode list2) {
		DocumentIDNode ResultHead = null, ResultTail = null;

		while (list1 != null && list2 != null) {
			if (list1.getDocID() == list2.getDocID()) {
				ResultHead = AddToResult(ResultHead, ResultTail, list1.getDocID());
				if (ResultTail == null) {
					ResultTail = ResultHead; 
				} else {
					ResultTail = ResultTail.getNext();
				}
				list1 = list1.getNext();
				list2 = list2.getNext();
			} else if (list1.getDocID() < list2.getDocID()) {
				list1 = list1.getNext();
			} else {
				list2 = list2.getNext();
			}
		}

		return ResultHead;
	}

	

	private DocumentIDNode Union(DocumentIDNode list1, DocumentIDNode list2) {
		DocumentIDNode ResultHead = null, ResultTail = null;

	
		while (list1 != null) {
			if (ResultHead == null) {
				ResultHead = new DocumentIDNode(list1.getDocID());
				ResultTail = ResultHead;
			} else {
				ResultTail.setNext(new DocumentIDNode(list1.getDocID()));
				ResultTail = ResultTail.getNext();
			}
			list1 = list1.getNext();
		}

	
		while (list2 != null) {
			boolean isDuplicate = false;
			DocumentIDNode current = ResultHead;

			while (current != null) {
				if (current.getDocID() == list2.getDocID()) {
					isDuplicate = true;
					break;
				}
				current = current.getNext();
			}

			if (!isDuplicate) {
				ResultTail.setNext(new DocumentIDNode(list2.getDocID()));
				ResultTail = ResultTail.getNext();
			}

			list2 = list2.getNext();
		}
		

		return ResultHead;
	}


	private DocumentIDNode AddToResult(DocumentIDNode head, DocumentIDNode tail, int docID) {
	
		DocumentIDNode current = head;
		while (current != null) {
			if (current.getDocID() == docID) {
				return head; 
			}
			current = current.getNext();
		}

	
		DocumentIDNode newNode = new DocumentIDNode(docID);

	
		if (head == null) {
			head = newNode; 
			tail = newNode; 
		} else {
			if (tail != null) {
				tail.setNext(newNode); 
			}
			tail = newNode; 
		}

		return head; 
	}

	public NumNode RankedRetrieval(String query) {
	    NumNode scoreHead = null;

	  
	    String[] terms = query.toLowerCase().split("\\s+");

	  
	    for (String term : terms) {
	        DocumentIDNode docNode = BasicIndex.GetDocuments(term); 

	      
	        while (docNode != null) {
	            int docID = docNode.getDocID();
	            int termFrequency = docNode.getNumberOfTimes(); 

	         
	            scoreHead = AddScore(scoreHead, docID, termFrequency);

	            docNode = docNode.getNext();
	        }
	    }

	    // Sort the ranked results
	    return SortByScoreAscending(scoreHead);
	}


	private NumNode AddScore(NumNode head, int docID, int scoreToAdd) {
	    NumNode current = head, previous = null;

	    while (current != null) {
	        if (current.getDocID() == docID) {
	            current.setScore(current.getScore() + scoreToAdd); 
	            return head;
	        }
	        previous = current;
	        current = current.getNext();
	    }

	    
	    NumNode newNode = new NumNode(docID, scoreToAdd);
	    if (previous == null) {
	        return newNode; // First node in the list
	    } else {
	        previous.setNext(newNode);
	    }
	    return head;
	}


	private NumNode SortByScoreAscending(NumNode head) {
		if (head == null || head.getNext() == null) {
			return head; 
		}

	
		NumNode sorted = null; 

		while (head != null) {
			
			NumNode minNode = head;
			NumNode minPrev = null;
			NumNode current = head;
			NumNode prev = null;

			while (current != null) {
				if (current.getScore() < minNode.getScore()) { 
					minNode = current;
					minPrev = prev;
				}
				prev = current;
				current = current.getNext();
			}

			
			if (minPrev != null) {
				minPrev.setNext(minNode.getNext());
			} else {
				head = head.getNext(); 
			}

			
			minNode.setNext(sorted);
			sorted = minNode;
		}

		return sorted; 
	}


}
---------------------------------------------------------------------------------------------------------------------------
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DocumentProcessor {
	private DocumentLL DocumentList;
	private StopWordsLL StopWords;
	/*
	 * this is complicated class so ill explain it method by method in short 1-it
	 * loads a document 2-filters words 3-builds the inverted index
	 */

	public DocumentProcessor(String stopWordsFilePath) {
		DocumentList = new DocumentLL();
		StopWords = new StopWordsLL();
		StopWords.LoadStopWords(stopWordsFilePath);// go to class stopwordsLL
	}

	private String[] Filtering(String content) 
	/*
	 * take a sentence,splits it 
	 * handles all punctuation marks and then adds it to an array called filtered words
	 * 
	 */
	{
		String[] RawWords = content.toLowerCase().split("\\s+");
		String[] FilteredWords = new String[RawWords.length];
		int count = 0;

		for (String word : RawWords) {
			word = word.replaceAll("[^a-zA-Z0-9]", "");

			if (!StopWords.IsStopWord(word) && !word.isEmpty()) {
				FilteredWords[count++] = word;
			}
		}

		String[] result = new String[count];
		System.arraycopy(FilteredWords, 0, result, 0, count);
		return result;
	}

	public void LoadDocuments(String filePath) 
	/*
	 * reads the cvs files and skips the first line because it isnt useful 
	 * and stops reading when it encounters an empty line
	 * we have an array called parts which has 2 contents the first one
	 * is the id the second one is the string or the sentence 
	 */
	{
		try (BufferedReader a = new BufferedReader(new FileReader(filePath))) {
			String line;

			a.readLine(); // We are skipping the first line here

			while ((line = a.readLine()) != null) {
				line = line.trim();// for the "whitespace" of lines
				if (line.isEmpty())
					continue;
				
			
				String[] parts = line.split(",", 2);
				/*
				 * splits the line into two parts once it detects  
				 * the first ","
				 * the reason for the number 2 
				 * is that a sentence may have a lot of commas
				 * but we only want to split it once
				 * with the first comma being the one 
				 * that indicates the ending of the 
				 * doc id and the beginning of the string content
				 */

				try {
					int documentId = Integer.parseInt(parts[0].trim());
					/*
					 *converts the document id from a string
					 *(since it is handled this way)
					 *to a number using parseInt 
					 */
					String content = parts[1].trim();
					/*
					 * again just removing whitespace
					 */
					String[] Sentences = Filtering(content);
					BasicIndex.AddDocument(documentId,Sentences);

					Document doc = new Document(documentId, String.join(" ", Sentences));
					/*
					 * we use join since the Filtering method returns 
					 * an array of words,we could use another way to do this
					 * but its the most obvious solution 
					 */
					DocumentList.addDocument(doc);
				} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
					continue;
				}
			}
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
	}

	private InvertedIndexBST InvertedIndex = new InvertedIndexBST();


	public void BuildInvertedIndex() 
	/*
	 * goes through a loop of documents
	 * splits the strings into singular words
	 * then adds the word using its doc id
	 */
	{
		for (int i = 0; i < DocumentList.getSize(); i++) {
			Document doc = DocumentList.getDocument(i);
			String[] words = doc.getContent().split("\\s+");
			for (String word : words) {
				InvertedIndex.AddWord(word, doc.getDocumentId());
			}
		}
	}
	private BasicIndex BasicIndex = new BasicIndex();
	
	public BasicIndex getBasicIndex() {
	    return BasicIndex;
	}

	public InvertedIndexBST getInvertedIndex() {
		return InvertedIndex;
	}

	public DocumentLL getDocumentList() {
		return DocumentList;
	}
}
--------------------------------------------------------------------------------------------------------------------------
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class StopWordsLL {
    private StopWordNode Head;
    /*
     * a linked list representing stop words
     */

    public StopWordsLL() {
        Head = null;
    }
    

    /*
     * loads the stop words and adds them using the below method
     */

    public void LoadStopWords(String filePath) {
        try (BufferedReader a = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = a.readLine()) != null) {
                AddStopWord(line.trim().toLowerCase());//we dont need to make them lowercase,but its more professional 
            }
        } catch (IOException e) {
            System.out.println("Error reading stop words file: " + e.getMessage());
        }
    }
    /*
     * adds a word using the normal linked list method
     */


    private void AddStopWord(String word) {
        StopWordNode newNode = new StopWordNode(word);
        if (Head == null) {
            Head = newNode;
        } else {
            StopWordNode current = Head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
    }
    /*
     * a check if a word belongs to this class,useful later
     */


    public boolean IsStopWord(String word) {
        StopWordNode current = Head;
        while (current != null) {
            if (current.getWord().equals(word)) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }
}
--------------------------------------------------------------------------------------------------------------------------
public class NumNode {
    private int DocID;
    private int Score;
    private NumNode Next;
    /*
     * classic node class 
     * useful for ranked retrieval in the queryprocessor class 
     * 
     */

    public NumNode(int docID, int score) {
        this.DocID = docID;
        this.Score = score;
        this.Next = null;
    }

    public int getDocID() {
        return DocID;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        this.Score = score;
    }

    public NumNode getNext() {
        return Next;
    }

    public void setNext(NumNode next) {
        this.Next = next;
    }
}
------------------------------------------------------------------------------------------------------------------------
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
						System.out.println(
								"Document ID: " + rankedResults.getDocID() + " - Score: " + rankedResults.getScore());
						rankedResults = rankedResults.getNext();
					}
				}
				break;

			case 3:
			    System.out.println("\n*** Basic Index documents ***");
			    documentProcessor.getBasicIndex().displayAllDocuments();
			    break;


			case 4:
			    System.out.println("\n*** Words in Basic Index ***");
			    BasicIndex basicIndex1 = documentProcessor.getBasicIndex();

			   
			    WordDocumentLL wordToDocuments = new WordDocumentLL();

			   
			    DocumentNode currentDoc = basicIndex1.getHead(); 
			    while (currentDoc != null) {
			        int docID = currentDoc.getDocument().getDocumentId();
			        String[] words = currentDoc.getDocument().getContent().split("\\s+");

			      
			        for (String word : words) {
			            wordToDocuments.addWord(word, docID);
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

			case 5:
				System.out.println("Exiting the program");
				return;

			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}
}
