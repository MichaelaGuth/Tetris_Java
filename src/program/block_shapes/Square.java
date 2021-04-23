package program.block_shapes;

import javafx.scene.image.Image;
import program.game.Block;


/**
 * Created by IntelliJ IDEA.
 * User: Kimiko
 * Date: 23. 8. 2018
 * Time: 19:14
 */
public class Square extends Shape {
    public Square(Image image) {
        super(image); //zavola konstruktor predka
        this.shape = new Block[][] {
                {null,null,null,null},
                {null,null,null,null},
                {new Block(image),new Block(image),null,null},
                {new Block(image),new Block(image),null,null},
        };

    }
}