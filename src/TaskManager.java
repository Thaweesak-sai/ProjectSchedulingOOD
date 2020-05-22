import java.util.ArrayList;
import java.util.List;

/**
 * A class to keep all list of the project and handle things related to task
 *
 *   Created by Jednipit Tantaletong (Pleum) 60070503411
 *              Thaweesak Saiwongse (Note) 60070503429
 *              22/04/2020
 */
public class TaskManager
{
    /** list of all tasks in project*/
    private List<Task> taskList;

    /**
     * Constructor for creating task manager which also initialize start and end mile stone
     */
    public TaskManager()
    {
        this.taskList = new ArrayList<Task>();
        taskList.add(new Milestone("Start","Starting Task"));
        taskList.add(new Milestone("End","Ending Task"));
    }

    /**
     * Get start milestone
     * @return start milestone
     */
    public Task getStartMilestone()
    {
        return taskList.get(0);
    }

    /**
     * Get end milestone
     * @return end milestone
     */
    public Task getEndMilestone()
    {
        return taskList.get(1);
    }

    /**
     * Add task to the task list,
     * Also add dependency to connect start milestone and end milestone to this task
     * @param task task to add to task list
     */
    public void addTask(Task task)
    {
        Task startMilestone = getStartMilestone();
        startMilestone.addDependency(startMilestone,task);
        task.addDependency(task,getEndMilestone());
        taskList.add(task);

    }

    /**
     * Check the new task name with all tasks in the list
     * @param taskName task name that user input
     * @return true if the task name isn't duplicated otherwise false
     */
    public boolean checkTaskName(String taskName)
    {
        for(Task checkTask : taskList)
        {
            if(checkTask.getTaskName().toLowerCase().equals(taskName.toLowerCase()))
            {
                return false;
            }
        }
        return true;
    }


    /**
     * Get task from the task list
     * @param taskName task name
     * @return task that match with the task name that user enter otherwise return null
     */
    public Task getTask(String taskName)
    {
        List<Task> allTask = getAllTask();
        for(Task task : allTask)
        {
            if(task.getTaskName().toLowerCase().equals(taskName.toLowerCase()))
            {
                return task;
            }
        }
        return null;
    }

    /**
     * Delete the task ,
     * also delete all the dependencies that are related to this task
     * @param deletedTask task to delete
     */
    public void deleteTask(Task deletedTask)
    {
        List<Task> preDecessorList = getPreDecessorTask(deletedTask);
        for (Task task : preDecessorList)
        {
            removeDependency(task,deletedTask);
        }
        List<Task> successorTaskList = getSuccessorTask(deletedTask);
        for (Task task : successorTaskList)
        {
            removeDependency(deletedTask,task);
        }
        getStartMilestone().removeDependency(deletedTask);
        taskList.remove(deletedTask);
    }

    /**
     * Show all task name in the list
     */
    public void showAllTaskName()
    {
        System.out.println("All the tasks in this project");
        List<Task> availableTask = getAllTask();
        for(Task task : availableTask)
        {
            System.out.println("Task : " + task.getTaskName());
        }
    }

    /**
     * Show all task infomration
     */
    public void showAllTaskInformation()
    {
        List<Task> availableTask = getAllTask();
        for(Task task : availableTask)
        {
            showTaskInformation(task);
        }
    }


    /**
     * Get all the task except start and end milestones
     * @return all the task except start and end milestones
     */
    public List<Task> getAllTask ()
    {
        List<Task> availableTask = new ArrayList<Task>(taskList);
        availableTask.removeIf(task -> task instanceof Milestone);
        return availableTask;
    }

    /**
     * Check if task list is empty or not
     * @return true if the task list is empty otherwise false
     */
    public boolean isTaskListEmpty(){

        if(getAllTask().size() == 0){
            return true;
        }
        return false;
    }

    /**
     *  Get available task which remove itself and start and end milestone
     * @param selectedTask selected task
     * @return list of task
     */
    private List<Task> getAvailableTask (Task selectedTask)
    {
        List<Task> availableTask = new ArrayList<Task>(taskList);
        availableTask.remove(selectedTask);
        availableTask.removeIf(task -> task instanceof Milestone);
        return availableTask;
    }


    /**
     * Get list of predecessor task of selected task
     * @param selectedTask selected task
     * @return list of predecessor task of selected task
     */
    private List<Task> getPreDecessorTask (Task selectedTask)
    {
        List<Task> availableTask = getAvailableTask(selectedTask);
        List<Task> preDecessorList = new ArrayList<Task>();
        for(Task task : availableTask)
        {
            List<Dependency> dependencyList = task.getDependencyList();
            for(Dependency dependency : dependencyList)
            {
                if(dependency.getSuccessorTask().equals(selectedTask))
                {
                    preDecessorList.add(task);
                }
            }
        }
        return preDecessorList;
    }

