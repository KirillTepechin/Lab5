package logic;

import model.Employee;
import model.Position;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public class EmployeeLogic {
    private Session session;

    public EmployeeLogic(Session session){
        this.session=session;
    }

    public Employee getEmployee(int id){
        return session.get(Employee.class, id);
    }

    public List<Employee> readEmployees(){
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
        Root<Employee> root = query.from(Employee.class);

        query.select(root);
        var list = session.createQuery(query).getResultList();
        return list;
    }
    public void createEmployee(String surname, String name, String patronymic, float individualSurcharges){
        Transaction transaction = session.beginTransaction();

        Employee employee = new Employee();
        employee.setSurname(surname);
        employee.setName(name);
        employee.setPatronymic(patronymic);
        employee.setIndividualSurcharges(individualSurcharges);
        session.save(employee);

        transaction.commit();
    }

    public void deleteEmployee(int id){
        Transaction transaction = session.beginTransaction();

        Position position = session.get(Position.class, getEmployee(id).getPosition().getId());
        position.setNumberOfEmployees(position.getNumberOfEmployees() - 1);
        session.update(position);
        session.delete(getEmployee(id));

        transaction.commit();
    }

    public void updateEmployee(int id, String surname, String name, String patronymic, float individualSurcharges){
        Transaction transaction = session.beginTransaction();

        Employee employee = getEmployee(id);
        employee.setSurname(surname);
        employee.setName(name);
        employee.setPatronymic(patronymic);
        employee.setIndividualSurcharges(individualSurcharges);
        session.update(employee);

        transaction.commit();
    }
    public void addPosition(int employeeId, int positionId){
        Transaction transaction = session.beginTransaction();

        Employee employee = getEmployee(employeeId);
        if(employee.getPosition()!=null){
            Position positionDel = session.get(Position.class, employee.getPosition().getId());
            positionDel.setNumberOfEmployees(positionDel.getNumberOfEmployees()-1);
            session.update(positionDel);
        }
        Position position = session.get(Position.class, positionId);
        position.setNumberOfEmployees(position.getNumberOfEmployees()+1);
        employee.setPosition(position);
        session.update(employee);
        session.update(position);

        transaction.commit();
    }
}
