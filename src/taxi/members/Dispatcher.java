package taxi.members;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;

//Используется паттерн проектирования Singleton
public class Dispatcher {

    private static Dispatcher dispatcher = new Dispatcher();
    private HashMap<Integer, AbstractExecutor> executors  = new HashMap<>();
    private final static Object locker = new Object();
    private static int messageId = 1;
    private final static int MAX_ID = 1000000;

    private Dispatcher(){
        File executorsDir = new File("executors");
        if(!(executorsDir.exists() && executorsDir.isDirectory())){
            boolean result = executorsDir.mkdir();
            if(result){
                System.out.println("Папка executors создана\n");
            }
        }
    }

    public static Dispatcher getInstance(){
        return dispatcher;
    }

    public void addExecutor(AbstractExecutor newExecutor){
        executors.put(newExecutor.getId(), newExecutor);
    }

    public void receiveMessage(String message, int clientID){
        if(checkMessage(message)){
            int executorId;
            executorId = getExecutorId(message);
            if(executorId != -1) {
                System.out.println(String.format("Получено сообщение от клиента %d исполнителю %d", clientID, executorId));
                synchronized (locker) {
                    message = addDispatchedId(message);
                    messageId++;
                    if(messageId > MAX_ID){
                        messageId = 1;
                    }
                }
                System.out.println(String.format("Сообщение отправляется исполнителю %d", executorId));
                sendMessage(message, executorId);
            }
            else{
                System.out.println("Ошибка в теге target");
            }
        }
        else{
            System.out.println("Ошибка формата сообщения!");
        }
    }

    private void sendMessage(String message, int idOfExecutor){
        executors.get(idOfExecutor).receiveMessage(message);
    }

    private boolean checkMessage(String text){
        Pattern messageTagOpen = Pattern.compile("<message>");
        Pattern messageTagClose = Pattern.compile("</message>");

        Matcher search = messageTagOpen.matcher(text);
        int count = 0;
        while(search.find()){
            count++;
        }
        if(count != 1){
            return false;
        }

        search = messageTagClose.matcher(text);
        count = 0;
        while(search.find()){
            count++;
        }
        return count == 1;
    }

    private int getExecutorId(String text){
        Pattern targetTag = Pattern.compile("<target id=\"(\\d)*\"/>\n");
        Matcher search = targetTag.matcher(text);
        if(search.find()){
            String target = search.group();
            Pattern idPattern = Pattern.compile("(\\d)+");
            Matcher searchNumber = idPattern.matcher(target);
            if(searchNumber.find()){
                String numberStr = searchNumber.group();
                return Integer.parseInt(numberStr);
            }
        }
        return -1;
    }

    private String addDispatchedId(String text){
        Pattern pattern = Pattern.compile("<message>");
        Matcher search = pattern.matcher(text);
        if(search.find()){
            int startIndex = search.end();
            String newTag = String.format("\n<dispatched id=\"%d\"/>\n", messageId);
            text = text.substring(0, startIndex) + newTag + text.substring(startIndex + 1);
        }
        return text;
    }

}
