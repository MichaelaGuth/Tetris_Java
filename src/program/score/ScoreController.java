package program.score;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import program.Main;
import program.menu.Controller;
import program.pictures.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Kimiko
 * Date: 22. 8. 2018
 * Time: 22:30
 */
public class ScoreController {
    public AnchorPane anchorPane;
    private Image backButtonImage;
    public ImageView backButton;
    public TableView tableView;
    public TableColumn players;
    public TableColumn scores;
    public TableColumn ranks;

    /**
     * TODO comments + constants + maybe more functions
     */
    @FXML
    public void initialize() {

        // TODO
        Image image = ImageLoader.LoadImage("TetrisBackground.png");
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        anchorPane.setBackground(background);

        // TODO
        backButtonImage = ImageLoader.LoadImage("backButton.png");
        backButton.setImage(backButtonImage);

        // TODO
        ArrayList<String> scores;
        Score score = new Score("HighScore.txt");
        scores = score.loadScore();
        List list = new ArrayList();

        // TODO <????
        ranks.setCellValueFactory(new PropertyValueFactory("Poradi"));
        players.setCellValueFactory(new PropertyValueFactory("JmenoHrace"));
        this.scores.setCellValueFactory(new PropertyValueFactory("HighScore"));

        // TODO ???
        for (String line : scores) {
            String[] tmp = score.split(line);
            list.add(new HighScoreItem(tmp[0], Integer.parseInt(tmp[1])));
        }

        // TODO
        ObservableList data = FXCollections.observableList(list);
        SortedList<HighScoreItem> sortedList = new SortedList<HighScoreItem>(data,
                (HighScoreItem item1, HighScoreItem item2) ->
                {
                   return item2.getHighScore()-item1.getHighScore();
                });

        // TODO
        for (int i = 0; i < sortedList.size(); i++) {
            sortedList.get(i).setRank((i+1)+".");
        }

        //TODO
        tableView.setItems(sortedList);
    }

    /**
     * TODO: handle exception + comment
     * pokud stiskneme tla????tko zp??t, vr??t?? n??s zp??t do menu a vypne timer
     * @throws Exception
     */
    public void backButtonAction() throws Exception {
        Parent root = FXMLLoader.load(Controller.class.getResource("menu.fxml"));    //na??ten?? popisu sc??ny
        Main.stage.setScene(new Scene(root, 600, 800));                 //vytvo??en?? sc??ny a nastaven?? zobrazen??
        Main.stage.show();
    }

    /**
     * TODO
     * p??i najet?? kurzorem my??i na tla????tko - zm??na obr??zku (rozsv??cen?? tla????tka)
     */
    public void onMouseEnterBackButton() {
        backButton.setImage(ImageLoader.LoadImage("onMouseEnterBackButton.png"));
    }

    /**
     * TODO
     * zm??na obr??zku
     */
    public void onMouseLeaveBackButton() {
        backButton.setImage(backButtonImage);
    }
}
