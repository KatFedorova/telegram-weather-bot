package io.github.ITMO.weather_bot.bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static io.github.ITMO.weather_bot.bot.Utils.getToken;
import static io.github.ITMO.weather_bot.bot.Utils.showWeather;

public class Bot extends TelegramLongPollingBot {
    private static String BOT_NAME = "Kate_First_Weather_Bot";
    private static String BOT_TOKEN = getToken(0) ;

    private static String PROXY_HOST = "52.177.121.201" /* proxy host */;
    private static Integer PROXY_PORT = 1080 /* proxy port */;

    public Bot(DefaultBotOptions options) {
        super(options);
    }

    public static void main(String[] args) {
        try {
            ApiContextInitializer.init();
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi();


            // Create the TelegramBotsApi object to register bot
            DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

            botOptions.setProxyHost(PROXY_HOST);
            botOptions.setProxyPort(PROXY_PORT);
            // Select proxy type: [HTTP|SOCKS4|SOCKS5] (default: NO_PROXY)
            botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);

            telegramBotsApi.registerBot(new Bot(botOptions));


        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public void sendMsg(Message message, String reply) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(reply);
        try {
            setButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if ((message != null) && message.hasText()) {
            switch (message.getText()) {
                case "/help":
                case "/помощь":
                    sendMsg(message, "Чем я могу помочь?");
                    break;

                case "/settings":
                case "/настройки":
                    sendMsg(message, "Что будем настраивать?");
                    break;

                case "/привет":
                case "/start":
                    sendMsg(message, "Здравствуй! Давай узнаем погоду!");
                    break;
                case "/subscribe":
                case "/подписаться":

                    sendMsg(message, "Введи геолокацию для подписки");

                    break;
                case "Узнать погоду!":
                    sendMsg(message, "Жми на скрепку, чтобы сообщить свою геопозицию");
                    break;
                default:
                    sendMsg(message, "Такой команды нет");

            }
        }
        if ((message != null) && message.hasLocation()) {
            sendMsg(message, showWeather(message));


            DBConnection dbConnection = new DBConnection();
            double latitude = message.getLocation().getLatitude();
            double longitude = message.getLocation().getLongitude();

            dbConnection.open();
            dbConnection.insert(message.getChatId().toString(), latitude, longitude); //запись в бд
            dbConnection.close();

        }}


    public void setButtons(SendMessage sendMessage) {
       ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
       sendMessage.setReplyMarkup(replyKeyboardMarkup);
       replyKeyboardMarkup.setSelective(true);
       replyKeyboardMarkup.setResizeKeyboard(true);
       replyKeyboardMarkup.setOneTimeKeyboard(false);

       List<KeyboardRow> keyboardRowList = new ArrayList<>();
       KeyboardRow keyboardFirstRow = new KeyboardRow();
       keyboardFirstRow.add(new KeyboardButton("Узнать погоду!"));
       keyboardRowList.add(keyboardFirstRow);
       replyKeyboardMarkup.setKeyboard(keyboardRowList);

   }

    public String testCoordinate(Message message) {
        double latitude = message.getLocation().getLatitude();
        double longitude = message.getLocation().getLongitude();
        return "Latitude is: " + latitude + ", longitude is: " + longitude;
    }


    public String getBotUsername() {
        return BOT_NAME;
    }

    public String getBotToken() {
        return BOT_TOKEN;
    }






}
