import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ApiTest {/*
    public static void main(String[] args) {
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

          System.out.print("За окном ");
          System.out.print(weather.getMain().getTemp());
          System.out.println("°C");

          System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static int fahrenheitToCelsius(double tempFahrenheit) {
        int tempCelsius = (int)((tempFahrenheit - 32)/ 1.8);
        return tempCelsius;
    }

    public static int kelvinToCelsius(double tempFahrenheit) {
        int tempCelsius = (int)(tempFahrenheit - 273.15);
        return tempCelsius;
    }
*/}
