package com.microsoft.safetravel.SmsParser;

public class ParsedSms {
    private String serviceName;
    private String bookingId;
    private String bookingTime;
    private String driverName;
    private String driverMobile;
    private String vehicleModel;
    private String vehicleNumber;

    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getBookingId() {
        return bookingId;
    }
    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingTime() {
        return bookingTime;
    }
    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getDriverName() {
        return driverName;
    }
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverMobile() {
        return driverMobile;
    }
    public void setDriverMobile(String driverMobile) {
        this.driverMobile = driverMobile;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }
    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }

    public boolean isValid() {
        return !StringUtil.isNullOrEmpty(serviceName)
                && !StringUtil.isNullOrEmpty(bookingId)
                && !StringUtil.isNullOrEmpty(bookingTime)
                && !StringUtil.isNullOrEmpty(driverName)
                && !StringUtil.isNullOrEmpty(driverMobile)
                && !StringUtil.isNullOrEmpty(vehicleModel)
                && !StringUtil.isNullOrEmpty(vehicleNumber);
    }
}
