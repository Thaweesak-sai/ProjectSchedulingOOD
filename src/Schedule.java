import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * a class for scheduling the project date and check cycle of the project tasks
 */
public class Schedule {

    /**
     * Assign the start and end date to all tasks of the projects according to the dependencies
     * @param project Project to schedule
     */
    public static void assignDate(Project project)
    {
        TaskManager taskManager = project.getTaskManager();
        taskManager.resetDate();
        taskManager.getStartMilestone().setStartDate(project.getStartDate());
        for(Dependency dependency : taskManager.getStartMilestone().getDependencyList())
        {
            System.out.print("START --> ");
            iterateAssignDate(dependency.getSuccessorTask(),taskManager.getStartMilestone());
            System.out.println("END");
            System.out.println();
        }
        project.setEndDate(taskManager.getEndMilestone().getEndDate());
    }

    /**
     * Iterate through all taks in the project by using the dependencies of each task as a guide
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
                LocalDate newStartDate = addDaysSkippingWeekends(preDecessorTask.getEndDate(),1);
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
            task.setEndDate(addDaysSkippingWeekends(task.getStartDate(),task.getDuration() - 1 ));
            System.out.print("Task " + task.getTaskName());
            if(dependency.getSuccessorTask() != null)
            {
                System.out.print(" --> ");
                iterateAssignDate(dependency.getSuccessorTask(),task);
            }
        }
        if(task instanceof Milestone)
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

//    private static Date getLatestDate(Date firstDate, Date secondDate)
//    {
//        Calendar firstDateCalendar = Calendar.getInstance();
//        Calendar secondDateCalendar = Calendar.getInstance();
//        firstDateCalendar.setTime(firstDate);
//        secondDateCalendar.setTime(secondDate);
//        int result = firstDateCalendar.compareTo(secondDateCalendar);
//        if(result >= 0)
//        {
//            return firstDate;
//        }
//        else
//        {
//            return secondDate;
//        }
//    }

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


//    public static Date incrementDaysExcludingWeekends(Date startDate, int duration)
//    {
//        Calendar calendar = Calendar.getInstance();
//        // format of date is passed as an argument
//        // base date which will be incremented
//        // set calendar time with given date
//        calendar.setTime(startDate);
//        // add days to date
//        calendar.add(Calendar.DAY_OF_WEEK, duration-1);
//        // check if the date after addition is a working day.
//        // If not then keep on incrementing it till it is a working day
//        while(!isWorkingDay(calendar.getTime(), calendar))
//        {
//            calendar.add(Calendar.DAY_OF_WEEK, 1);
//        }
////        simpleDateFormat.format(calendar.getTime());
//        return calendar.getTime();
//    }

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

//    private static  boolean isWorkingDay(Date date, Calendar calendar)
//    {
//        // set calendar time with given date
//        calendar.setTime(date);
//        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
//        // check if it is Saturday(day=7) or Sunday(day=1)
//        if ((dayOfWeek == 7) || (dayOfWeek == 1))
//        {
//            return false;
//        }
//        return true;
//    }

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
        preDecessorTask.addDependency(preDecessorTask,successorTask);
        for(Dependency dependency : taskManager.getStartMilestone().getDependencyList())
        {
            if(iterateCycle(dependency.getSuccessorTask(),alreadyVisitedTask))
            {
                preDecessorTask.removeDependency(successorTask);
                System.out.println("Cycle Detected");
                return true;
            }
            System.out.println();
            alreadyVisitedTask.clear();
        }
        preDecessorTask.removeDependency(successorTask);
        return false;
    }

    /**
     * Iterate through the project task to detect cycle
     * @param task current task of iteration
     * @param alreadyVisitedTask list of tasks that is already visited
     * @return true if it found that the task has already visited otherwise false
     */
    public static boolean iterateCycle(Task task,List<Task> alreadyVisitedTask)
    {
        List<Dependency> dependencies = task.getDependencyList();
        if(alreadyVisitedTask.contains(task))
        {
            return true;
        }
        else
        {
            alreadyVisitedTask.add(task);
            for(Dependency dependency : dependencies)
            {
                if(dependency.getSuccessorTask() != null && !(dependency.getSuccessorTask() instanceof Milestone))
                {
                    if(iterateCycle(dependency.getSuccessorTask(),alreadyVisitedTask))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
