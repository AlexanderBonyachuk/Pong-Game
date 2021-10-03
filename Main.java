package com.company;

import javax.swing.*;

public class Main {

    // Простенькая игра понг, игрок VS компьютер

    public static void main(String[] args) {
        JFrame window = new JFrame("Pong");     // окно называется Понг

        Logiс game = new Logiс();

        window.setSize(600, 400);       // размеры
        window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);  // закрытие по крестику
        window.setResizable(false);                 // размер окна нельзя поменять
        window.setVisible(true);                    // видимость окна
        window.setLocation(1500, 400);        // расположение окна
        window.add(game);                           // добавление логики
        /* без строчки ниже в jar-нике не считывалось нажатие Enter
        * игра не начиналась, но если в начале убрать считвание Enter (keyReleased)
        *, то клавиши вверх/вниз считывались нормально
        * странно ведь переходя в конструктор Logiс() есть setFocusable(true) (?)
        * */
        game.setFocusable(true);
    }
}
