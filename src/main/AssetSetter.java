package main;

import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;
import object.OBJ_feather;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObjects() {
        gp.objects[0] = new OBJ_feather();
        gp.objects[0].worldX = 16 * gp.tileSize;
        gp.objects[0].worldY = 39 * gp.tileSize;

        //24 40
        gp.objects[1] = new OBJ_Key();
        gp.objects[1].worldX = 32 * gp.tileSize;
        gp.objects[1].worldY = 19 * gp.tileSize;

        gp.objects[2] = new OBJ_Key();
        gp.objects[2].worldX = 24 * gp.tileSize;
        gp.objects[2].worldY = 40 * gp.tileSize;

        gp.objects[3] = new OBJ_Key();
        gp.objects[3].worldX = 9 * gp.tileSize;
        gp.objects[3].worldY = 6 * gp.tileSize;

        gp.objects[4] = new OBJ_Door();
        gp.objects[4].worldX = 25 * gp.tileSize;
        gp.objects[4].worldY = 19 * gp.tileSize;

        gp.objects[5] = new OBJ_Door();
        gp.objects[5].worldX = 38 * gp.tileSize;
        gp.objects[5].worldY = 10 * gp.tileSize;

        gp.objects[6] = new OBJ_Door();
        gp.objects[6].worldX = 39 * gp.tileSize;
        gp.objects[6].worldY = 35 * gp.tileSize;

        gp.objects[7] = new OBJ_Chest();
        gp.objects[7].worldX = 38 * gp.tileSize;
        gp.objects[7].worldY = 8 * gp.tileSize;

    }
}
