package io.github.ITMO.weather_bot.bot;

import com.google.gson.Gson;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {
    public static String showWeather(Message message) {
        String showWeather;
        double latitude = message.getLocation().getLatitude();
        double longitude = message.getLocation().getLongitude();
        String myAPIkey = getToken(1);

        String urlStr = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&units=metric&APPID=" + myAPIkey;

        try {
            URL url = new URL(urlStr);
            URLConnection urlConnection = url.openConnection();

            Gson gson = new Gson();
            io.github.ITMO.weather_bot.model.weather.Weather weather =
                    gson.fromJson(new InputStreamReader(urlConnection.getInputStream()),
                            io.github.ITMO.weather_bot.model.weather.Weather.class);

            showWeather = "За окном " + weather.getMain().getTemp() + "°C. \nДавление " + pressureConverter(weather.getMain().getPressure()) + " мм.рт.ст.";
            return showWeather;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Не найдено";
    }

    public static String showForecast(Message message) {
        String showForecast;
        double latitude = message.getLocation().getLatitude();
        double longitude = message.getLocation().getLongitude();
        String myAPIkey = getToken(1);

        String urlStr = "https://api.openweathermap.org/data/2.5/forecast?lat="
                + latitude + "&lon=" + longitude + "&units=metric&APPID=" + myAPIkey;

        try {
            URL url = new URL(urlStr);
            URLConnection urlConnection = url.openConnection();

            Gson gson = new Gson();
            io.github.ITMO.weather_bot.model.weather_forecast.Forecast forecast =
                    gson.fromJson(new InputStreamReader(urlConnection.getInputStream()),
                            io.github.ITMO.weather_bot.model.weather_forecast.Forecast.class);

            showForecast = " тут прогноз" ;
            return showForecast;
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
