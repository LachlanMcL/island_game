package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    //Inventory
    int keys = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.width / 2 - (gp.tileSize / 2);
        screenY = gp.height / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImages();
    }

    public void getPlayerImages() {
        try {
            up1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/back1.png"));
            up2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/back2.png"));
            down1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/forward1.png"));
            down2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/forward2.png"));
            left1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/left1.png"));
            left2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/left2.png"));
            right1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/right1.png"));
            right2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/right2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 18;
        worldY = gp.tileSize * 25;
        speed = 4;
        direction = "down";
    }

    public void update() {

        if (keyH.upPressed) direction = "up";
        else if (keyH.downPressed) direction = "down";
        else if (keyH.leftPressed) direction = "left";
        else if (keyH.rightPressed) direction = "right";
        else direction = "none";

        collisionOn = gp.cChecker.checkTile(this); //check tile collision

        //check object collision. index of -1 means no object was detected.
        int objectIndex = gp.cChecker.checkObject(this,true);
        if (objectIndex != -1) {
            switch (gp.objects[objectIndex].name) {
                case "Key" -> {
                    keys++;
                    gp.objects[objectIndex] = null;
                    gp.playSE(0);
                }
                case "Door" -> {
                    if (keys > 0) {
                        keys--;
                        gp.objects[objectIndex] = null;
                        gp.playSE(2);
                    } else {
                        gp.player.collisionOn = true;
                    }
                }
                case "Ethereal Feather" -> {
                    gp.objects[objectIndex] = null;
                    gp.player.speed += 2;
                    gp.playSE(3);
                }
            }
            System.out.println("Keys:"+keys);
        }

        if (!collisionOn) {
            if (direction.equals("up")) worldY -= speed;
            if (direction.equals("down")) worldY += speed;
            if (direction.equals("left")) worldX -= speed;
            if (direction.equals("right")) worldX += speed;
        }

    }

    public void draw(Graphics2D g2) {

        BufferedImage image = down1;

        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed && spriteNum == 1) image = up1;
            if (keyH.upPressed && spriteNum == 2) image = up2;

            if (keyH.downPressed && spriteNum == 1) image = down1;
            if (keyH.downPressed && spriteNum == 2) image = down2;

            if (keyH.leftPressed && spriteNum == 1) image = left1;
            if (keyH.leftPressed && spriteNum == 2) image = left2;

            if (keyH.rightPressed && spriteNum == 1) image = right1;
            if (keyH.rightPressed && spriteNum == 2) image = right2;

            if (spriteCounter > 10) {
                spriteNum = spriteNum == 1 ? 2 : 1; //sprintNum can either be 1 or 2 for player, just flip between the two.
                spriteCounter = 0;
            }
            spriteCounter++;
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
