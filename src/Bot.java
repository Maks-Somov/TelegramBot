import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class Bot extends TelegramLongPollingBot {

    Book book = new Book();
    private long chat_id;

    @Override
    public void onUpdateReceived(Update update) {
        update.getUpdateId();
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());//class for send message
        chat_id = update.getMessage().getChatId();
        sendMessage.setText(input(update.getMessage().getText()));
        try{
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String input(String msg) {
        if(msg.contains("hi") || msg.contains("hello")){
            return "hi guy";
        }
        return msg;
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
