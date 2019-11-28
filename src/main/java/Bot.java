import com.google.gson.Gson;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
                    sendMsg(message, "Чем я могу помочь?" +getToken(0));
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

                    sendMsg(message, "Этого я ещё не умею, но скоро научусь ^^' ");
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
        }
    }

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

    public static String showWeather(Message message) {
        String showWeather;
        double latitude = message.getLocation().getLatitude();
        double longitude = message.getLocation().getLongitude();
        String myAPIkey = getToken(1);

        String urlStr = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&units=metric&APPID=" + myAPIkey;

        try {
            URL url = new URL(urlStr);
            URLConnection urlConnection = url.openConnection();
            //  BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            //  String input;
            //  while ((input = br.readLine()) != null) {
            //      System.out.println(input);
            //  }
            //  br.close();

            Gson gson = new Gson();
            Weather.Weather weather = gson.fromJson(new InputStreamReader(urlConnection.getInputStream()), Weather.Weather.class);

            showWeather = "За окном " +weather.getMain().getTemp() +"°C. \nДавление " + pressureConverter(weather.getMain().getPressure()) +" мм.рт.ст.";
            return showWeather;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Не найдено";
    }
    public static String pressureConverter(double value) {
        return String.format("%.1f", value / 1.333);
    }

    public static String getToken(int i) {
        String pathName = "C:\\Keys\\botKeys.txt";
        List<String> keys = new ArrayList<>();
        Path path = Paths.get(pathName);

        try (Stream<String> lineStream = Files.lines(path)) {
            keys = lineStream.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return keys.get(i);
    }

}
