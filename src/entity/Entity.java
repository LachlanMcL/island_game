package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public int worldX, worldY;
    public int speed;

    BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    int spriteCounter = 0;
    int spriteNum = 1;

    public Rectangle solidArea;
    public boolean collisionOn = false;
}
