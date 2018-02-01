import java.time.Duration;

public class Main {

    public static void main(String[] args) {
        int taskQuantity = PropertiesReader.getRequestsQuantity();
        Duration duration = PropertiesReader.calculateDuration();

        TaskExecutor executor = new TaskExecutor(taskQuantity, duration);
        executor.executeTasks();


    }



}