
import java.time.DayOfWeek;
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
    JTable table;
    public GanttChart(Project project){
        this.project = project;
        this.projectEndDate=project.getEndDate();
        this.projectStartDate=project.getStartDate();
    }

    public void getGanttChart() {
         frame = new JFrame();
         frame.setTitle("Project : "+project.getName() +" Gantt Chart");
        TaskManager taskManager = project.getTaskManager();
        List<Task> task = taskManager.getAllTask();
        List<LocalDate> projectRange = getDateInBetween(projectStartDate, projectEndDate);
        String[] column = new String[projectRange.size()+1];
        column[0] = "DATE : ";
        for(int i =1;i<projectRange.size()+1;i++)
        {
            column[i] = DateFormatter.formatDateToStringForDisplay(projectRange.get(i-1));
        }
        String[][] data = new String[task.size()][projectRange.size()+1];
        System.out.println(projectRange);
        System.out.println(column.length);
        for(int i =0;i<task.size();i++)
        {
            List<LocalDate> taskRange = getDateInBetween(task.get(i).getStartDate(),task.get(i).getEndDate());
            System.out.println("task "+task.get(i).getTaskName()+"Range : "+taskRange);
            data[i][0] = task.get(i).getTaskName();
            for(int j=1;j<column.length;j++)
            {
                data[i][j] = "-"; /* initialize all table's cell with '-'*/
                for(int k=0;k<taskRange.size();k++)
                {
                    if (DateFormatter.formatDateToStringForDisplay(taskRange.get(k)).equals(column[j]))
                    {
                        data[i][j] = "X";
                        if(taskRange.get(k).getDayOfWeek()== DayOfWeek.SATURDAY||taskRange.get(k).getDayOfWeek()==DayOfWeek.SUNDAY)
                        {
                            data[i][j] = "-";
                        }
                    }
                }
            }
        }
        table = new JTable(data,column);
        table.setBounds(30,40,200,300);
        JScrollPane sp = new JScrollPane(table);
        frame.add(sp);
        frame.setSize(500,200);
        frame.setVisible(true);
    }
    /**
     * getDateInBetween(). A method of list all the dates in between two dates
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

