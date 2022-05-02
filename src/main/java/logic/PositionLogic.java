package logic;

import model.Employee;
import model.Position;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Currency;
import java.util.List;

public class PositionLogic {
    private Session session;

    public PositionLogic(Session session){
        this.session=session;
    }

    public Position getPosition(int id){
        return session.get(Position.class, id);
    }

    public List<Position> readPosition(){
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Position> query = builder.createQuery(Position.class);
        Root<Position> root = query.from(Position.class);

        query.select(root);

        var list = session.createQuery(query).getResultList();
        return list;
    }
    public void createPosition(String name, float salary){
        Transaction transaction = session.beginTransaction();

        Position position = new Position();
        position.setName(name);
        position.setSalary(salary);
        position.setNumberOfEmployees(0);
        session.save(position);

        transaction.commit();
    }

    public void deletePosition(int id){
        Transaction transaction = session.beginTransaction();
        EmployeeLogic employeeLogic = new EmployeeLogic(session);
        var employees = employeeLogic.readEmployees();
        employees.stream().filter(e->e.getPosition().equals(getPosition(id))).forEach(Employee::nullificationPosition);
        session.delete(getPosition(id));
        transaction.commit();
    }

    public void updatePosition(int id, String name, float salary){
        Transaction transaction = session.beginTransaction();

        Position position = getPosition(id);
        position.setName(name);
        position.setSalary(salary);
        session.update(position);

        transaction.commit();
    }
}
