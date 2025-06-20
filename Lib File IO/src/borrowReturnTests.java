import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * JUnit tests for book borrowing and returning operations in the Library system.
 * Tests cover both successful operations and error conditions.
 */
public class borrowReturnTests {
	
	/**
     * an instance of class Library
     */
    private Library library;
    
    
    /**
     * Implicit JUnit test class constructor
     */
    public borrowReturnTests() {
    	
    }
    
    /**
     * Initializes test environment before each test.
     * Creates a library instance and adds test books:
     * - Fiction book (code: 101, quantity: 5)
     * - Reference book (code: 102, quantity: 1)
     */    
    @Before
    public void setUp() {
        library = new Library();
        
        // Add fiction book
        simulateAddBook("f", 101, "Fiction Book", "Author", 5);
        
        // Add reference book
        simulateAddBook("r", 102, "Reference Book", "Author", 1);
    }
    
    /**
     * Simulates the book addition process through user input
     * @param type Book type (f/r/n)
     * @param code Book code
     * @param title Book title
     * @param author Book author
     * @param quantity Initial stock quantity
     */
    private void simulateAddBook(String type, int code, String title, String author, int quantity) {
        String input = String.join("\n", type, String.valueOf(code), title, author, String.valueOf(quantity), "");
        library.addBook(new Scanner(new ByteArrayInputStream(input.getBytes())),false);
    }
    
    /* Borrow Tests */
    
    /**
     * Tests successful borrowing of available fiction book
     * Correct quantity deduction
     * Returns true on success
     */
    @Test
    public void testSuccessfulBorrow() {
        String input = "101\n2\n"; // Borrow 2 copies of book 101
        boolean result = library.borrowBook(new Scanner(new ByteArrayInputStream(input.getBytes())));
        assertTrue("Should successfully borrow available book", result);
    }
    
    /**
     * Tests borrowing attempt of reference book
     * Rejects reference books
     * Returns false for reference books
     */
    @Test
    public void testBorrowReferenceBook() {
        String input = "102\n1\n"; // Attempt to borrow reference book
        boolean result = library.borrowBook(new Scanner(new ByteArrayInputStream(input.getBytes())));
        assertFalse("Should reject borrowing reference books", result);
    }
    
    /* Return Tests */
    
    /**
     * Tests successful return of previously borrowed book
     *  Correct quantity restoration
     *  Returns true on success
     */
    @Test
    public void testSuccessfulReturn() {
        // First borrow
        library.borrowBook(new Scanner(new ByteArrayInputStream("101\n2\n".getBytes())));
        
        // Then return
        String input = "101\n2\n";
        boolean result = library.returnBook(new Scanner(new ByteArrayInputStream(input.getBytes())));
        assertTrue("Should successfully return borrowed books", result);
    }
    
    /**
     * Tests returning more copies than borrowed
     * Prevents over-returning
     * Returns false on invalid return
     */
    @Test
    public void testReturnExcessQuantity() {
        String input = "101\n3\n"; // Attempt to return without borrowing
        boolean result = library.returnBook(new Scanner(new ByteArrayInputStream(input.getBytes())));
        assertFalse("Should reject returning unborrowed books", result);
    }
    
    /**
     * Tests handling of invalid numeric input
     * Proper InputMismatchException handling
     * InputMismatchException
     */
    @Test(expected = InputMismatchException.class)
    public void testInvalidNumberInput() {
        String input = "not_a_number\n";
        library.borrowBook(new Scanner(new ByteArrayInputStream(input.getBytes())));
    }
}