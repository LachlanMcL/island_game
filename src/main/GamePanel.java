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

    public final int maxScreenCol = 16;
    public final int maxScreenRow = 10;
    public final int width = maxScreenCol * tileSize;
    public final int height = maxScreenRow * tileSize;

    //WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    final int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    public Player player = new Player(this, keyH);
    TileManager tileManager = new TileManager(this, player);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public SuperObject[] objects = new SuperObject[10];
    AssetSetter aSetter = new AssetSetter(this);
    Sound soundManager = new Sound();

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
        soundManager.setFile(i);
        soundManager.play();
        soundManager.loop();
    }

    public void stopMusic() {
        soundManager.stop();
    }

    public void playSE(int i) {
        soundManager.setFile(i);
        soundManager.play();
    }



    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        tileManager.draw(g2);
        //loop through objects after tiles
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] != null) objects[i].draw(g2, this);
        }
        player.draw(g2);

        g2.dispose();
    }
}

/*
TINGS TO DO:
part 10
- UI and fixing stuf
- make 2 sound objects, one for music one for SE
- UI.java in main
	- draw(Grapics2d g2)
	- display keys that player has
	- create a reusable Font object
		- arial_40 = new Font("Arial", Font.PLAIN, 40)
	- g2.setFont
	- g2.setColor
	- g2.drawString(string, x, y)
- make image of key display
- showMessage(String text)
	- messageOn and message for UI
- during UI draw method check if messageOn and if so display it.
- g2.getFont().deriveFont(30F)
- set messageCounter, after it reaches a max, messageOn is false
- gameFinished boolean in UI
- `textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth()`
- `double playTime`
- `DecimalFormat dFormat = new DecimalFormat("#0.00")`
- dFormat.format(playTime)

https://www.youtube.com/watch?v=0yD5iT8ObCs&list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq&index=11&ab_channel=RyiSnow
 */