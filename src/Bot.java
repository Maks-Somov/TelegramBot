import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Bot extends TelegramLongPollingBot {

    Book book = new Book();
    private long chat_id;
    private String text;

    @Override
    public void onUpdateReceived(Update update) {
        update.getUpdateId();
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());//class for send message
        chat_id = update.getMessage().getChatId();
        text = update.getMessage().getText();
        sendMessage.setText(input(text));
        try{
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String input(String msg) {
        String error = "Неудача, попробуй снова";
        if(msg.contains("hi") || msg.contains("hello") || msg.contains("Hi")){
            return "hi guy";
        }
        if(msg.contains("Информация о книге")){
            return getInfoBook();
        }
        if(msg.contains("/person")){
            msg = msg.replace("/person ","");
            return getInfoPerson(msg);
        }
        return error;
    }

    public String getInfoBook(){
        try {
            URL url = new URL(book.getImage());
            // берем сслыку на изображение
            BufferedImage img = ImageIO.read(url);
            // качаем изображение в буфер
            File outputfile = new File("image.jpg");
            //создаем новый файл в который поместим
            //скачанное изображение
            ImageIO.write(img, "jpg", outputfile);
            //преобразовуем наше буферное изображение
            //в новый файл
            SendPhoto sendPhoto = new SendPhoto().setChatId(chat_id);
            sendPhoto.setPhoto(outputfile);
            execute(sendPhoto);
        } catch (Exception e){
            System.out.println("File not found");
            e.printStackTrace();
        }

        String info = book.getTitle()
                + "\nАвтор" + book.getAutorName()
                + "\nЖанр" + book.getGenres()
                + "\n\nОписание\n" + book.getDescription()
                + "\n\nКоличество лайков " + book.getLikes()
                + "\n\nПоследние комментарии\n" + book.getCommentList();

        return info;
    }

    public String getInfoPerson(String msg){
        Autor aut = new Autor(msg);

        SendPhoto sendPhotoRequest = new SendPhoto();
        try(InputStream in = new URL(aut.getImg()).openStream()) {
            Files.copy(in, Paths.get("D:\\srgBook"));//выгружаем изображение из интернета
            sendPhotoRequest.setChatId(chat_id);
            sendPhotoRequest.setPhoto(new File("D:\\srgBook"));
            execute(sendPhotoRequest);//отправка картинки
            Files.delete(Paths.get("D:\\srgBook"));
        } catch (IOException | TelegramApiException e) {
            e.printStackTrace();
        }
        return aut.getInfoPerson();
    }


    @Override
    public String getBotUsername() {
        return "MyBotOnJava";
    }

    @Override
    public String getBotToken() {
        return "1116726600:AAE4GBI5mA9k4dpWA2hv_dHKW4dj9LTHJRc";
    }
}
