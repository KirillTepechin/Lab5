package queryDTO;

import java.sql.Date;

public class PeriodDTO {
    private String surname;
    private Date appointmentDate;
    private Date dismissalDate;
    private String department;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Date getDismissalDate() {
        return dismissalDate;
    }

    public void setDismissalDate(Date dismissalDate) {
        this.dismissalDate = dismissalDate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Фамилия: "+surname+"; Дата наз.: "+appointmentDate +"; Дата увол.: "+dismissalDate+ "; Отдел: "+department;
    }
}
