package queryDTO;


public class HandBookDTO {

    private String surname;
    private int salary;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Фамилия: "+surname+"; Зарплата за месяц: "+salary;
    }
}
