package logic;

import model.Department;
import model.Position;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class DepartmentLogic {
    private Session session;

    public DepartmentLogic(Session session){
        this.session=session;
    }

    public Department getDepartment(int id){
        return session.get(Department.class, id);
    }

    public List<Department> readDepartment(){
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Department> query = builder.createQuery(Department.class);
        Root<Department> root = query.from(Department.class);

        query.select(root);

        return session.createQuery(query).getResultList();
    }
    public void createDepartment(String name, int numberOfEmployees, String phoneNumber){
        Transaction transaction = session.beginTransaction();

        Department department = new Department();
        department.setName(name);
        department.setNumberOfEmployees(numberOfEmployees);
        department.setPhoneNumber(phoneNumber);
        session.save(department);

        transaction.commit();
    }

    public void deleteDepartment(int id){
        Transaction transaction = session.beginTransaction();

        session.delete(getDepartment(id));

        transaction.commit();
    }

    public void updateDepartment(int id, String name, int numberOfEmployees, String phoneNumber){
        Transaction transaction = session.beginTransaction();

        Department department = getDepartment(id);
        department.setName(name);
        department.setNumberOfEmployees(numberOfEmployees);
        department.setPhoneNumber(phoneNumber);
        session.update(department);

        transaction.commit();
    }
}
