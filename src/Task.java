import java.time.LocalDate;
import java.util.ArrayList;

/**
 * A class represent a task of project
 *
 *   Created by Jednipit Tantaletong (Pleum) 60070503411
 *              Thaweesak Saiwongse (Note) 60070503429
 *              22/04/2020
 */
public class Task {
    /** task name */
    private String taskName;
    /** task description */
    private String taskDescription;
    /** start date of the task */
    private LocalDate startDate;
    /** end date of the task */
    private LocalDate endDate;
    /** duration to finish this task */
    private int duration;
    /** List of dependency that this task has */
    private ArrayList<Dependency> dependencyList;

    /**
     * Constructor for creating a task
     * @param taskName task name
     * @param taskDescription task description
     * @param duration duration to finish this task
     */
    public Task(String taskName, String taskDescription,int duration)
    {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.duration = duration;
        this.dependencyList = new ArrayList<Dependency>();
    }

    /**
     * Get task name
     * @return task name
     */
    public String getTaskName()
    {
        return taskName;
    }

    /**
     * Set task name
     * @param taskName task name
     */
    public void setTaskName(String taskName)
    {
        this.taskName = taskName;
    }

    /**
     * Get task description
     * @return task description
     */
    public String getTaskDescription()
    {
        return taskDescription;
    }

    /**
     * Set task description
     * @param taskDescription task description
     */
    public void setTaskDescription(String taskDescription)
    {
        this.taskDescription = taskDescription;
    }

    /**
     * Get start date of the task
     * @return start date of the task
     */
    public LocalDate getStartDate()
    {
        return startDate;
    }

    /**
     * Set start date of the task
     * @param startDate start date of the task
     */
    public void setStartDate(LocalDate startDate)
    {
        this.startDate = startDate;
    }

    /**
     * Get end date of the task
     * @return end date of the task
     */
    public LocalDate getEndDate()
    {
        return endDate;
    }

    /**
     * Set end date of the task
     * @param endDate end date of the task
     */
    public void setEndDate(LocalDate endDate)
    {
        this.endDate = endDate;
    }

    /**
     * Get duration to finish the task
     * @return duration to finish the task
     */
    public int getDuration()
    {
        return duration;
    }

    /**
     * Set duration to finish the task
     * @param duration duration to finish the task
     */
    public void setDuration(int duration)
    {
        this.duration = duration;
    }


    /**
     * Get dependency list of this task
     * @return dependency list of this task
     */
    public ArrayList<Dependency> getDependencyList()
    {
        return dependencyList;
    }

    /**
     * Add new dependency to the dependency list
     * @param preDecessorTask predecessor task which is this task
     * @param successorTask successor task
     */
    public void addDependency(Task preDecessorTask, Task successorTask)
    {
        dependencyList.add(new Dependency(preDecessorTask,successorTask));
    }

    /**
     * Remove the dependency from the dependency list
     * @param successorTask successor task for find the dependency to remove
     */
    public void removeDependency(Task successorTask)
    {
        dependencyList.removeIf(dependency -> dependency.getSuccessorTask() == successorTask);
    }

}
