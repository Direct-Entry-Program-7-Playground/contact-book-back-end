package lk.ijse.dep7.contactbookbackend.service;

import lk.ijse.dep7.contactbookbackend.dto.ContactDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertTrue;


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
//        connection.rollback();
        connection.setAutoCommit(true);
        connection.close();
    }

    @Test
    void saveContact() throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get("/home/manoj/Documents/MRR/IJSE/DEP/Phase - 2/Day_89 (2021-10-11)/contact-book-front-end/asset/img/bee.jpeg"));
        String s = contactService.saveContact(new ContactDTO("Dulaj", "Fernando", "071-456-1258", "dulaj@email.com", "Kurunegala", bytes));
        assertTrue(s.matches("CID\\d{3,}"));
    }

    @Test
    void readContact() throws SQLException, IOException {
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM contact WHERE id=15");
        ResultSet rst = stm.executeQuery();
        rst.next();
        Blob picture = rst.getBlob("picture");
        byte[] bytes = picture.getBytes(1, (int) picture.length());
        Files.write(Paths.get("/home/manoj/Documents/MRR/IJSE/DEP/Phase - 2/Day_89 (2021-10-11)/contact-book-back-end/src/main/uploaded/img/bee.jpeg"),bytes);

    }

}