import java.util.Date;

public class Project {
    private TaskManager taskList;
    private Date startDate;
    private Date endDate;
    private String projectName;
    private String projectDesc;

    public Project(String projectName, String projectDesc, Date startDate)
    {
        System.out.println(projectName +" is successfully created");
        this.projectName = projectName;
        this.projectDesc = projectDesc;
        this.startDate = startDate;
        this.taskList = new TaskManager();
        taskList.addTask(new Milestone("Start","Starting Task"));
        taskList.addTask(new Milestone("End","Ending Task"));
    }

    public TaskManager getTaskList() {
        return taskList;
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
    public void showProjectInformation(){
        System.out.println("Project name: "+projectName);
        System.out.println("Project description: "+projectDesc);
        System.out.println("Start date: "+startDate);
    }
    public void calculateSchedule() {
        int a = 0;
        int b = 1;
        int c = a + b;
        System.out.println(c);
    }

}
