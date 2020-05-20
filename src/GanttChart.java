import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.*;

public class GanttChart {
    private Project project;
    private LocalDate projectEndDate;
    private LocalDate projectStartDate;
    JFrame frame;
    public GanttChart(Project project){
        this.project = project;
        this.projectEndDate=project.getEndDate();
        this.projectStartDate=project.getStartDate();
    }

    public void getGanttChart()
    {
        TaskManager taskManager = project.getTaskManager();
        List<Task> task = taskManager.getAllTask();
        /*assign value*/
        ArrayList<ArrayList<String>> ganttChartt = new ArrayList<>();
        ganttChartt.add(new ArrayList<>());
        ganttChartt.get(0).add("DATE");
        List<LocalDate> dateInBetween = getDateInBetween(projectStartDate,projectEndDate);
        for(int i=0;i<dateInBetween.size();i++)
        {
            ganttChartt.get(0).add(DateFormatter.formatDateToStringForDisplay(dateInBetween.get(i)));
        }
        int tableSize = ganttChartt.get(0).size();
        for(int row =1,i=0;row<task.size()+1;row++,i++)
        {
            /* initialize arrayList size to be the number of date in between project*/
            ganttChartt.add(new ArrayList<String>(Collections.nCopies(tableSize,"---------------")));
            /* assign task name to the first element of each row*/
            ganttChartt.get(row).set(0,task.get(i).getTaskName());
            List<LocalDate> taskRange = getDateInBetween(task.get(i).getStartDate(),task.get(i).getEndDate());
            for(int j=0;j<taskRange.size();j++) {
                if (dateInBetween.contains(taskRange.get(j))) {
                    int indexWorked = dateInBetween.indexOf(taskRange.get(j)) + 1;
                    ganttChartt.get(row).set(indexWorked, "XXXXXXXXXXXXXXX");
                }
            }
        }
        for(int i=0;i<ganttChartt.size();i++)
        {
            System.out.println(ganttChartt.get(i));
        }

        /* Wait for motivation,  Jframe + Table Studying.*/
//        ArrayList<String> taskName = new ArrayList<>(ganttChartt.get(0));
//        System.out.println(taskName);
//        frame = new JFrame();
//        JTable jt = new JTable(ganttChartt,taskName);
    }
    /**
     * getDateInBetween(). A method of list all the dates in between two dates
     * */
    private List<LocalDate> getDateInBetween(LocalDate startDate, LocalDate endDate)
    {
        List<LocalDate> inBetweenDate = new ArrayList<>();
        LocalDate date;
        for(date = startDate;!date.isEqual(endDate);date = date.plusDays(1))
        {
            inBetweenDate.add(date);
        }
        return inBetweenDate;
    }
}

