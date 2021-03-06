import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * ProjectManager Class
 * A class to manage project things (Singleton)
 *
 *
 * Created by   Jednipit Tantaletong (Pleum) 60070503411
 *              Thaweesak Saiwongse (Note)   60070503429
 *              16/04/2020
 * */
public class ProjectManager
{
    /** Instance of project manager*/
    private static ProjectManager projectManager_instance = null;
    /** List of projects */
    private static ArrayList<Project> projectList = new ArrayList<>();
    /** textFileIO Instance*/
    TextFileIO textFileIO = new TextFileIO();

    /**
     * Constructor
     */
    private ProjectManager()
    {

    }
    /**
     * getInstance
     * A method to return the instance of project manager
     * @return projectManager_instance, instance of project manager
     * */
    public static ProjectManager getInstance(){
        if (projectManager_instance == null)
            projectManager_instance = new ProjectManager();

        return projectManager_instance;
    }

    public Project createProject(String projectName, String projectDescription, LocalDate startDate )
    {
        Project project = new Project(projectName,projectDescription,startDate);
        addProject(project);
        return project;
    }
    /**
     * addProject
     * A method to add project to list
     * @param project , project
     *
     * */
    public void addProject(Project project)
    {
            if(!projectList.add(project))
            {
                System.out.println("ERROR : Fail to add project to a list");
            }
    }

    /**
     * loadProject
     * A method to load project from text file and add to project list
     * @param projectName project name
     * */
    public void loadProject(String projectName)
    {
        Project loadedProject = textFileIO.readProjectFile(projectName);
        addProject(loadedProject);
    }

    /**
     * printAllProject
     * A method to print all project name in the directory
     * @return splitedString, array string contains all file name
     * */
    public String[] getAllProjectName()
    {
        String stringReturned = textFileIO.getAllFileName();
        if(stringReturned==null)
        {
            return null;
        }
        String[] splitedString = stringReturned.split("\\|");
        for(int i=0;i<splitedString.length;i++ )
        {
            System.out.println(splitedString[i]);
        }
        return splitedString;
    }

    /**
     * getProject
     * A method to get the seleected project instance
     * @param projectName project name that want to have
     * @return project, project instance
     * */
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

    /**
     * save
     * A method to save a project to call textfileIO to save to text file
     * @param project project to save
     * @return  true if succeed
     *          false if fails
     * */
    public boolean save(Project project)
    {
        boolean bOK = true;
        textFileIO.writeProjectFile(project);
        return  bOK;
    }
}
