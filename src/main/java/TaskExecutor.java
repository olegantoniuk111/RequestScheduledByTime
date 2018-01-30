
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import java.util.Collection;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskExecutor {

    private ScheduledThreadPoolExecutor scheduledExecutorService ;
    private   HttpClientConnectionManager manager;
    private CloseableHttpClient client;


    public TaskExecutor() {
        manager = new PoolingHttpClientConnectionManager();
        client = HttpClients
                .custom().setConnectionManager(manager).build();
    }

    public void executeTasks(int taskQuantity, int intervalBetweenTasksInMilliseconds)  {
        scheduledExecutorService = new ScheduledThreadPoolExecutor(taskQuantity);
        Collection <Task> tasks = Task.createRequestTasks(client, taskQuantity);
        int time = 0;
        for(Task task : tasks){
            scheduledExecutorService.schedule(task,time, TimeUnit.MILLISECONDS);
            time += intervalBetweenTasksInMilliseconds;
        }
        stopTasksExecution(scheduledExecutorService);
    }

    private void stopTasksExecution(ScheduledThreadPoolExecutor scheduledExecutorService) {
        scheduledExecutorService.shutdown();
        try {
            boolean tasksDone = false;
            do{
                tasksDone = scheduledExecutorService.awaitTermination(1, TimeUnit.MILLISECONDS);
            }while (!tasksDone);
        }catch (InterruptedException e){
            System.out.println("Tasks execution was interrupted");
            scheduledExecutorService.shutdownNow();
            manager.shutdown();
            HttpClientUtils.closeQuietly(client);
        }finally {
            manager.shutdown();
            HttpClientUtils.closeQuietly(client);
        }
    }

}


