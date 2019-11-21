package com.apms_user;

public class SlotFetchHelper {

    private String isFilled;
    private int slotNumber;
    private String userInSlot;


    public SlotFetchHelper()
    {

    }

    public SlotFetchHelper(String isFilled, int slotNumber,String userInSlot)
    {
        this.isFilled = isFilled;
        this.slotNumber = slotNumber;
        this.userInSlot = userInSlot;
    }


    public String getIsFilled() {
        return isFilled;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public String getUserInSlot() {
        return userInSlot;
    }

    public void setIsFilled(String isFilled) {
        this.isFilled = isFilled;
    }

    public void setSlotNumber(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    public void setUserInSlot(String userInSlot) {
        this.userInSlot = userInSlot;
    }

}
