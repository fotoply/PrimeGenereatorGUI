package view;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import model.PrimeGenerator;
import model.PrimeListFileHandler;

import javax.management.InstanceNotFoundException;
import java.io.File;
import java.util.ArrayList;

/**
 * Created 12/1/15
 *
 * @author Niels Norberg
 */
public class MainWindowController {

    ObservableList<Integer> primes = FXCollections.observableArrayList();
    PrimeListFileHandler fileHandler = new PrimeListFileHandler();

    @FXML
    private Button chooseFileButton;

    @FXML
    private Label fileChosenLabel;

    @FXML
    private Label currentNumberLabel;

    @FXML
    private TableView<Integer> primeTableView;

    @FXML
    private TableColumn<Integer, Integer> IDTableColumn;

    @FXML
    private TableColumn<Integer, Integer> valueTableColumn;

    @FXML
    void chooseFileClicked(ActionEvent event) throws InstanceNotFoundException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose the file to read from/write to.");
        File selectedFile = fileChooser.showOpenDialog(chooseFileButton.getScene().getWindow());

        fileHandler.setFile(selectedFile);
        primes.addAll(fileHandler.load());

        Thread calculatorThread = new Thread(()->{
            PrimeGenerator generator = new PrimeGenerator();
            generator.currentNumber = primes.get(primes.size()-1);
            System.out.println(generator.currentNumber);
            generator.primes = primes;
            generator.run();
        });
        calculatorThread.run();

        primes.addListener((ListChangeListener<? super Integer>) c -> {
            if(c.getAddedSize() > 1) {
            } else {
                try {
                    fileHandler.save(primes.get(primes.size()-1));
                } catch (InstanceNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @FXML
    private void initialize() {
        valueTableColumn.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue()).asObject());

        primeTableView.setItems(primes);
    }

}
