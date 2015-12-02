package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Created 12/1/15
 *
 * @author Niels Norberg
 */
public class MainWindowController {

    @FXML
    private Button chooseFileButton;

    @FXML
    private Label fileChosenLabel;

    @FXML
    private Label currentNumberLabel;

    @FXML
    private TableView<?> primeTableView;

    @FXML
    private TableColumn<?, ?> IDTableColumn;

    @FXML
    private TableColumn<?, ?> valueTableColumn;

    @FXML
    void chooseFileClicked(ActionEvent event) {

    }


}
