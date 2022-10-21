package data.model;

public class EmployeeModel {

    private String employeeId;
    private String name;
    private String address;
    private String dateOfBirth;
    private String phone;

    public EmployeeModel(String employeeId, String name, String address, String dateOfBirth, String phone) {
        this.employeeId = employeeId;
        this.name = name;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
