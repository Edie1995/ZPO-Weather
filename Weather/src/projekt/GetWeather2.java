package projekt;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

/**
 * Klasa obslugujaca watek za pomoca implementacji interfejsu Runnable, oraz uaktualniajaca dane na podstawie interfejsu Observerable
 *
 * @see Runnable
 * @see Observerable
 */
public class GetWeather2 implements Runnable, Observerable {

    private Thread worker;
    private volatile boolean isRunning = false;
    private int interval;
    LocalDateTime time;
    private String cityName;
    private String weather;
    private volatile ArrayList<Observer> observers = new ArrayList<>();
    private Double temp;
    private Double humidyty;
    private Double pressure;
    private Double speed;


    /**
     * Konstuktor pozwalajacy na ustalenie odpowiedniego czasu wywolywania watku w programie, oraz wybrania odpowiedniego miasta
     * @param interval
     * @param cityName
     */
    public GetWeather2(int interval, String cityName) {
        this.interval = interval;
        this.cityName = cityName;
    }


    /**
     * Metoda rozpoczynajaca wykonywanie watku
     */
    public void start() {

        worker = new Thread(this, " Clock thread");
        worker.start();


    }

    /**
     * Metoda kończaca watek, za pomoca zmiany zminnej isRunning na false, przez co petla w metodzie run kończy sie
     */
    public void stop() {
        isRunning = false;
    }

    /**
     * Metoda wywolywujaca przerwanie watku
     */
    public void interrupt() {

        isRunning = false;
        worker.interrupt();
    }

    /**
     * Przeslonieta metoda z interfejsu Obsrverable dodajaca nowego observatora do tablicy observatorow
     */
    @Override
    public void addObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Przeslonieta metoda z interfejsu Obsrverable usuwajaca istniejacego observatora z tablicy observatorow
     */
    @Override
    public void removeObserver(Observer observer) {
        if (observers.contains(observer)) {
            observers.remove(observer);
        }
    }

    /**
     * Przeslonieta metoda z interfejsu Obsrverable uaktualniajaca dane obiektow korzystajacych z interfejsu Observer
     */
    @Override
    public void updateObservers() {
        for (Observer o : observers) {
            time = LocalDateTime.now();
            o.update(temp, pressure, humidyty, speed, weather, time);

        }
    }

    /**
     * Przeslonieta metoda z interfejsu Runnable uruchamiajaca watek o czasie uspienia rownym podanemu w konstruktorze tej klasy
     */
    @Override
    public void run() {

        isRunning = true;
        while (isRunning) {
            try {
                weatherUpdate();
                updateObservers();
                Thread.sleep(interval);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Failed to complete operation");
            }

        }
    }

    /**
     * Metoda pobierajace dane z serwera i na podstawie odpowiedzi pozyskujaca wybrane przez nas parametry.
     *
     * @return "404" jesli nie uda sie znalezc strony, w przeciwnym wypadku odpowiedz z servera w formie String
     */
    public String weatherUpdate() {

        StringBuffer response = new StringBuffer();
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&units=metric&APPID=861ae9cc557d394649a841a8e998e35b";

        try {
            response.delete(0, response.length());
            // Uspij watek na czas wskazany w sleepTime
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == 404) {
                return "404";
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
        } catch (ProtocolException e) {
            System.out.println("blad");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        JsonObject jsonObject = (new JsonParser()).parse(response.toString()).getAsJsonObject();
        JsonArray jsonArray;
        Map map = gson.fromJson(response.toString(), Map.class);
        Map mapWind = gson.fromJson(map.get("wind").toString(), Map.class);
        Map mapMain = gson.fromJson(map.get("main").toString(), Map.class);
        jsonArray = jsonObject.getAsJsonArray("weather");
        Map mapW = gson.fromJson(jsonArray.get(0).toString(), Map.class);
        weather = (String.valueOf(mapW.get("id")));
        speed = (Double) mapWind.get("speed");
        temp = (Double) mapMain.get("temp");
        humidyty = (Double) mapMain.get("humidity");
        pressure = (Double) mapMain.get("pressure");
        return response.toString();

    }
}
