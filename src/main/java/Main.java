import java.time.Duration;

public class Main {

    public static void main(String[] args) {
        int taskQuantity = PropertiesReader.getRequestsQuantity();
        long interval = calculateInterval();
        System.out.println(interval);
        TaskExecutor executor = new TaskExecutor(taskQuantity);
        executor.executeTasks(taskQuantity, interval);
    }

    private static long calculateInterval (){
        Duration duration = Duration.ofSeconds(PropertiesReader.getTimeForExecution());
        return duration.dividedBy(PropertiesReader.getRequestsQuantity()).toNanos();
    }

}