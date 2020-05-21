/**
 * A class represent a milestone for a project which is start and end milestones
 *
 *   Created by Jednipit Tantaletong (Pleum) 60070503411
 */
public class Milestone extends Task
{
    /**
     * Constructor for creating milestone
     * @param taskName task name
     * @param taskDescription task description
     */
    public Milestone(String taskName, String taskDescription)
    {
        super(taskName, taskDescription, 0);
    }

}
