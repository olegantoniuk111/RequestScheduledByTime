
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import java.time.Duration;
import java.util.Collection;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskExecutor {

    private ScheduledThreadPoolExecutor scheduledExecutorService ;
    private PoolingHttpClientConnectionManager manager;
    private CloseableHttpClient client;
    private int taskQuantity;
    private Duration duration;


    public TaskExecutor(int taskQuantity, int timeForExecution) {
        this.taskQuantity = taskQuantity;
        this.duration = calculateDuration(taskQuantity, timeForExecution);
        manager = new PoolingHttpClientConnectionManager();
        client = HttpClients
                .custom().setConnectionManager(manager).build();
        scheduledExecutorService = new ScheduledThreadPoolExecutor(taskQuantity);

    }
    private  Duration calculateDuration (int requestsQuantity, int timeForExecution){
        return Duration.ofSeconds(timeForExecution).dividedBy(requestsQuantity);
    }


    public void executeTasks( )  {
        Collection <Task> tasks = Task.createRequestTasks(client, taskQuantity);
        long time = 0;
        for(Task task : tasks){
            scheduledExecutorService.schedule(task, time, TimeUnit.NANOSECONDS);
            time += duration.toNanos();

        }
        stopTasksExecution(scheduledExecutorService);

    }

    private void stopTasksExecution(ScheduledThreadPoolExecutor scheduledExecutorService) {
        scheduledExecutorService.shutdown();
        try {
            boolean tasksDone;
            do{
                tasksDone = scheduledExecutorService.awaitTermination(500, TimeUnit.MILLISECONDS);
            }while (!tasksDone);
        }catch (InterruptedException e){
            System.out.println("Tasks execution was interrupted");
            scheduledExecutorService.shutdownNow();
            HttpClientUtils.closeQuietly(client);
            manager.shutdown();
        }finally {
            scheduledExecutorService.shutdownNow();
            HttpClientUtils.closeQuietly(client);
            manager.shutdown();

        }
    }

}