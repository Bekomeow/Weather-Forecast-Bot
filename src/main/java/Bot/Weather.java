package Bot;

import org.json.JSONObject;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Weather {
    private String weatherText;
    private String language;
    private String APIkey = "9714754a807fba1c9a40b5eb1e9d8f8f";
    public static String getUrlContent(String urlAddress) throws Exception {
        StringBuffer content = new StringBuffer();

        URL url = new URL(urlAddress);
        URLConnection urlConnection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

        String line;
        while((line = bufferedReader.readLine()) != null) {
            content.append(line + "\n");
        }

        bufferedReader.close();

        return content.toString();
    }

    public String getWeather(String city) throws Exception {

        String output = getUrlContent("https://api.openweathermap.org/data/2.5/weather?q="+
                city +"&appid="+APIkey+"&units=metric");

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        if(!output.isEmpty()) {
            JSONObject jsonObject = new JSONObject(output);
            weatherText = "Temperature:   "+jsonObject.getJSONObject("main").getDouble("temp")+" °C\n" +
                    "Feels Like:   "+jsonObject.getJSONObject("main").getDouble("feels_like")+" °C\n" +
                    "Humidity:   "+jsonObject.getJSONObject("main").getInt("humidity")+" %\n" +
                    "Wind:   "+jsonObject.getJSONObject("wind").getInt("speed")+" m/s\n" +
                    "Pressure:   "+jsonObject.getJSONObject("main").getDouble("pressure")+" mbar\n\n"+
                    dateFormat.format(date);
        }
        return weatherText;
    }
}
