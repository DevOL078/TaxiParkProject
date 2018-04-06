package taxi.factory;

import taxi.message.AbstractMessage;

//Используется паттерн проектирования Абстрактная фабрика
public interface AbstractMessageFactory {

    AbstractMessage createMessage (int clientID, int executorID);

}
