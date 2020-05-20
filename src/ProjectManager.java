import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

public class ProjectManager {
    private static ProjectManager projectManager_instance = null;
    private static ArrayList<Project> projectList = new ArrayList<>();
    TextFileIO textFileIO = new TextFileIO();

    private ProjectManager(){

    }
    public static ProjectManager getInstance(){
        if (projectManager_instance == null)
            projectManager_instance = new ProjectManager();

        return projectManager_instance;
    }


    public Boolean addProject(Project project)
    {
            if(!projectList.add(project))
            {
                System.out.println("ERROR : Fail to add project to a list");
                return false;
            }
        return true;
    }


    public void loadProject(String projectName) throws ParseException
    {
        Project loadedProject = textFileIO.readProjectFile(projectName);
        addProject(loadedProject);
    }

    public void printAllProject() throws IOException
    {
        String stringReturned = textFileIO.getAllFileName();
        String[] allFileName = stringReturned.split("\\|");
        for (String s : allFileName)
        {
            System.out.println(s);
        }

    }
    public Boolean deleteProject(Project project) throws IOException
    {
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

    public Boolean save(Project project) throws IOException
    {
        if(textFileIO.writeProjectFile(project))
        {
            return true;
        }
        return false;
    }
}
