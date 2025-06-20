import java.util.Formatter;
import java.util.Scanner;

/**
 * CET - CS Academic Level 3
 * Declaration: I declare that this is my own original work and is free from Plagiarism
 * This class contains the methods relevant to FictionBook
 * Student Name: Dennis Nsi 
 * Student Number: 041149827 
 * Course: CST8130 - Data Structures
 * CET-CS-Level 3
 * Task: Assignment 2
 * @author/Professor James Mwangi PhD. 
 * 
 */

public class FictionBook extends Book{

	/**
	 * Default constructor
	 */	
	public FictionBook() {
		super();
		genre = "Fiction";
	}

	/**
	 * Custom Constructor
	 * @param _bookCode - the unique code identifying the book
	 * @param _title - the title of the book
	 * @param _author - the author of the book
	 * @param _quantityInStock - the number of copies currently in stock
	 * @param _genre - the genre/category of the book always set to Fiction
	 */
	public FictionBook(int _bookCode, String _title, String _author, int _quantityInStock, String _genre) { 
		super(_bookCode, _title, _author, _quantityInStock, _genre);
		genre = "Fiction";
	}

	/**
	 * Populates book data by reading from user input via Scanner.
	 * @param scanner The Scanner object used to read user input
	 * @return true if all fields were successfully read, false otherwise
	 */
	@Override
	public boolean addBook(Scanner scanner, boolean fromFile) {
		genre = "Fiction";
		return super.addBook(scanner, false);
	}

	/**
	 * Generates a string representation of the fiction book's details.
	 * @return A formatted string containing all fiction book information
	 */
	@Override
	public String toString() {
		if(genre == "f") {
			genre = "Fiction";
		}
		return super.toString();
	}

	/**
	 * @param writer displays the output of a book
	 */
	@Override
	public void outputBook(Formatter writer) {
		genre = "f";
		super.outputBook(writer);
	}

}
