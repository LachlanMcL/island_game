package object;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    public BufferedImage image;
    public String name;
    boolean collision;
    public int worldX;
    public int worldY;
    public Rectangle solidArea = new Rectangle(0,0,48,48); //full block size for any items.

    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX; //relative (to player) pixel position of tile.
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        //only draw object on screen if it is within the window view.
        if (worldX > gp.player.worldX - gp.player.screenX - gp.tileSize
                && worldX < gp.player.worldX + gp.player.screenX + gp.tileSize
                && worldY > gp.player.worldY - gp.player.screenY - gp.tileSize
                && worldY < gp.player.worldY + gp.player.screenY + gp.player.screenX) {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        }
    }
}
