import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ProjectSchedulingApplication {
    private static Scanner scanner = new Scanner(System.in);
    private static TaskManager taskManager = new TaskManager();
    private static ProjectManager projectManager = new ProjectManager();


    private static boolean addNewTask(){
        System.out.print("Task Name: ");
        String taskName = scanner.nextLine();
        System.out.print("Description: " );
        String taskDescription = scanner.nextLine();
        System.out.print("Duration: " );
        int duration = scanner.nextInt();
        Task task = new Task(taskName,taskDescription,duration);
        task.showTaskInformation();
        return taskManager.addTask(task);
    }

    private static boolean createNewProject() throws ParseException {
        System.out.print("Project Name: ");
        String projectName = scanner.nextLine();
        System.out.print("Project description: ");
        String projectDesc = scanner.nextLine();
        System.out.print("Start date (DD-MM-YYYY): ");
        String dateInString = scanner.nextLine();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = formatter.parse(dateInString);
        Project newProject = new Project(projectName,projectDesc,date);
        return projectManager.addProject(newProject);
    }

    private static boolean loadProject() throws IOException {
        projectManager.printAllProject();
        System.out.print("Project name to load:");
        String projectName = scanner.next();
        Project project = projectManager.getProject(projectName);  /* Load ไฟล์โปรเจคยังไม่มาแล้วจะเอาอะไรมาให้.... */
        project.showProjectInformation();
        projectPage(project);
        return true;
    }

    private static void projectPage(Project project){
        System.out.println("Project: "+project.getName());
        System.out.println("1. Edit Project Information");
        System.out.println("2. Add New Task");
        System.out.println("3. Select Task");
        System.out.println("4. Print Schedule Report");
        System.out.println("5. Save & Exit");
        System.out.print("Enter: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice){
            case 1:

                break;
            case 2:
                addNewTask();
                break;
            case 3:
                taskManager.showAllTask();
                break;
            case 4:
                project.scheduleReport();
                break;
            case 5:
                try {
                    projectManager.save(project);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    public static void main(String[] args) {
        System.out.println("Welcome to Project Scheduling Application");
        int choice = -1;
        do{
                System.out.println("1. Create New Project");
                System.out.println("2. Load Project");
                System.out.println("3. Exit Program");
                System.out.print("Enter: ");
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.println(" Create New Project");
                        try {
                            createNewProject();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        System.out.println("Load Project");
                        try {
                            loadProject();
                        } catch (IOException e) {
                            e.printStackTrace();
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
