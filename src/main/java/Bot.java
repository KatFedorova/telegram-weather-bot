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
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static org.glassfish.jersey.client.ClientProperties.PROXY_PASSWORD;

public class Bot extends TelegramLongPollingBot {
    private static String BOT_NAME = "Kate_First_Weather_Bot";
    private static String BOT_TOKEN = "1066638100:AAGrpYXdmhwdw05QyZU8VZAXO3mkyVvaEq0";

    private static String PROXY_HOST = "166.62.121.76" /* proxy host */;
    private static Integer PROXY_PORT = 48329 /* proxy port */;

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
                    sendMsg(message, "Чем могу помочь?");
                    break;

                case "/settings":
                case "/настройки":
                    sendMsg(message, "Что будем настраивать?");
                    break;

                case "/привет":
                case "/start":
                    sendMsg(message, "Здравствуй! Давай узнаем погоду!");
                    break;
                case "Узнать погоду!":
                    sendMsg(message, "Тут как-будто погода и совет тепло одеваться");
                    break;
                default:
                    sendMsg(message, "Такой команды не существует. Отправь /start, чтобы начать работу," +
                            "/settings, чтобы настроить меня, или /help, если тебе нужна помощь.");

            }
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

    public String getBotUsername() {
        return BOT_NAME;
    }

    public String getBotToken() {
        return BOT_TOKEN;
    }

   /* public String showMeWeather() {
        String showWeather;
        double lat = 60.06715;
        double lon = 30.334128;
        String myAPIkey = "72c922a6d9d7cdb2d8a88561fbfb9cb3";

        String urlStr = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&units=metric&APPID=" + myAPIkey;

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

            showWeather = "За окном " +weather.getMain().getTemp() +"°C";
            return showWeather;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Не найдено";
    }*/
}
