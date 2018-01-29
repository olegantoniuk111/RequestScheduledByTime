import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskExecutor {
    private PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
    private CloseableHttpClient client = HttpClients
            .custom().setConnectionManager(manager).build();
    private ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);


    public void executeTasks(int tasksAmount, int intervalBetweenTasksInMilliseconds){
        manager.setDefaultMaxPerRoute(5);
        Collection<Task> tasks = createTasks(tasksAmount);
        int time = 0;
        for(Task task : tasks){
            scheduledExecutorService.schedule(task,time, TimeUnit.MILLISECONDS);
            time += intervalBetweenTasksInMilliseconds;
        }
        scheduledExecutorService.shutdown();
        manager.shutdown();

    }

    private Collection<Task> createTasks(int tasksAmount){
        Collection<Task> tasks = new LinkedList<Task>();
        for(int i=0; i<tasksAmount; i++){
            HttpGet get = new HttpGet(PropertiesReader.getHostName());
            tasks.add(new Task(get,client, i));
        }
        return tasks;

    }

}
