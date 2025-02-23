package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_feather extends SuperObject {
    public OBJ_feather() {
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/ethereal_feather.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        name = "Ethereal Feather";
    }
}
