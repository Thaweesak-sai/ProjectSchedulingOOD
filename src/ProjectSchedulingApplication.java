import java.util.Scanner;

public class ProjectSchedulingApplication {
    private static Scanner scanner = new Scanner(System.in);
    private static TaskManager taskManager = new TaskManager();
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

    private static boolean findTask(){
        String inputTask = scanner.nextLine();
        Task result = taskManager.getTask(inputTask);
        if(result != null){
            addDependency(result);
            return true;
        } else {
            return false;
        }
    }

    private static boolean createNewProject(){
        return true;
    }

    private static boolean loadProject(){
        return true;
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
        System.out.println("Task: " + selectedTask.getTaskName());
        System.out.println("Description: " + selectedTask.getTaskDescription());
        System.out.println("Duration: " + selectedTask.getDuration());
        System.out.println("1. Edit Task Information");
        System.out.println("2. Add Dependency");
        System.out.println("3. Remove Dependency");
        System.out.println("4. Back");
        System.out.print("Enter: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice){
            case 1:
                editTaskPage();
                break;
            case 2:
                taskManager.showAllTask();
                findTask();
                break;
            case 3:
                break;
            case 4:
                projectPage();
        }
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
                selectedTask = addNewTask();
                taskPage();
                break;
            case 3:
                taskManager.showAllTask();
                break;
        }
    }




    public static void main(String[] args) {
        taskManager.addTask(new Task("1","1",1));
        taskManager.addTask(new Task("2","2",1));
//        System.out.println("Welcome to Project Scheduling Application");
//        System.out.println("1. Create New Project");
//        System.out.println("2. Load Project");
//        System.out.print("Enter: ");
//        int choice = scanner.nextInt();
//        scanner.nextLine();
//        switch (choice){
//            case 1:
//                System.out.println(" Create New Project");
//                createNewProject();
//                break;
//            case 2:
//                System.out.println("Load Project");
//                loadProject();
//                break;
//        }
        projectPage();
    }
}
