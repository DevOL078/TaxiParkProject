package taxi.members;

import taxi.factory.AbstractMessageFactory;

public class Client{

    private int id;
    private AbstractMessageFactory messageFactory;

    public Client(int id, AbstractMessageFactory messageFactory){
        this.id = id;
        this.messageFactory = messageFactory;
    }

    public void sendMessage(int executorID){
        messageFactory.createMessage(id, executorID).send();
    }

}
