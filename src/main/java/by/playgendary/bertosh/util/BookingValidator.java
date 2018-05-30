package by.playgendary.bertosh.util;

import by.playgendary.bertosh.entities.Booking;
import by.playgendary.bertosh.entities.Company;
import by.playgendary.bertosh.exceptions.DatabaseException;
import by.playgendary.bertosh.exceptions.EntityNotFoundException;
import by.playgendary.bertosh.exceptions.IllegalArgumentsException;
import by.playgendary.bertosh.repositories.implementations.BookingDao;
import by.playgendary.bertosh.repositories.implementations.CompanyDao;
import by.playgendary.bertosh.repositories.implementations.RoomDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Component
public class BookingValidator {

    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private BookingDao bookingDao;
    @Autowired
    private RoomDao roomDao;

    public BookingValidator() {

    }

    public boolean isRoomBooked(Long roomId, Date bookingDate, Time startTime, Time endTime)
            throws DatabaseException, EntityNotFoundException, IllegalArgumentsException {
        if (!isTimeIntervalValid(startTime, endTime)) {
            return false;
        }
        if (!isTimeIntervalBelongsToCompanyWorkTime(roomId, startTime, endTime)) {
            return false;
        }
        List<Booking> bookingList = bookingDao.findByDate(bookingDate);
        if (bookingList == null) {
            return true;
        }
        bookingList.removeIf(b -> b.getRoom().getId() != roomId);
        if (bookingList.size() == 0) {
            return true;
        }
        bookingList.removeIf(
                b -> !(startTime.compareTo(b.getStartTime()) >= 0  && startTime.compareTo(b.getEndTime()) <= 0));
        bookingList.removeIf(
                b -> !(endTime.compareTo(b.getEndTime()) >= 0 && endTime.compareTo(b.getEndTime()) <= 0));
        if (bookingList.size() == 0) {
            return true;
        } else {
            throw new IllegalArgumentsException("Room for this time and date booked");
        }
    }

    private boolean isTimeIntervalValid(Time startTime, Time endTime) throws IllegalArgumentsException {
        if (startTime.compareTo(endTime) > 0) {
            throw new IllegalArgumentsException("The start time should be shorter than the end time");
        } else {
            return true;
        }
    }

    private boolean isTimeIntervalBelongsToCompanyWorkTime(Long roomId, Time startTime, Time endTime)
            throws DatabaseException, EntityNotFoundException, IllegalArgumentsException {
        Company company = companyDao.findById(roomDao.findById(roomId).getCompany().getId());
        if (startTime.compareTo(company.getOpenTime()) < 0) {
            throw new IllegalArgumentsException("Start time should be greater than company open time");
        }
        if (startTime.compareTo(company.getCloseTime()) > 0) {
            throw new IllegalArgumentsException("Start time should be shorter than company close time");
        }
        if (endTime.compareTo(company.getCloseTime()) > 0) {
            throw new IllegalArgumentsException("End time should me shorter than company close time");
        }
        if (endTime.compareTo(company.getOpenTime()) < 0) {
            throw new IllegalArgumentsException("End time should me greater than company open time");
        }
        return true;
    }
}
