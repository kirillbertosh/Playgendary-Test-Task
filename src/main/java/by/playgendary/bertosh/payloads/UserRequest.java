package by.playgendary.bertosh.payloads;

public class UserRequest {

    private String firstName;
    private String lastName;
    private String email;
    private Long companyId;

    public UserRequest() {

    }

    public UserRequest(String firstName, String lastName, String email, Long companyId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.companyId = companyId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
