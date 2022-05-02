package model;

import com.sun.istack.Nullable;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "employee",indexes = { @Index(name = "IDX_MYIDX_EMPLOYEE", columnList = "id, surname") })
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
    private Position position = null;

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

    public void nullificationPosition(){
        this.position = null;
    }
    @Override
    public String toString() {
        if(position==null){
            return "ФИО: "+getFIO()+"; Инд.доп: "+individualSurcharges+"; Должность: "+" || id="+id;
        }
        return "ФИО: "+getFIO()+"; Инд.доп: "+individualSurcharges+"; Должность: "+position.getName()+" || id="+id;
    }
}
