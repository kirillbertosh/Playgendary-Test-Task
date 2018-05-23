package by.playgendary.bertosh.payloads;

import java.sql.Date;
import java.sql.Time;

public class BookingRequest {

    private Integer roomNumber;
    private Date bookingDate;
    private Time startTime;
    private Time endTime;
    private Long userId;

    public BookingRequest() {

    }

    public BookingRequest(Integer roomNumber, Date bookingDate, Time startTime, Time endTime, Long userId) {
        this.roomNumber = roomNumber;
        this.bookingDate = bookingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userId = userId;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
