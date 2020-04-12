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
            if(task.getTaskName().equals(taskName)){
                return task;
            }
        }
        return null;
    }

    public boolean deleteTask(String taskName){
        for(Task task : taskList){
            if(task.getTaskName().equals(taskName)){
                taskList.remove(task);
                return true;
            }
        }
        return false;
    }

    public void showAllTask(){
        for(Task task : taskList){
                System.out.println(task.getTaskName());
            }
        }
}
