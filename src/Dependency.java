/**
 *  A class represent dependency of the two tasks
 *
 *   Created by Jednipit Tantaletong (Pleum) 60070503411
 */
public class Dependency
{
    /** Predecessor task*/
    private Task preDecessorTask;
    /** Successor task*/
    private Task successorTask;


    /**
     * A constructor for creating dependency
     * @param preDecessorTask predecessor task
     * @param successorTask successor task
     */
    public Dependency(Task preDecessorTask,Task successorTask)
    {
        this.preDecessorTask = preDecessorTask;
        this.successorTask = successorTask;
    }

    /**
     * Get predecessor task
     * @return predecessor task
     */
    public Task getPreDecessorTask()
    {
        return preDecessorTask;
    }

    /**
     * Get successor task
     * @return successor task
     */
    public Task getSuccessorTask()
    {
        return successorTask;
    }


}
