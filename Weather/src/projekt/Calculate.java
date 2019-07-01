package projekt;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Klasa Calculate odpowiedzialna jest za obliczanie wartosci na podstawie danych pogodowych. W zależnosci od metody w tej klasie obliczane sa: odchylenie standardowe,
 * minimalna i maksymalna wartosc danego parametru oraz srednia.
 *
 * @author Edyta Krukowska
 * @version 2
 */
public class Calculate {
    /**
     * Metoda oblicza wartosc odchylenia standardowego dla zadanej tablicy danych. Metoda ta kozysta rowniez z metody average.
     *
     * @param x jest to tablica, w ktorej przechowywane sa dane, na podstawie ktorych ma zostac obliczone odchylenie standardowe
     * @return zwracana jest zaokraglona wartosc odchylenia standardowego
     */    public double std(ArrayList<Double> x) {
        double sum = 0;
        double round;
        for (int i = 0; i < x.size(); i++) {
            sum += Math.pow((x.get(i) - average(x)), 2) / x.size();
        }
        round = Math.sqrt(sum) * 100;
        round = Math.round(round);
        round /= 100;
        return round;


    }

    /**
     * Metoda oblicza wartosc sredniej dla zadanej tablicy danych.
     *
     * @param x jest to tablica, w ktorej przechowywane sa dane, na podstawie ktorych ma zostac obliczona srednia
     * @return zwracana jest zaokraglona wartosc sredniej
     */
    public double average(ArrayList<Double> x) {
        double sum = 0;
        double round;
        for (int i = 0; i < x.size(); i++) {
            sum += x.get(i);
        }
        round = sum / x.size() * 100;
        round = Math.round(round);
        round /= 100;
        return round;
    }

    /**
     * Metoda oblicza wartosc minimalna dla zadanej tablicy danych.
     *
     * @param x jest to tablica, w ktorej przechowywane sa dane, na podstawie ktorych ma zostac wybrane minimum
     * @return zwracana jest zaokraglona wartosc minimalna
     */
    public double min(ArrayList<Double> x) {
        double round;
        round = Collections.min(x) * 100;
        round = Math.round(round);
        round /= 100;
        return round;
    }

    /**
     * Metoda oblicza wartosc minimalna dla zadanej tablicy danych.
     *
     * @param x jest to tablica, w ktorej przechowywane sa dane, na podstawie ktorych ma zostac wybrane maksimum
     * @return zwracana jest zaokraglona wartosc maksymalna
     */
    public double max(ArrayList<Double> x) {
        double round;
        round = Collections.max(x) * 100;
        round = Math.round(round);
        round /= 100;
        return round;

    }
}
