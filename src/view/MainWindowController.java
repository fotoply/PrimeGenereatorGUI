package view;

import javafx.application.Platform;
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
    PrimeGenerator generator = new PrimeGenerator();
    private int originalIndex = 0;
    private Thread calculatorThread;

    @FXML
    private Button chooseFileButton;

    @FXML
    private TextField currentFileField;

    @FXML
    private ListView<Integer> primeTableView;

    @FXML
    private Button startButton;


    @FXML
    void chooseFileClicked(ActionEvent event) throws InstanceNotFoundException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose the file to read from/write to.");
        File selectedFile = fileChooser.showOpenDialog(chooseFileButton.getScene().getWindow());
        currentFileField.setText("File: " + selectedFile.getPath());

        fileHandler.setFile(selectedFile);
        primes.clear();
        primes.addAll(fileHandler.load());
        originalIndex = primes.size()-1;
        if(originalIndex == -1) {
            originalIndex = 0;
        }

    }

    @FXML
    private void startButtonClicked() throws InstanceNotFoundException {
        if(startButton.getText().equals("Start")) {
            startButton.setText("Pause");

            generator.shouldRun = true;
            calculatorThread = new Thread(()->{
                if(primes.size() > 0) {
                    generator.currentNumber = primes.get(primes.size()-1);
                }
                generator.primes = primes;
                generator.run();
            });
            calculatorThread.setDaemon(true);
            calculatorThread.start();
        } else {
            generator.shouldRun = false;
            while (calculatorThread.isAlive())  {

            }
            fileHandler.save(primes.subList(originalIndex,primes.size()));
            originalIndex = primes.size()-1;
            startButton.setText("Start");
        }
    }

    @FXML
    private void initialize() {
        primeTableView.setItems(primes);
    }

}
