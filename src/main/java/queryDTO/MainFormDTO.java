package queryDTO;

public class MainFormDTO {
    private String FIO;
    private String position;
    private String department;

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "ФИО: "+FIO+"; Должность: "+position+"; Отдел: "+ department;
    }
}
