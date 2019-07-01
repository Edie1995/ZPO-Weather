package projekt;


import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

/**
 * Klasa odpowiedzialna za zapis otrzymanych danych w formacie Json
 */
public class SaveJson {
    private ArrayList<Double> speed;
    private ArrayList<Double> temperature;
    private ArrayList<Double> pressure;
    private ArrayList<Double> humidyty;
    private ArrayList<String> time;
    private ArrayList<String> weather;

    /**
     * Konstruktor, przyjmujacy listy z danymi pogodowymi ze strony
     * @param speed
     * @param temperature
     * @param pressure
     * @param humidyty
     * @param time
     * @param weather
     */
    public SaveJson(ArrayList<Double> speed, ArrayList<Double> temperature, ArrayList<Double> pressure, ArrayList<Double> humidyty, ArrayList<String> time, ArrayList<String> weather) {
        this.speed = speed;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidyty = humidyty;
        this.weather = weather;
        this.time = time;
        save();
    }

    /**
     * Metoda odpowiedzialna za zapis otrzymanych danych do pliku w formacie txt, o nazwie i lokalizacji wybranej przez uzytkownika za pomoca funkcji FileChooser
     */
    public void save() {

        FileChooser chooser = new FileChooser();
        File file = chooser.showSaveDialog(new Stage());

        if (!file.getPath().isEmpty()) {
            try {
                BufferedWriter writer;
                if (file.getPath().contains(".txt"))
                    writer = new BufferedWriter(new FileWriter(file));
                else
                    writer = new BufferedWriter(new FileWriter(file + ".txt"));

                writer.write("{");

                for (int i = 0; i < speed.size(); i++) {
                    if (i == 0)
                        writer.write("response" + i + ":");
                    else
                        writer.write(",response" + i + ":");
                    writer.write("{\"temp\":");
                    writer.write(temperature.get(i).toString());
                    writer.write(",\"pressure\":");
                    writer.write(pressure.get(i).toString());
                    writer.write(",\"speed\":");
                    writer.write(speed.get(i).toString());
                    writer.write(",\"humidyty\":");
                    writer.write(humidyty.get(i).toString());
                    writer.write(",\"weather\":");
                    writer.write(weather.get(i));
                    writer.write(",\"time\":");
                    writer.write("\"" + time.get(i) + "\"}");
                }
                writer.write("}");

                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
