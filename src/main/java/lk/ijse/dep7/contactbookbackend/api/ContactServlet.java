package lk.ijse.dep7.contactbookbackend.api;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lk.ijse.dep7.contactbookbackend.dto.ContactDTO;
import lk.ijse.dep7.contactbookbackend.service.ContactService;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

@MultipartConfig(
        location = "/home/manoj/Documents/MRR/IJSE/DEP/Phase - 2/Day_89 (2021-10-11)/contact-book-back-end/src/main/uploaded/img/",
        maxFileSize = 1024 * 1024 * 50)
@WebServlet(name = "ContactServlet", value = "/contact", loadOnStartup = 0)
public class ContactServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/contact")
    private DataSource dataSource;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doGet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doPost");


        try (Connection connection = dataSource.getConnection()) {
            ContactService contactService = new ContactService(connection);

            String fname = request.getParameter("fname");
            String lname = request.getParameter("lname");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String address = request.getParameter("address");
            Part cimage = request.getPart("cimage");

            InputStream is = cimage.getInputStream();
            byte[] picture = new byte[is.available()];
            is.read(picture);
            ContactDTO contact = new ContactDTO(fname, lname, phone, email, address, new String(picture));

            System.out.println(contactService.saveContact(contact));;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }


    }
}
