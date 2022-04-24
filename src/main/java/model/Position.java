package model;

import javax.persistence.*;

@Entity
@Table(name = "position")
public class Position {
    @Id
    @SequenceGenerator(name="identifier", sequenceName="seq_position", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="identifier")
    private int id;

    private String name;

    private int salary;

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

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
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
