import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

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
        PrintWriter writer = new PrintWriter(project.getName()+".txt", StandardCharsets.UTF_8);
        writer.println(project.getName());
        writer.println(project.getDesc());
        writer.println(project.getStartDate());
        writer.println(project.getEndDate());
        /* ต้องมีข้อมูลอะไรบ้างใน file*/ /*จะเอา task มาใส่ยังไง*/
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

    public String getAllFileName() throws IOException {
        Path currentPath = FileSystems.getDefault().getPath("").toAbsolutePath();
        File folder = new File(String.valueOf(currentPath));
        File[] projectFileList = folder.listFiles((file, name) -> name.endsWith(".txt"));
        StringBuilder filesName = null;
        if(projectFileList==null)
        {
            return "Project file can't be found in "+currentPath;
        }
       for (File file :projectFileList){
           filesName.append(file.getName());
       }
       return filesName.toString();
    }
}
