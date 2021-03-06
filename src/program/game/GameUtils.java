package program.game;

import javafx.scene.image.Image;
import program.block_shapes.*;

import java.util.Map;
import java.util.Random;

import static program.Constants.NUMBER_OF_COLORS;
import static program.Constants.NUMBER_OF_BLOCKS;

/**
 * Created by IntelliJ IDEA.
 * User: MichaelaGuth
 * Date: 26. 8. 2018
 * Time: 18:10
 */
public class GameUtils {

    /**
     * Creates a copy of given block array.
     * @param src The given block array.
     * @return The copy.
     */
    public static Block[][] copy(Block src[][]) {
        Block[][] copy = new Block[src.length][src[0].length];

        for (int i = 0; i < src.length; i++) {

            for (int j = 0; j < src[0].length; j++) {
                copy[i][j] = src[i][j];
            }

        }

        return copy;
    }


    /**
     * Enum with status info.
     */
    public enum blockInsertStatus {
        OK, COLLISION_WITH_WALL, COLLISION_WITH_END, COLLISION_WITH_OTHER_BLOCK_FROM_SIDE
    }

    /**
     * Tries to insert the shape in given game board.
     * @param shape The shape.
     * @param oldGameBoard Old game board.
     * @param newGameBoard New game board.
     * @param direction The direction where the shape moved.
     * @return Status of the moved block.
     */
    public static blockInsertStatus insertBlock(Shape shape, Block[][] oldGameBoard, Block[][] newGameBoard, Direction direction) {

        int x = shape.getX() + direction.getX(); //změna souřednic
        int y = shape.getY() + direction.getY();

        blockInsertStatus status = blockInsertStatus.OK;

        loop: for (int row = 0; row < shape.getShape().length; row++) {
            for (int col = 0; col < shape.getShape()[row].length; col++) {

                if (shape.getShape()[row][col] == null) {     // kontrola zda ma kostka na danem miste kosticku
                    /*
                     * Aby se kostka nezasekla prazdnym mistem, tak jej ignorovat.
                     * [.][.]
                     *    [.][.]       [.][.]
                     *                    [.][.]
                     * [.]        =>   [.]
                     * [.]             [.]
                     * [.]             [.]
                     * [.]             [.]
                     */
                    continue;
                }

                if (col + x >= oldGameBoard[0].length || col + x < 0) {                    //kontrola jestli kostka nenarazila na levou nebo pravou stěnou
                    status = blockInsertStatus.COLLISION_WITH_WALL;
                    break loop;
                }

                if (row + y == oldGameBoard.length) {                                            //kontrola jestli kostka nedopadla ke spodní hraně
                    status = blockInsertStatus.COLLISION_WITH_END;
                    break loop;
                }

                if (oldGameBoard[row + y][col + x] == null) {                                //kontrola jestli na místě kam se má posunout kostka je místo
                    newGameBoard[row + y][col + x] = shape.getShape()[row][col];
                } else {
                    if (direction != Direction.DOWN) {
                        status = blockInsertStatus.COLLISION_WITH_OTHER_BLOCK_FROM_SIDE;
                    } else {
                        status = blockInsertStatus.COLLISION_WITH_END;
                    }
                    break loop;
                }
            }
        }

        return status;
    }

    /**
     * TODO
     * vynasobi 2 matice
     * @param matrix1
     * @param matrix2
     * @return
     */
    public static int[][] matrixMultiplication(int[][] matrix1, int[][] matrix2) {
        int[][] newMatrix = new int[matrix1.length][matrix2[0].length];
        int j = 0;
        for (int newMatrixLine = 0; newMatrixLine < newMatrix.length; newMatrixLine++) {
            for (int newMatrixColumn = 0; newMatrixColumn < newMatrix[0].length; newMatrixColumn++) {
                for (int i = 0; i < matrix1[0].length; i++) {
                    j = j + matrix1[newMatrixLine][i] * matrix2[i][newMatrixColumn];
                }
                newMatrix[newMatrixLine][newMatrixColumn] = j;
                j = 0;
            }
        }
        return newMatrix;
    }

    /**
     * TODO
     * umaze lineNumber z matice
     * @param lineNumber lineNumber ke smazani
     * @param gameBoard
     */
    public static void deleteLine(int lineNumber, Block[][] gameBoard) {
        for (int col = 0; col < gameBoard[0].length; col++) {
            gameBoard[lineNumber][col] = null;
        }
    }

    /**
     * TODO
     * posune kosticky, tak aby se vyplnil prazdny radek
     * @param emptyLine souradnice prazdneho radku
     * @param gameBoard hraci pole
     * @return
     */
    public static Block[][] moveTheRestBlocksDown(int emptyLine, Block[][] gameBoard) {
        Block[][] newGameBoard = GameUtils.copy(gameBoard);

        for (int line = emptyLine - 1; line > 0; line--) {
            for (int col = 0; col < gameBoard[0].length; col++) {
                newGameBoard[line+1][col] = gameBoard[line][col];
            }
        }
        return newGameBoard;
    }

    /**
     * TODO
     * zkontroluje, jestli neni gameOver
     * @param gameBoard hraci pole ke kontrole
     * @return true pokud nastal gameOver jinak false
     */
    public static boolean checkGameOver(Block[][] gameBoard) {
        for (int col = 0; col < gameBoard[0].length; col++) {
            if (gameBoard[4][col] != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * TODO
     * vygeneruje náhodnou kostičku
     * @param blocksImages mapa pro obrázky kostiček
     * @return vrací náhodnou kostičku
     */
    public static Shape generateRandomShape(Map<BlockEnum, Image> blocksImages) {
        Random random = new Random();
        int randomIndex = random.nextInt(NUMBER_OF_BLOCKS);
        Image randomColor = randomColor(blocksImages);  //nastaví náhodnou barvu pro kosticky

        switch (randomIndex) {
            case 0:
                return new Square(randomColor);
            case 1:
                return new MirrorL(randomColor);
            case 2:
                return new NormalL(randomColor);
            case 3:
                return new ShapeT(randomColor);
            case 4:
                return new Tube(randomColor);
            case 5:
                return new MirrorZ(randomColor);
            case 6:
                return new NormalZ(randomColor);
            default:
                return new NormalZ(randomColor);
        }
    }

    /**
     * TODO
     * nastaví náhodnou barvu
     * @param blocksImages
     * @return
     */
    public static Image randomColor(Map<BlockEnum, Image> blocksImages) {
        Random random = new Random();
        int randomIndex = random.nextInt(NUMBER_OF_COLORS);
        return blocksImages.get(BlockEnum.values()[randomIndex]);
    }



}
