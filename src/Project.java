import java.util.Date;

public class Project {
    private TaskManager taskList;
    private Date startDate;
    private Date endDate;
    private String projectName;
    private String projectDesc;

    public Project(String projectName, String projectDesc, Date startDate)
    {
        System.out.println(projectName +"is successfully created");
    }
    public String getName(){
        return this.projectName;
    }
    public void setName(String name){
        this.projectName = name;
    }
    public String getDesc(){
        return this.projectDesc;
    }
    public void setDesc(String description){
        this.projectDesc = description;
    }
    public Date getStartDate(){
        return this.startDate;
    }
    public void setStartDate(Date startDate){
        this.startDate = startDate;
    }
    public Date getEndDate(){
        return this.endDate;
    }
    public void setEndDate(Date endDate){
        this.endDate = endDate;
    }
    public void scheduleReport(){
        System.out.println("Wait for motivation");
    }
    public Boolean save(){
        return true;
    }
    public void assignDate(){

    }
    public void calculateSchedule() {
        int a = 0;
        int b = 1;
        int c = a + b;
        System.out.println(c);
    }

}
