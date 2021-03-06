package program.block_shapes;

import javafx.scene.image.Image;
import program.game.Block;

/**
 * Created by IntelliJ IDEA.
 * User: MichaelaGuth
 * Date: 24. 8. 2018
 * Time: 12:09
 */
public class ShapeT extends Shape {
    public ShapeT(Image image) {
        super(image);
        this.shape = new Block[][] {
                {null,                  null,                   null,               null},
                {null,                  null,                   null,               null},
                {null,                  new Block(image),       null,               null},
                {new Block(image),      new Block(image),       new Block(image),   null},
        };
    }
}
