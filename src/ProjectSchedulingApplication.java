import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ProjectSchedulingApplication {
    private static Scanner scanner = new Scanner(System.in);
    private static TaskManager selectedTaskManager;
    private static ProjectManager projectManager = new ProjectManager();
    private static Project selectedProject;
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

    private static Date getDateInput(String label)
    {
        Date date;
        while(true)
        {
            System.out.print(label);
            String buffer = scanner.nextLine();
            try
            {
                date = DateFormatter.formatStringToDate(buffer);
                break;
            }
            catch (ParseException e)
            {
                System.out.println("Invalid input. Please try again!!!");
            }
        }
        return date;
    }




    private static Task addNewTask()
    {
        String taskName = getStringInput("Task Name: ");
        String taskDescription = getStringInput("Description: ");
        int duration = getIntegerInput("Duration: " );
        Task task = new Task(taskName,taskDescription,duration);
        selectedTaskManager.addTask(task);
        return task;
    }

    private static Task findTask()
    {
        String inputTask = getStringInput("Task Name: ");
        return selectedTaskManager.getTask(inputTask);
    }


    private static Project createNewProject()
    {
        String projectName = getStringInput("Project Name: ");
        String projectDesc = getStringInput("Project description: ");
        Date date = getDateInput("Start date (DD-MM-YYYY): ");
        Project newProject = new Project(projectName,projectDesc,date);
        projectManager.addProject(newProject);
        return newProject;
    }

    private static boolean loadProject() throws IOException
    {
        projectManager.loadAllProject();
        projectManager.printAllProject();
        System.out.print("Project name to load:");
        String projectName = scanner.next();
        selectedProject = projectManager.getProject(projectName);  /* Load ไฟล์โปรเจคยังไม่มาแล้วจะเอาอะไรมาให้.... */
        selectedProject.showProjectInformation();
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
        System.out.println("8. Save & Exit");
        int choice = getIntegerInput("Enter: ");
        switch (choice)
        {
            case 1:
                break;
            case 2:
                selectedTask = addNewTask();
                projectPage();
                break;
            case 3:
                selectedTaskManager.showAllTaskName();
                selectedTask = findTask();
                if(selectedTask != null)
                {
                   editTaskPage();
                }
                else
                {
                    System.out.println("Can't find the task that you enter");
                    projectPage();
                }
                break;
            case 4:
                selectedTaskManager.showAllTaskName();
                selectedTask = findTask();
                if(selectedTask != null)
                {
                    selectedTaskManager.deleteTask(selectedTask);
                }
                else
                {
                    System.out.println("Can't find the task that you enter");
                }
                projectPage();
                break;
            case 5:
                selectedTaskManager.showAllTaskInformation();
                System.out.print("Predecessor Task: ");
                Task preDecessorTask = findTask();
                if(preDecessorTask != null)
                {
                    System.out.print("Successor Task: ");
                    Task successorTask = findTask();
                    if(successorTask != null)
                    {
                        boolean hasCycle = Schedule.hasCycle(selectedTaskManager,preDecessorTask,successorTask);
                        if(!hasCycle)
                        {
                            if(selectedTaskManager.addDependency(preDecessorTask,successorTask))
                            {
                                System.out.println("Successfully add dependency");
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
                projectPage();
                break;
            case 6:
                selectedTaskManager.showAllTaskInformation();
                System.out.print("Predecessor Task: ");
                Task removePreDecessorTask = findTask();
                if(removePreDecessorTask != null)
                {
                    System.out.print("Successor Task: ");
                    Task successorTask = findTask();
                    if(successorTask != null)
                    {
                        if(selectedTaskManager.removeDependency(removePreDecessorTask,successorTask))
                        {
                            System.out.println("Successfully delete dependency");
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
                projectPage();
               break;
            case 7:
                Schedule.assignDate(selectedProject);
                selectedProject.scheduleReport();
                projectPage();
                break;
            case 8:
                try
                {
                    projectManager.save(selectedProject);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                break;
        }
    }

    private static void editTaskPage(){
        System.out.println("Select which fields to edit: ");
        System.out.println("1. Task Name");
        System.out.println("2. Task Description");
        System.out.println("3. Task Duration");
        System.out.print("Enter: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice)
        {
            case 1:
                String newName = getStringInput("New Name: ");
                selectedTask.setTaskName(newName);
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
                projectPage();
                break;
        }
    }


    public static void main(String[] args) throws ParseException {
        selectedProject = new Project("1","1",DateFormatter.formatStringToDate("20-04-2020"));
        selectedTaskManager = selectedProject.getTaskManager();
        projectPage();
//        System.out.println("Welcome to Project Scheduling Application");
//        int choice = -1;
//        do{
//                System.out.println("1. Create New Project");
//                System.out.println("2. Load Project");
//                System.out.println("3. Exit Program");
//                System.out.print("Enter: ");
//                choice = scanner.nextInt();
//                scanner.nextLine();
//
//                switch (choice) {
//                    case 1:
//                        System.out.println(" Create New Project");
//                        try {
//                            selectedProject = createNewProject();
//                            selectedTaskManager = selectedProject.getTaskList();
//                            projectPage();
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                        break;
//                    case 2:
//                        System.out.println("Load Project");
//                        try {
//                            loadProject();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        break;
//                    case 3:
//                        System.out.println("Exiting Program...");
//                        break;
//                    default:
//                        System.out.println("Invalid menu choice. Please try again...");
//                }
//        }while(choice!=3);
    }
}
