import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class ProjectSchedulingApplication {
    /** Scanner to receive input from user*/
    private static Scanner scanner = new Scanner(System.in);
    /** task manager of the current project*/
    private static TaskManager selectedTaskManager;
    /** Project manager instance */
    private static ProjectManager projectManager = ProjectManager.getInstance();
    /** current porject that user selected*/
    private static Project selectedProject;
    /** current task that user selected */
    private static Task selectedTask;



    private static String getStringInput(String label)
    {
        String input;
        while (true)
        {
            System.out.print(label);
            input = scanner.nextLine();
            if(input.equals(""))
            {
                System.out.println("Invalid input. Please try again!!!");
            }
            else
            {
                break;
            }
        }
        return input;
    }

    private static int getIntegerInput(String label)
    {
        int input;
        while (true)
        {
            System.out.print(label);
            String buffer = scanner.nextLine();
            try
            {
                input = Integer.parseInt(buffer);
                break;
            }
            catch (NumberFormatException nfe)
            {
                System.out.println("Invalid input. Please try again!!!");
            }
        }
        return input;
    }

    private static LocalDate getDateInput(String label)
    {
        LocalDate date;
        while(true)
        {
            System.out.print(label);
            String buffer = scanner.nextLine();
            try
            {
                date = DateFormatter.formatStringToDate(buffer);
                if(date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY)
                {
                   break;
                }
                else
                {
                    System.out.println("Starting date can't be on weekend. Please try again!!!");
                }
            }
            catch (DateTimeParseException e)
            {
                System.out.println("Invalid input. Please try again!!!");
            }
        }
        return date;
    }


    private static Task findTask(String label)
    {
        String inputTask = getStringInput(label);
        return selectedTaskManager.getTask(inputTask);
    }


    private static Task addNewTask()
    {
        String taskName = getStringInput("Task Name: ");
        String taskDescription = getStringInput("Description: ");
        int duration = getIntegerInput("Duration: " );
        Task task = new Task(taskName,taskDescription,duration);
        if(selectedTaskManager.checkTaskName(taskName))
        {
            selectedTaskManager.addTask(task);
            return task;
        }
        else
        {
            return null;
        }
    }


    private static Project createNewProject()
    {
        String projectName = getStringInput("Project Name: ");
        String projectDesc = getStringInput("Project description: ");
        LocalDate date = getDateInput("Start date (DD-MM-YYYY): ");
        Project newProject = new Project(projectName,projectDesc,date);
        projectManager.addProject(newProject);
        return newProject;
    }

    private static boolean loadProject() throws IOException, ParseException {
        projectManager.printAllProject();
        String projectName = getStringInput("Project name to load:  ");
        projectManager.loadProject(projectName+".txt");
        selectedProject = projectManager.getProject(projectName);
        selectedTaskManager = selectedProject.getTaskManager();
        selectedProject.showProjectInformation();
        selectedTaskManager.showAllTaskName();
        selectedTaskManager.showAllTaskInformation();
        projectPage();
        return true;
    } 


    private static void projectPage()
    {
        System.out.println("Project: "+ selectedProject.getName());
        System.out.println("1. Edit Project Information");
        System.out.println("2. Add New Task");
        System.out.println("3. Edit Task Information");
        System.out.println("4. Remove Task");
        System.out.println("5. Add Task Dependency");
        System.out.println("6. Remove Task Dependency");
        System.out.println("7. Print Schedule Report");
        System.out.println("8. Display Gantt Chart");
        System.out.println("9. Save & Exit");
        int choice = getIntegerInput("Enter: ");
        switch (choice)
        {
            case 1:
                editProjectPage();
                break;
            case 2:
                selectedTask = addNewTask();
                if(selectedTask != null)
                {
                    System.out.println("Successfully create a task");
                    Schedule.assignDate(selectedProject);
                }
                else
                {
                    System.out.println("There is already this task name in this project. Please try again!!!");
                }
                projectPage();
                break;
            case 3:
                if(!selectedTaskManager.isTaskListEmpty()){
                    selectedTaskManager.showAllTaskName();
                    selectedTask = findTask("Task Name: ");
                    if(selectedTask != null)
                    {
                        selectedTaskManager.showTaskInformation(selectedTask);
                        editTaskPage();
                    }
                    else
                    {
                        System.out.println("Can't find the task that you enter");
                        projectPage();
                    }
                }
                else
                {
                    System.out.println("There is no tasks in this project");
                    projectPage();
                }

                break;
            case 4:
                if(!selectedTaskManager.isTaskListEmpty())
                {
                    selectedTaskManager.showAllTaskName();
                    selectedTask = findTask("Task Name: ");
                    if(selectedTask != null)
                    {
                        selectedTaskManager.deleteTask(selectedTask);
                        Schedule.assignDate(selectedProject);
                    }
                    else
                    {
                        System.out.println("Can't find the task that you enter");
                    }
                }
                else
                {
                    System.out.println("There is no tasks in this project");
                }

                projectPage();
                break;
            case 5:
                if(!selectedTaskManager.isTaskListEmpty()){
                    selectedTaskManager.showAllTaskInformation();
                    Task preDecessorTask = findTask("Predecessor Task: ");
                    if(preDecessorTask != null)
                    {
                        Task successorTask = findTask("Successor Task: ");
                        if(successorTask != null)
                        {
                            boolean hasCycle = Schedule.hasCycle(selectedTaskManager,preDecessorTask,successorTask);
                            if(!hasCycle)
                            {
                                if(selectedTaskManager.addDependency(preDecessorTask,successorTask))
                                {
                                    System.out.println("Successfully add dependency");
                                    Schedule.assignDate(selectedProject);
                                }
                                else
                                {
                                    System.out.println("Error: Can't add dependency");
                                }
                            }
                            else
                            {
                                System.out.println("Error: Can't add dependency because it creates cycle");
                            }
                        }
                        else
                        {
                            System.out.println("Invalid Successor Task");
                        }
                    }
                    else
                    {
                        System.out.println("Invalid Predecessor Task");
                    }
                }
                else
                {
                    System.out.println("There is no task in the project");
                }

                projectPage();
                break;
            case 6:
                if(!selectedTaskManager.isTaskListEmpty()){
                    selectedTaskManager.showAllTaskInformation();
                    Task removePreDecessorTask = findTask("Predecessor Task: ");
                    if(removePreDecessorTask != null)
                    {
                        Task successorTask = findTask("Successor Task: ");
                        if(successorTask != null)
                        {
                            if(selectedTaskManager.removeDependency(removePreDecessorTask,successorTask))
                            {
                                System.out.println("Successfully delete dependency");
                                Schedule.assignDate(selectedProject);
                            }
                            else
                            {
                                System.out.println("Error: Can't remove dependency");
                            }
                        }
                        else
                        {
                            System.out.println("Invalid Successor Task");
                        }
                    }
                    else
                    {
                        System.out.println("Invalid Predecessor Task");
                    }
                }
                else
                {
                    System.out.println("There is no task in this project");
                }
                projectPage();
               break;
            case 7:
                selectedProject.scheduleReport();
                projectPage();
                break;
            case 8:
                selectedProject.getGanttChart();
                projectPage();
                break;
            case 9:
                try
                {
                    projectManager.save(selectedProject);
                    System.out.println("Successfully saved");
                    System.out.println("Exiting...");
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                break;
        }
    }
    private static void editProjectPage()
    {
        System.out.println("== Edit project information page ==");
        selectedProject.showProjectInformation();
        int choice;
        do{
        System.out.println("Select which fields to edit: ");
        System.out.println("1. Project name");
        System.out.println("2. Project description");
        System.out.println("3. Project start date");
        System.out.println("4. Exit to Project Page");
        choice = getIntegerInput("Enter: ");
        switch (choice)
        {
            case 1:
                String newName = getStringInput("New Name: ");
                selectedProject.setName(newName);
                break;
            case 2:
                String newDescription = getStringInput("New Description: ");
                selectedProject.setDesc(newDescription);
                break;
            case 3:
                LocalDate newStartDate = getDateInput("New Start Date: ");
                selectedProject.setStartDate(newStartDate);
                Schedule.assignDate(selectedProject);
                break;
        } }while (choice !=4);
        projectPage();
    }

    private static void editTaskPage(){
        System.out.println("Select which fields to edit: ");
        System.out.println("1. Task Name");
        System.out.println("2. Task Description");
        System.out.println("3. Task Duration");
        System.out.print("Enter: ");
        int choice = getIntegerInput("Enter: ");
        switch (choice)
        {
            case 1:
                String newName = getStringInput("New Name: ");
                if(selectedTaskManager.checkTaskName(newName))
                {
                    selectedTask.setTaskName(newName);
                }
                else
                {
                    System.out.println("There is already this task name in this project. Please try again!!!");
                }
                projectPage();
                break;
            case 2:
                String newDescription = getStringInput("New Description: ");
                selectedTask.setTaskDescription(newDescription);
                projectPage();
                break;
            case 3:
                int newDuration = getIntegerInput("New Duration: ");
                selectedTask.setDuration(newDuration);
                Schedule.assignDate(selectedProject);
                projectPage();
                break;
        }
    }


    public static void main(String[] args) throws ParseException {
        System.out.println("Welcome to Project Scheduling Application");
        int choice;
        do{
                System.out.println("1. Create New Project");
                System.out.println("2. Load Project");
                System.out.println("3. Exit Program");
                choice = getIntegerInput("Enter: ");
                switch (choice) {
                    case 1:
                        System.out.println(" Create New Project");
                        selectedProject = createNewProject();
                        selectedTaskManager = selectedProject.getTaskManager();
                        projectPage();
                        break;
                    case 2:
                        System.out.println("Load Project");
                        try {
                            loadProject();
                        } catch (IOException e) {
                            System.out.println("Error: Can't load the project file");
                        }
                        break;
                    case 3:
                        System.out.println("Exiting Program...");
                        break;
                    default:
                        System.out.println("Invalid menu choice. Please try again...");
                }
        }while(choice!=3);
    }
}
