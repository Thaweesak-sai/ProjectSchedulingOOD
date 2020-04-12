import java.util.ArrayList;

public class ProjectManager {
    private ArrayList<Project> projectList;

    public ProjectManager(){

    }
    public Boolean addProject(Project project){
        return true;
    }
    public void loadProject(){

    }
    public void printAllProject(){

    }
    public Boolean deleteProject(Project project){
        return true;
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
