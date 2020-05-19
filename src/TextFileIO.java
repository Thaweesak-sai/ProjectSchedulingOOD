import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextFileIO {
    private BufferedReader reader = null;
    public void openProjectFile(String fileName){
        boolean bOk = true;
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
            bOk = false;
            reader = null;
        }
    }
    public String getNextLine(){
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
    public void close(){
        try
        {
            reader.close();
        }
        catch (IOException ioe)
        {
            System.out.println("ERROR can't close the file");
        }
    }

    public boolean writeProjectFile(Project project) throws IOException {
        PrintWriter writer = new PrintWriter(new FileOutputStream(project.getName() + ".txt", Boolean.parseBoolean(String.valueOf(StandardCharsets.UTF_8))),false);
        writer.println("project|" + project.getName());
        writer.println("project|" + project.getDesc());
        writer.println("project|" + DateFormatter.formatDateToString(project.getStartDate()));
        if(project.getEndDate()!=null)
            writer.println("project|" + DateFormatter.formatDateToString(project.getEndDate()));
        int numberOfTask = project.getTaskManager().getAllTask().size();
        List<Task> taskList = project.getTaskManager().getAllTask();
        for( int i=0;i < numberOfTask; i++)
        {
            List<Dependency> taskDependency = taskList.get(i).getDependencyList();
            writer.println("task|" + taskList.get(i).getTaskName());
            writer.println("task|" + taskList.get(i).getTaskDescription());
            writer.println("task|" + taskList.get(i).getDuration());
            if(taskList.get(i).getStartDate()!=null)
                writer.println("task|" + DateFormatter.formatDateToString(taskList.get(i).getStartDate()));
            if(taskList.get(i).getEndDate()!=null)
                writer.println("task|" + DateFormatter.formatDateToString(taskList.get(i).getEndDate()));
            writer.println("ENDTASK| ");
            for (int j = 0; j < taskDependency.size(); j++)
            {
                writer.println("dependency|" + taskDependency.get(j).getPreDecessorTask().getTaskName());
                writer.println("dependency|" + taskDependency.get(j).getSuccessorTask().getTaskName());
                writer.println("ENDDEPENDENCY| ");
            }
        }
        writer.close();
        return true;
    }
    public Project readProjectFile(String fileName) throws ParseException {
        ArrayList<String> projectData = new ArrayList<>();
        ArrayList<ArrayList<String>> taskData = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> dependencyData = new ArrayList<ArrayList<String>>();
        int taskCount=0;
        int dependencyCount=0;
        openProjectFile(fileName);
        String currentLine = null;
        while((currentLine = getNextLine())!=null){
            String[] line = currentLine.split("\\|");
            String lineHeader = line[0];
            String lineData = line[1];
            switch (lineHeader) {
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
                    System.out.println("ERROR on reading");
                    break;
            }
        }
        Project loadedProject = new Project(projectData.get(0),projectData.get(1),
                DateFormatter.formatStringToDate(projectData.get(2)));
        if(projectData.size()==4) {
            loadedProject.setEndDate(DateFormatter.formatStringToDate(projectData.get(3)));
        }
        TaskManager loadedProjectTaskManager= loadedProject.getTaskManager();
        /* load tasks in the project*/
        for(int i=0;i<taskCount;i++)
        {
            Task loadedTask = new Task(taskData.get(i).get(0),taskData.get(i).get(1),Integer.parseInt(taskData.get(i).get(2)));
            if(taskData.get(i).size()>=4)
                loadedTask.setStartDate(DateFormatter.formatStringToDate(taskData.get(i).get(3)));
            if(taskData.get(i).size()>=5)
                loadedTask.setEndDate(DateFormatter.formatStringToDate(taskData.get(i).get(4)));
            loadedProjectTaskManager.addTask(loadedTask);
        }
        for(int j=0;j<dependencyCount;j++)
        {
            Task preDecessorTask = loadedProjectTaskManager.getTask(dependencyData.get(j).get(0));
            Task successorTask = loadedProjectTaskManager.getTask(dependencyData.get(j).get(1));
            preDecessorTask.addDependency(preDecessorTask,successorTask);
        }
        close();
        return loadedProject;
    }

    public boolean deleteProjectFile(Project project) throws IOException {
        Path currentPath = FileSystems.getDefault().getPath("").toAbsolutePath();
        try{
            File file = new File(currentPath+project.getName()+".txt");
            if(file.delete())
            {
                System.out.println("File "+project.getName()+" has been deleted successfully");
            }
            else
            {
                System.out.println("Failed to delete the file ' "+project.getName()+" '");
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String getAllFileName(){
        Path currentPath = FileSystems.getDefault().getPath("").toAbsolutePath();
        System.out.println("Searching all files in " + currentPath + " ....");
        File folder = new File(String.valueOf(currentPath));
        File[] projectFileList = folder.listFiles((file, name) -> name.endsWith(".txt"));
        if(projectFileList==null)
        {
            return "Project file can't be found in "+currentPath;
        }
        String allFileName = projectFileList[0].getName();
        allFileName = allFileName.concat("|");
        for (int i =1;i<projectFileList.length;i++) {
            allFileName = allFileName.concat(projectFileList[i].getName());
            allFileName = allFileName.concat("|");
        }
        return allFileName;
    }
}
