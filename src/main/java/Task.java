import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import java.io.IOException;
import java.time.LocalTime;

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

    private void sendRequest() {
        try {
            System.out.println("task "+ number +" started at " + getTime());
            response = client.execute(httpGet, httpClientContext);

        } catch (IOException e) {
            System.out.println("exeption occur during request execution");
        }finally {
            try {
                response.close();
            } catch (IOException e) {
                System.out.println("exeption occur during releasing response resources");
            }
        }
    }

    private LocalTime getTime(){
        return LocalTime.now();
    }
}
