package model;

import javax.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @SequenceGenerator(name="identifier", sequenceName="seq_employee", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="identifier")
    private Integer id;

    private String surname;

    private String name;

    private String patronymic;

    @Column(name = "individual_surcharges")
    private float individualSurcharges;

    @ManyToOne(cascade = CascadeType.ALL)
    private Position position;

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public float getIndividualSurcharges() {
        return individualSurcharges;
    }

    public void setIndividualSurcharges(float individualSurcharges) {
        this.individualSurcharges = individualSurcharges;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getFIO(){
        return surname+" "+name+" "+patronymic;
    }

    @Override
    public String toString() {
        return "ФИО: "+getFIO()+"; Инд.доп: "+individualSurcharges+"; Должность: "+position.getName()+" || id="+id;
    }
}
