import java.time.LocalDate;
import java.util.Date;

public class Project {
    private TaskManager taskManager;
    private LocalDate startDate;
    private LocalDate endDate;
    private String projectName;
    private String projectDesc;

    public Project(String projectName, String projectDesc, LocalDate startDate)
    {
        System.out.println(projectName +" is successfully created");
        this.projectName = projectName;
        this.projectDesc = projectDesc;
        this.startDate = startDate;
        this.taskManager = new TaskManager();
    }

    public TaskManager getTaskManager() {
        return taskManager;
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
    public LocalDate getStartDate(){
        return this.startDate;
    }
    public void setStartDate(LocalDate startDate){
        this.startDate = startDate;
    }
    public LocalDate getEndDate(){
        return this.endDate;
    }
    public void setEndDate(LocalDate endDate){
        this.endDate = endDate;
    }

    public void scheduleReport()
    {
        this.showProjectInformation();
        this.taskManager.showAllTaskInformation();
        this.taskManager.showTaskInformation(taskManager.getStartMilestone());
        this.taskManager.showTaskInformation(taskManager.getEndMilestone());
    }
    public Boolean save(){
        
        return true;
    }


    public void showProjectInformation(){
        System.out.println("--------------------------------");
        System.out.println("Project name: "+projectName);
        System.out.println("Project description: "+projectDesc);
        System.out.println("Start date: "+ DateFormatter.formatDateToStringForDisplay(startDate));
        if (endDate!=null)
        {
            System.out.println("End date: "+ DateFormatter.formatDateToStringForDisplay(endDate));
        }
        else
        {
            System.out.println("End date: -");
        }
        System.out.println("--------------------------------");
    }


}
