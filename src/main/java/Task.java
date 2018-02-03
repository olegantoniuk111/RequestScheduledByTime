import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import java.time.LocalTime;
import java.util.*;

public class Task implements Runnable {
    private HttpGet httpGet;
    private CloseableHttpClient client;
    private HttpClientContext httpClientContext;
    private CloseableHttpResponse response;
    private int number;


    public Task(HttpGet httpGet, CloseableHttpClient client, int number) {
        this.httpGet = httpGet;
        this.client = client;
        this.number = number;
        this.httpClientContext = HttpClientContext.create();

    }

    public void run() {
        sendRequest();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return number == task.number &&
                Objects.equals(httpGet, task.httpGet) &&
                Objects.equals(client, task.client) &&
                Objects.equals(httpClientContext, task.httpClientContext) &&
                Objects.equals(response, task.response);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpGet, client, httpClientContext, response, number);
    }

    private void sendRequest() {
        try {
            logRequest();
            response = client.execute(httpGet, httpClientContext);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("exeption occur during request execution");
        }finally {
            HttpClientUtils.closeQuietly(response);
        }
    }

    private void logRequest(){
        System.out.println("request "+ number +" send at " + LocalTime.now().toString());
    }

    private void logResponse(){
        System.out.println("response "+ number + " back at"+ getTime() + "with status code" + response.getStatusLine().getStatusCode() );
    }

    private LocalTime getTime(){
        return LocalTime.now();
    }

    public static Collection<Task> createRequestTasks(CloseableHttpClient client, int quantity){
        Collection<Task> tasks = new LinkedHashSet<>();
        for(int i=0; i < quantity; i++){
            HttpGet get = new HttpGet(PropertiesReader.getHostName());
            tasks.add(new Task(get, client, i));
        }
        return tasks;

    }
}