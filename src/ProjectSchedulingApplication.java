import java.io.IOException;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * A class to run the application
 *
 *   Created by Jednipit Tantaletong (Pleum) 60070503411
 *              Thaweesak Saiwongse (Note) 60070503429
 *              22/04/2020
 */
public class ProjectSchedulingApplication
{
    /** Scanner to receive input from user*/
    private static Scanner scanner = new Scanner(System.in);
    /** task manager of the current project*/
    private static TaskManager selectedTaskManager;
    /** Project manager instance */
    private static ProjectManager projectManager = ProjectManager.getInstance();
    /** current project that user selected*/
    private static Project selectedProject;
    /** current task that user selected */
    private static Task selectedTask;


    /**
     * Check that string input that user enter valid or not
     * @param label Label for the input
     * @return string that user input
     */
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

    /**
     * Check that integer input that user enter valid or not
     * @param label label for the input
     * @return integer that user input
     */
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

    /**
     * Check that string input that user enter for date is valid or not
     * also convert string to date
     * @param label label for the input
     * @return date that user input
     */
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


    /**
     * Find the task in project from the task name that user input
     * @param label label for the input
     * @return task that user want to select
     */
    private static Task findTask(String label)
    {
        String inputTask = getStringInput(label);
        return selectedTaskManager.getTask(inputTask);
    }

    /**
     * Ask for user input for new task in the project
     * @return task if it is successfully added to the project otherwise null
     */
    private static Task addNewTask()
    {
        String taskName = getStringInput("Task Name: ");
        String taskDescription = getStringInput("Description: ");
        int duration = getIntegerInput("Duration: " );
        if(selectedTaskManager.checkTaskName(taskName))
        {
            Task task = new Task(taskName,taskDescription,duration);
            selectedTaskManager.addTask(task);
            return task;
        }
        else
        {
            return null;
        }
    }

    /**
     * Ask for project information to create new project
     * @return project that is successfully created
     */
    private static Project createNewProject()
    {

        String projectName = getStringInput("Project Name: ");
        String projectDesc = getStringInput("Project description: ");
        LocalDate date = getDateInput("Start date (DD-MM-YYYY): ");
        return projectManager.createProject(projectName,projectDesc,date);
    }

    /**
     * loadProject
     * Ask for project to load, and check for its input
     * */
    private static void loadProject() 
    {
        String[] allFilesName = projectManager.getAllProjectName();
        if(allFilesName==null)
        {
            System.out.println("There is no text file in the directory!!");
        }
        else
        {
            String[] projectName = new String[2];
            boolean fileFounded = false;
            do {
                String input = getStringInput("Project name to load :  ");
                if(input.endsWith(".txt"))
                {
                    projectName = input.split(".txt");
                }
                else
                {
                    projectName[0] = input;
                }
                for (int i = 0; i < allFilesName.length; i++) {
                    if (allFilesName[i].equals(projectName[0]+".txt")) {
                        fileFounded = true;
                        break;
                    }
                }
                if(!fileFounded)
                    System.out.println("Can't find the project you enter! TRY AGAIN!");
            } while (!fileFounded);
            projectManager.loadProject(projectName[0]+".txt");
            selectedProject = projectManager.getProject(projectName[0]);
            selectedTaskManager = selectedProject.getTaskManager();
            selectedProject.showProjectInformation();
            selectedTaskManager.showAllTaskName();
            selectedTaskManager.showAllTaskInformation();
            projectPage();
        }

    }


    /**
     * Menu that user can select to do with the project
     */
    private static void projectPage()
    {
        System.out.println("\n=========================================");
        System.out.println("\t\tProject: "+ selectedProject.getName());
        System.out.println("=========================================");
        System.out.println("1. Edit Project Information");
        System.out.println("2. Add New Task");
        System.out.println("3. Edit Task Information");
        System.out.println("4. Remove Task");
        System.out.println("5. Add Task Dependency");
        System.out.println("6. Remove Task Dependency");
        System.out.println("7. Print Schedule Report");
        System.out.println("8. Display Gantt Chart");
        System.out.println("9. Discard changes");
        System.out.println("10. Save & Exit");
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
                        System.out.println("Successfully delete the task");
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
                if(!selectedTaskManager.isTaskListEmpty())
                {
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
                while(true)
                {
                    String confirm = getStringInput("Do you sure to discard? (Y/N) :  ");
                    if (confirm.equals("Y"))
                    {
                        System.out.println("Discard Changes");
                        System.out.println("Returning to home page...");
                        break;
                    }
                    else if (confirm.equals("N"))
                    {
                        projectPage();
                        break;
                    }
                    else
                    {
                        System.out.println("Please input only 'Y' or 'N'");
                    }
                }
                break;
            case 10:
                if(projectManager.save(selectedProject))
                {
                    System.out.println("Successfully saved");
                    System.out.println("Returning to home page...");
                }
                else{
                    System.out.println("Error occurred in writing the file");
                }
                break;
        }
    }

    /**
     * Menu for edit project information
     */
    private static void editProjectPage()
    {
        System.out.println("=========================================");
        System.out.println("\tEdit Project Information Page ");
        System.out.println("=========================================");
        selectedProject.showProjectInformation();
        int choice;
        do
        {
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
            }
        }
        while (choice !=4);
        projectPage();
    }

    /**
     * Menu for edit task information
     */
    private static void editTaskPage(){
        System.out.println("=========================================");
        System.out.println("\tEdit Task Page ");
        System.out.println("=========================================");
        System.out.println("Select which fields to edit: ");
        System.out.println("1. Task Name");
        System.out.println("2. Task Description");
        System.out.println("3. Task Duration");
        System.out.println("4. Exit to Project Page");
        int choice;
        do
        {
            choice = getIntegerInput("Enter: ");
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
        while(choice!=4);
        projectPage();
    }


    public static void main(String[] args) throws ParseException {
        System.out.println("=========================================");
        System.out.println("Welcome to Project Scheduling Application");
        System.out.println("=========================================");
        int choice;
        do{
                System.out.println("1. Create New Project");
                System.out.println("2. Load Project");
                System.out.println("3. Exit Program");
                choice = getIntegerInput("Enter: ");
                switch (choice) {
                    case 1:
                        System.out.println("\n=========================================");
                        System.out.println("\t\tCreate New Project");
                        System.out.println("=========================================");
                        selectedProject = createNewProject();
                        selectedTaskManager = selectedProject.getTaskManager();
                        projectPage();
                        break;
                    case 2:
                        System.out.println("\n=========================================");
                        System.out.println("\t\tLoad Project");
                        System.out.println("=========================================");
                        loadProject();
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
