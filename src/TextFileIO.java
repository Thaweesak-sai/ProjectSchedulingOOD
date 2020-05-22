import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 *  TextFileIO Class
 * A class to manage writing/reading text file.
 *
 * Created by   Jednipit Tantaletong (Pleum) 60070503411
 *              Thaweesak Saiwongse (Note)   60070503429
 *              16/05/2020
 * */

public class TextFileIO
{

    /** Reader object to access the file */
    private BufferedReader reader = null;

    /** openProjectFile method
     *  A method to open the project file to read/write
     * @param fileName is project's file name
     * */
    private void openProjectFile(String fileName){
        try
        {
            if (reader != null)
                reader.close();
        }
        catch (IOException io)
        {
            reader = null;
        }
        try
        {
            reader = new BufferedReader(new FileReader(fileName));
        }
        catch (FileNotFoundException fnf)
        {
            reader = null;
        }
    }
    /**
     *  getNextLine
     *  A method to read a line from open file.
     * @return lineRead, return line as a string, next line is nothing or error occurred.
     * Created By Sally Goldin, 21 March 2012, modified by Thaweesak Saiwongse 16/05/2020
     * */
    private String getNextLine(){
        String lineRead = null;
        try
        {
            if (reader != null)  /* if reader is null, file is not open */
            {
                lineRead = reader.readLine();
                if (lineRead == null)  /* end of the file */
                {
                    reader.close();
                }
            } /* end if reader not null */
        }
        catch (IOException ioe)
        {
            lineRead = null;
        }
        return lineRead;
    }

    /**
     * Close
     * A method to close the opened file.
     *
     */
    private void close(){
        try
        {
            reader.close();
        }
        catch (IOException ioe)
        {
            System.out.println("ERROR can't close the file");
        }
    }

    /**
     * writeProjectFile
     * A method to store project's data as a text file.
     * @param project ,the project the user selected to save as a text file.
     *
     * */
    public void writeProjectFile(Project project) 
    {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileOutputStream(project.getName() + ".txt", Boolean.parseBoolean(String.valueOf(StandardCharsets.UTF_8))),false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(writer!=null)
        {
            writer.println("project|" + project.getName());
            writer.println("project|" + project.getDesc());
            writer.println("project|" + DateFormatter.formatDateToStringForSaving(project.getStartDate()));
            if (project.getEndDate() != null) {
                writer.println("project|" + DateFormatter.formatDateToStringForSaving(project.getEndDate()));
            }
            int numberOfTask = project.getTaskManager().getAllTask().size();
            List<Task> taskList = project.getTaskManager().getAllTask();
            for (int i = 0; i < numberOfTask; i++) {
                List<Dependency> taskDependency = taskList.get(i).getDependencyList();
                writer.println("task|" + taskList.get(i).getTaskName());
                writer.println("task|" + taskList.get(i).getTaskDescription());
                writer.println("task|" + taskList.get(i).getDuration());
                if (taskList.get(i).getStartDate() != null) {
                    writer.println("task|" + DateFormatter.formatDateToStringForSaving(taskList.get(i).getStartDate()));
                }
                if (taskList.get(i).getEndDate() != null) {
                    writer.println("task|" + DateFormatter.formatDateToStringForSaving(taskList.get(i).getEndDate()));
                }
                writer.println("ENDTASK| ");
                for (int j = 0; j < taskDependency.size(); j++) {
                    writer.println("dependency|" + taskDependency.get(j).getPreDecessorTask().getTaskName());
                    writer.println("dependency|" + taskDependency.get(j).getSuccessorTask().getTaskName());
                    writer.println("ENDDEPENDENCY| ");
                }
            }
            writer.close();
        }
    }

    /**
     *  readProjectFile
     *  A method to read text file and create all project objects.
     * @param fileName ,a file that want to load/read
     * @return Project, project instance.
     * */
    public Project readProjectFile(String fileName)
    {
        ArrayList<String> projectData = new ArrayList<>();          /* Array list to store project data*/
        ArrayList<ArrayList<String>> taskData = new ArrayList<ArrayList<String>>(); /* ArrayList of ArrayList to store multiple tasks and their data*/
        ArrayList<ArrayList<String>> dependencyData = new ArrayList<ArrayList<String>>();   /*ArrayList of ArrayList to store multiple dependencies and their data*/
        int taskCount=0;
        int dependencyCount=0;
        openProjectFile(fileName);
        String currentLine ;
        while((currentLine = getNextLine())!=null)
        {
            String[] line = currentLine.split("\\|");
            String lineHeader = line[0];
            String lineData = line[1];
            switch (lineHeader)
            {
                case "project":
                    projectData.add(lineData);
                    break;
                case "ENDTASK":
                    taskCount++;
                    break;
                case "task":
                    taskData.add(new ArrayList<>());
                    taskData.get(taskCount).add(lineData);
                    break;
                case "ENDDEPENDENCY":
                    dependencyCount++;
                    break;
                case "dependency":
                    dependencyData.add(new ArrayList<>());
                    dependencyData.get(dependencyCount).add(lineData);
                    break;
                default:
                    System.out.println("ERROR : on reading");
                    break;
            }
        }
        /* Create Project object*/
        Project loadedProject = new Project(projectData.get(0),projectData.get(1),
                DateFormatter.formatStringToDate(projectData.get(2)));
        /* if the project already has ended Date*/
        if(projectData.size()==4) {
            loadedProject.setEndDate(DateFormatter.formatStringToDate(projectData.get(3)));
        }
        TaskManager loadedProjectTaskManager= loadedProject.getTaskManager();
        /* load tasks in the project*/
        for(int i=0;i<taskCount;i++)
        {
            Task loadedTask = new Task(taskData.get(i).get(0),taskData.get(i).get(1),Integer.parseInt(taskData.get(i).get(2)));
            /* if has start date*/
            if(taskData.get(i).size()>=4)
                loadedTask.setStartDate(DateFormatter.formatStringToDate(taskData.get(i).get(3)));
            /* if has end date*/
            if(taskData.get(i).size()>=5)
                loadedTask.setEndDate(DateFormatter.formatStringToDate(taskData.get(i).get(4)));
            loadedProjectTaskManager.addTask(loadedTask);
        }
        /*load dependency*/
        for(int j=0;j<dependencyCount;j++)
        {
            Task preDecessorTask = loadedProjectTaskManager.getTask(dependencyData.get(j).get(0));
            Task successorTask = loadedProjectTaskManager.getTask(dependencyData.get(j).get(1));
            if(preDecessorTask != null && successorTask != null)
            {
                loadedProjectTaskManager.addDependency(preDecessorTask,successorTask);
            }
        }
        close();
        return loadedProject;
    }


    /**
     * getAllFileName
     * A method to read all text file name in the directory
     * @return allFileName, string of all file names.
     * */
    public String getAllFileName()
    {
        Path currentPath = FileSystems.getDefault().getPath("").toAbsolutePath();
        System.out.println("Searching all files in " + currentPath + " ....");
        File folder = new File(String.valueOf(currentPath));
        File[] projectFileList = folder.listFiles((file, name) -> name.endsWith(".txt"));
        String allFileName = null;
        /* no text file in the directory*/
        if (projectFileList != null)
        {
            if(projectFileList.length==0)
            {
                return null;
            }
            else
            {
                allFileName = projectFileList[0].getName();
                allFileName = allFileName.concat("|");
                for (int i =1;i<projectFileList.length;i++)
                {
                    allFileName = allFileName.concat(projectFileList[i].getName());
                    allFileName = allFileName.concat("|");
                }
            }
        }
        return allFileName;
    }
}
