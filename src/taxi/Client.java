package taxi;

import java.util.Random;

public class Client extends Thread{

    private int id;
    private String text = "<?xml version='1.0' encoding='UTF-8'?>\n" +
            "<message>\n" +
            "<target id=\"%d\"/>\n" +
            "<sometags>\n" +
            "<data> </data>\n" +
            "<data> </data>\n" +
            "<data> </data>\n" +
            "</sometags>\n" +
            "</message>";
    private static Random rand = new Random();
    private static int executorsNumber = 0;

    public Client(int id){ this.id = id; }

    private void sendMessage(int idOfSender){
        System.out.println(String.format("Сообщение отправлено от клиента %d исполнителю %d", id, idOfSender));
        Dispatcher.getInstance().receiveMessage(String.format(text, idOfSender), id);
    }

    @Override
    public void run(){
        sendMessage(rand.nextInt(executorsNumber) + 1);
    }

    static void setExecutorsNumber(int number){
        executorsNumber = number;
    }

}
