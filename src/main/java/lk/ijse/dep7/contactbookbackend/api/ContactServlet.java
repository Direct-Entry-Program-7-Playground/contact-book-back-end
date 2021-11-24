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
import java.io.PrintWriter;
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

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getContentType() == null || !request.getContentType().startsWith("multipart/form-data")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request");
            return;
        }

        String errMsg = null;


        String fname = null;
        String lname = null;
        String phone = null;
        String email = null;
        String address = null;
        Part cimage = null;

        byte[] picture = null;
        try {
            fname = request.getParameter("fname");
            lname = request.getParameter("lname");
            phone = request.getParameter("phone");
            email = request.getParameter("email");
            address = request.getParameter("address");
            cimage = request.getPart("cimage");
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request");
            return;
        }

        if (fname == null) {
            errMsg = "First name can't be empty";
        } else if (!fname.trim().matches("^[A-Za-z]{3,}")) {
            errMsg = "Invalid name";
        } else if (phone == null && email == null) {
            errMsg = "Phone and email both are empty, please add at least one contact detail";
        } else if (phone != null && !phone.trim().matches("^[\\d]{3,10}")) {
            errMsg = "Invalid phone number";
        } else if (email != null && !email.trim().matches("^[A-Za-z\\d.]{3,}@[A-Za-z\\d]{3,}(.[A-Za-z]{2,})+")) {
            errMsg = "Invalid email address";
        } else if (address != null && !(address.trim().length() >= 3)) {
            errMsg = "Invalid address";
        } else {
            pic:
            if (cimage != null) {
                if (cimage.getContentType() == null || !cimage.getContentType().startsWith("image")) {
                    errMsg = "Invalid contact image";
                    break pic;
                } else {
                    try {
                        InputStream is = cimage.getInputStream();
                        picture = new byte[is.available()];
                        is.read(picture);
                    } catch (Exception e) {
                        errMsg = "Failed to read the contact image";
                    }
                }
            }
        }

        if (errMsg != null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, errMsg);
            return;
        }

        try (Connection connection = dataSource.getConnection()) {
            ContactService contactService = new ContactService(connection);

            ContactDTO contact = new ContactDTO(fname, lname, phone, email, address, picture);

            String contactId = contactService.saveContact(contact);
            System.out.println(contactId);
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.println(contactId);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }


    }
}
