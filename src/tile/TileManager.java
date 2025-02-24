package tile;

import entity.Player;
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
    GamePanel gp;
    public int[][] map;
    Player player;

    public TileManager(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;

        tiles = new Tile[50];
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
                        if (Integer.parseInt(tileData[i]) == 1) {
                            int randomIndex = (int)(Math.random() * 5);
                            switch (randomIndex) {
                                case 0 -> tileImageIndex = 1;
                                case 1 -> tileImageIndex = 6;
                                case 2 -> tileImageIndex = 7;
                                case 3 -> tileImageIndex = 8;
                                case 4 -> tileImageIndex = 9;
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

    public void getTileImage() {
        try {
            tiles[0] = new Tile();
            tiles[0].tileImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/bricks4.png"));
            tiles[0].collision = true;

            tiles[1] = new Tile();
            tiles[1].tileImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/grass1.png"));

            tiles[2] = new Tile();
            tiles[2].tileImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/ice.png"));
            tiles[2].collision = true;

            tiles[3] = new Tile();
            tiles[3].tileImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/tree4.png"));
            tiles[3].collision = true;

            tiles[4] = new Tile();
            tiles[4].tileImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/sand.png"));

            tiles[5] = new Tile();
            tiles[5].tileImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/dirt.png"));

            tiles[6] = new Tile();
            tiles[6].tileImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/grass5.png"));

            tiles[7] = new Tile();
            tiles[7].tileImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/grass3.png"));

            tiles[8] = new Tile();
            tiles[8].tileImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/grass4.png"));

            tiles[9] = new Tile();
            tiles[9].tileImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/grass6.png"));

            tiles[10] = new Tile();
            tiles[10].tileImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/water1.png"));
            tiles[10].collision = true;

            tiles[11] = new Tile();
            tiles[11].tileImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/water2.png"));
            tiles[11].collision = true;

            tiles[12] = new Tile();
            tiles[12].tileImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/water3.png"));
            tiles[12].collision = true;

            tiles[13] = new Tile();
            tiles[13].tileImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/water4.png"));
            tiles[13].collision = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
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
                int screenX = worldX - player.worldX + player.screenX; //relative (to player) pixel position of tile.
                int screenY = worldY - player.worldY + player.screenY;

                //only draw tile on screen if it is within the window view.
                if (worldX > player.worldX - player.screenX - gp.tileSize
                    && worldX < player.worldX + player.screenX + player.screenX
                    && worldY > player.worldY - player.screenY - gp.tileSize
                    && worldY < player.worldY + player.screenY + player.screenX) {
                    g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                }
                worldRow++;
            }
            worldCol++;
        }
    }
}