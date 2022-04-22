package model;

import javax.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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

    public void setId(int id) {
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

}
