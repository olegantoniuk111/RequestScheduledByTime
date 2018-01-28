

public class Main {
    public static void main(String[] args) {


    }

    private static int calculateIntervalinmilliseconds (){
        return PropertiesReader.getTimeForExecution()*1000
                /PropertiesReader.getRequestsAmount(); // calculate interval in milliseconds for execution Task
    }

}
