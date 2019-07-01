package projekt;

import javafx.application.Platform;
import javafx.scene.image.ImageView;

import java.time.LocalDateTime;

/**
 * Klasa odpowiedzialna za wyswietlanie wybranych obrazow w zaleznosci od wybranego pola do jego wyswietlenia.
 * Dzieki tej klasie obrazy zmieniaja sie wraz ze zmiana odczytanych wartosci ze strony
 *
 * @see ImageChooser
 */
public class DisplayImage implements Observer {

    private ImageView imageView;
    private ImageChooser imageChooser = new ImageChooser();

    /**
     * Konstruktor pozwalajacy wybrać odpowiednie pole, w ktorym ma sie aktualizować grafika
     * @param imageView
     */
    public DisplayImage(ImageView imageView) {
        this.imageView = imageView;

    }

    /**
     * Metoda pobierajaca zaktualizowane dane i na tej podstawie generujaca odpowiednia grafike
     *
     * @param temperature wartosć temperatury otrzymana z odpowiedzi ze strony
     * @param pressure    wartosć cisnienia otrzymana z odpowiedzi ze strony
     * @param humidyty    wartosć wilgotnosci otrzymana z odpowiedzi ze strony
     * @param speed       wartosć predkosci wiatru otrzymana z odpowiedzi ze strony
     * @param weather     id pogody otrzymana z odpowiedzi ze strony
     * @param time        czas otrzymany z odpowiedzi ze strony
     */
    @Override
    public void update(double temperature, double pressure, double humidyty, double speed, String weather, LocalDateTime time) {

        imageView.setVisible(true);
        Platform.runLater(() -> {
            imageChooser.display(imageView, weather, time);
        });

    }
}
