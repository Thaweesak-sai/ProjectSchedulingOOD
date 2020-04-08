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
        return true;
    }

    public boolean addSuccessorTask(Task task){
        return true;
    }

    public boolean removePreDecessorTask(Task task){
        return true;
    }

    public boolean removeSuccessorTask(Task task){
        return true;
    }

}
