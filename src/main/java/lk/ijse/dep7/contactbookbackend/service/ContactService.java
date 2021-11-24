package lk.ijse.dep7.contactbookbackend.service;

import lk.ijse.dep7.contactbookbackend.dto.ContactDTO;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            stm.setBlob(6, contact.getPicture() != null ? new SerialBlob(contact.getPicture()) : null);

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

    public List<ContactDTO> findContact(String query) {

        List<ContactDTO> contacts = new ArrayList<>();
        query = "%" + query + "%";

        try {
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM contact WHERE id LIKE ? OR fname LIKE ? OR lname LIKE ? OR phone LIKE ? OR email LIKE ? OR address LIKE ?;");

            stm.setString(1, query);
            stm.setString(2, query);
            stm.setString(3, query);
            stm.setString(4, query);
            stm.setString(5, query);
            stm.setString(6, query);

            ResultSet rst = stm.executeQuery();

            while (rst.next()) {
                Blob picture = rst.getBlob("picture");
                byte[] bytes = picture.getBytes(1, (int) picture.length());

                contacts.add(new ContactDTO(
                        String.format("CID%03d", rst.getInt("id")),
                        rst.getString("fname"),
                        rst.getString("lname"),
                        rst.getString("phone"),
                        rst.getString("email"),
                        rst.getString("address"),
                        bytes
                ));
            }

            return contacts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
