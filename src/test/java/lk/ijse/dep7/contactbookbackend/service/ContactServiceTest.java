package lk.ijse.dep7.contactbookbackend.service;

import lk.ijse.dep7.contactbookbackend.dto.ContactDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContactServiceTest {
    private Connection connection;
    private ContactService contactService;

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cbook", "root", "7251mMm7251");
        connection.setAutoCommit(false);
        contactService = new ContactService(connection);
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
        connection.close();
    }

    @Test
    void saveContact() throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get("/home/manoj/Documents/MRR/IJSE/DEP/Phase - 2/Day_89 (2021-10-11)/contact-book-front-end/asset/img/bee.jpeg"));
        String s = contactService.saveContact(new ContactDTO("Dulaj", "Fernando", "071-456-1258", "dulaj@email.com", "Kurunegala", bytes));
        assertTrue(s.matches("CID\\d{3,}"));
        String s2 = contactService.saveContact(new ContactDTO("Milan", "Gunashinghe", "071-159-7895", "milan@email.com", "Giriulla", bytes));
        assertTrue(s2.matches("CID\\d{3,}"));
        String s3 = contactService.saveContact(new ContactDTO("Wanidu", "Silva", "077-147-2589", "wanidu@email.com", "Galle", bytes));
        assertTrue(s3.matches("CID\\d{3,}"));
        String s4 = contactService.saveContact(new ContactDTO("Dasun", "Shanaka", "075-254-5632", "dasun@email.com", "Negombo", bytes));
        assertTrue(s4.matches("CID\\d{3,}"));
    }


    @Test
    void findContact() {
        List<ContactDTO> contacts = contactService.findContact("");
        contacts.forEach(System.out::println);
    }
}