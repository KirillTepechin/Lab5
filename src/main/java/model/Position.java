package model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Currency;

@Entity
@Table(name = "position",indexes = { @Index(name = "IDX_MYIDX_POSITION", columnList = "id, salary,number_of_employeers") })
public class Position {
    @Id
    @SequenceGenerator(name="identifier", sequenceName="seq_position", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="identifier")
    private int id;

    private String name;

    private float salary;

    @Column(name = "number_of_employeers")
    private int numberOfEmployees;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(int numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    @Override
    public String toString() {
        return "Название: "+name+"; Зарплата: "+salary+"; Кол-во работников: "+numberOfEmployees+" || id="+id;
    }
}