    /**
     * Get list of successor task of selected task
     * @param selectedTask selected task
     * @return list of successor task of selected task
     */
    private List<Task> getSuccessorTask(Task selectedTask)
    {
        List<Dependency> dependencyList = selectedTask.getDependencyList();
        List<Task> successorTaskList = new ArrayList<Task>();
        for(Dependency dependency : dependencyList)
        {
            successorTaskList.add(dependency.getSuccessorTask());
        }
        return successorTaskList;
    }

    /**
     * Show all predecessor tasks of selected task
     * @param selectedTask selected task
     */
    private void showPreDecessorTask(Task selectedTask)
    {
        List<Task> preDecessorList = getPreDecessorTask(selectedTask);

        if(preDecessorList.size() != 0)
        {
            System.out.println("Predecessor Task: ");
            for(Task task : preDecessorList)
            {
                System.out.println("Task : " + task.getTaskName());
            }
        }
        else
        {
            System.out.println("Predecessor Task: -");
        }
    }

    /**
     * Show all successor tasks of selected task
     * @param selectedTask selected task
     */
    private void showSuccessorTask(Task selectedTask)
    {
        List<Task> successorTaskList = getSuccessorTask(selectedTask);
        successorTaskList.remove(getEndMilestone());
        if(successorTaskList.size() != 0)
        {
            System.out.println("Successor Task: ");
            for(Task task : successorTaskList)
            {
                    System.out.println("Task: " + task.getTaskName());
            }
        }
        else
        {
            System.out.println("Successor Task: -");
        }
    }

    /**
     * Add dependency of the task. The dependency will keep at dependency list of predecessor task
     * Also checking if the dependency is already exist or not,
     * and manage dependency of start and end milestones with related tasks
     * @param preDecessorTask predecessor task
     * @param successorTask successor task
     * @return true if successfully add dependency otherwise false
     */
    public boolean addDependency(Task preDecessorTask, Task successorTask)
    {
        List<Dependency> checkList = preDecessorTask.getDependencyList();
        for(Dependency dependency : checkList)
        {
            if(dependency.getPreDecessorTask().equals(preDecessorTask) && dependency.getSuccessorTask().equals(successorTask))
            {
                System.out.println("This dependency is already exist");
                return false;
            }
        }
        getStartMilestone().removeDependency(successorTask);
        preDecessorTask.removeDependency(getEndMilestone());
        preDecessorTask.addDependency(preDecessorTask,successorTask);
        return true;
    }


    /**
     * Remove dependency of the task.
     * Also checking if the dependency that want to delete is exist or not,
     * and manage dependency of start and end milestones with related tasks
     * @param preDecessorTask predecessor task
     * @param successorTask successor task
     * @return true if successfully remove dependency otherwise false
     */
    public boolean removeDependency(Task preDecessorTask, Task successorTask)
    {
        List<Dependency> checkList = preDecessorTask.getDependencyList();
        for(Dependency dependency : checkList)
        {
            if(dependency.getPreDecessorTask().equals(preDecessorTask) && dependency.getSuccessorTask().equals(successorTask))
            {
                preDecessorTask.removeDependency(successorTask);
                if(getSuccessorTask(preDecessorTask).size() == 0)
                {
                    preDecessorTask.addDependency(preDecessorTask,getEndMilestone());
                }
                if(getPreDecessorTask(successorTask).size() == 0)
                {
                    getStartMilestone().addDependency(getStartMilestone(),successorTask);
                }
                return true;
            }
        }
        return false;
    }


    /**
     * Show detail of the task
     * @param task task to show detail
     */
    public void showTaskInformation(Task task)
    {
        System.out.println("--------------------------------");
        System.out.println("Task Name: " + task.getTaskName());
        System.out.println("Description: " + task.getTaskDescription());
        System.out.println("Duration: " + task.getDuration());
        if(task.getStartDate() != null)
        {
            System.out.println("Start Date: " + DateFormatter.formatDateToStringForDisplay(task.getStartDate()));
        }
        else
        {
            System.out.println("Start Date: -");
        }
        if(task.getEndDate() != null)
        {
            System.out.println("End Date: " + DateFormatter.formatDateToStringForDisplay(task.getEndDate()));
        }
        else
        {
            System.out.println("End Date: -");
        }
        showPreDecessorTask(task);
        showSuccessorTask(task);
        System.out.println("--------------------------------");
        System.out.println();
    }


    /**
     * Reset the start and end date of all tasks in list
     */
    public void resetDate()
    {
        for(Task task : taskList)
        {
            task.setStartDate(null);
            task.setEndDate(null);
        }
    }


}
