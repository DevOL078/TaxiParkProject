package taxi.message;

import taxi.members.Dispatcher;

public class XMLMessage extends Thread implements AbstractMessage {

    private String text = "<?xml version='1.0' encoding='UTF-8'?>\n" +
            "<message>\n" +
            "<target id=\"%d\"/>\n" +
            "<sometags>\n" +
            "<data> </data>\n" +
            "<data> </data>\n" +
            "<data> </data>\n" +
            "</sometags>\n" +
            "</message>";

    private int clientID;
    private int executorID;


    public XMLMessage(int clientID, int executorID){
        this.clientID = clientID;
        this.executorID = executorID;
    }

    @Override
    public void send() {
        start();
    }

    @Override
    public void run(){
        System.out.println(String.format("Сообщение отправлено от клиента %d исполнителю %d", clientID, executorID));
        String message = String.format(text, executorID);
        Dispatcher.getInstance().receiveMessage(message, clientID);
    }
}
