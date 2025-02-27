package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    public Tile[] tiles;
    int tileCount;
    GamePanel gp;
    public int[][] map;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tiles = new Tile[50];
        tileCount = 0;

        getTileImage();
        map = new int[gp.maxWorldRow][gp.maxWorldCol];
        loadMap("maps/map2.txt");
    }

    public void loadMap(String filePath) {
        try {
                InputStream is = getClass().getClassLoader().getResourceAsStream(filePath);
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                int col = 0;
                while (true) {
                    String line = br.readLine();
                    if (line == null) break;
                    String[] tileData = line.split(" ");
                    for (int i = 0; i < tileData.length; i++) {
                        int tileImageIndex = Integer.parseInt(tileData[i]);

                        //index == 1 means it is a grass tile. if it is a grass tile, randomize it to a different grass tile (so it looks better in game).
                        if (Integer.parseInt(tileData[i]) == 1) {
                            int randomIndex = (int)(Math.random() * 5);
                            switch (randomIndex) {
                                case 0 -> tileImageIndex = 1;
                                case 1 -> tileImageIndex = 2;
                                case 2 -> tileImageIndex = 3;
                                case 3 -> tileImageIndex = 4;
                                case 4 -> tileImageIndex = 5;
                            }
                        }

                        map[col][i] = tileImageIndex;
                    }
                    col++;
                }

                br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tileSetter(String tileName) {
        try {
            tiles[tileCount] = new Tile();
            tiles[tileCount].tileImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/" + tileName + ".png"));
            tileCount++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tileSetter(String tileName, boolean collision) {
        try {
            tiles[tileCount] = new Tile();
            tiles[tileCount].tileImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/" + tileName + ".png"));
            tiles[tileCount].collision = collision;
            tileCount++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getTileImage() {
        tileSetter("bricks", true);
        tileSetter("grass1");
        tileSetter("grass2");
        tileSetter("grass3");
        tileSetter("grass4");
        tileSetter("grass5");
        tileSetter("tree", true);
        tileSetter("water_top", true);
        tileSetter("water_plain", true);
        tileSetter("water_plain_heavy_current", true);
        tileSetter("water_plain_light_current", true);
        tileSetter("water_top_left_corner", true);
        tileSetter("water_left", true);
        tileSetter("water_right", true);
        tileSetter("water_top_right_corner", true);
        tileSetter("water_bottom_right_corner", true);
        tileSetter("water_bottom_left_corner", true);
        tileSetter("water_bottom", true);
        tileSetter("water_bottom_right_corner_2", true);
        tileSetter("water_bottom_left_corner_2", true);
        tileSetter("water_top_right_corner_2", true);
        tileSetter("water_top_left_corner_2", true);
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        BufferedImage image ;

        while (worldCol < gp.maxWorldCol) {
            int worldRow = 0;
            while (worldRow < gp.maxWorldRow) {
                int tileIndex = map[worldRow][worldCol];
                image = tiles[tileIndex].tileImage;

                int worldX = worldCol * gp.tileSize; //absolute pixel position of tile.
                int worldY = worldRow * gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX; //relative (to player) pixel position of tile.
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                //only draw tile on screen if it is within the window view.
                if (worldX > gp.player.worldX - gp.player.screenX - gp.tileSize
                    && worldX < gp.player.worldX + gp.player.screenX + gp.tileSize
                    && worldY > gp.player.worldY - gp.player.screenY - gp.tileSize
                    && worldY < gp.player.worldY + gp.player.screenY + gp.tileSize) {
                    g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                }
                worldRow++;
            }
            worldCol++;
        }
    }
}