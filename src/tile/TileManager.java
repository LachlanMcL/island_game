package tile;

import entity.Player;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    public Tile[] tiles;
    GamePanel gp;
    public int[][] map;
    Player player;

    public TileManager(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;

        tiles = new Tile[10];
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
                        map[col][i] = Integer.parseInt(tileData[i]);
                    }
                    col++;
                }

                br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getTileImage() {
        try {
            tiles[0] = new Tile();
            tiles[0].tileImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/bricks.png"));

            tiles[1] = new Tile();
            tiles[1].tileImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/grass.png"));

            tiles[2] = new Tile();
            tiles[2].tileImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/ice.png"));
            tiles[2].collision = true;

            tiles[3] = new Tile();
            tiles[3].tileImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/tree.png"));
            tiles[3].collision = true;

            tiles[4] = new Tile();
            tiles[4].tileImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/sand.png"));

            tiles[5] = new Tile();
            tiles[5].tileImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/dirt.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {


        int worldCol = 0;

        while (worldCol < gp.maxWorldCol) {
            int worldRow = 0;
            while (worldRow < gp.maxWorldRow) {
                int tileIndex = map[worldRow][worldCol];

                int worldX = worldCol * gp.tileSize; //absolute pixel position of tile.
                int worldY = worldRow * gp.tileSize;
                int screenX = worldX - player.worldX + player.screenX; //relative (to player) pixel position of tile.
                int screenY = worldY - player.worldY + player.screenY;

                //only draw tile on screen if it is within the window view.
                if (worldX > player.worldX - player.screenX - gp.tileSize
                    && worldX < player.worldX + player.screenX + player.screenX
                    && worldY > player.worldY - player.screenY - gp.tileSize
                    && worldY < player.worldY + player.screenY + player.screenX) {
                    g2.drawImage(tiles[tileIndex].tileImage, screenX, screenY, gp.tileSize, gp.tileSize, null);
                }
                worldRow++;
            }
            worldCol++;
        }
    }
}