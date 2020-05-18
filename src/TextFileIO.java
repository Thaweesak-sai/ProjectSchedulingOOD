import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

public class TextFileIO {
    private BufferedReader reader = null;
    public Boolean openProjectFile(String fileName){
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
        return bOk;
    }
    public String getNextLine(String filename){
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
        PrintWriter writer = new PrintWriter(project.getName() + ".txt", String.valueOf(StandardCharsets.UTF_8));
        writer.println("project:" + project.getName());
        writer.println("project:" + project.getDesc());
        writer.println("project:" + project.getStartDate());
        writer.println("project:" + project.getEndDate());
        int numberOfTask = project.getTaskManager().getTaskList().size();
        List<Task> taskList = project.getTaskManager().getTaskList();
        for( int i=0;i < numberOfTask; i++) {
            List<Dependency> taskDependency = taskList.get(i).getDependencyList();
            writer.println("task " + i + ":" + taskList.get(i).getTaskName());
            writer.println("task " + i + ":" + taskList.get(i).getTaskDescription());
            writer.println("task " + i + ":" + taskList.get(i).getDuration());
            writer.println("task " + i + ":" + taskList.get(i).getStartDate());
            writer.println("task " + i + ":" + taskList.get(i).getEndDate());
            for (int j = 0; j < taskDependency.size(); j++) {
                writer.println("dependency " + i + ":" + taskDependency.get(j).getPreDecessorTask());
                writer.println("dependency " + i + ":" + taskDependency.get(j).getSuccessorTask());
            }
            /* ต้องมีข้อมูลอะไรบ้างใน file*/ /*จะเอา task มาใส่ยังไง*/
        }
        return true;
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
