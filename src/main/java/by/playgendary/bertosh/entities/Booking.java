package by.playgendary.bertosh.entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue
    private long id;
    @OneToOne
    private Room room;
    @Column
    private Date bookingDate;
    @Column
    private Time startTime;
    @Column
    private Time endTime;
    @OneToOne
    private User user;

    public Booking() {

    }

    public long getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Booking booking = (Booking) o;

        if (id != booking.id) return false;
        if (room != null ? !room.equals(booking.room) : booking.room != null) return false;
        if (bookingDate != null ? !bookingDate.equals(booking.bookingDate) : booking.bookingDate != null) return false;
        if (startTime != null ? !startTime.equals(booking.startTime) : booking.startTime != null) return false;
        if (endTime != null ? !endTime.equals(booking.endTime) : booking.endTime != null) return false;
        return user != null ? user.equals(booking.user) : booking.user == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, room, bookingDate,startTime, endTime, user);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", room=" + room +
                ", bookingDate=" + bookingDate +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", user=" + user +
                '}';
    }
}
