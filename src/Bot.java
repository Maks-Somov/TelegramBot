import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class Bot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());//class for send message
        if(update.getMessage().getText().equals("hello")){
            sendMessage.setText("hi");
            try{
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else sendMessage.setText(update.getMessage().getText());
        try{
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
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
