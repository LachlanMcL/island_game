package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {

    GamePanel gp;
    Font arial_40;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message;
    int messageTimer = 0;

    public UI (GamePanel gp) {
        this.gp = gp;

        OBJ_Key key = new OBJ_Key();
        keyImage = key.image;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
    }

    public void draw(Graphics2D g2) {
        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);
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
