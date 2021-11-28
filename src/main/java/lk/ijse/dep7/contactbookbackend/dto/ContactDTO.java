package lk.ijse.dep7.contactbookbackend.dto;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.json.bind.annotation.JsonbTransient;

import java.io.Serializable;
import java.util.Base64;

@JsonbPropertyOrder({"id", "fName", "lName", "phone", "email", "address"})
public class ContactDTO implements Serializable {
    private String id;
    private String fName;
    private String lName;
    private String phone;
    private String email;
    private String address;
    @JsonbTransient
    private byte[] picture;

    public ContactDTO() {
    }

    public ContactDTO(String fName, String lName, String phone, String email, String address, byte[] picture) {
        this.fName = fName;
        this.lName = lName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.picture = picture;
    }

    public ContactDTO(String id, String fName, String lName, String phone, String email, String address, byte[] picture) {
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

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    @JsonbProperty("picture")
    public String getPictureAsDataURl() {
        if (picture != null) {

            String base64EncorderedPicture = Base64.getEncoder().encodeToString(picture);
            StringBuilder sb = new StringBuilder();
            sb.append("data:");
            sb.append("image/*");
            sb.append(";base64,");
            sb.append(base64EncorderedPicture);
            return sb.toString();
        }
        return null;
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
