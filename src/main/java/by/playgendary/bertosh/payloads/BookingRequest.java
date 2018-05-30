package by.playgendary.bertosh.payloads;

import java.sql.Date;
import java.sql.Time;

public class BookingRequest {

    private Long roomId;
    private Date bookingDate;
    private Time startTime;
    private Time endTime;
    private Long userId;

    public BookingRequest() {

    }

    public BookingRequest(Long roomId, Date bookingDate, Time startTime, Time endTime, Long userId) {
        this.roomId = roomId;
        this.bookingDate = bookingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userId = userId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
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
