package projekt;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Klasa odpowiedzialna za cała funkcjonalnosc Interfejsu uzytkownika
 */
public class Controller {
    private GetWeather2 getWeather;
    private DisplayChart chatTemp;
    private DisplayChart chartPress;
    private DisplayChart chartSpeed;
    private DisplayChart chartHumid;
    private OpenJson open;
    private DisplayImage displayImage;


    @FXML
    private Text dataWindow;

    @FXML
    private TextField city;


    @FXML
    private ImageView weatherImage;


    @FXML
    private ScatterChart<String, Number> pressureChart;


    @FXML
    private ScatterChart<String, Number> temperatureChart;


    @FXML
    private ScatterChart<String, Number> windChart;


    @FXML
    private ScatterChart<String, Number> rainfallChart;


    @FXML
    private Text numberOfSamples;


    @FXML
    private Text minValueTemp;

    @FXML
    private Text minValueSpeed;

    @FXML
    private Text minValuePress;

    @FXML
    private Text minValueHum;

    @FXML
    private Text maxValueTemp;

    @FXML
    private Text maxValueSpeed;

    @FXML
    private Text maxValuePress;

    @FXML
    private Text maxValueHum;

    @FXML
    private Text stdTemp;

    @FXML
    private Text stdSpeed;

    @FXML
    private Text stdPress;

    @FXML
    private Text stdHum;

    @FXML
    private Text averageTemp;

    @FXML
    private Text averageSpeed;

    @FXML
    private Text averagePress;

    @FXML
    private Text averageHum;

    @FXML
    private CheckBox temperatureCheck;

    @FXML
    private CheckBox rainfallCheck;

    @FXML
    private CheckBox windCheck;

    @FXML
    private CheckBox pressureCheck;

    @FXML
    private Button startButton;

    @FXML
    private Button pauseButton;


    @FXML
    private Button stopButton;

    @FXML
    private Button resume;

