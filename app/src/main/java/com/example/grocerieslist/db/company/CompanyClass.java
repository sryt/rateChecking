package com.example.grocerieslist.db.company;

/**
 * Created by Tejaswi on 30/06/21.
 */
public class CompanyClass {
    String id;
    String companyName;
    String companyOwner;
    String companyDesc;
    String address;
    String place;
    String pinCode;
    String phoneNumber;
    String emailId;
    String gstNo;
    String accNumber;
    String bankName;
    String ifscCode;
    String bankBranch;
    String status;
    String flag;

    public CompanyClass() {
    }

    public CompanyClass(String companyName, String companyOwner, String companyDesc, String address, String place, String pinCode, String phoneNumber, String emailId, String gstNo, String accNumber, String bankName, String ifscCode, String bankBranch, String status, String flag) {
        this.companyName = companyName;
        this.companyOwner = companyOwner;
        this.companyDesc = companyDesc;
        this.address = address;
        this.place = place;
        this.pinCode = pinCode;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
        this.gstNo = gstNo;
        this.accNumber = accNumber;
        this.bankName = bankName;
        this.ifscCode = ifscCode;
        this.bankBranch = bankBranch;
        this.status = status;
        this.flag = flag;
    }

    public CompanyClass(String id, String companyName, String companyOwner, String companyDesc, String address, String place, String pinCode, String phoneNumber, String emailId, String gstNo, String accNumber, String bankName, String ifscCode, String bankBranch, String status, String flag) {
        this.id = id;
        this.companyName = companyName;
        this.companyOwner = companyOwner;
        this.companyDesc = companyDesc;
        this.address = address;
        this.place = place;
        this.pinCode = pinCode;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
        this.gstNo = gstNo;
        this.accNumber = accNumber;
        this.bankName = bankName;
        this.ifscCode = ifscCode;
        this.bankBranch = bankBranch;
        this.status = status;
        this.flag = flag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyOwner() {
        return companyOwner;
    }

    public void setCompanyOwner(String companyOwner) {
        this.companyOwner = companyOwner;
    }

    public String getCompanyDesc() {
        return companyDesc;
    }

    public void setCompanyDesc(String companyDesc) {
        this.companyDesc = companyDesc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getGstNo() {
        return gstNo;
    }

    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "CompanyClass{" +
                "id='" + id + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyOwner='" + companyOwner + '\'' +
                ", companyDesc='" + companyDesc + '\'' +
                ", address='" + address + '\'' +
                ", place='" + place + '\'' +
                ", pinCode='" + pinCode + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", emailId='" + emailId + '\'' +
                ", gstNo='" + gstNo + '\'' +
                ", accNumber='" + accNumber + '\'' +
                ", bankName='" + bankName + '\'' +
                ", ifscCode='" + ifscCode + '\'' +
                ", bankBranch='" + bankBranch + '\'' +
                ", status='" + status + '\'' +
                ", flag='" + flag + '\'' +
                '}';
    }
}
