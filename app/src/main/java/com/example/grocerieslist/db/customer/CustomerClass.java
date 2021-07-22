package com.example.grocerieslist.db.customer;

/**
 * Created by Tejaswi on 01/07/21.
 */
public class CustomerClass {
    String id;
    String name;
    String address;
    String address1;
    String address2;
    String place;
    String state;
    String scode;
    String pincode;
    String phone1;
    String phone2;
    String email;
    String gst;
    String type;
    String status;

    public CustomerClass() {
    }

    public CustomerClass(String name, String address, String address1, String address2, String place, String state, String scode, String pincode, String phone1, String phone2, String email, String gst, String type, String status) {
        this.name = name;
        this.address = address;
        this.address1 = address1;
        this.address2 = address2;
        this.place = place;
        this.state = state;
        this.scode = scode;
        this.pincode = pincode;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.email = email;
        this.gst = gst;
        this.type = type;
        this.status = status;
    }

    public CustomerClass(String id, String name, String address, String address1, String address2, String place, String state, String scode, String pincode, String phone1, String phone2, String email, String gst, String type, String status) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.address1 = address1;
        this.address2 = address2;
        this.place = place;
        this.state = state;
        this.scode = scode;
        this.pincode = pincode;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.email = email;
        this.gst = gst;
        this.type = type;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CustomerClass{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", place='" + place + '\'' +
                ", state='" + state + '\'' +
                ", scode='" + scode + '\'' +
                ", pincode='" + pincode + '\'' +
                ", phone1='" + phone1 + '\'' +
                ", phone2='" + phone2 + '\'' +
                ", email='" + email + '\'' +
                ", gst='" + gst + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
