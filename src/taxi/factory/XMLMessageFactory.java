package taxi.factory;

import taxi.message.AbstractMessage;
import taxi.message.XMLMessage;

public class XMLMessageFactory implements AbstractMessageFactory{

    @Override
    public AbstractMessage createMessage(int clientID, int executorID) {
        return new XMLMessage(clientID, executorID);
    }

}
