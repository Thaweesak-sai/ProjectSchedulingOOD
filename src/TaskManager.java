import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private List<Task> taskList;

    public TaskManager() {
        this.taskList = new ArrayList<Task>();
    }

    public boolean addTask(Task task){
        taskList.add(task);
        return true;
    }

    public Task getTask(String taskName){
        for(Task task : taskList){
            if(task.getTaskName().equals(taskName) && !(task instanceof  Milestone)){
                return task;
            }
        }
        return null;
    }

    public Task getTaskExcept(String taskName,Task selectedTask){
        for(Task task : taskList){
            if(task.getTaskName().equals(taskName) && !task.equals(selectedTask) && !(task instanceof  Milestone)){
                return task;
            }
        }
        return null;
    }

    public boolean deleteTask(Task deletedTask){
        return taskList.remove(deletedTask);
    }

    public void showAllTask(){
        System.out.println("All the tasks in this project");
        for(Task task : taskList){
            if(!(task instanceof  Milestone))
                System.out.println("Task: " + task.getTaskName());
            }
    }

    public boolean showAllTaskExcept(Task selectedTask){
        List<Task> availableTask = new ArrayList<Task>(taskList);
        availableTask.remove(selectedTask);
        for(Task task : availableTask){
            if(task instanceof Milestone){
                availableTask.remove(task);
            }
        }
        for(Dependency dependency : selectedTask.getDependencyList()){
            if(availableTask.contains(dependency.getSuccessorTask())){
                availableTask.remove(dependency.getSuccessorTask());
            }
        }
        if(taskList.size() > 2){
            System.out.println("Available tasks in this project");
            for (Task task : taskList){
                if(!task.equals(selectedTask) && !(task instanceof  Milestone)){
                    System.out.println("Task: " + task.getTaskName());
                }
            }
            return true;
        }else {
            return false;
        }

    }
}
