package logic;

import model.Appointment;
import model.Department;
import model.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Date;
import java.util.List;

public class AppointmentLogic {
    private Session session;

    public AppointmentLogic(Session session){
        this.session=session;
    }

    public Appointment getAppointment(int id){
        return session.get(Appointment.class, id);
    }

    public List<Appointment> readAppointment(){
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Appointment> query = builder.createQuery(Appointment.class);
        Root<Appointment> root = query.from(Appointment.class);

        query.select(root);

        return session.createQuery(query).getResultList();
    }
    public void createAppointment(int departmentId, int employeeId, Date appointmentDate, Date dismissalDate){
        Transaction transaction = session.beginTransaction();

        Appointment appointment = new Appointment();
        appointment.setDepartment(session.get(Department.class, departmentId));
        appointment.setEmployee(session.get(Employee.class, employeeId));
        appointment.setAppointmentDate(appointmentDate);
        appointment.setDismissalDate(dismissalDate);

        session.save(appointment);

        transaction.commit();
    }

    public void deleteAppointment(int id){
        Transaction transaction = session.beginTransaction();

        session.delete(getAppointment(id));

        transaction.commit();
    }

    public void updateAppointment(int id, int departmentId, int employeeId, Date appointmentDate, Date dismissalDate){
        Transaction transaction = session.beginTransaction();

        Appointment appointment = new Appointment();
        appointment.setDepartment(session.get(Department.class, departmentId));
        appointment.setEmployee(session.get(Employee.class, employeeId));
        appointment.setAppointmentDate(appointmentDate);
        appointment.setDismissalDate(dismissalDate);

        transaction.commit();
    }
}
