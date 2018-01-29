

public class Main {
    public static void main(String[] args) {
        TaskExecutor executor = new TaskExecutor();
        executor.executeTasks(PropertiesReader.getRequestsAmount(), calculateIntervalinmilliseconds());
    }

    private static int calculateIntervalinmilliseconds (){
        return PropertiesReader.getTimeForExecution()*1000
                /PropertiesReader.getRequestsAmount(); // calculate interval in milliseconds for execution Task
    }

}
