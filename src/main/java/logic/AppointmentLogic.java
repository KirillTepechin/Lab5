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

        var list = session.createQuery(query).getResultList();
        return list;
    }
    public void createAppointment(int departmentId, int employeeId, Date appointmentDate, Date dismissalDate){
        Transaction transaction = session.beginTransaction();

        Appointment appointment = new Appointment();

        Department department = session.get(Department.class, departmentId);
        department.setNumberOfEmployees(department.getNumberOfEmployees()+1);

        appointment.setDepartment(department);
        appointment.setEmployee(session.get(Employee.class, employeeId));
        appointment.setAppointmentDate(appointmentDate);
        appointment.setDismissalDate(dismissalDate);

        session.save(appointment);
        session.update(department);
        transaction.commit();
    }

    public void deleteAppointment(int id){
        Transaction transaction = session.beginTransaction();

        Department department = session.get(Department.class, getAppointment(id).getDepartment().getId());
        department.setNumberOfEmployees(department.getNumberOfEmployees() - 1);
        session.delete(getAppointment(id));
        session.update(department);

        transaction.commit();
    }

    public void updateAppointment(int id, int departmentId, int employeeId, Date appointmentDate, Date dismissalDate){
        Transaction transaction = session.beginTransaction();

        Appointment appointment = getAppointment(id);
        Department departmentOld = appointment.getDepartment();
        departmentOld.setNumberOfEmployees(departmentOld.getNumberOfEmployees()-1);
        Department departmentNew = session.get(Department.class, departmentId);
        appointment.setDepartment(departmentNew);
        departmentNew.setNumberOfEmployees(departmentNew.getNumberOfEmployees()+1);
        appointment.setEmployee(session.get(Employee.class, employeeId));
        appointment.setAppointmentDate(appointmentDate);
        appointment.setDismissalDate(dismissalDate);

        session.update(appointment);
        session.update(departmentOld);
        session.update(departmentNew);

        transaction.commit();
    }
}
