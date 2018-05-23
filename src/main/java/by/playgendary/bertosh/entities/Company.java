package by.playgendary.bertosh.entities;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue
    private long id;
    @Column
    private String companyName;
    @Column
    private Time openTime;
    @Column
    private Time closeTime;
    /*@OneToMany
    @JoinTable(name = "company_room", joinColumns
            = @JoinColumn(name = "company_id",
            referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "room_id",
                    referencedColumnName = "id"))
    private Set<Room> rooms;
    */

    public Company() {

    }

    public long getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Time getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Time openTime) {
        this.openTime = openTime;
    }

    public Time getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Time closeTime) {
        this.closeTime = closeTime;
    }

    /*public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }
    */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (id != company.id) return false;
        if (companyName != null ? !companyName.equals(company.companyName) : company.companyName != null) return false;
        if (openTime != null ? !openTime.equals(company.openTime) : company.openTime != null) return false;
        return closeTime != null ? closeTime.equals(company.closeTime) : company.closeTime == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, companyName, openTime, closeTime);
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", openTime=" + openTime +
                ", closeTime=" + closeTime +
                '}';
    }
}
