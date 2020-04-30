import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Schedule {


    public static void assignDate(Project project){
        TaskManager taskManager = project.getTaskManager();
        taskManager.resetDate();
        taskManager.getStartMilestone().setStartDate(project.getStartDate());
        for(Dependency dependency : taskManager.getStartMilestone().getDependencyList()){
            System.out.print("START --> ");
            iterateAssignDate(dependency.getSuccessorTask(),taskManager.getStartMilestone());
            System.out.println("END");
            System.out.println();
        }
        taskManager.showAllTaskInformation();
        taskManager.showTaskInformation(taskManager.getStartMilestone());
        taskManager.showTaskInformation(taskManager.getEndMilestone());
    }

    public static void iterateAssignDate(Task task, Task preDecessorTask) {
        List<Dependency> dependencies = task.getDependencyList();
        for(Dependency dependency : dependencies){
            if(preDecessorTask instanceof Milestone){
               task.setStartDate(preDecessorTask.getStartDate());
            } else {
                Date newStartDate = incrementDaysExcludingWeekends(preDecessorTask.getEndDate(),2);
                if(task.getStartDate() != null){
                    Date latestDate = getLatestDate(task.getStartDate(),newStartDate);
                    task.setStartDate(latestDate);
                } else {
                    task.setStartDate(newStartDate);
                }
            }
            task.setEndDate(incrementDaysExcludingWeekends(task.getStartDate(),task.getDuration()));
            System.out.print("Task " + task.getTaskName());
            if(dependency.getSuccessorTask() != null){
                System.out.print(" --> ");
                iterateAssignDate(dependency.getSuccessorTask(),task);
            }
        }
        if(task instanceof Milestone){
            if(task.getEndDate() != null){
                task.setEndDate(getLatestDate(task.getEndDate(),preDecessorTask.getEndDate()));
            } else {
                task.setEndDate(preDecessorTask.getEndDate());
            }

        }
    }

    private static Date getLatestDate(Date firstDate, Date secondDate)
    {
        Calendar firstDateCalendar = Calendar.getInstance();
        Calendar secondDateCalendar = Calendar.getInstance();
        firstDateCalendar.setTime(firstDate);
        secondDateCalendar.setTime(secondDate);
        int result = firstDateCalendar.compareTo(secondDateCalendar);
        if(result >= 0){
            return firstDate;
        } else {
            return secondDate;
        }
    }


    public static Date incrementDaysExcludingWeekends(Date startDate, int duration) {
        Calendar calendar = Calendar.getInstance();
        // format of date is passed as an argument
        // base date which will be incremented
        // set calendar time with given date
        calendar.setTime(startDate);
        // add days to date
        calendar.add(Calendar.DAY_OF_WEEK, duration-1);
        // check if the date after addition is a working day.
        // If not then keep on incrementing it till it is a working day
        while(!isWorkingDay(calendar.getTime(), calendar)) {
            calendar.add(Calendar.DAY_OF_WEEK, 1);
        }
//        simpleDateFormat.format(calendar.getTime());
        return calendar.getTime();
    }

    private static  boolean isWorkingDay(Date date, Calendar calendar) {
        // set calendar time with given date
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        // check if it is Saturday(day=7) or Sunday(day=1)
        if ((dayOfWeek == 7) || (dayOfWeek == 1)) {
            return false;
        }
        return true;
    }

}
