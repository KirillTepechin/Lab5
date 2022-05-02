package model;

import javax.persistence.*;

@Entity
@Table(name = "department",indexes = { @Index(name = "IDX_MYIDX_EMPLOYEE", columnList = "id, name") })
public class Department {
    @Id
    @SequenceGenerator(name="identifier", sequenceName="seq_department", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="identifier")
    private int id;

    private String name;

    @Column(name = "number_of_employeers")
    private int numberOfEmployees;

    @Column(name = "phone_number")
    private String phoneNumber;

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

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(int numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Название: "+name+"; Номер тел.: "+phoneNumber+"; Кол-во работников: "+numberOfEmployees+" || id="+id;
    }
}
