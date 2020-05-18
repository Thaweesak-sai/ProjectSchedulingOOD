import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
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
    /**
     *  แก้ตรงต้อง parse เป็น String ให้หมดเพื่อ make sure
     * */
    public boolean writeProjectFile(Project project) throws IOException {
        PrintWriter writer = new PrintWriter(project.getName() + ".txt", String.valueOf(StandardCharsets.UTF_8));
        writer.println("project:" + project.getName());
        writer.println("project:" + project.getDesc());
        writer.println("project:" + project.getStartDate());
        writer.println("project:" + project.getEndDate());
        int numberOfTask = project.getTaskManager().getTaskList().size();
        List<Task> taskList = project.getTaskManager().getTaskList();
        for( int i=0;i < numberOfTask; i++) {
            List<Dependency> taskDependency = taskList.get(i).getDependencyList();
            writer.println("task:" + taskList.get(i).getTaskName());
            writer.println("task:" + taskList.get(i).getTaskDescription());
            writer.println("task:" + taskList.get(i).getDuration());
            writer.println("task:" + taskList.get(i).getStartDate());
            writer.println("task:" + taskList.get(i).getEndDate());
            writer.println("ENDTASK: ");
            for (int j = 0; j < taskDependency.size(); j++) {
                writer.println("dependency:" + taskDependency.get(j).getPreDecessorTask());
                writer.println("dependency:" + taskDependency.get(j).getSuccessorTask());
                writer.println("ENDDEPENDENCY: ");
            }
        }
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
            String[] line = currentLine.split(":");
            String lineHeader = line[0];
            String lineData = line[1];
            if(lineHeader.equals("project"))
            {
                projectData.add(lineData);
            }
            else if (lineHeader.equals("ENDTASK"))
            {
                taskCount++;
            }
            else if(lineHeader.equals("task"))
            {
                taskData.get(taskCount).add(lineData);

            }
            else if (lineHeader.equals("ENDDEPENDENCY"))
            {
                dependencyCount++;
            }
            else if(lineHeader.equals("dependency"))
            {
                dependencyData.get(dependencyCount).add(lineData);
            }
            else
            {
                System.out.println("ERROR on reading");
                break;
            }
        }
        Project loadedProject = new Project(projectData.get(0),projectData.get(1),
                DateFormatter.formatStringToDate(projectData.get(2)));
        if(projectData.get(3)!=null) {
            loadedProject.setEndDate(DateFormatter.formatStringToDate(projectData.get(3)));
        }
        for(int i=0;i<taskCount;i++)
        {
            Task loadedTask = new Task(taskData.get(i).get(0),taskData.get(i).get(1),Integer.parseInt(taskData.get(i).get(2)));
            if(taskData.get(i).get(3)!=null)
                loadedTask.setStartDate(DateFormatter.formatStringToDate(taskData.get(i).get(3)));
            if(taskData.get(i).get(4)!=null)
                loadedTask.setEndDate(DateFormatter.formatStringToDate(taskData.get(i).get(4)));
            for(int j=0;j<dependencyCount;j++)
            {
                /* น่าจะต้องสร้าง task ให้หมดแล้วค่อยสร้าง dependency*/
//                Dependency loadedDependency = new Dependency(dependencyData.);
            }
        }
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
        System.out.println("Searching all files..." + currentPath);
        File folder = new File(String.valueOf(currentPath));
        File[] projectFileList = folder.listFiles((file, name) -> name.endsWith(".txt"));
        StringBuilder filesName = null;
        if(projectFileList==null)
        {
            return "Project file can't be found in "+currentPath;
        }
       for (File file :projectFileList){
           filesName.append(file.getName());
           filesName.append(".");
       }
       return filesName.toString();
    }
}
