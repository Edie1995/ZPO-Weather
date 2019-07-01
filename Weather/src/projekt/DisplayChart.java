package projekt;

import javafx.application.Platform;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Klasa DisplayChart odpowiedzialna jest za bierzace wyswietlanie wykresow w aplikacji.
 *
 * @author Edyta Krukowska
 * @version 2
 */
public class DisplayChart implements Observer {

    private ScatterChart<String, Number> temp_chart;
    private XYChart.Series<String, Number> modelSeries;
    private Types types;
    private ArrayList<Double> temperatures = new ArrayList<>();
    private ArrayList<Double> speeds = new ArrayList<>();
    private ArrayList<Double> pressures = new ArrayList<>();
    private ArrayList<Double> rains = new ArrayList<>();
    private ArrayList<String> times = new ArrayList<>();
    private ArrayList<String> weathers = new ArrayList<>();

    /**
     * Konstruktor klasy, pozwalacjacy na precyzowanie typu danych i wykresu do narysowania.
     *
     * @param temp_chart jest to wykres, na krtorym po odwolaniu sie do klasy beda zaznaczane otrzymane dane
     * @param types      dotyczy typu wykresu jaki chcemy wykreslic
     */
    public DisplayChart(ScatterChart<String, Number> temp_chart, Types types) {
        this.temp_chart = temp_chart;
        this.types = types;
        modelSeries = new XYChart.Series<>();
        Platform.runLater(() -> temp_chart.getData().add(modelSeries));
    }


    /**
     * Metoda zwraca wartosc listy Stringow zawierajacych dane na temat pogody
     *
     * @return ArrayList String weathes
     */
    public ArrayList<String> getWeathers() {
        return weathers;
    }

    /**
     * Metoda zwraca wartosc listy Double zawierajacych dane na temat predkosci wiatru
     *
     * @return ArrayList Double speeds
     */
    public ArrayList<Double> getSpeeds() {
        return speeds;
    }

    /**
     * Metoda zwraca wartosc listy Double zawierajacych dane na temat cisnienia
     *
     * @return ArrayList Double pressure
     */
    public ArrayList<Double> getPressures() {
        return pressures;
    }

    /**
     * Metoda zwraca wartosc listy Double zawierajacych dane na temat wilgotnosci
     *
     * @return ArrayList Double rains
     */
    public ArrayList<Double> getRains() {
        return rains;
    }

    /**
     * Metoda zwraca wartosc listy Stringow zawierajacych dane na temat czasu podczas pomiarow
     *
     * @return ArrayList String times
     */
    public ArrayList<String> getTimes() {
        return times;
    }


    /**
     * Metoda zwraca wartosc listy Double zawierajacych dane na temat temperatury
     *
     * @return ArrayList Double temperature
     */
    public ArrayList<Double> getTemperatures() {
        return temperatures;
    }

    /**
     * Przeslonieta metoda umozliwajaca ukatualnianie danych pobieranych z watku i dodawanie ich do serii danych rysowanej na wykresie
     *
     * @param temperature wartosc temperatury otrzymana z odpowiedzi ze strony
     * @param pressure    wartosc cisnienia otrzymana z odpowiedzi ze strony
     * @param humidyty    wartosc wilgotnosci otrzymana z odpowiedzi ze strony
     * @param speed       wartosc predkosci wiatru otrzymana z odpowiedzi ze strony
     * @param weather     id pogody otrzymana z odpowiedzi ze strony
     * @param time        czas otrzymany z odpowiedzi ze strony
     */
    @Override
    public void update(double temperature, double pressure, double humidyty, double speed, String weather, LocalDateTime time) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        String timer = time.format(dtf);

        Platform.runLater(() -> {
            if (types.equals(Types.TEMPERATURE)) {
                modelSeries.getData().add(new XYChart.Data<>(timer, temperature));
                temperatures.add(temperature);
                times.add(timer);
                weathers.add(weather);
            }
            if (types.equals(Types.PRESSURE)) {
                modelSeries.getData().add(new XYChart.Data<>(timer, pressure));
                pressures.add(pressure);
            }
            if (types.equals(Types.SPEED)) {
                modelSeries.getData().add(new XYChart.Data<>(timer, speed));
                speeds.add(speed);
            }

            if (types.equals(Types.HUMIDYTY)) {
                modelSeries.getData().add(new XYChart.Data<>(timer, humidyty));
                rains.add(humidyty);
            }
        });
    }
}
