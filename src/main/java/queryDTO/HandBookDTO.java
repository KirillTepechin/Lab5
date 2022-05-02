package queryDTO;


public class HandBookDTO {

    private String surname;
    private float salary;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Фамилия: "+surname+"; Зарплата за месяц: "+salary;
    }
}
