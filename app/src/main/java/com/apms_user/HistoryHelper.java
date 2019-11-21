package com.apms_user;

public class HistoryHelper {

    private String parkStatus;
    private String slotNo;
    private String time;


    public HistoryHelper()
    {

    }

    public HistoryHelper(String parkStatus, String slotNo, String time)
    {
        this.parkStatus = parkStatus;
        this.slotNo = slotNo;
        this.time = time;
    }

    public String getSlotNo() {
        return slotNo;
    }

    public String getParkStatus() {
        return parkStatus;
    }

    public String getTime() {
        return time;
    }

    public void setSlotNo(String slotNo) {
        this.slotNo = slotNo;
    }

    public void setParkStatus(String parkStatus) {
        this.parkStatus = parkStatus;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
