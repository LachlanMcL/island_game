package main;

import entity.Entity;
import object.SuperObject;

import java.awt.*;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public boolean checkTile(Entity entity) {

        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = (entityLeftWorldX - entity.speed)/ gp.tileSize;
        int entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
        int entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
        int entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;

        //there can be more than one tile in one direction next to player if they are in between two tiles.
        int tile1Index = 0;
        int tile2Index = 0;

        if (entity.direction.equals("up")) {
            tile1Index = gp.tileManager.map[entityTopRow][entityLeftCol];
            tile2Index = gp.tileManager.map[entityTopRow][entityRightCol];
        }

        if (entity.direction.equals("down")) {
            tile1Index = gp.tileManager.map[entityBottomRow][entityLeftCol];
            tile2Index = gp.tileManager.map[entityBottomRow][entityRightCol];
        }

        if (entity.direction.equals("left")) {
            tile1Index = gp.tileManager.map[entityTopRow][entityLeftCol];
            tile2Index = gp.tileManager.map[entityBottomRow][entityLeftCol];
        }

        if (entity.direction.equals("right")) {
            tile1Index = gp.tileManager.map[entityTopRow][entityRightCol];
            tile2Index = gp.tileManager.map[entityBottomRow][entityRightCol];
        }

        return gp.tileManager.tiles[tile1Index].collision && gp.tileManager.tiles[tile2Index].collision;
    }

    public int checkObject(Entity entity, boolean player) {
        int index = -1;
        for (int i = 0; i < gp.objects.length; i++) {
            if (gp.objects[i] == null) continue;
            SuperObject currObject = gp.objects[i];

            Rectangle objectCollisionArea = new Rectangle(currObject.solidArea);
            objectCollisionArea.x += currObject.worldX; //set true value of solid area
            objectCollisionArea.y += currObject.worldY;

            Rectangle entityCollisionArea = new Rectangle(entity.solidArea);
            entityCollisionArea.x = entity.worldX;
            entityCollisionArea.y = entity.worldY;

            if (player) {
                //move entityCollisionArea up, down, left, right depending on direction.
                switch (entity.direction) {
                    case "up" -> entityCollisionArea.y -= entity.speed;
                    case "down" -> entityCollisionArea.y += entity.speed;
                    case "left" -> entityCollisionArea.x -= entity.speed;
                    case "right" -> entityCollisionArea.x += entity.speed;
                }
                if (entityCollisionArea.intersects(objectCollisionArea)) return i; //if player is moving over an object, return the object index for the gp.objects array.
            }
        }

        return index;
    }
}
