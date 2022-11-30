package com.example.proj4fx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class HelloApplication extends Application {
    private Button play = new Button("Play");
    private Button playAgain = new Button("Play Again");

    private Label leftLabelForMode = new Label("");
    private ComboBox<String> comboBoxOperations = new ComboBox<>();
    private RadioButton radioButtonO = new RadioButton("O");
    private RadioButton radioButtonX = new RadioButton("X");
    private String currentPlayer = "human";
    private Button[] buttons = new Button[9];
    private GridPane gridPane = new GridPane();
    private String currentPlayerChar = "O";
    private String currentAiChar = "X";
    private Label outputWinnerLabel = new Label("");
    private int numOfPlayed = 0;
    private HBox hBoxChar = new HBox(7);
    private Stage primaryStage = new Stage();

    public static void main(String[] args) {

        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

        //

        // stage = primaryStage;

        primaryStage.setTitle("Hello Tic Tac Toe");
        primaryStage.setScene(mainAppScene());
        primaryStage.show();
    }

    private Scene mainAppScene() {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10, 10, 70, 10));

        Label welcomeLabel = new Label("Welcome to Tic Tac Toe game AI");
        welcomeLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        welcomeLabel.setTextFill(Color.DARKGRAY);

        borderPane.setTop(welcomeLabel);
        BorderPane.setAlignment(welcomeLabel, Pos.TOP_CENTER);


        gridPane.setHgap(2);
        gridPane.setVgap(2);


        //
        //borderPane.setLeft(leftLabelForMode);
        borderPane.setCenter(gridPane);
        gridPane.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(gridPane, Pos.CENTER);

        comboBoxOperations.getItems().addAll("Easy mode", "Play with friend", "Hard mode");
        comboBoxOperations.getSelectionModel().selectFirst();

        borderPane.setRight(comboBoxOperations);

        outputWinnerLabel.setFont(Font.font("arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 20));
        outputWinnerLabel.setTextFill(Color.RED);


        HBox hBoxBottom = new HBox(50);
        play.setOnAction(new PlayButtonHandler());

        playAgain.setVisible(false);

        playAgain.setOnAction(new PlayAgainButtonHandler());
        hBoxBottom.getChildren().addAll(outputWinnerLabel, play, playAgain);

        borderPane.setBottom(hBoxBottom);
        Label chooseYourCharLabel = new Label("Choose the letter you want");


        ToggleGroup toggleGroup = new ToggleGroup();

        radioButtonO.setSelected(true);
        radioButtonO.setToggleGroup(toggleGroup);
        radioButtonX.setToggleGroup(toggleGroup);

        hBoxChar.getChildren().addAll(chooseYourCharLabel, radioButtonO, radioButtonX);
        VBox vBox = new VBox(20);
        vBox.getChildren().addAll(hBoxChar, leftLabelForMode);
        borderPane.setLeft(vBox);

        //
        // System.out.println(gridPane.getChildren().get(1));

        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(borderPane, 550, 440);
        return scene;
    }

    public void setTextOfButton(Button button, char x_o) {
        button.setText((x_o + "").toUpperCase());
    }

    private void makeInvisible(Node hBoxChar, Node comboBoxOperations, Node play) {
        hBoxChar.setVisible(false);
        comboBoxOperations.setVisible(false);
        play.setVisible(false);

    }

    private void makeVisible(Node hBoxChar, Node comboBoxOperations, Node play) {
        hBoxChar.setVisible(true);
        comboBoxOperations.setVisible(true);
        play.setVisible(true);

    }

    private void makeVisiblePlayAgain() {
        playAgain.setVisible(true);
    }

    private boolean checkIfWon(String playerChar) {


        return checkRowsWon(playerChar) || checkColsWon(playerChar) || checkDiagonalsWon(playerChar);
        //return false;

    }

    private boolean checkRowsWon(String playerChar) {
        int counter = 0;

        for (int i = 0; i < 3; i++) {

            int countEqualsRows = 0;
            for (int j = 0; j < 3; j++) {
                Button buttonRows = (Button) gridPane.getChildren().get(counter);

                if (buttonRows.getText().equals(playerChar.trim())) {
                    countEqualsRows++;
                }
                if (countEqualsRows == 3)
                    return true;
                counter++;
            }
        }
        return false;

    }

    private boolean checkColsWon(String playerChar) {


        int indexOriginal = -3;
        for (int i = 0; i < 3; i++) {

            int index = indexOriginal + i;
            int countEqualsCols = 0;

            for (int j = 0; j < 3; j++) {
                index += 3;
                Button buttonCls = (Button) gridPane.getChildren().get(index);

                // constAdd=3;
                if (buttonCls.getText().equals(playerChar.trim())) {
                    countEqualsCols++;
                }


            }
            if (countEqualsCols == 3)
                return true;
        }

        return false;
    }

    private boolean checkDiagonalsWon(String playerChar) {


        // Button buttonCls = (Button) gridPane.getChildren().get(index);


        int index = 0;

        int countEqualsDiagonals = 0;
        for (int j = 0; j < 3; j++) {
            Button buttonCls = (Button) gridPane.getChildren().get(index);
            if (buttonCls.getText().equals(playerChar.trim())) {
                countEqualsDiagonals++;
            }
            index += 4;
        }
        if (countEqualsDiagonals == 3)
            return true;


        index = 2;

        countEqualsDiagonals = 0;
        for (int j = 0; j < 3; j++) {
            Button buttonCls = (Button) gridPane.getChildren().get(index);
            if (buttonCls.getText().equals(playerChar.trim())) {
                countEqualsDiagonals++;
            }
            index += 2;
        }
        return countEqualsDiagonals == 3;
    }

    private void winnerEvent(String currentPlayerChar) {

        for (int i = 0; i < buttons.length; i++) {

            buttons[i].setDisable(true);
        }
        outputWinnerLabel.setText("'" + currentPlayerChar + "' is the Winner");

    }

    private void addButtonsToCenter(int mode) {
        int counter = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // buttons[i] = new Button(i +" " + j);
                buttons[counter] = new Button();
                buttons[counter].setPrefSize(60, 60);
                buttons[counter].setMaxSize(60, 60);
                buttons[counter].setFont(Font.font("arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 20));
                gridPane.add(buttons[counter], j, i);
                if (mode == 0)
                    buttons[counter].setOnAction(new ButtonHandlerEasy());
                else if (mode == 1)
                    buttons[counter].setOnAction(new ButtonHandlerFriend());
                else
                    buttons[counter].setOnAction(new ButtonHandlerHard());

                counter++;

            }
        }
    }

    private class ButtonHandlerFriend implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {

            System.out.println("I am inside the handerler for friend");

            Button clickedButton = (Button) event.getSource();
            //String ch = clickedButton.getText();

            //if (!(ch.equalsIgnoreCase("x")) && !(ch.equalsIgnoreCase("o"))) {

            setTextOfButton(clickedButton, currentPlayerChar.trim().charAt(0));
            // check if the "human"won call here exactly
            clickedButton.setDisable(true);
            boolean won = checkIfWon(currentPlayerChar);
            if (won) {
                winnerEvent(currentPlayerChar);
                //makeVisible(hBoxChar, comboBoxOperations, play);
                makeVisiblePlayAgain();
            } else {

                if (currentPlayer.equalsIgnoreCase("1"))
                    currentPlayer = "2";
                else
                    currentPlayer = "1";
                leftLabelForMode.setText("It is player " + currentPlayer + " turn");

                if (currentPlayerChar.equalsIgnoreCase("X"))
                    currentPlayerChar = "O";
                else
                    currentPlayerChar = "X";

                numOfPlayed++;
                if (numOfPlayed != 9) {
                    //nextTurn();
                } else {
                    outputWinnerLabel.setText("equality (tie)");
                    makeVisiblePlayAgain();

                    numOfPlayed = 0;
                }
            }


        }


    }

    private class ButtonHandlerHard implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {

            Button clickedButton = (Button) event.getSource();
            //String ch = clickedButton.getText();

            //if (!(ch.equalsIgnoreCase("x")) && !(ch.equalsIgnoreCase("o"))) {
            if (currentPlayer.equals("human")) {
                setTextOfButton(clickedButton, currentPlayerChar.trim().charAt(0));
                clickedButton.setDisable(true);
                // check if the "human"won call here exactly
                boolean won = checkIfWon(currentPlayerChar);
                if (won) {
                    winnerEvent(currentPlayerChar);
                    //makeVisible(hBoxChar, comboBoxOperations, play);
                    makeVisiblePlayAgain();
                } else {

                    currentPlayer = "ai";
                    numOfPlayed++;
                    if (numOfPlayed != 9) {
                        nextTurnWithBestMove();
                    } else {
                        outputWinnerLabel.setText("equality (tie)");
                        makeVisiblePlayAgain();

                        numOfPlayed = 0;
                    }
                }
            }

        }

        private void nextTurnWithBestMove() {



            int bestScore = Integer.MIN_VALUE;
            int bestMove = 0;
            for (int i = 0; i < buttons.length; i++) {

                if (!buttons[i].isDisabled()) {
                    setTextOfButton(buttons[i], currentAiChar.trim().charAt(0));
                    buttons[i].setDisable(true);
                    int score = miniMax( false); // score 1: means best for the "AI" player
                    buttons[i].setDisable(false);                   // the "AI" player is the maximizing
                    buttons[i].setText("");                         // so i am checking the move after me
                    if (score >= bestScore) {                        // (which is the human) so i pass the "false"
                        bestScore = score;
                        bestMove = i;
                    }

                }
            }
            setTextOfButton(buttons[bestMove], currentAiChar.trim().charAt(0));
            buttons[bestMove].setDisable(true);


            // check if the "ai "won call here exactly

            boolean won = checkIfWon(currentAiChar);
            if (won) {
                winnerEvent(currentAiChar);
                // makeVisible(hBoxChar, comboBoxOperations, play);
                makeVisiblePlayAgain();

            } else {


                //aiButton.isDisable();
                currentPlayer = "human";
                numOfPlayed++;
                if (numOfPlayed == 9) {

                    outputWinnerLabel.setText("equality (tie)");
                    makeVisiblePlayAgain();

                    numOfPlayed = 0;
                }
            }
        }

        private int miniMax(boolean isMaximizing) {  // returns the score: 1, -1,0

            boolean playerWon = checkIfWon(currentPlayerChar);
            boolean aiWon = checkIfWon(currentAiChar);
            int score;

            if (playerWon)
                score = -1;

            else if (aiWon)

                score = 1;
            else {

                score = 0; // this means "tie"- they are equal
                for (int i = 0; i < buttons.length; i++) {
                    if (!buttons[i].isDisabled())
                        score = Integer.MAX_VALUE;
                }
            }


            if (score != Integer.MAX_VALUE) {
                return score;
            }
            if (isMaximizing) { // the 'ai' turn

                int bestScore = Integer.MIN_VALUE;

                for (int i = 0; i < buttons.length; i++) {

                    if (!buttons[i].isDisabled()) {
                        setTextOfButton(buttons[i], currentAiChar.trim().charAt(0));
                        buttons[i].setDisable(true);
                        int scoreFrom = miniMax( false);
                        buttons[i].setDisable(false);
                        buttons[i].setText("");
                        bestScore = Math.max(scoreFrom, bestScore);
                       /* if (scoreFrom > bestScore) {
                            bestScore = scoreFrom;
                            bestMove = i;
                        }*/
                        // j++;
                    }
                }
                return bestScore;


            } else {
                int bestScore = Integer.MAX_VALUE;

                for (int i = 0; i < buttons.length; i++) {

                    if (!buttons[i].isDisabled()) {
                        setTextOfButton(buttons[i], currentPlayerChar.trim().charAt(0));
                        buttons[i].setDisable(true);
                        int scoreFrom = miniMax( true);
                        buttons[i].setDisable(false);
                        buttons[i].setText("");
                        bestScore = Math.min(scoreFrom, bestScore);

                       /* if (scoreFrom < bestScore) {
                            bestScore = scoreFrom;
                            bestMove = i;
                        }*/
                        // j++;
                    }
                }
                return bestScore;
            }


            //return 1; // always picks the first move*/

        }
    }

    private class ButtonHandlerEasy implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {

            System.out.println("I am inside the handerler");

            Button clickedButton = (Button) event.getSource();
            //String ch = clickedButton.getText();

            //if (!(ch.equalsIgnoreCase("x")) && !(ch.equalsIgnoreCase("o"))) {
            if (currentPlayer.equals("human")) {
                setTextOfButton(clickedButton, currentPlayerChar.trim().charAt(0));
                // check if the "human"won call here exactly
                boolean won = checkIfWon(currentPlayerChar);
                if (won) {
                    winnerEvent(currentPlayerChar);
                    //makeVisible(hBoxChar, comboBoxOperations, play);
                    makeVisiblePlayAgain();
                } else {
                    clickedButton.setDisable(true);
                    currentPlayer = "ai";
                    numOfPlayed++;
                    if (numOfPlayed != 9) {
                        nextTurn();
                    } else {
                        outputWinnerLabel.setText("equality (tie)");
                        makeVisiblePlayAgain();

                        numOfPlayed = 0;
                    }
                }

            }

            // }

        }

        private void nextTurn() {
            Button[] availableButtons = new Button[9];


            int j = 0;
            for (int i = 0; i < buttons.length; i++) {
                if (!buttons[i].isDisabled()) {
                    availableButtons[j] = buttons[i];
                    j++;
                }
            }
            Random random = new Random();
            int move = 0;
            move = random.nextInt(j);


            Button aiButton = availableButtons[move];

            setTextOfButton(aiButton, currentAiChar.trim().charAt(0));
            // check if the "ai "won call here exactly

            boolean won = checkIfWon(currentAiChar);
            if (won) {
                winnerEvent(currentAiChar);
                // makeVisible(hBoxChar, comboBoxOperations, play);
                makeVisiblePlayAgain();

            } else {

                aiButton.setDisable(true);
                //aiButton.isDisable();
                currentPlayer = "human";
                numOfPlayed++;
                if (numOfPlayed == 9) {

                    outputWinnerLabel.setText("equality (tie)");
                    makeVisiblePlayAgain();

                    numOfPlayed = 0;
                }
            }

        }
    }

    private class PlayButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {

            if (radioButtonO.isSelected()) {
                currentPlayerChar = "O";
                currentAiChar = "X";
            } else {
                currentPlayerChar = "X";
                currentAiChar = "O";
            }


            int chosenMode = comboBoxOperations.getSelectionModel().getSelectedIndex();

            addButtonsToCenter(chosenMode);

            makeInvisible(hBoxChar, comboBoxOperations, play);
            if (chosenMode == 1) {
                currentPlayer = "1";

                leftLabelForMode.setText(comboBoxOperations.getSelectionModel().getSelectedItem() + "\n"
                        + "Player " + currentPlayer + " turn");
                // 0: easy, 1: with friend, 2: hard (minimax)
            }



        }
    }

    private class PlayAgainButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {

            // reset the data;
            play = new Button("Play");
            playAgain = new Button("Play Again");

            comboBoxOperations = new ComboBox<>();
            radioButtonO = new RadioButton("O");
            radioButtonX = new RadioButton("X");
            currentPlayer = "human";
            buttons = new Button[9];
            gridPane = new GridPane();
            currentPlayerChar = "O";
            currentAiChar = "X";
            outputWinnerLabel = new Label("");
            numOfPlayed = 0;
            hBoxChar = new HBox(7);


            primaryStage.setScene(mainAppScene());
            primaryStage.show();
        }
    }

}