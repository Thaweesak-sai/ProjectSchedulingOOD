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

    public Task getTaskExcept(String taskName,Task selectedTask){
        for(Task task : taskList){
            if(task.getTaskName().equals(taskName) && !task.equals(selectedTask)){
                return task;
            }
        }
        return null;
    }

    public boolean deleteTask(Task deletedTask){
        List<Task> preDecessorList = deletedTask.getDependency().getPreDecessorTask();
        List<Task> successorList = deletedTask.getDependency().getSuccessorTask();
        for(Task task : preDecessorList){
            task.getDependency().getSuccessorTask().remove(deletedTask);
        }
        for(Task task : successorList){
            task.getDependency().getPreDecessorTask().remove(deletedTask);
        }
        return taskList.remove(deletedTask);
    }

    public void showAllTask(){
        System.out.println("All the tasks in this project");
        for(Task task : taskList){
                System.out.println("Task: " + task.getTaskName());
            }
    }

    public void showAllTaskExcept(Task selectedTask){
        for (Task task : taskList){
            if(!task.equals(selectedTask)){
                System.out.println("Task: " + task.getTaskName());
            }
        }
    }
}
