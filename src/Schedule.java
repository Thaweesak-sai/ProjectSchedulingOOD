import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * a class for scheduling the project date and check cycle of the project tasks
 *
 *   Created by Jednipit Tantaletong (Pleum) 60070503411
 *              Thaweesak Saiwongse (Note) 60070503429
 *              29/04/2020
 */
public class Schedule {

    /**
     * Assign the start and end date to all tasks of the projects according to the dependencies
     * @param project Project to schedule
     */
    public static void assignDate(Project project)
    {
        TaskManager taskManager = project.getTaskManager();
        taskManager.resetDate(); //reset all start and end date of all tasks
        taskManager.getStartMilestone().setStartDate(project.getStartDate());
        for(Dependency dependency : taskManager.getStartMilestone().getDependencyList()) // get start milestone and start iterate through network
        {
            iterateAssignDate(dependency.getSuccessorTask(),taskManager.getStartMilestone()); // iterate
        }
        project.setEndDate(taskManager.getEndMilestone().getEndDate());
    }

    /**
     * Iterate through all tasks in the project by using the dependencies of each task as a guide
     * Recursive method
     * @param task current task
     * @param preDecessorTask predecessor of the current task
     */
    public static void iterateAssignDate(Task task, Task preDecessorTask)
    {
        List<Dependency> dependencies = task.getDependencyList();
        for(Dependency dependency : dependencies)
        {
            if(preDecessorTask instanceof Milestone)
            {
               task.setStartDate(preDecessorTask.getStartDate());
            }
            else
            {
                LocalDate newStartDate = addDaysSkippingWeekends(preDecessorTask.getEndDate(),1); // find the start date of the task
                if(task.getStartDate() != null)
                {
                    LocalDate latestDate = getLatestDate(task.getStartDate(),newStartDate);
                    task.setStartDate(latestDate);
                }
                else
                {
                    task.setStartDate(newStartDate);
                }
            }
            task.setEndDate(addDaysSkippingWeekends(task.getStartDate(),task.getDuration() - 1 )); // find end date
            if(dependency.getSuccessorTask() != null) // if task have successor task, continue to iterate
            {
                iterateAssignDate(dependency.getSuccessorTask(),task);
            }
        }
        if(task instanceof Milestone) // set end milestone end date
        {
            if(task.getEndDate() != null)
            {
                task.setEndDate(getLatestDate(task.getEndDate(),preDecessorTask.getEndDate()));
            }
            else
            {
                task.setEndDate(preDecessorTask.getEndDate());
            }

        }
    }


    /**
     * Compare two dates and return the latest one
     * @param firstDate date to compare
     * @param secondDate another date to compare
     * @return the latest date
     */
    private static LocalDate getLatestDate(LocalDate firstDate, LocalDate secondDate)
    {
        int result = firstDate.compareTo(secondDate);
        if(result >= 0)
        {
            return firstDate;
        }
        else
        {
            return secondDate;
        }
    }


    /**
     * Calculate the ending date of the task by skipping the weekends
     * @param date starting date of the task
     * @param duration duration to finish the task
     * @return ending date which is date after add the duration
     */
    public static LocalDate addDaysSkippingWeekends(LocalDate date, int duration) {
        LocalDate result = date;
        int addedDays = 0;
        while (addedDays < duration) {
            result = result.plusDays(1);
            if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY || result.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                addedDays++;
            }
        }
        return result;
    }

    /**
     * Validate that the dependency that is going to add will create cycle in the project or not
     * @param taskManager task manager of the project
     * @param preDecessorTask predecessor task
     * @param successorTask successor task
     * @return true if it has cycle otherwise false
     */
    public static boolean hasCycle(TaskManager taskManager,Task preDecessorTask, Task successorTask)
    {
        List<Task> alreadyVisitedTask = new ArrayList<Task>();
        preDecessorTask.addDependency(preDecessorTask,successorTask); // try to add dependency
        preDecessorTask.removeDependency(taskManager.getEndMilestone()); // remove dependency from end milestone
        for(Dependency dependency : taskManager.getStartMilestone().getDependencyList())
        {
            if(iterateCycle(dependency.getSuccessorTask(),alreadyVisitedTask)) // iterate through all tasks
            {
                preDecessorTask.removeDependency(successorTask);
                preDecessorTask.addDependency(preDecessorTask,taskManager.getEndMilestone());
                taskManager.removeDependency(preDecessorTask,successorTask);
                System.out.println("Cycle Detected");
                return true;
            }
            System.out.println();
            alreadyVisitedTask.clear();
        }
        preDecessorTask.removeDependency(successorTask);
        preDecessorTask.addDependency(preDecessorTask,taskManager.getEndMilestone());
        return false;
    }

    /**
     * Iterate through all project tasks to detect cycle
     * Recursive method
     * @param task current task of iteration
     * @param alreadyVisitedTask list of tasks that is already visited
     * @return true if it found that the task has already visited otherwise false
     */
    public static boolean iterateCycle(Task task,List<Task> alreadyVisitedTask)
    {
        List<Dependency> dependencies = task.getDependencyList();
        if(alreadyVisitedTask.contains(task)) // check if we already visited this task or not
        {
            return true;
        }
        else
        {
            alreadyVisitedTask.add(task);
            for(Dependency dependency : dependencies)
            {
                if(dependency.getSuccessorTask() != null )
                {
                    if(dependency.getSuccessorTask() instanceof Milestone) // clear visited task list if we reach the end milestone
                    {
                        alreadyVisitedTask.clear();
                    }

                    if(iterateCycle(dependency.getSuccessorTask(),alreadyVisitedTask)) // continue to iterate
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
