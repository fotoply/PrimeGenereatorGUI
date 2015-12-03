package view;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private ListView<Integer> primeTableView;


    @FXML
    void chooseFileClicked(ActionEvent event) throws InstanceNotFoundException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose the file to read from/write to.");
        File selectedFile = fileChooser.showOpenDialog(chooseFileButton.getScene().getWindow());

        fileHandler.setFile(selectedFile);
        primes.addAll(fileHandler.load());

        Thread calculatorThread = new Thread(()->{
            PrimeGenerator generator = new PrimeGenerator();
            if(primes.size() > 0) {
                generator.currentNumber = primes.get(primes.size()-1);
            }
            generator.primes = primes;
            generator.run();
        });
        calculatorThread.start();

        primes.addListener((ListChangeListener<? super Integer>) c -> {
            c.next();
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
        primeTableView.setItems(primes);
    }

}
