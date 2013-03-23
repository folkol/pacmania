import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class PoC {
    static int width = 800, height = 600;
    static long lastTime = System.currentTimeMillis();
    private static BufferedImage sprite;

    public static void main(String[] args) throws IOException {
        Frame window = new Frame();
        window.setIgnoreRepaint(true);
        window.setVisible(true);
        window.createBufferStrategy(3);
        window.setSize(width, height);

        window.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent winEv) {
                System.exit(0);
            }
        });

        sprite = ImageIO.read(new File("sprite.bmp"));
        BufferStrategy bufferStrategy = window.getBufferStrategy();
        Random random = new Random();
        while (true) {
            long before = System.currentTimeMillis();
            Graphics drawGraphics = bufferStrategy.getDrawGraphics();

            drawGraphics.clearRect(0, 0, width, height);

            drawPacmans(window, random, drawGraphics);
            showFPS(before, drawGraphics, 30, 50);

            drawGraphics.dispose();
            bufferStrategy.show();
        }
    }

    private static void drawPacmans(Frame window, Random random, Graphics drawGraphics) {
        for (int i = 0; i < 1000; i++) {
            final int x = random.nextInt(window.getWidth());
            final int y = random.nextInt(window.getHeight());
            drawGraphics.drawImage(sprite, x, y, null);
        }
    }

    private static void showFPS(long before, Graphics drawGraphics, int x, int y) {
        drawGraphics.setColor(Color.WHITE);
        drawGraphics.fillRect(x, y, 75, 20);
        drawGraphics.setColor(Color.BLACK);
        drawGraphics.drawRect(x, y, 75, 20);

        double elapsed = (System.currentTimeMillis() - before) / 1000.0;
        if(elapsed == 0) elapsed = 1;
        drawGraphics.drawString(String.format("FPS: %3.0f",  1.0 / elapsed), x + 15, y + 15);
    }
}