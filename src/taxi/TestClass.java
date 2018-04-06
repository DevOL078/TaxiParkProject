package taxi;

import taxi.factory.AbstractMessageFactory;
import taxi.factory.XMLMessageFactory;
import taxi.members.Client;
import taxi.members.Dispatcher;
import taxi.members.Executor;

import java.util.Random;

public class TestClass {

    public static void main(String[] args) {
        int executorsNumber = 10;
        int clientsNumber = 100;

        for(int i = 0; i < executorsNumber; ++i){
            Dispatcher.getInstance().addExecutor(new Executor(i+1));
        }

        Random random = new Random();
        AbstractMessageFactory messageFactory = new XMLMessageFactory();
        for(int i = 0; i < clientsNumber; ++i){
            new Client(i+1, messageFactory).sendMessage(random.nextInt(10) + 1);
        }
    }

}
