package by.playgendary.bertosh.payloads;

public class RoomRequest {

    private Integer roomNumber;
    private Long companyId;

    public RoomRequest() {

    }

    public RoomRequest(Integer roomNumber, Long companyId) {
        this.roomNumber = roomNumber;
        this.companyId = companyId;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
