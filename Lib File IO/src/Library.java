import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * CET - CS Academic Level 3
 * Declaration: I declare that this is my own original work and is free from Plagiarism
 * This class contains the methods relevant to Library operations
 * Student Name: Dennis Nsi 
 * Student Number: 041149827 
 * Course: CST8130 - Data Structures
 * CET-CS-Level 3
 * Task: Assignment 2
 * @author/Professor James Mwangi PhD. 
 * 
 */


public class Library {
	/**
	 * catalog is an array of type Book
	 */
	private ArrayList<Book> catalog;
	/**
	 * keeps track of the number of books in the array
	 */
	private int numBooks;

	/**
	 * Default constructor for Library class
	 */
	public Library() {
		numBooks = 0;
		catalog = new ArrayList<Book>();
	}

	/**
	 * Returns a string representation of all books in the catalog
	 * @return empty string 
	 */
	@Override
	public String toString() {
		Collections.sort(catalog);
		for(Book b: catalog) {
			System.out.println(b.toString());
		}
		return "";
	}

	/**
	 * Checks if a book already exists in the catalog
	 * @param book the Book object to check for existence
	 * @return index of the existing book if found, -1 otherwise
	 */
	public int alreadyExists(Book book) {
		for(int i = 0; i < catalog.size(); i++) {
			if(!catalog.isEmpty() && catalog.get(i).bookCode == book.bookCode) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Adds a new book to the library catalog
	 * @param scanner takes the user input
	 * @param fromFile checks if books are added from a file
	 * @return a boolean based on if the operation was successful or not
	 */
	public boolean addBook(Scanner scanner, boolean fromFile) {
		boolean result=false;
		// Check if catalog is full
		Book b = null;
		// Get book type from user
		if(fromFile) {
			String genre = scanner.nextLine().trim();
			String codeStr = scanner.nextLine().trim();
			String title = scanner.nextLine().trim();
			String quantityStr = scanner.nextLine().trim();
			String author = scanner.nextLine().trim();

			int code = Integer.parseInt(codeStr);
			int quantity = Integer.parseInt(quantityStr);

			switch (genre) {
			case "f": 
				b = new FictionBook(code, title, author, quantity, genre);
				break;
			case "n": 
				String fieldOfStudy = scanner.nextLine().trim();
				b = new NonFictionBook(code, title, author, quantity, genre, fieldOfStudy);
				break;
			case "r": 
				String edition = scanner.nextLine().trim();
				b = new ReferenceBook(code, title, author, quantity, genre, edition);
				break;
			}

			if(alreadyExists(b) != -1) {
				System.out.println("Book code already exists");
				System.out.println("Error Encountered while reading the file, aborting...");
				return result;
			}

		} else {
			String inpt="";
			do {
				System.out.print("Do you wish to add a Fiction(f), Non-Fiction(n), or Reference(r) book? ");
				inpt = scanner.nextLine().toLowerCase();
				// Create appropriate book type based on input 
				//		   
				switch (inpt) {
				case "f":
					b = new FictionBook();
					break;
				case "n":
					b = new NonFictionBook();
					break;
				case "r":
					b = new ReferenceBook();
					break;
				default: 
					System.out.println("Invalid entry");
				} 
			}while(b == null);

			if(!b.addBook(scanner, !fromFile)) {
				return result;
			}

			// Check if book already exists
			if(alreadyExists(b) != -1) {
				System.out.println("Book code already exists.");
				return result;
			}

		}

		// Add book to catalog
		catalog.add(b);
		numBooks++;

		return true;

	}

	/**
	 * Borrows a book from the library catalog
	 * @param scanner Scanner for user input
	 * @return true if borrow was successful, false otherwise
	 */
	public boolean borrowBook(Scanner scanner) {

		try {
			if(getNumBooks() == 0) {
				System.out.println("Error...could not borrow book");
				return false;
			}
			System.out.print("Enter the book code to borrow: ");
			int code = scanner.nextInt();
			scanner.nextLine();
			Book b = null;
			// Search for book in catalog
			for (int i = 0; i < getNumBooks(); i++) {
				if (catalog.get(i).bookCode == code) {      
					b = catalog.get(i);
					break;
				}
			}
			//Book no found case
			if (b == null) {
				System.out.println("Error...could not borrow book");
				return false;
			}

			// Check if book is reference (can't be borrowed)
			if (b.genre.equals("Reference")) {
				System.out.println("Error: Reference books cannot be borrowed");
				return false;
			}
			System.out.print("Enter quantity to borrow: ");
			int quantity = scanner.nextInt();
			scanner.nextLine(); // Consume newline

			// Validate and process borrowing
			if(quantity > 0 && quantity <= b.quantityInStock ) {
				b.updateQuantity(-quantity);
				return true;
			}else {
				System.out.println("Error...could not borrow book");
				return false;
			}
		} catch (InputMismatchException e) {
			System.out.println("Error: Invalid input");
			scanner.nextLine(); // Clear invalid input
			return false;
		}
	}

	/**
	 * Returns a book to the library catalog
	 * @param scanner Scanner for user input
	 * @return true if return was successful, false otherwise
	 */
	public boolean returnBook(Scanner scanner) {

		try {
			if(getNumBooks() == 0) {
				System.out.println("Error...could not borrow book");
				return false;
			}
			System.out.print("Enter the code for the book: ");
			int code = scanner.nextInt();
			scanner.nextLine(); // Consume newline
			// Search for book in catalog
			Book b = null;
			for (int i = 0; i < getNumBooks(); i++) {
				if (catalog.get(i).bookCode == code) {      
					b = catalog.get(i);
					break;
				}
			}
			//Book not found case
			if(b == null) {
				System.out.println("Error...could not return book");
				return false;
			}       
			System.out.print("Enter valid quantity to return: ");
			int quantity = scanner.nextInt();
			scanner.nextLine(); // Consume newline 

			// Reference books cannot be returned (as they can't be borrowed)
			if(b.genre.equals("Reference")) {
				System.out.println("Error...could not return book");
				return false;
			}

			// Validate and process borrowing
			if(quantity > 0 && quantity <= b.max - b.quantityInStock ) {
				b.updateQuantity(quantity);
				System.out.println("Successfully returned " + quantity + " copies");
				return true;
			}else {
				System.out.println("Error...could not return book");
				return false;
			}
		}catch (InputMismatchException e) {
			System.out.println("Error: Invalid input");
			scanner.nextLine(); // Clear invalid input
			return false;
		}
	}

	/**
	 * Searches for a book in a catalog
	 * @param scanner - takes the book code 
	 */
	public void searchForBook(Scanner scanner) {

		int code = 0;


		try {
			code = scanner.nextInt();
			scanner.nextLine();      // Clear invalid input
		}catch (Exception e) {
			System.out.print("Invalid entry. Please enter a number");
			scanner.nextLine();
		}

		//		scanner.nextLine();
		if (numBooks == 0) {
			System.out.println("Code not found in catalog...");   
			return;
		}

		Collections.sort(catalog);
		int low = 0;	//Defining the low index
		int high = catalog.size() - 1;  //Defining the high index
		boolean check = false;

		while(low <= high) {		
			int mid = low + (high - low)/2;	 	//sets the midpoint for the array

			if(code == catalog.get(mid).bookCode){
				System.out.println(catalog.get(mid).toString());
				check = true;
				break;
			} else if(catalog.get(mid).bookCode < code) {	//increase low if the array value is less than the search key
				low = mid + 1;
			} else  {
				high = mid - 1;
			}
		}

		if(!check) {
			System.out.println("Code not found in catalog...");   		
		}

	}

	/**
	 * Saves the catalog and its attributes to a file
	 * @param scanner - takes user input for file name
	 */
	public void saveToFile(Scanner scanner) {
		String filename = scanner.nextLine().trim();

		try(Formatter writer = new Formatter(filename)) {

			for(Book b: catalog) {
				b.outputBook(writer);
			}
		} catch (InvalidPathException e) {
			System.out.println("Invalid path" + filename + "' " + e.getReason());
		} catch(IOException e) {
			System.out.println("Error saving file '" + filename + "' " + e.getMessage());
		} 
	}

	/**
	 * Reads the attributes of books and adds it to the catalog
	 * @param scanner - takes user input for file name
	 */
	public void readFromFile(Scanner scanner) {
		String filename = scanner.nextLine().trim();

		try (Scanner fileScanner = new Scanner(new File(filename))) {
			int lineNumber = 0;	
			while(fileScanner.hasNextLine()) {
				try {
					lineNumber++;
					boolean result = addBook(fileScanner, true);
					if(!result)  	break;

				} catch(NumberFormatException  e) {
					System.out.println("Line %d: Invalid numberic value for code" + lineNumber);
				}
			}
		}catch(FileNotFoundException e) {
			System.out.println("File Not Found, ignoring...");
		}

	}

	/**
	 * Returns the number of books in the catalog
	 * @return the number of books in the catalog
	 */
	public int getNumBooks() {
		return numBooks;
	}

}



