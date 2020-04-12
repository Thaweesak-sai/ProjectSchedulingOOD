import java.io.IOException;
import java.util.ArrayList;

public class ProjectManager {
    private ArrayList<Project> projectList;
    TextFileIO textFileIO = new TextFileIO();
    public ProjectManager(){

    }
    public Boolean addProject(Project project){
        try {
            projectList.add(project);
        }
        catch (Exception e)
        {
            System.out.println("ERROR : Fail to add project to a list");
            return false;
        }
        return true;
    }
    public void loadProject(){

    }
    public void printAllProject() throws IOException {
        textFileIO.getAllFileName();
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
}
