package taxi.members;

//Используется паттерн проектирования Фабричный метод
public interface AbstractExecutor {

    void receiveMessage(String message);
    int getId();

}
