
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import java.util.Collection;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskExecutor {

    private ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
    public static HttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
    private CloseableHttpClient client = HttpClients
            .custom().setConnectionManager(TaskExecutor.manager).build();
    Collection<Task> tasks;


    public void executeTasks(int taskQuantity, int intervalBetweenTasksInMilliseconds){
        tasks = Task.createRequestTasks(client,taskQuantity );
        int time = 0;
        for(Task task : tasks){
            scheduledExecutorService.schedule(task,time, TimeUnit.MILLISECONDS);
            time += intervalBetweenTasksInMilliseconds;
        }
        scheduledExecutorService.shutdown();
        manager.shutdown();
    }


    }


