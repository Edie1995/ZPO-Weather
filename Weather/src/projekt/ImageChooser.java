package projekt;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Klasa dobierajaca odpowiednia grafike na podstawie orzymanych danych pogodowych oraz czasu
 */
public class ImageChooser {

    LocalDateTime time;

    /**
     * Metoda wyswietlajaca odpowiednie obrazki w zaleznosci od pobranego id pogody z serwera oraz  na podstawie czasu.
     */
    void display(ImageView imageView, String weather, LocalDateTime time) {
        LocalTime changes = LocalTime.of(19, 00, 00);
        LocalTime morning = LocalTime.of(6, 00, 00);
        if (weather.equals("500.0") || weather.equals("501.0") || weather.equals("502.0") || weather.equals("503.0") || weather.equals("504.0")) {
            if (time.toLocalTime().isBefore(morning) || time.toLocalTime().isAfter(changes)) {
                Image image = new Image("\\PNG\\Chance_Of_Showers.png");
                imageView.setImage(image);
            } else {
                Image image = new Image("\\PNG\\Few_Showers.png");
                imageView.setImage(image);
            }
        } else if (weather.equals("800.0")) {
            if (time.toLocalTime().isAfter(changes) || time.toLocalTime().isBefore(morning)) {
                Image image = new Image("\\PNG\\Clear.png");
                imageView.setImage(image);
            } else {
                Image image = new Image("\\PNG\\Sunny.png");
                imageView.setImage(image);
            }
        } else if (weather.equals("600.0") || weather.equals("601.0") || weather.equals("602.0") || weather.equals("611.0") || weather.equals("612.0") || weather.equals("615.0") || weather.equals("616.0") || weather.equals("620.0") || weather.equals("621.0") || weather.equals("622.0")) {
            Image image = new Image("\\PNG\\Snow.png");
            imageView.setImage(image);
        } else if (weather.equals("200.0") || weather.equals("201.0") || weather.equals("202.0") || weather.equals("210.0") || weather.equals("211.0") || weather.equals("212.0") || weather.equals("221.0") || weather.equals("230.0") || weather.equals("231.0") || weather.equals("232.0")) {

            Image image = new Image("\\PNG\\Thunderstorms.png");
            imageView.setImage(image);

        } else if (weather.equals("803.0") || weather.equals("804.0") || weather.equals("802.0")) {
            Image image = new Image("\\PNG\\Cloudy.png");
            imageView.setImage(image);
        } else if (weather.equals("801")) {
            if (time.toLocalTime().isAfter(changes) || time.toLocalTime().isBefore(morning)) {
                Image image = new Image("\\PNG\\Cloudy_Period.png");
                imageView.setImage(image);
            } else {
                Image image = new Image("\\PNG\\Mostly_Cloudy.png");
                imageView.setImage(image);
            }
        } else if (weather.equals("701.0") || weather.equals("711.0") || weather.equals("721.0") || weather.equals("731.0") || weather.equals("741.0") || weather.equals("751.0") || weather.equals("761.0") || weather.equals("762.0") || weather.equals("771.0") || weather.equals("781.0")) {

            Image image = new Image("\\PNG\\Fog.png");
            imageView.setImage(image);

        } else if (weather.equals("300.0") || weather.equals("301.0") || weather.equals("302.0") || weather.equals("310.0") || weather.equals("311.0") || weather.equals("312.0") || weather.equals("313.0") || weather.equals("314.0") || weather.equals("321.0") || weather.equals("520.0") || weather.equals("521.0") || weather.equals("522.0") || weather.equals("531.0")) {

            Image image = new Image("\\PNG\\Rain.png");
            imageView.setImage(image);

        } else if (weather.equals("511.0")) {
            Image image = new Image("\\PNG\\Rain_Or_Snow.png");
            imageView.setImage(image);

        }
    }
}
