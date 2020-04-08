import java.util.Scanner;

public class ProjectSchedulingApplication {
    private static Scanner scanner = new Scanner(System.in);
    private static TaskManager taskManager = new TaskManager();


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

    private static boolean createNewProject(){
        return true;
    }

    private static boolean loadProject(){
        return true;
    }

    private static void projectPage(){
        System.out.println("Project: ");
        System.out.println("1. Edit Project Information");
        System.out.println("2. Add New Task");
        System.out.println("3. Select Task");
        System.out.println("4. Print Schedule Report");
        System.out.println("5. Save & Exit");
        System.out.print("Enter: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice){
            case 2:
                addNewTask();
                break;
            case 3:
                taskManager.showAllTask();
                break;
        }
    }




    public static void main(String[] args) {
        System.out.println("Welcome to Project Scheduling Application");
        System.out.println("1. Create New Project");
        System.out.println("2. Load Project");
        System.out.print("Enter: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice){
            case 1:
                System.out.println(" Create New Project");
                createNewProject();
                break;
            case 2:
                System.out.println("Load Project");
                loadProject();
                break;
        }
    }
}
