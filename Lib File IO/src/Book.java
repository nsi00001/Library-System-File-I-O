import java.util.Formatter;
import java.util.Scanner;

/**
 * CET - CS Academic Level 3
 * Declaration: I declare that this is my own original work and is free from Plagiarism
 * This class contains the methods relevant to Book
 * Student Name: Dennis Nsi 
 * Student Number: 041149827 
 * Course: CST8130 - Data Structures
 * CET-CS-Level 3
 * Task: Assignment 2
 * @author/Professor James Mwangi PhD. 
 * 
 */

public class Book implements Comparable<Book>{

	/**
	 * the code of a book
	 */
	protected int bookCode;
	/**
	 * the book title
	 */
	protected String title;
	/**
	 * the author of a book 
	 */
	protected String author;
	/**
	 * quantity of a book in store
	 */
	protected int quantityInStock;
	/**
	 * the book genre
	 */
	protected String genre;
	/**
	 *  max quantity of a book
	 */
	protected int max;

	/**
	 * Default Constructor
	 */
	public Book() { 
		bookCode = 0;
		title = "";
		author = "";
		quantityInStock = 0;
	}

	/**
	 * Custom Constructor
	 * @param bookCode - the unique code identifying the book
	 * @param title - the title of the book 
	 * @param author - the author of the book
	 * @param quantityInStock - the number of copies currently in stock
	 * @param genre - the genre/category of the book
	 */
	public Book(int bookCode, String title, String author, int quantityInStock, String genre) { 
		this.bookCode = bookCode;
		this.title = title;
		this.author = author;
		this.quantityInStock = quantityInStock;
		this.genre = genre;	
		this.max = quantityInStock;
	}

	/**
	 * This method returns the quantity of a book in stock
	 * @return the quantity of a book in stock
	 */
	public int getQuantityInStock() {
		return quantityInStock;
	}

	/**
	 * This method returns the book genre
	 * @return the genre of a book
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * Generates a string representation of the book's details.
	 * @return A formatted string containing all book information
	 */
	public String toString() {
		return String.format("Book: " + bookCode + " " + title + " " + quantityInStock + " Author: " + author + " " + "Genre: " + genre);
	}

	/**
	 * Updates the inventory quantity while maintaining non-negative stock levels.
	 * @param amount The amount to adjust inventory by
	 * @return true if update was successful, false if it would result in negative quantity
	 */
	public boolean updateQuantity(int amount) {
		if((quantityInStock + amount) >= 0) {
			quantityInStock += amount;
			return true;
		}
		return false;
	}

	/**
	 * Compares this book with another book for equality based on title and author.
	 * @param book The Book object to compare with
	 * @return true if books have same title and author, false otherwise
	 */
	public boolean isEqual(Book book) {

		if(title.equals(book.title) && author.equals(book.author)){
			return true;
		}
		return false;
	}

	/**
	 * Populates book data by reading from user input via Scanner.
	 * @param scanner The Scanner object used to read user input
	 * @param fromFile checks if books are added from a file
	 * @return true if all fields were successfully read, false otherwise
	 */
	public boolean addBook(Scanner scanner, boolean fromFile) {
		boolean result = true;
		if(!inputCode(scanner, false)) return false;

		System.out.print("Enter the book title: ");
		title = scanner.nextLine();

		System.out.print("Enter the author of the book: ");
		author = scanner.nextLine();

		do {
			System.out.print("Enter the quantity of the book: ");
			try {
				result = true;
				quantityInStock = scanner.nextInt();
				scanner.nextLine();      // Clear invalid input
				if(quantityInStock <= 0) {
					result = false;
					System.out.println("Invalid entry");
				}
				max = quantityInStock;


			} catch (Exception e) {
				System.out.println("Invalid entry");
				scanner.nextLine();
				result = false;
			}
		}while(!result);
		return true;
	}

	/**
	 * It returns an integer
	 * @return an integer of the book code
	 */
	public int getBookCode() {
		return bookCode;
	}

	/**
	 * Reads and validates a Fiction book code from user input.
	 * @param scanner The Scanner object used to read user input
	 * @param fromFile checks if book code is added from a file
	 * @return true if valid book code was read, false otherwise
	 */
	public boolean inputCode(Scanner scanner, boolean fromFile) {

		while(true){
			System.out.print("Enter the code for the book: ");
			try {
				int code = scanner.nextInt();
				scanner.nextLine();    // Clear invalid input

				if(code != bookCode) {
					bookCode = code;
					return true;
				}
				System.out.print("Book code already exists.");

			}catch (Exception e) {
				System.out.println("Invalid code");
				scanner.nextLine();
			}
		}	
	}

	/**
	 * Writes the attributes of Book to a file
	 * @param writer is a parameter of type Formatter
	 */
	public void outputBook(Formatter writer) {
		writer.format("%s\n%d\n%s\n%d\n%s\n",genre,bookCode,title,quantityInStock,author);
	}

	@Override
	public int compareTo(Book book) {
		return Integer.compare(bookCode, book.bookCode);
	}

}
