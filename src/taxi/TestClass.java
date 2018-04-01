package taxi;

public class TestClass {

    public static void main(String[] args) {
        int executorsNumber = 10;
        int clientsNumber = 100;

        for(int i = 0; i < executorsNumber; ++i){
            Dispatcher.getInstance().addExecutor(new Executor(i+1));
        }

        Client.setExecutorsNumber(executorsNumber);
        for(int i = 0; i < clientsNumber; ++i){
            new Client(i+1).start();
        }
    }

}
