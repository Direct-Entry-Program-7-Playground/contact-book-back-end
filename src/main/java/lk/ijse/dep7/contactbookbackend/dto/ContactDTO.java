package lk.ijse.dep7.contactbookbackend.dto;

import java.io.Serializable;
import java.util.Arrays;

public class ContactDTO implements Serializable {
    private String id;
    private String fName;
    private String lName;
    private String phone;
    private String email;
    private String address;
    private String picture;

    public ContactDTO() {
    }

    public ContactDTO(String fName, String lName, String phone, String email, String address, String picture) {
        this.fName = fName;
        this.lName = lName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.picture = picture;
    }

    public ContactDTO(String id, String fName, String lName, String phone, String email, String address, String picture) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.picture = picture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "ContactDTO{" +
                "id='" + id + '\'' +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
