import java.time.LocalDate;
import java.util.ArrayList;

public class Task {
    private String taskName;
    private String taskDescription;
    private LocalDate startDate;
    private LocalDate endDate;
    private int duration;
    private ArrayList<Dependency> dependencyList;

    public Task(String taskName, String taskDescription,int duration)
    {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.duration = duration;
        this.dependencyList = new ArrayList<Dependency>();
    }

    public String getTaskName()
    {
        return taskName;
    }

    public void setTaskName(String taskName)
    {
        this.taskName = taskName;
    }

    public String getTaskDescription()
    {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription)
    {
        this.taskDescription = taskDescription;
    }

    public LocalDate getStartDate()
    {
        return startDate;
    }

    public void setStartDate(LocalDate startDate)
    {
        this.startDate = startDate;
    }

    public LocalDate getEndDate()
    {
        return endDate;
    }

    public void setEndDate(LocalDate endDate)
    {
        this.endDate = endDate;
    }

    public int getDuration()
    {
        return duration;
    }

    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    public ArrayList<Dependency> getDependencyList()
    {
        return dependencyList;
    }

    public boolean addDependency(Task preDecessorTask, Task successorTask)
    {
        dependencyList.add(new Dependency(preDecessorTask,successorTask));
        return true;
    }

    public boolean removeDependency(Task successorTask)
    {
        dependencyList.removeIf(dependency -> dependency.getSuccessorTask() == successorTask);
        return true;
    }

    @Override
    public String toString()
    {
        return "Task{" +
                "taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", duration=" + duration +
                ", dependencyList=" + dependencyList +
                '}';
    }
}
