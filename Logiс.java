package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// класс наследуется от Джава Панели и реализует интерфейсы Обработчик событий и Слушатель клавиш
public class Logiс extends JPanel implements ActionListener, KeyListener {
    private int Bit1Y = 120;       // координаты биты
    private int Bit2Y = 120;
    private int BallPX = 283;       // координаты мяча
    private int BallPY = 150;
    private int BallX = 3;          // скорость мяча
    private int BallY = 3;
    private Timer timer;
    private int playerScore1 = 0;    // счетчики очков
    private int playerScore2 = 0;
    private boolean start = true;

    public Logiс() {
        addKeyListener(this);
        timer = new Timer(10, this);       // таймер, который обновляется через 10 микросекунд
        setFocusable(true);                            // чтобы окошко связывалось с клавишами
    }

    @Override                                        // прорисовка Объектов
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);                      // цвет фона
        g.fillRect(0, 0, 600,400);  // задний фон

        g.setColor(Color.YELLOW);                     // разделительная линия
        g.drawLine(290,0,290,400);

        g.setColor(Color.WHITE);                       // цвет биты #1
        g.fillRect(0, Bit1Y,10, 100);   // позиция и размер биты #1

        g.setColor(Color.WHITE);                       // цвет биты #2
        g.fillRect(572, Bit2Y,10, 100); // позиция и размер биты #2 (NPC)

        g.setColor(Color.WHITE);                        // мяч
        g.fillOval(BallPX, BallPY,14, 14);  // позиция и размер мяча

        if (start) {
            Font textStart = new Font("Arial", Font.BOLD, 20);      // шрифт Arial жирный высота 20
            g.setFont(textStart);
            g.drawString("Для старта нажмите Enter", 180, 100);  // надпись старт
        }

        Font text = new Font("Arial", Font.BOLD, 25);      // шрифт Arial жирный высота 25
        g.setFont(text);
        g.drawString(String.valueOf(playerScore1), 250, 30);    // отображение счета игрока
        g.drawString(String.valueOf(playerScore2), 315, 30);    // отображение счета второго игрока (NPC)

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();             // перерисовка экрана каждый такт

        // перемещение мяча
        BallPX += BallX;       // изменение координат мяча
        BallPY += BallY;
        if (BallPY <= 0) {      // верхняя граница мяча
            BallY = -BallY;
        }
        if (BallPY > 350) {      // нижняя граница мяча
            BallY = -BallY;
        }

        // перемещение биты противника (NPC)
        if (((BallPY - 50) <= Bit2Y) && (BallPX >= 300) && (BallX > 0)) {
            Bit2Y -= 3;                // смещение биты игрока2 вниз в зависимости от позиции мяча
        }
        if (((BallPY - 50) >= Bit2Y) && (BallPX >= 300) && (BallX > 0)) {
            Bit2Y += 3;                // смещение биты игрока2 вверх в зависимости от позиции мяча
        }

        // рисуем поверх нашего прямоугольника еще один и определяем intersects - есть ли пересечение с мячом
        if (new Rectangle(BallPX, BallPY,14, 14).intersects(new Rectangle(572, Bit2Y,10, 100))) {
            BallX = -Math.abs(BallX + 1);           // мяч отскакивает и летит обратно + ускорение мяча на 1
        }

        // отслеживаем совпадение биты игрока и мяча
        if (new Rectangle(BallPX, BallPY,14, 14).intersects(new Rectangle(0, Bit1Y,10, 100))) {
            BallX = -(BallX - 1);              // мяч отскакивает и летит обратно + ускорение мяча на 1
        }

        // счет игры
        // если мяч пропустил игрок 1, то у игрока 2 (NPC) счет увеличивается
        if (BallPX < -20) {
            playerScore2++;
            timer.stop();       // останавливаем таймер
            BallPX = 283;       // задаем начальные координаты мяча
            BallPY = 150;
            BallX = 3;          // чтобы мяч летел в сторону выигравшего
            BallY = 3;
            Bit1Y = 120;        // ставим биты в начальные позиции
            Bit2Y = 120;
        }
        // если мяч пропустил игрок 2 (NPC), то у игрока 1 счет увеличивается
        if (BallPX > 620) {
            playerScore1++;
            timer.stop();       // останавливаем таймер
            BallPX = 283;       // задаем начальные координаты мяча
            BallPY = 150;
            BallX = -3;         // чтобы мяч летел в сторону выигравшего
            BallY = -3;
            Bit1Y = 120;        // ставим биты в начальные позиции
            Bit2Y = 120;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {     // если нажата клавиша вниз
            Bit1Y += 10;                              // увеличиваем позицию по Y биты игрока
            if (Bit1Y >= 262) {                       // чтобы бита не уползала вниз
                Bit1Y = 262;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {        // если нажата клавиша вверх
            Bit1Y -= 10;                               // уменьшаем позицию по Y биты игрока
            if (Bit1Y <= 0) {                          // чтобы бита не уползала вверх
                Bit1Y = 0;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {     // если нажата клавиша ENTER
            timer.start();                             // запуск таймера - запуск игры
            start = false;
        }
    }
}
