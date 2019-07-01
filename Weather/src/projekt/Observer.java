package projekt;

import java.time.LocalDateTime;
import java.time.LocalTime;
/**Interfejs pozwalajacy na uaktualnianie danych znajdujących sie w parametrach metody*/
public interface Observer {

    void update(double temperature, double pressure, double humidyty, double speed, String weather, LocalDateTime time);

}
