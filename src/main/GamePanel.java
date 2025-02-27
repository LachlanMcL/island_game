package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;

    //SCREEN SETTINGS
    final int maxScreenCol = 16;
    final int maxScreenRow = 10;
    public final int width = maxScreenCol * tileSize;
    public final int height = maxScreenRow * tileSize;

    //WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    final int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    public Player player = new Player(this, keyH);
    TileManager tileManager = new TileManager(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public SuperObject[] objects = new SuperObject[10];
    AssetSetter aSetter = new AssetSetter(this);
    Sound musicManager = new Sound();
    Sound soundEffectManager = new Sound();
    public UI ui = new UI(this);

    Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setUpGame() {
        aSetter.setObjects();
        playMusic(1);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void playMusic(int i) {
        musicManager.setFile(i);
        musicManager.play();
        musicManager.loop();
    }

    public void stopMusic() {
        musicManager.stop();
    }

    public void playSE(int i) {
        soundEffectManager.setFile(i);
        soundEffectManager.play();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        long drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
//                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        //draw order is important so the right things display over each other.
        tileManager.draw(g2);
        for (SuperObject object : objects) {
            if (object != null) object.draw(g2, this);
        }
        player.draw(g2);
        ui.draw(g2);

        g2.dispose();
    }
}