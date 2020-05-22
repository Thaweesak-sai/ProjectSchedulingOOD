import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.*;

/**
 * GanttChart Class
 * A class represents the gantt chart of each project
 *
 * Created By  Jednipit Tantaletong 60070503411 (Pleum)
 *             Thaweesak Saiwongse 60070503429 (Note)
 *             20/05/2020
 * */
public class GanttChart
{
    /** project instance*/
    private Project project;
    /** project end date*/
    private LocalDate projectEndDate;
    /** project starting date*/
    private LocalDate projectStartDate;
    /** JFrame for gantt chart*/
    JFrame frame;
    /** JTable for gantt chart*/
    JTable table;

    /**
     * GanttChart
     * A constructor to create GanttChart
     * */
    public GanttChart(Project project)
    {
        this.project = project;
        this.projectEndDate=project.getEndDate();
        this.projectStartDate=project.getStartDate();
    }

    /**
     * getGanttChart
     * A method to display gantt chart of the project through JFrame & JTable
     * */
    public void getGanttChart()
    {
         frame = new JFrame();
         frame.setTitle("Project : "+project.getName() +" Gantt Chart");
        TaskManager taskManager = project.getTaskManager();
        /* if there is no task in the project */
        if(taskManager.getAllTask().size()==0)
        {
            System.out.println("\n");
            System.out.println("\t\tThis project doesn't have any task!!\t\t");
        }
        else
        {
            List<Task> task = taskManager.getAllTask();
            List<LocalDate> projectRange = getDateInBetween(projectStartDate, projectEndDate);
            String[] column = new String[projectRange.size() + 1];        /* Header of the table */
            column[0] = "DATE : ";                                      /* Assign the (0,0) cell the display "DATE" */
            /* assign data to header of the table */
            for (int i = 1; i < projectRange.size() + 1; i++)
            {
                column[i] = DateFormatter.formatDateToStringForDisplay(projectRange.get(i - 1));
            }
            String[][] data = new String[task.size()][projectRange.size() + 1];   /*data in the table*/
            for (int i = 0; i < task.size(); i++)
            {
                List<LocalDate> taskRange = getDateInBetween(task.get(i).getStartDate(), task.get(i).getEndDate());
                data[i][0] = task.get(i).getTaskName();
                for (int j = 1; j < column.length; j++)
                {
                    data[i][j] = "-"; /* initialize all table's cell with '-'*/
                    for (int k = 0; k < taskRange.size(); k++)
                    {
                        if (DateFormatter.formatDateToStringForDisplay(taskRange.get(k)).equals(column[j]))
                        {
                            data[i][j] = "X";
                            /* if the day is weekend(Sat/Sun) */
                            if (taskRange.get(k).getDayOfWeek() == DayOfWeek.SATURDAY || taskRange.get(k).getDayOfWeek() == DayOfWeek.SUNDAY)
                            {
                                data[i][j] = "-";
                            }
                        }
                    }
                }
            }
            table = new JTable(data, column);
            table.setBounds(30, 40, 200, 300);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            JScrollPane sp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            frame.add(sp);
            frame.setSize(500, 200);
            frame.setVisible(true);
        }
    }
    /**
     * getDateInBetween().
     * A method of list all the dates in between two dates
     * @param startDate starting date
     * @param endDate  ending date
     * @return inBetweenDate , all dates from starting date to ending date
     * */
    private List<LocalDate> getDateInBetween(LocalDate startDate, LocalDate endDate)
    {
        List<LocalDate> inBetweenDate = new ArrayList<>();
        LocalDate date;
        for(date = startDate;!date.isAfter(endDate);date = date.plusDays(1))
        {
            inBetweenDate.add(date);
        }
        return inBetweenDate;
    }
}

