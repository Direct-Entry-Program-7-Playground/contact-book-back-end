package lk.ijse.dep7.contactbookbackend.service;

import lk.ijse.dep7.contactbookbackend.dto.ContactDTO;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.*;

public class ContactService {
    private Connection connection;

    public ContactService(Connection connection) {
        this.connection = connection;
    }

    public String saveContact(ContactDTO contact) {

        try {
            PreparedStatement stm = connection.prepareStatement("INSERT INTO contact (fname, lname, phone, email, address, picture) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, contact.getfName());
            stm.setString(2, contact.getlName());
            stm.setString(3, contact.getPhone());
            stm.setString(4, contact.getEmail());
            stm.setString(5, contact.getAddress());
            stm.setBlob(6, new SerialBlob(contact.getPicture()));

            if (stm.executeUpdate() == 1) {
                ResultSet keys = stm.getGeneratedKeys();
                keys.next();
                return String.format("CID%03d", keys.getInt(1));
            }
            throw new RuntimeException("Failed to save the contact");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
