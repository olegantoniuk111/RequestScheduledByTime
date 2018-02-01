
public class Main {

    public static void main(String[] args) {
        int taskQuantity = PropertiesReader.getRequestsQuantity();
        int timeForExecution = PropertiesReader.getTimeForExecution();

        TaskExecutor executor = new TaskExecutor(taskQuantity, timeForExecution);
        executor.executeTasks();


    }



}