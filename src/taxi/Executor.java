package taxi;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Executor implements AbstractExecutor {

    private int id;
    private String directoryPath;


    public Executor(int id){
        this.id = id;
        directoryPath = String.format("executors/%d", id);
        Dispatcher.getInstance().addExecutor(this);
    }

    @Override
    public synchronized void receiveMessage(String message) {
        System.out.println(String.format("Сообщение получено исполнителем %d", id ));
        saveMessage(message);
        System.out.println(String.format("Исполнитель %d занят", id));
        try {
            sleep();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(String.format("Исполнитель %d свободен", id));
    }

    private void saveMessage(String message){
         File directory = new File(directoryPath);
         boolean result = true;
         if(!(directory.exists() && directory.isDirectory())){
             result = directory.mkdir();
         }
         if(result){
             Date dateNow = new Date();
             SimpleDateFormat dateFormat = new SimpleDateFormat("hh_mm_ss");
             String path = directoryPath + "/" + dateFormat.format(dateNow) + ".message";
             try (FileWriter writer = new FileWriter(path)){
                 writer.write(message);
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }

    }

    private void sleep() throws InterruptedException {
        Thread.sleep(3000);
    }

    @Override
    public int getId() {
        return id;
    }

}
