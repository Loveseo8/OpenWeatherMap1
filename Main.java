package com.aad;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.*;

public class Main {

    class Weather {

        String name;
        M main;

    }

    class M {

        double temp;

    }

    public static Scanner in = new Scanner(System.in);
    public static PrintStream out = System.out;

    public static void main(String[] args) {

        ArrayList<Integer> cities = new ArrayList<>(); // Список в котором лежат все id городов

        // Добовляем id городов в список

        cities.add(2023469); // Irkustk
        cities.add(2051523); // Bratsk
        cities.add(498817); // Saint Petersburg
        cities.add(524901); // Moscow
        cities.add(554234); // Kaliningrad
        cities.add(1502026); // Krasnoyarsk
        cities.add(500096); // Ryazan
        cities.add(532288); //  Magnitogorsk
        cities.add(2014407); //  Ulan-Ude
        cities.add(491422); // Sochi
        cities.add(520555); // Nizhny Novgorod

        Gson gson = new Gson();

        String[] jsons = new String[cities.size()]; // В этот массив будут записаны ответы сайта в формате json

        // Получаем с сайта json файлы для каждого города по их id и записываем их в массив "jsons"

        for (int i = 0; i < cities.size(); i++) {

            try {

                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?id=" + cities.get(i) + "&units=metric&appid=d6843ab8ee963f5d372296dfff62aed7");
                URLConnection yc = url.openConnection();
                BufferedReader buffIn = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                String inputLine;
                while ((inputLine = buffIn.readLine()) != null) jsons[i] = (inputLine);
                buffIn.close();

            } catch (IOException | NullPointerException e) {

                e.printStackTrace();

            }

        }

        Map<Double, String> cityInfo = new TreeMap<>(Collections.reverseOrder()); // Сюда запишем полученную нами информацию, для ключа устанавливаем температуру, тк сортировка идёт по возрастанию, а нам нужно выводить информацию наоборот, поэтому используем функцию reverseOrder()
        Weather w;

        for (int i = 0; i < jsons.length; i++) {

            w = gson.fromJson(jsons[i], Weather.class);

            cityInfo.put(w.main.temp, w.name);

        }

        // Выводим информацию

        for (Map.Entry<Double, String> entry : cityInfo.entrySet()) {

            out.printf("%s %s℃ \n", entry.getValue(), entry.getKey());

        }

    }
}
