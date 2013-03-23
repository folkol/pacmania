import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class PoC {
    int width = 800, height = 600;
    long lastTime;
    BufferedImage sprite, buffer;

    public static void main(String[] args) throws IOException {
        new PoC().run();
    }

    public void run() throws IOException {
        Window window = createWindow();

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();

        sprite = ImageIO.read(new File("sprite.bmp"));
        buffer = gc.createCompatibleImage(width, height);

        BufferStrategy bufferStrategy = window.getBufferStrategy();
        while (true) {
            drawPacmans();

            Graphics drawGraphics = bufferStrategy.getDrawGraphics();
            drawGraphics.clearRect(0, 0, width, height);
            drawGraphics.drawImage(buffer, 0, 0, null);
            drawGraphics.dispose();
            bufferStrategy.show();
        }

    }

    private Window createWindow() {
        Window window = new Frame();
        window.setIgnoreRepaint(true);
        window.setVisible(true);
        window.createBufferStrategy(3);
        window.setSize(width, height);

        window.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent winEv) {
                System.exit(0);
            }
        });

        return window;
    }

    private void drawPacmans() {
        Random random = new Random();

        Graphics graphics = buffer.getGraphics();
        for (int i = 0; i < 1000; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            graphics.drawImage(sprite, x, y, null);
        }
        showFPS(graphics);

        graphics.dispose();
    }

    private void showFPS(Graphics g) {
        int x = 30;
        int y = 50;

        double elapsed = (System.currentTimeMillis() - lastTime) / 1000.0;
        if (elapsed == 0)
            elapsed = 1;
        double fps = 1.0 / elapsed;
        lastTime = System.currentTimeMillis();

        g.setColor(Color.WHITE);
        g.fillRect(x, y, 75, 20);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, 75, 20);
        g.drawString(String.format("FPS: %3.0f", fps), x + 15, y + 15);
    }
}