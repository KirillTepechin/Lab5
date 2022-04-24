package model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @SequenceGenerator(name="identifier", sequenceName="seq_appointment", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="identifier")
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Department department;

    @ManyToOne(cascade = CascadeType.ALL)
    private Employee employee;

    @Column(name = "appointment_date")
    private Date appointmentDate;

    @Column(name = "dismissal_date")
    private Date dismissalDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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

    @Override
    public String toString() {
        return "Отдел: "+ department.getName()+"; Работник: "+ employee.getFIO() + "; Дата наз.: "+appointmentDate+"; Дата увол.: "+dismissalDate+" || id="+id;
    }
}
