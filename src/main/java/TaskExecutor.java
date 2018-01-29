
import org.apache.http.HttpClientConnection;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import java.util.Collection;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskExecutor {

    private ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);
    public static HttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();

    public void executeTasks(Collection<Task> tasks, int intervalBetweenTasksInMilliseconds){
        int time = 0;
        for(Task task : tasks){
            scheduledExecutorService.schedule(task,time, TimeUnit.MILLISECONDS);
            time += intervalBetweenTasksInMilliseconds;
        }
        scheduledExecutorService.shutdown();
        manager.shutdown();
    }


    }


