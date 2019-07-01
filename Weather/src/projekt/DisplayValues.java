package projekt;

import javafx.application.Platform;
import javafx.scene.text.Text;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Klasa ta jest odpowiedzialna za wyswietlanie wartosci obliczonych wartosci odchylenia standardowego, sredniej, mini max funkcji oraz ilosci pobranych danych
 *
 * @see Types
 * @see Param
 * @see Calculate
 */

public class DisplayValues implements Observer {

    private int counter;
    private Types types;
    private Param param;


    private double wysw;
    private ArrayList<Double> temp = new ArrayList<>();
    private ArrayList<Double> wind = new ArrayList<>();
    private ArrayList<Double> press = new ArrayList<>();
    private ArrayList<Double> hum = new ArrayList<>();
    private Text textfiels;

    /**
     * Konstruktor pozwalajacy na wybor typu, parametrow i pola tekstowego na ktorym ma zostac wyswietlona obliczona wartosc
     * @param types
     * @param textfiels
     * @param param
     */
    public DisplayValues(Types types, Text textfiels, Param param) {
        this.types = types;
        this.textfiels = textfiels;
        this.param = param;
    }

    /**
     * Metoda pobierajaca zaktualizowane dane i na tej podstawie generujaca odpowiednie dane na wybranych polach tekstowych
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
        Calculate calc = new Calculate();

        counter++;
        Platform.runLater(() -> {
            if (types.equals(Types.TEMPERATURE)) {
                temp.add(temperature);
                if (param.equals(Param.AVER))
                    wysw = calc.average(temp);
                if (param.equals(Param.SAM))
                    wysw = counter;
                if (param.equals(Param.STD))
                    wysw = calc.std(temp);
                if (param.equals(Param.MIN))
                    wysw = calc.min(temp);
                if (param.equals(Param.MAX))
                    wysw = calc.max(temp);
                textfiels.setText(String.valueOf(wysw));

            }
            if (types.equals(Types.PRESSURE)) {
                press.add(pressure);
                if (param.equals(Param.AVER))
                    wysw = calc.average(press);
                if (param.equals(Param.SAM))
                    wysw = counter;
                if (param.equals(Param.STD))
                    wysw = calc.std(press);
                if (param.equals(Param.MIN))
                    wysw = calc.min(press);
                if (param.equals(Param.MAX))
                    wysw = calc.max(press);
                textfiels.setText(String.valueOf(wysw));
            }
            if (types.equals(Types.HUMIDYTY)) {

                hum.add(humidyty);
                if (param.equals(Param.AVER))
                    wysw = calc.average(hum);
                if (param.equals(Param.SAM))
                    wysw = counter;
                if (param.equals(Param.STD))
                    wysw = calc.std(hum);
                if (param.equals(Param.MIN))
                    wysw = calc.min(hum);
                if (param.equals(Param.MAX))
                    wysw = calc.max(hum);
                textfiels.setText(String.valueOf(wysw));
            }
            if (types.equals(Types.SPEED)) {
                wind.add(speed);
                if (param.equals(Param.AVER))
                    wysw = calc.average(wind);
                if (param.equals(Param.SAM))
                    wysw = counter;
                if (param.equals(Param.STD))
                    wysw = calc.std(wind);
                if (param.equals(Param.MIN))
                    wysw = calc.min(wind);
                if (param.equals(Param.MAX))
                    wysw = calc.max(wind);
                textfiels.setText(String.valueOf(wysw));
            }
            if (types.equals(Types.TIME)) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd \n HH:mm:ss");
                String timer = time.format(dtf);
                textfiels.setText(timer);

            }
        });


    }


}