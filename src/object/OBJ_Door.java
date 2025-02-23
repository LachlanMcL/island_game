package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Door extends SuperObject{
    public OBJ_Door() {
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/door.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        name = "Door";
        collision = true;
    }
}
