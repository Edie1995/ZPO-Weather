package projekt;

import com.google.gson.*;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Klasa otierajaca zapisany wczesniej w formacie JSON plik
 */
public class OpenJson {


    private ArrayList<Double> temperature = new ArrayList<>();
    private ArrayList<Double> pressure = new ArrayList<>();
    private ArrayList<Double> humidyty = new ArrayList<>();
    private ArrayList<Double> speed = new ArrayList<>();
    private ArrayList<String> time = new ArrayList<>();
    private ArrayList<String> weaher = new ArrayList<>();

    /**
     * Metoda otierajaca plik za pomoca FileChooser i dodajaca odpowiednie odczytane wartosci do tablic
     */
    public void openFile() {

        JsonParser parser = new JsonParser();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(new Stage());
        if (!file.getPath().isEmpty() && file.getPath().contains(".txt")) {
            try {
                Object obj = parser.parse(new FileReader(file));
                JsonObject jsonObject = (JsonObject) obj;


                for (int i = 0; i < jsonObject.size(); i++) {
                    temperature.add(Double.valueOf(jsonObject.get("response" + i).getAsJsonObject().get("temp").toString()));
                    pressure.add(Double.valueOf(jsonObject.get("response" + i).getAsJsonObject().get("pressure").toString()));
                    humidyty.add(Double.valueOf(jsonObject.get("response" + i).getAsJsonObject().get("humidyty").toString()));
                    speed.add(Double.valueOf(jsonObject.get("response" + i).getAsJsonObject().get("speed").toString()));
                    weaher.add(jsonObject.get("response" + i).getAsJsonObject().get("weather").toString());
                    time.add(jsonObject.get("response" + i).getAsJsonObject().get("time").toString().replace('\"', ' '));

                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Niestety ten plik nie zawiera danych, które mogą zostać odczytane");
            alert.setHeaderText("NIEODPOWIEDNI PLIK");
            alert.showAndWait();
            throw new NoSuchElementException();
        }
    }

    /**
     * Metoda wyswietlajaca pobrane z pliku dane w polach tekstowych. Dotycza one odchylenia standardowego, min, max i sredniej odczytanych z pliku wartosci.
     @param textfiels
     @param param
     @param types


     */
    public void displayOpenedValues(Types types, Param param, Text textfiels) {
        Calculate calc = new Calculate();
        double wysw = 0;
        if (types.equals(Types.TEMPERATURE)) {

            if (param.equals(Param.AVER))
                wysw = calc.average(temperature);
            if (param.equals(Param.SAM))
                wysw = temperature.size();
            if (param.equals(Param.STD))
                wysw = calc.std(temperature);
            if (param.equals(Param.MIN))
                wysw = calc.min(temperature);
            if (param.equals(Param.MAX))
                wysw = calc.max(temperature);
            textfiels.setText(String.valueOf(wysw));
        }
        if (types.equals(Types.PRESSURE)) {

            if (param.equals(Param.AVER))
                wysw = calc.average(pressure);
            if (param.equals(Param.SAM))
                wysw = pressure.size();
            if (param.equals(Param.STD))
                wysw = calc.std(pressure);
            if (param.equals(Param.MIN))
                wysw = calc.min(pressure);
            if (param.equals(Param.MAX))
                wysw = calc.max(pressure);
            textfiels.setText(String.valueOf(wysw));
        }
        if (types.equals(Types.HUMIDYTY)) {


            if (param.equals(Param.AVER))
                wysw = calc.average(humidyty);
            if (param.equals(Param.SAM))
                wysw = humidyty.size();
            if (param.equals(Param.STD))
                wysw = calc.std(humidyty);
            if (param.equals(Param.MIN))
                wysw = calc.min(humidyty);
            if (param.equals(Param.MAX))
                wysw = calc.max(humidyty);
            textfiels.setText(String.valueOf(wysw));
        }
        if (types.equals(Types.SPEED)) {

            if (param.equals(Param.AVER))
                wysw = calc.average(speed);
            if (param.equals(Param.SAM))
                wysw = speed.size();
            if (param.equals(Param.STD))
                wysw = calc.std(speed);
            if (param.equals(Param.MIN))
                wysw = calc.min(speed);
            if (param.equals(Param.MAX))
                wysw = calc.max(speed);
            textfiels.setText(String.valueOf(wysw));
        }
    }

    /**
     * Metoda wyswietlajaca wykresy na podstawie odczytanych z pliku wartosci
     * @param temp_chart
     * @param types
     */
    public void displayOpenedChart(ScatterChart<String, Number> temp_chart, Types types) {

        XYChart.Series<String, Number> modelSeries = new XYChart.Series<>();
        for (int i = 0; i < temperature.size(); i++) {
            if (types.equals(Types.TEMPERATURE))
                modelSeries.getData().add(new XYChart.Data<>(time.get(i), temperature.get(i)));
            if (types.equals(Types.PRESSURE))
                modelSeries.getData().add(new XYChart.Data<>(time.get(i), pressure.get(i)));
            if (types.equals(Types.SPEED))
                modelSeries.getData().add(new XYChart.Data<>(time.get(i), speed.get(i)));
            if (types.equals(Types.HUMIDYTY))
                modelSeries.getData().add(new XYChart.Data<>(time.get(i), humidyty.get(i)));
        }
        temp_chart.getData().add(modelSeries);
    }
    /**
     * Metoda wyswietlajaca ikonke pogody na podstawie odczytanych z pliku wartosci
     * @param imageView
     */
    public void displayOpenedWeather(ImageView imageView) {
        ImageChooser imageChooser = new ImageChooser();
        imageChooser.display(imageView, weaher.get(weaher.size() - 1), LocalDateTime.now());
    }
}
