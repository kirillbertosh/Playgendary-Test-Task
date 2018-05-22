package by.playgendary.bertosh.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue
    private long id;
    @Column
    private int roomNumber;
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    public Room() {

    }

    public long getId() {
        return id;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (id != room.id) return false;
        if (roomNumber != room.roomNumber) return false;
        return company != null ? company.equals(room.company) : room.company == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomNumber, company);
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomNumber=" + roomNumber +
                ", company=" + company +
                '}';
    }
}
