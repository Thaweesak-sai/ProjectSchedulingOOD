import java.util.Date;

public class Task {
    private String taskName;
    private String taskDescription;
    private Date startDate;
    private Date endDate;
    private int duration;
    private Dependency dependency;

    public Task(String taskName, String taskDescription,int duration) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.duration = duration;
        this.dependency = new Dependency();
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Dependency getDependency() {
        return dependency;
    }

    public void setDependency(Dependency dependency) {
        this.dependency = dependency;
    }

    public void showTaskInformation(){
        System.out.println("Task Name: " + this.taskName);
        System.out.println("Description: " + this.taskDescription);
        System.out.println("Duration: " + this.duration);
        if(startDate != null){
            System.out.println("Start Date: " + this.startDate.toString());
        }
        else {
            System.out.println("Start Date: -");
        }
        if(endDate != null){
            System.out.println("End Date: " + this.endDate.toString());
        }
        else {
            System.out.println("End Date: -");
        }
        this.dependency.printAllPredecessorTask();
        this.dependency.printAllSuccessorTask();

    }

    @Override
    public String toString() {
        return "Task{" +
                "taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", duration=" + duration +
                ", dependency=" + dependency +
                '}';
    }
}
