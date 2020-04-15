import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ProjectSchedulingApplication {
    private static Scanner scanner = new Scanner(System.in);
    private static TaskManager taskManager = new TaskManager();
    private static ProjectManager projectManager = new ProjectManager();
    private static Project selectedProject;
    private static Task selectedTask;


    private static Task addNewTask(){
        System.out.print("Task Name: ");
        String taskName = scanner.nextLine();
        System.out.print("Description: " );
        String taskDescription = scanner.nextLine();
        System.out.print("Duration: " );
        int duration = scanner.nextInt();
        scanner.nextLine();
        Task task = new Task(taskName,taskDescription,duration);
        task.showTaskInformation();
        taskManager.addTask(task);
        return task;
    }

    private static void addDependency(Task task) {
        selectedTask.getDependency().addPreDecessorTask(task);
        task.getDependency().addSuccessorTask(selectedTask);
    }

    private static void removeDependency(Task task) {
        selectedTask.getDependency().removePreDecessorTask(task);
        task.getDependency().removeSuccessorTask(selectedTask);
    }

    private static Task findTask(){
        String inputTask = scanner.nextLine();
        return taskManager.getTask(inputTask);
    }

    private static Task findTaskExcept(){
        String inputTask = scanner.nextLine();
        return taskManager.getTaskExcept(inputTask,selectedTask);
    }

    private static Project createNewProject() throws ParseException {
        System.out.print("Project Name: ");
        String projectName = scanner.nextLine();
        System.out.print("Project description: ");
        String projectDesc = scanner.nextLine();
        System.out.print("Start date (DD-MM-YYYY): ");
        String dateInString = scanner.nextLine();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = formatter.parse(dateInString);
        Project newProject = new Project(projectName,projectDesc,date);
        projectManager.addProject(newProject);
        return newProject;
    }

    private static boolean loadProject() throws IOException {
        projectManager.loadAllProject();
        projectManager.printAllProject();
        System.out.print("Project name to load:");
        String projectName = scanner.next();
        selectedProject = projectManager.getProject(projectName);  /* Load ไฟล์โปรเจคยังไม่มาแล้วจะเอาอะไรมาให้.... */
        selectedProject.showProjectInformation();
        projectPage();
        return true;
    }

    private static void projectPage(){
        System.out.println("Project: "+ selectedProject.getName());
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
                selectedTask = addNewTask();
                taskPage();
                break;
            case 3:
                taskManager.showAllTask();
                selectedTask = findTask();
                if(selectedTask != null){
                   taskPage();
                }
                else {
                    System.out.println("Can't find the task that you enter");
                }
                break;
            case 4:
                selectedProject.scheduleReport();
                break;
            case 5:
                try {
                    projectManager.save(selectedProject);
                } catch (IOException e) {
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
        switch (choice){
            case 1:
                System.out.print("New Name: ");
                String newName = scanner.nextLine();
                selectedTask.setTaskName(newName);
                taskPage();
                break;
            case 2:
                System.out.print("New Description: ");
                String newDescription = scanner.nextLine();
                selectedTask.setTaskDescription(newDescription);
                taskPage();
                break;
            case 3:
                System.out.print("New Duration: ");
                int newDuration = scanner.nextInt();
                selectedTask.setDuration(newDuration);
                scanner.nextLine();
                taskPage();
                break;
        }

    }

    private static void taskPage(){
        selectedTask.showTaskInformation();
        System.out.println("1. Edit Task Information");
        System.out.println("2. Add Dependency");
        System.out.println("3. Remove Dependency");
        System.out.println("4. Remove Task");
        System.out.println("5. Back");
        System.out.print("Enter: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice){
            case 1:
                editTaskPage();
                break;
            case 2:
                taskManager.showAllTaskExcept(selectedTask);
                Task dependencyTask = findTaskExcept();
                addDependency(dependencyTask);
                taskPage();
                break;
            case 3:
                selectedTask.getDependency().printAllPredecessorTask();
                Task removeDependencyTask = findTaskExcept();
                removeDependency(removeDependencyTask);
                taskPage();
                break;
            case 4:
                taskManager.deleteTask(selectedTask);
                selectedTask = null;
                projectPage();
            case 5:
                projectPage();
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
                            selectedProject = createNewProject();
                            projectPage();
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
