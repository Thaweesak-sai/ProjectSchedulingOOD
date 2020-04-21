import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Schedule {


    // End date still wrong because of getLatest method
    // Need to reset all date in case of deleting and removing dependency

    public static void assignDate(Project project){
        TaskManager taskManager = project.getTaskManager();
        taskManager.resetDate();
        taskManager.getStartMilestone().setStartDate(project.getStartDate());
        showGraph(taskManager.getStartMilestone());
        taskManager.showAllTaskInformation();
        taskManager.showTaskInformation(taskManager.getStartMilestone());
        taskManager.showTaskInformation(taskManager.getEndMilestone());
    }


    public static void iterate(Task task,Task preDecessorTask) {
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
                iterate(dependency.getSuccessorTask(),task);
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

    public static void showGraph(Task startMilestone) {
        for(Dependency dependency : startMilestone.getDependencyList()){
            System.out.print("START --> ");
            iterate(dependency.getSuccessorTask(),startMilestone);
            System.out.println("END");
            System.out.println();
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
