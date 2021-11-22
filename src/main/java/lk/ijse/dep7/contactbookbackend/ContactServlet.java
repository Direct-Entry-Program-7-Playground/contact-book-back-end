package lk.ijse.dep7.contactbookbackend;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@MultipartConfig(
        location = "/home/manoj/Documents/MRR/IJSE/DEP/Phase - 2/Day_89 (2021-10-11)/contact-book-back-end/src/main/uploaded/img/",
        maxFileSize = 1024*1024*50)
@WebServlet(name = "ContactServlet", value = "/contact")
public class ContactServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doGet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doPost");

        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        Part cimage = request.getPart("cimage");

        System.out.println(fname);
        System.out.println(lname);
        System.out.println(phone);
        System.out.println(email);
        System.out.println(address);
        System.out.println(cimage.getSubmittedFileName());
        cimage.write(cimage.getSubmittedFileName());
    }
}
