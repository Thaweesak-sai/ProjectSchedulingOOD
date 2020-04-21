import java.io.IOException;
import java.util.ArrayList;

public class ProjectManager {
    private static ArrayList<Project> projectList = new ArrayList<>();
    TextFileIO textFileIO = new TextFileIO();
    public ProjectManager(){

    }
    public Boolean addProject(Project project){
            if(!projectList.add(project))
            {
                System.out.println("ERROR : Fail to add project to a list");
                return false;
            }
        return true;
    }
    public void loadAllProject(){
        String allFileNames = textFileIO.getAllFileName();
        String [] buffer = allFileNames.split(".");
        for(int i=0;i<buffer.length;i++)
        {
        }

        
    }
    public void printAllProject() throws IOException {
        System.out.println(textFileIO.getAllFileName());
    }
    public Boolean deleteProject(Project project) throws IOException {
        return textFileIO.deleteProjectFile(project);
    }
    public Project getProject(String projectName)
    {
        for(Project project : projectList){
            if(project.getName().equals(projectName))
            {
                return project;
            }
        }
        return null;
    }
    public Boolean save(Project project) throws IOException {
        textFileIO.writeProjectFile(project);
        return true;
    }
}
