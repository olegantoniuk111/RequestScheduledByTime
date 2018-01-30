
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskExecutor {

    private ScheduledExecutorService scheduledExecutorService ;
    public static HttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
    private CloseableHttpClient client = HttpClients
            .custom().setConnectionManager(TaskExecutor.manager).build();



    public void executeTasks(int taskQuantity, int intervalBetweenTasksInMilliseconds)  {
        scheduledExecutorService = new ScheduledThreadPoolExecutor(taskQuantity);
        Collection <Task> tasks = Task.createRequestTasks(client, taskQuantity);
        int time = 0;
        for(Task task : tasks){
            scheduledExecutorService.schedule(task,time, TimeUnit.MILLISECONDS);
            time += intervalBetweenTasksInMilliseconds;
        }
            scheduledExecutorService.shutdown();


    }

    private void shutDownexecutor(ScheduledExecutorService scheduledExecutorService) {
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


