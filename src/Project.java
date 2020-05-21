import java.time.LocalDate;

/**
 * Project
 * A class represent a project
 *
 *  Created by  Jednipit Tantaletong (Pleum) 60070503411
 *              Thaweesak Saiwongse (Note)   60070503429
 *              14/04/2020
 * */
public class Project {
    /** task manager of the project */
    private TaskManager taskManager;
    /** starting date of the project */
    private LocalDate startDate;
    /** ending date of the project */
    private LocalDate endDate;
    /** project's name */
    private String projectName;
    /** project's description */
    private String projectDesc;

    /**
     * constructor to create a project
     * @param projectName , project name
     * @param projectDesc , project description
     * @param startDate  , project starting date
     * */
    public Project(String projectName, String projectDesc, LocalDate startDate)
    {
        System.out.println(projectName +" is successfully created");
        this.projectName = projectName;
        this.projectDesc = projectDesc;
        this.startDate = startDate;
        this.taskManager = new TaskManager();
    }
    /**
     * getTaskManager
     * a method to get Task manager of the project
     * @return Taskmanager
     * */
    public TaskManager getTaskManager()
    {
        return taskManager;
    }
    /**
     * getName
     * a method to get the name of the project
     * @return projectName , project name
     * */
    public String getName()
    {
        return this.projectName;
    }
    /**
     * setName
     * a method to set the name of the project
     * @param name , new name of the project
     * */
    public void setName(String name)
    {
        this.projectName = name;
    }
    /**
     * getDesc
     * a method to get the description of the project
     * @return projectDesc, project description
     * */
    public String getDesc()
    {
        return this.projectDesc;
    }
    /**
     * setDesc
     * a method to set new project description
     * @param description , new project description
     * */
    public void setDesc(String description)
    {
        this.projectDesc = description;
    }

    /**
     * getStartDate
     * A method to get the starting date of the project
     * @return startDate, starting date of the project
     * */
    public LocalDate getStartDate()
    {
        return this.startDate;
    }
    /**
     * setStartDate
     * A method to set new starting date of the project
     * @param startDate , new starting date of the project
     * */
    public void setStartDate(LocalDate startDate)
    {
        this.startDate = startDate;
    }
    /**
     * getEndDate
     * A method to get ending date of the project
     * @return endDate, project's ending date
     * */
    public LocalDate getEndDate()
    {
        return this.endDate;
    }
    /**
     * setEndDate
     * A method to set new ending date of the project
     * @param endDate , new ending date
     * */
    public void setEndDate(LocalDate endDate)
    {
        this.endDate = endDate;
    }
    /**
     * scheduleReport
     * A method to show all project and its tasks and dependencies information
     * */
    public void scheduleReport()
    {
        this.showProjectInformation();
        this.taskManager.showAllTaskInformation();
    }

    /**
     * showProjectInformation
     * A method to show only on the project information
     * such as  name, description, start date and end date
     * */
    public void showProjectInformation(){
        System.out.println("--------------------------------");
        System.out.println("Project name: "+projectName);
        System.out.println("Project description: "+projectDesc);
        System.out.println("Start date: "+ DateFormatter.formatDateToStringForDisplay(startDate));
        if (endDate!=null)
        {
            System.out.println("End date: "+ DateFormatter.formatDateToStringForDisplay(endDate));
        }
        else
        {
            System.out.println("End date: -");
        }
        System.out.println("--------------------------------");
    }
    /**
     * getGanttChart()
     * A method to get the gantt chart for the project
     * */
    public void getGanttChart(){
        GanttChart ganttChart = new GanttChart(this);
        ganttChart.getGanttChart();
    }

}
