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




    public static void main(String[] args) {
	// write your code here
        System.out.println("Welcome to Project Scheduling Application");
        System.out.println("1. Create New Project");
        System.out.println("2. Load Project");
        System.out.print("Enter: ");
        int choice = scanner.nextInt();
        switch (choice){
            case(1):
                System.out.println(" Create New Project");
                createNewProject();
                break;
            case(2):
                System.out.println("Load Project");
                loadProject();
                break;
        }
    }
}
