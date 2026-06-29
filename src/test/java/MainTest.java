import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class MainTest {
    private PhoneBook phoneBook;

    @BeforeEach
    void setUp() {
        phoneBook = new PhoneBook();
    }

    @Test
    void testAdd() {
        assertEquals(1, phoneBook.add("John", "111"));
        assertEquals(2, phoneBook.add("Alice", "222"));
        assertEquals(2, phoneBook.add("John", "333"));
        assertEquals(3, phoneBook.add("Bob", "444"));
    }

    @Test
    void testFindByNumber() {
        phoneBook.add("John", "111");
        phoneBook.add("Alice", "222");

        assertEquals("John", phoneBook.findByNumber("111"));
        assertEquals("Alice", phoneBook.findByNumber("222"));
        assertNull(phoneBook.findByNumber("555"));
    }

    @Test
    void testFindByName() {
        phoneBook.add("John", "111");
        phoneBook.add("Alice", "222");

        assertEquals("111", phoneBook.findByName("John"));
        assertEquals("222", phoneBook.findByName("Alice"));
        assertNull(phoneBook.findByName("Bob"));
    }

    @Test
    void testPrintAllNames() {
        phoneBook.add("John", "111");
        phoneBook.add("Alice", "222");
        phoneBook.add("Bob", "666");
        phoneBook.add("Charlie", "4444444444");

        List<String> names = phoneBook.printAllNames();
        assertEquals(List.of("Alice", "Bob", "Charlie", "John"), names);
    }

    @Test
    void testComplexScenario() {
        phoneBook.add("John", "111");
        phoneBook.add("Alice", "222");
        phoneBook.add("Bob", "666");
        phoneBook.add("Charlie", "4444444444");

        assertEquals("Alice", phoneBook.findByNumber("222"));
        assertEquals("Charlie", phoneBook.findByNumber("4444444444"));
        assertNull(phoneBook.findByNumber("1111111111"));

        assertEquals("111", phoneBook.findByName("John"));
        assertEquals("666", phoneBook.findByName("Bob"));
        assertNull(phoneBook.findByName("David"));

        List<String> names = phoneBook.printAllNames();
        assertEquals(List.of("Alice", "Bob", "Charlie", "John"), names);

        assertEquals(4, phoneBook.add("John", "9999999999"));
        assertEquals("111", phoneBook.findByName("John"));
    }
}