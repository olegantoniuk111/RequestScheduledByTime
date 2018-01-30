

public class Main {

    public static void main(String[] args) {
        int taskQuantity = PropertiesReader.getRequestsQuantity();
        int interval = calculateIntervalinmilliseconds();

        TaskExecutor executor = new TaskExecutor();
        executor.executeTasks(taskQuantity, interval);
    }

    private static int calculateIntervalinmilliseconds (){
        return PropertiesReader.getTimeForExecution()*1000
                /PropertiesReader.getRequestsQuantity(); // calculate interval in milliseconds between  Tasks
    }

}
