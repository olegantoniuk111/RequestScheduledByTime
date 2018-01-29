import java.util.Collection;

public class Main {
    public static void main(String[] args) {
        int taskQuantity = PropertiesReader.getRequestsQuantity();
        int interval = calculateIntervalinmilliseconds();

        TaskCreator creator = new TaskCreator();
        Collection<Task> tasks = creator.createTasks(taskQuantity);

        TaskExecutor executor = new TaskExecutor();
        executor.executeTasks(tasks, interval);

    }

    private static int calculateIntervalinmilliseconds (){
        return PropertiesReader.getTimeForExecution()*1000
                /PropertiesReader.getRequestsQuantity(); // calculate interval in milliseconds between  Tasks
    }

}
