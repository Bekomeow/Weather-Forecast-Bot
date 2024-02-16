package Bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Bot extends TelegramLongPollingBot {
    private final Weather weather = new Weather();

    public static void main(String[] args) {
        try {
            Bot bot = new Bot();
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return "BekoWeatherBot";
    }

    @Override
    public String getBotToken() {
        return "6658296629:AAERnkefRZy3zUZeMmDJQhuPbadI-0rLZJg";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            switch(messageText) {
                case "/start":
                    sendMessage(chatId, "Hi! I'll help you find out the weather, just write the name of the city.");
                    break;
                case "/help":
                    sendMessage(chatId, "To find out the weather, send the name of the city in this form: \n[Сity_Name погода] \nIf there is no such city, I will keep silent!\n"+
                            "");
                    break;
                default:
                    if(messageText.split(" ")[1].toLowerCase().equals("погода")) {
                        try {
                            sendMessage(chatId, weather.getWeather(messageText.split(" ")[0]));
                        } catch (Exception e) {
                            sendMessage(chatId, "This city isn't found");
                        }
                    }
            }
        }
    }

    public void sendMessage(Long chatId, String messageText) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(messageText);
        sendMessage.setChatId(chatId);

        try {
            executeAsync(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
