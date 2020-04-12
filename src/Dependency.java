import java.util.ArrayList;
import java.util.List;

public class Dependency {
    private List<Task> preDecessorTask;
    private List<Task> successorTask;

    public Dependency() {
        this.preDecessorTask = new ArrayList<Task>();
        this.successorTask = new ArrayList<Task>();
    }

    public List<Task> getPreDecessorTask() {
        return preDecessorTask;
    }

    public List<Task> getSuccessorTask() {
        return successorTask;
    }

    public boolean addPreDecessorTask(Task task){
        preDecessorTask.add(task);
        return true;
    }

    public boolean addSuccessorTask(Task task){
        successorTask.add(task);
        return true;
    }

    public boolean removePreDecessorTask(Task task){
        return preDecessorTask.remove(task);
    }

    public boolean removeSuccessorTask(Task task){
        return successorTask.remove(task);
    }

    public void printAllSuccessorTask(){
        if(this.successorTask.size() == 0){
            System.out.println("Successor Task: -");
        } else {
            System.out.println("Successor Task: ");
            for(Task task : successorTask){
                System.out.println("Task: " + task.getTaskName());
            }
        }
    }

    public void printAllPredecessorTask(){
        if(this.preDecessorTask.size() == 0){
            System.out.println("Predecessor Task: -");
        } else {
            System.out.println("Predecessor Task: ");
            for(Task task : preDecessorTask){
                System.out.println("Task: " + task.getTaskName());
            }
        }
    }

}
