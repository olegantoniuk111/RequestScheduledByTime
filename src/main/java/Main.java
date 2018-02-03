
public class Main {

    public static void main(String[] args) {
        int taskQuantity = PropertiesReader.getRequestsQuantity();
        int timeForExecution = PropertiesReader.getTimeForExecution();
        String host = PropertiesReader.getHostName();

        TaskExecutor executor = new TaskExecutor(taskQuantity, timeForExecution, host);
        executor.executeTasks();


    }



}