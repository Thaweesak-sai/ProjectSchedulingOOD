
public class Dependency
{
    private Task preDecessorTask;
    private Task successorTask;

    public Dependency(Task preDecessorTask,Task successorTask)
    {
        this.preDecessorTask = preDecessorTask;
        this.successorTask = successorTask;
    }

    public Task getPreDecessorTask()
    {
        return preDecessorTask;
    }

    public Task getSuccessorTask()
    {
        return successorTask;
    }


}
