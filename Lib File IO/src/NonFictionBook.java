import java.util.Formatter;
import java.util.Scanner;

/**
 * CET - CS Academic Level 3
 * Declaration: I declare that this is my own original work and is free from Plagiarism
 * This class contains the methods relevant to NonFictionBook
 * Student Name: Dennis Nsi 
 * Student Number: 041149827 
 * Course: CST8130 - Data Structures
 * CET-CS-Level 3
 * Task: Assignment 2
 * @author/Professor James Mwangi PhD. 
 * 
 */

public class NonFictionBook extends Book {

	/**
	 * fieldOfStudy - is the specified study subject
	 */
	private String fieldOfStudy;
	/**
	 * Default constructor
	 */	
	public NonFictionBook() {
		super();
		genre = "Non-Fiction";
	}

	/**
	 * Custom Constructor
	 * @param bookCode - the unique code identifying the book
	 * @param title - the title of the book
	 * @param author - the author of the book
	 * @param quantityInStock - the number of copies currently in stock
	 * @param genre - the genre/category of the book always set to Non Fiction
	 * @param fieldOfStudy - specifies the field of study of a non fiction book
	 */
	public NonFictionBook(int bookCode, String title, String author, int quantityInStock, String genre, String fieldOfStudy) { 
		super(bookCode, title, author, quantityInStock, genre);
		this.genre = "Non-Fiction";
		this.fieldOfStudy = fieldOfStudy;
	}

	/**
	 * Populates NonFictionBook data by reading from user input via Scanner.
	 * @param scanner The Scanner object used to read user input
	 * @return true if all fields were successfully read, false otherwise
	 */
	@Override
	public boolean addBook(Scanner scanner, boolean fromFile) {

		genre = "Non-Fiction";
		super.addBook(scanner, false);
		System.out.print("Enter the field of study: ");
		fieldOfStudy = scanner.nextLine();

		return true;
	}

	/**
	 * Generates a string representation of the non-fiction book's details.
	 * @return A formatted string containing all fiction book information
	 */
	@Override
	public String toString() {
		if(genre == "n") {
			genre = "Non-Fiction";
		}
		return super.toString() + " Field of Study: " + fieldOfStudy;
	}

	/**
	 * @param writer displays the output of a book
	 */
	@Override	
	public void outputBook(Formatter writer) {
		genre = "n";
		super.outputBook(writer);
		writer.format("%s\n" ,fieldOfStudy);	
	}

}
