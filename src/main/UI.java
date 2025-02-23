package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {

    GamePanel gp;

    Font arial_40;
    Font arial_80B;

    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message;
    int messageTimer = 0;
    public boolean gameFinished = false;
    double playTime;
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    public UI (GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);

        OBJ_Key key = new OBJ_Key();
        keyImage = key.image;


    }

    public void draw(Graphics2D g2) {

        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);

        playTime += (double) 1 / gp.FPS;
        g2.drawString(decimalFormat.format(playTime), gp.tileSize*13, gp.tileSize);

        if (gameFinished) {
            g2.setColor(Color.YELLOW);
            g2.setFont(arial_80B);

            String text = "You Win";
            int textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
            int x = gp.width / 2 - textLength / 2;
            int y = gp.height / 2;

            g2.drawString(text, x, y);
            gp.gameThread = null;
            return; //nothing more needs to be draw past this point.
        }

        g2.drawImage(keyImage, 48, 20, gp.tileSize, gp.tileSize, null);
        g2.drawString("x " + gp.player.keys, 100,60);

        if (messageOn) {
            g2.setFont(g2.getFont().deriveFont(30F));
            g2.drawString(message, gp.tileSize, gp.tileSize*5);
            if (messageTimer > 120) {
                messageOn = false;
                messageTimer = 0;
            }
            messageTimer++;
        }
    }
}
