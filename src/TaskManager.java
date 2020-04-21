import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TaskManager {
    private List<Task> taskList;

    public TaskManager() {
        this.taskList = new ArrayList<Task>();
        taskList.add(new Milestone("Start","Starting Task"));
        taskList.add(new Milestone("End","Ending Task"));
    }


    public Task getStartMilestone(){
        return taskList.get(0);
    }

    public Task getEndMilestone(){
        return taskList.get(1);
    }

    public boolean addTask(Task task){
        Task startMilestone = getStartMilestone();
        startMilestone.addDependency(startMilestone,task);
        task.addDependency(task,getEndMilestone());
        taskList.add(task);
        return true;
    }

    public Task getTask(String taskName){
        List<Task> allTask = getAllTask();
        for(Task task : allTask){
            if(task.getTaskName().equals(taskName)){
                return task;
            }
        }
        return null;
    }

    public boolean deleteTask(Task deletedTask){
        List<Task> preDecessorList = getPreDecessorTask(deletedTask);
        for (Task task : preDecessorList){
            task.removeDependency(deletedTask);
        }
        return taskList.remove(deletedTask);
    }

    public void showAllTaskName(){
        System.out.println("All the tasks in this project");
        List<Task> availableTask = getAllTask();
        for(Task task : availableTask){
            System.out.println("Task : " + task.getTaskName());
        }
    }

    public void showAllTaskInformation(){
        System.out.println("All the tasks in this project");
        List<Task> availableTask = getAllTask();
        for(Task task : availableTask){
            showTaskInformation(task);
        }
    }

    private List<Task> getAllTask () {
        List<Task> availableTask = new ArrayList<Task>(taskList);
        availableTask.removeIf(task -> task instanceof Milestone);
        return availableTask;
    }

    private List<Task> getAvailableTask (Task selectedTask) {
        List<Task> availableTask = new ArrayList<Task>(taskList);
        availableTask.remove(selectedTask);
        availableTask.removeIf(task -> task instanceof Milestone);
        return availableTask;
    }

    private List<Task> getAvailableDependencyTask(Task selectedTask){
        List<Task> availableTask = getAvailableTask(selectedTask);
        for(Dependency dependency : selectedTask.getDependencyList()){
            availableTask.remove(dependency.getSuccessorTask());
        }
        return availableTask;
    }

    private List<Task> getPreDecessorTask (Task selectedTask) {
        List<Task> availableTask = getAvailableTask(selectedTask);
        List<Task> preDecessorList = new ArrayList<Task>();
        for(Task task : availableTask){
            List<Dependency> dependencyList = task.getDependencyList();
            for(Dependency dependency : dependencyList){
                if(dependency.getSuccessorTask().equals(selectedTask)){
                    preDecessorList.add(task);
                }
            }
        }
        return preDecessorList;
    }

    private List<Task> getSuccessorTask(Task selectedTask){
        List<Dependency> dependencyList = selectedTask.getDependencyList();
        List<Task> successorTaskList = new ArrayList<Task>();
        for(Dependency dependency : dependencyList){
            successorTaskList.add(dependency.getSuccessorTask());
        }
        return successorTaskList;
    }


    private void showPreDecessorTask(Task selectedTask){
        List<Task> preDecessorList = getPreDecessorTask(selectedTask);
        if(preDecessorList.size() != 0){
            System.out.println("Predecessor Task: ");
            for(Task task : preDecessorList){
                System.out.println("Task : " + task.getTaskName());
            }
        } else {
            System.out.println("Predecessor Task: -");
        }
    }

    private void showSuccessorTask(Task selectedTask){
        List<Task> successorTaskList = getSuccessorTask(selectedTask);
        if(successorTaskList.size() != 0){
            System.out.println("Successor Task: ");
            for(Task task : successorTaskList){
                if(!task.equals(getEndMilestone())){
                    System.out.println("Task: " + task.getTaskName());
                }
            }
        } else {
            System.out.println("Successor Task: -");
        }
    }



    public boolean addDependency(Task preDecessorTask, Task successorTask){
        List<Dependency> checkList = preDecessorTask.getDependencyList();
        for(Dependency dependency : checkList){
            if(dependency.getPreDecessorTask().equals(preDecessorTask) && dependency.getSuccessorTask().equals(successorTask)){
                System.out.println("This dependency is already exist");
                return false;
            }
        }
        getStartMilestone().removeDependency(successorTask);
        preDecessorTask.removeDependency(getEndMilestone());
        preDecessorTask.addDependency(preDecessorTask,successorTask);
        return true;
    }


    public boolean removeDependency(Task preDecessorTask, Task successorTask){
        List<Dependency> checkList = preDecessorTask.getDependencyList();
        for(Dependency dependency : checkList){
            if(dependency.getPreDecessorTask().equals(preDecessorTask) && dependency.getSuccessorTask().equals(successorTask)){
                preDecessorTask.removeDependency(successorTask);
                if(getSuccessorTask(preDecessorTask).size() == 0){
                    preDecessorTask.addDependency(preDecessorTask,getEndMilestone());
                }
                if(getPreDecessorTask(successorTask).size() == 0){
                    getStartMilestone().addDependency(getStartMilestone(),successorTask);
                }
                return true;
            }
        }
        return false;
    }


    public boolean showAllTaskDependency (Task selectedTask){
        List<Task> availableTask = getAvailableDependencyTask(selectedTask);
        if(availableTask.size() > 0){
            for (Task task : availableTask){
                    System.out.println("Task: " + task.getTaskName());
                }
            return true;
        }
        else {
            return false;
        }

    }

    public void showTaskInformation(Task task){
        System.out.println("--------------------------------");
        System.out.println("Task Name: " + task.getTaskName());
        System.out.println("Description: " + task.getTaskDescription());
        System.out.println("Duration: " + task.getDuration());
        if(task.getStartDate() != null){
            System.out.println("Start Date: " + DateFormatter.formatDate(task.getStartDate()));
        }
        else {
            System.out.println("Start Date: -");
        }
        if(task.getEndDate() != null){
            System.out.println("End Date: " + DateFormatter.formatDate(task.getEndDate()));
        }
        else {
            System.out.println("End Date: -");
        }
        showPreDecessorTask(task);
        showSuccessorTask(task);
        System.out.println("--------------------------------");
        System.out.println();
    }


    public boolean showAllTaskExcept(Task selectedTask){
        List<Task> availableTask = getAvailableTask(selectedTask);
        if(availableTask.size() > 0){
            System.out.println("Available tasks in this project");
            for (Task task : availableTask){
                    System.out.println("Task: " + task.getTaskName());
            }
            return true;
        }
        else {
            return false;
        }

    }


    public void resetDate(){
        List<Task> allTask = getAllTask();
        for(Task task : allTask){
            task.setStartDate(null);
            task.setEndDate(null);
        }
        getEndMilestone().setEndDate(null);
    }

}