    /**
     * Metoda wywoływana po nacisnieciu Open Javadoc, otwiera w domyslnej przegladarce plik javadoc zawierajacy dokumentacje aplikacji
     */
    @FXML
    void openJavadocClicked(ActionEvent event) {
        File htmlFile = new File("D:\\Uczelnia\\5 semestr\\ZPO1\\Projekt\\index.html");
        try {
            Desktop.getDesktop().browse(htmlFile.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda wywoływana po nacisnieciu Exit, kończaca program
     */
    @FXML
    void exitMenu(ActionEvent event) {
        Platform.exit();
    }

    /**
     * Metoda wywoływana po nacisnieciu Open na pasku Menu. Odpowiedzialna jest za wywołanie odpowiednich metod i klas otwierajacych zapisany obieky
     *
     * @see OpenJson
     */
    @FXML
    void openMenu(ActionEvent event) {
        getWeather.stop();
        open = new OpenJson();
        open.openFile();
        pressureChart.getData().clear();
        temperatureChart.getData().clear();
        rainfallChart.getData().clear();
        windChart.getData().clear();
        openHum();
        openPress();
        openSpeed();
        openTemp();
    }

    /**
     * Metoda odpowiedzialana za zapis do pliku istniejacych danych lub wyswietleniu informacji o ich braku
     *
     * @see SaveJson
     */
    @FXML
    void saveMenu(ActionEvent event) {
        try {
            displayImage = new DisplayImage(weatherImage);
            new SaveJson(chartSpeed.getSpeeds(), chatTemp.getTemperatures(), chartPress.getPressures(), chartHumid.getRains(), chatTemp.getTimes(), chatTemp.getWeathers());
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("NIE MA DANYCH DO ZAPISU");
            alert.showAndWait();
        }
    }

    /**
     * Metoda odpowiedzialna za wstrzymanie watku
     */
    @FXML
    void pauseClicked(ActionEvent event) {
        pauseButton.setDisable(true);
        startButton.setDisable(true);
        resume.setDisable(false);
        getWeather.interrupt();

    }

    /**
     * Po nacisnieciu przycisku start uruchomiane jest szereg czynnosci programu. Tworzone sa wykresy, uruchamiany jest
     * watek, z ktorego pobierane sa dane, zaczynaja byc wyswietlane informacje o stanie pogody. W przypadku braku
     * wyszukiwanego miasta w bazie wyswietlany jest Alert.
     *
     * @see GetWeather2
     */
    @FXML
    void startClicked(ActionEvent event) throws InterruptedException {

        pressureChart.getData().clear();
        temperatureChart.getData().clear();
        rainfallChart.getData().clear();
        windChart.getData().clear();
        startButton.setDisable(true);
        resume.setDisable(true);
        pauseButton.setDisable(false);
        stopButton.setDisable(false);
        visibleOff();
        temperatureChart.setVisible(true);
        temperatureCheck.setSelected(true);
        getWeather = new GetWeather2(1000, citySpace(city));
        alert();
        city.setDisable(true);
        chatTemp = new DisplayChart(temperatureChart, Types.TEMPERATURE);
        getWeather.addObserver(chatTemp);
        chartHumid = new DisplayChart(rainfallChart, Types.HUMIDYTY);
        getWeather.addObserver(chartHumid);
        chartPress = new DisplayChart(pressureChart, Types.PRESSURE);
        getWeather.addObserver(chartPress);
        chartSpeed = new DisplayChart(windChart, Types.SPEED);
        getWeather.addObserver(chartSpeed);
        maxValueTemp.setVisible(true);
        minValueTemp.setVisible(true);
        averageTemp.setVisible(true);
        stdTemp.setVisible(true);
        addingTemp();
        addingSpeed();
        addingPress();
        addingHum();
        addingTime();
        images();
        getWeather.start();


    }

    /**
     * Metoda odpowiedzialna za zakończenie działania watku
     *
     * @see GetWeather2
     */
    @FXML
    void stopClicked(ActionEvent event) {
        startButton.setDisable(false);
        pauseButton.setDisable(true);
        stopButton.setDisable(true);
        resume.setDisable(true);
        getWeather.stop();
        city.setDisable(false);


    }

    /**
     * Po wcisnieciu przycisku resume wznawiane jest działanie watku
     */
    @FXML
    void resumeClicked(ActionEvent event) {
        startButton.setDisable(true);
        pauseButton.setDisable(false);
        stopButton.setDisable(false);
        resume.setDisable(true);
        getWeather.start();
    }

    /**
     * Wybor wyswietlania wykresu z temperatura
     */

    @FXML
    void temperatureCheck(ActionEvent event) {
        visibleOff();
        temperatureChart.setVisible(true);
        windCheck.setSelected(false);
        rainfallCheck.setSelected(false);
        pressureCheck.setSelected(false);
        maxValueTemp.setVisible(true);
        minValueTemp.setVisible(true);
        averageTemp.setVisible(true);
        stdTemp.setVisible(true);
        temperatureCheck.setSelected(true);

    }

    /**
     * Wybor wyswietlania wykresu z predkoscia wiatru
     */
    @FXML
    void windCheck(ActionEvent event) {
        visibleOff();

        windChart.setVisible(true);
        temperatureCheck.setSelected(false);
        rainfallCheck.setSelected(false);
        pressureCheck.setSelected(false);
        maxValueSpeed.setVisible(true);
        minValueSpeed.setVisible(true);
        stdSpeed.setVisible(true);
        averageSpeed.setVisible(true);
        windCheck.setSelected(true);
    }

    /**
     * Wybor wykresu wyswietlajacego dane na temat wilgotnosci
     */
    @FXML
    void rainfallCheck(ActionEvent event) {
        visibleOff();
        rainfallChart.setVisible(true);
        windCheck.setSelected(false);
        temperatureCheck.setSelected(false);
        pressureCheck.setSelected(false);
        maxValueHum.setVisible(true);
        minValueHum.setVisible(true);
        stdHum.setVisible(true);
        averageHum.setVisible(true);
        rainfallCheck.setSelected(true);

    }

    /**
     * Wybor wykresu wyswietajacego dane na temat cisnienia
     */
    @FXML
    void pressureCheck(ActionEvent event) {
        visibleOff();
        pressureChart.setVisible(true);
        windCheck.setSelected(false);
        temperatureCheck.setSelected(false);
        rainfallCheck.setSelected(false);
        maxValuePress.setVisible(true);
        minValuePress.setVisible(true);
        stdPress.setVisible(true);
        averagePress.setVisible(true);
        pressureCheck.setSelected(true);
    }

    /**
     * Tworzenie observatorow aby mozliwe było wyswietlenie danych na temat temperatury
     */
    void addingTemp() {
        ArrayList<DisplayValues> tempValues = new ArrayList<>();
        tempValues.add(new DisplayValues(Types.TEMPERATURE, numberOfSamples, Param.SAM));
        tempValues.add(new DisplayValues(Types.TEMPERATURE, averageTemp, Param.AVER));
        tempValues.add(new DisplayValues(Types.TEMPERATURE, minValueTemp, Param.MIN));
        tempValues.add(new DisplayValues(Types.TEMPERATURE, maxValueTemp, Param.MAX));
        tempValues.add(new DisplayValues(Types.TEMPERATURE, stdTemp, Param.STD));
        getWeather.addObserver(tempValues.get(0));
        getWeather.addObserver(tempValues.get(1));
        getWeather.addObserver(tempValues.get(2));
        getWeather.addObserver(tempValues.get(3));
        getWeather.addObserver(tempValues.get(4));

    }

    /**
     * Tworzenie observatorow aby mozliwe było wyswietlenie danych na temat predkosci
     */
    void addingSpeed() {
        ArrayList<DisplayValues> speedValues = new ArrayList<>();
        speedValues.add(new DisplayValues(Types.SPEED, minValueSpeed, Param.MIN));
        speedValues.add(new DisplayValues(Types.SPEED, maxValueSpeed, Param.MAX));
        speedValues.add(new DisplayValues(Types.SPEED, stdSpeed, Param.STD));
        speedValues.add(new DisplayValues(Types.SPEED, averageSpeed, Param.AVER));
        getWeather.addObserver(speedValues.get(0));
        getWeather.addObserver(speedValues.get(1));
        getWeather.addObserver(speedValues.get(2));
        getWeather.addObserver(speedValues.get(3));
    }

    /**
     * Tworzenie observatorow aby mozliwe było wyswietlenie danych na temat cisnienia
     */
    void addingPress() {
        ArrayList<DisplayValues> pressValues = new ArrayList<>();
        pressValues.add(new DisplayValues(Types.PRESSURE, minValuePress, Param.MIN));
        pressValues.add(new DisplayValues(Types.PRESSURE, maxValuePress, Param.MAX));
        pressValues.add(new DisplayValues(Types.PRESSURE, stdPress, Param.STD));
        pressValues.add(new DisplayValues(Types.PRESSURE, averagePress, Param.AVER));
        getWeather.addObserver(pressValues.get(0));
        getWeather.addObserver(pressValues.get(1));
        getWeather.addObserver(pressValues.get(2));
        getWeather.addObserver(pressValues.get(3));


    }

    /**
     * Tworzenie observatorow aby mozliwe było wyswietlenie danych na temat czasu
     */
    void addingTime() {
        DisplayValues time = new DisplayValues(Types.TIME, dataWindow, Param.SAM);
        getWeather.addObserver(time);
    }

    /**
     * Tworzenie observatorow aby mozliwe było wyswietlenie danych na temat wilgotnosci
     */
    void addingHum() {
        ArrayList<DisplayValues> humValues = new ArrayList<>();
        humValues.add(new DisplayValues(Types.HUMIDYTY, minValueHum, Param.MIN));
        humValues.add(new DisplayValues(Types.HUMIDYTY, maxValueHum, Param.MAX));
        humValues.add(new DisplayValues(Types.HUMIDYTY, stdHum, Param.STD));
        humValues.add(new DisplayValues(Types.HUMIDYTY, averageHum, Param.AVER));
        getWeather.addObserver(humValues.get(0));
        getWeather.addObserver(humValues.get(1));
        getWeather.addObserver(humValues.get(2));
        getWeather.addObserver(humValues.get(3));

    }

    /**
     * Tworzenie observatora dzieki ktoremu mozliwe bedzie wyswietlenie grafiki
     */
    void images() {
        displayImage = new DisplayImage(weatherImage);
        getWeather.addObserver(displayImage);

    }

    /**
     * Wygaszanie wszystkich widocznych wykresow i pol tekstowych
     */
    void visibleOff() {
        temperatureChart.setVisible(false);
        rainfallChart.setVisible(false);
        windChart.setVisible(false);
        pressureChart.setVisible(false);
        minValueHum.setVisible(false);
        minValuePress.setVisible(false);
        minValueSpeed.setVisible(false);
        minValueTemp.setVisible(false);
        maxValuePress.setVisible(false);
        maxValueHum.setVisible(false);
        maxValueSpeed.setVisible(false);
        maxValueTemp.setVisible(false);
        stdHum.setVisible(false);
        stdPress.setVisible(false);
        stdSpeed.setVisible(false);
        stdTemp.setVisible(false);
        averageHum.setVisible(false);
        averagePress.setVisible(false);
        averageSpeed.setVisible(false);
        averageTemp.setVisible(false);
    }

    /**
     * Metoda odpowiedzialna za interpretacje " " jako "+" aby mozliwe było pobranie danych odnosnie miasta ze strony
     */
    String citySpace(TextField city) {
        String newCity = city.getText();
        newCity.replaceAll(" ", "+");
        return newCity;
    }

    /**
     * Metoda wyswietlajaca odpowiednie wartosci temperatury pobrane z pliku
     *
     * @see OpenJson
     */
    void openTemp() {

        open.displayOpenedChart(temperatureChart, Types.TEMPERATURE);
        open.displayOpenedValues(Types.TEMPERATURE, Param.MIN, minValueTemp);
        open.displayOpenedValues(Types.TEMPERATURE, Param.MAX, maxValueTemp);
        open.displayOpenedValues(Types.TEMPERATURE, Param.AVER, averageTemp);
        open.displayOpenedValues(Types.TEMPERATURE, Param.SAM, numberOfSamples);
        open.displayOpenedValues(Types.TEMPERATURE, Param.STD, stdTemp);
    }

    /**
     * Metoda wyswietlajaca odpowiednie wartosci predkosci wiatru pobrane z pliku
     *
     * @see OpenJson
     */
    void openSpeed() {

        open.displayOpenedValues(Types.SPEED, Param.MIN, minValueSpeed);
        open.displayOpenedValues(Types.SPEED, Param.MAX, maxValueSpeed);
        open.displayOpenedValues(Types.SPEED, Param.AVER, averageSpeed);
        open.displayOpenedValues(Types.SPEED, Param.STD, stdSpeed);
        open.displayOpenedValues(Types.SPEED, Param.SAM, numberOfSamples);
        open.displayOpenedChart(windChart, Types.SPEED);

    }

    /**
     * Metoda wyswietlajaca odpowiednie wartosci cisnienia pobrane z pliku
     *
     * @see OpenJson
     */
    void openPress() {

        open.displayOpenedValues(Types.PRESSURE, Param.MIN, minValuePress);
        open.displayOpenedValues(Types.PRESSURE, Param.MAX, maxValuePress);
        open.displayOpenedValues(Types.PRESSURE, Param.AVER, averagePress);
        open.displayOpenedValues(Types.PRESSURE, Param.STD, stdPress);
        open.displayOpenedValues(Types.PRESSURE, Param.SAM, numberOfSamples);
        open.displayOpenedChart(pressureChart, Types.PRESSURE);

    }

    /**
     * Metoda wyswietlajaca odpowiednie wartosci wilgotnosci powietrza pobrane z pliku
     *
     * @see OpenJson
     */
    void openHum() {

        open.displayOpenedValues(Types.HUMIDYTY, Param.MIN, minValueHum);
        open.displayOpenedValues(Types.HUMIDYTY, Param.MAX, maxValueHum);
        open.displayOpenedValues(Types.HUMIDYTY, Param.AVER, averageHum);
        open.displayOpenedValues(Types.HUMIDYTY, Param.STD, stdHum);
        open.displayOpenedValues(Types.HUMIDYTY, Param.SAM, numberOfSamples);
        open.displayOpenedChart(rainfallChart, Types.HUMIDYTY);
        open.displayOpenedWeather(weatherImage);
    }

    /**
     * Metoda wyswietlajaca bład jesli watek zwroci wartosc 404, czyli nie odnajdzie strony z wybranym przez
     * uzytkownika miastem
     */
    void alert() {
        if (getWeather.weatherUpdate().equals("404")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Sprawdz pisownie i sprobuj ponownie");
            alert.setHeaderText("NIE MA TAKIEGO MIASTA");
            alert.showAndWait();
            startButton.setDisable(false);
            city.setDisable(false);
        }
    }


}
