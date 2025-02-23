package main;

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
        gp.objects[0].worldX = 20 * gp.tileSize;
        gp.objects[0].worldY = 25 * gp.tileSize;
//
        gp.objects[1] = new OBJ_Door();
        gp.objects[1].worldX = 18 * gp.tileSize;
        gp.objects[1].worldY = 12 * gp.tileSize;
////
        gp.objects[2] = new OBJ_Key();
        gp.objects[2].worldX = 19 * gp.tileSize;
        gp.objects[2].worldY = 25 * gp.tileSize;
//
//        gp.objects[3] = new OBJ_Chest();
//        gp.objects[3].worldX = 23 * gp.tileSize;
//        gp.objects[3].worldY = 25 * gp.tileSize;
    }
}
