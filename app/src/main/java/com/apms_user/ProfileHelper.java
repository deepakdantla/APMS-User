package com.apms_user;

public class ProfileHelper {

    private String addressUser;
    private String emailAddress;
    private String idNumber;
    private String slotNumber;
    private String username;


    public ProfileHelper()
    {

    }

    public ProfileHelper(String addressUser, String emailAddress, String idNumber, String slotNumber, String username)
    {
        this.addressUser = addressUser;
        this.emailAddress = emailAddress;
        this.idNumber = idNumber;
        this.slotNumber = slotNumber;
        this.username = username;
    }

    public String getAddressUser() {
        return addressUser;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getSlotNumber() {
        return slotNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setAddressUser(String addressUser) {
        this.addressUser = addressUser;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public void setSlotNumber(String slotNumber) {
        this.slotNumber = slotNumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
