import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.util.Collection;
import java.util.LinkedList;

public class TaskCreator {


    private CloseableHttpClient client = HttpClients
            .custom().setConnectionManager(TaskExecutor.manager).build();


    public  Collection<Task> createTasks(int tasksQuantity){
        Collection<Task> tasks = new LinkedList<Task>();
        for(int i=0; i < tasksQuantity; i++){
            HttpGet get = new HttpGet(PropertiesReader.getHostName());
            tasks.add(new Task(get,client, i));
        }
        return tasks;
}
}
