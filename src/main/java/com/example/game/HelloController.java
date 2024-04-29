package com.example.game;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.security.Key;
import java.util.HashMap;
import java.util.Optional;

public class HelloController {
    @FXML private GridPane gpPane;

    String[][] strArray = new String[4][4];
    Label[][] lbArray = new Label[4][4];
    private boolean canMove = true;
    private boolean moved = false;

    public void initialize(){
        gpPane.requestFocus();
        for (int i = 0; i < 4; i++){ for (int j = 0; j < 4; j++){
                strArray[i][j] = "O";
            lbArray[i][j] = new Label();
            lbArray[i][j].setPrefWidth(128);
            lbArray[i][j].setPrefHeight(128);
            lbArray[i][j].setAlignment(Pos.CENTER);
            lbArray[i][j].setFont(new Font(50));
            lbArray[i][j].setText(null);
            lbArray[i][j].setTranslateX(j*128);
            lbArray[i][j].setTranslateY(i*128);
            gpPane.getChildren().add(lbArray[i][j]);
            }
        }
        place2();
        place2();
    }

    private void place2(){
        int r1, r2;
        do{
            r1 = (int)(Math.random()*4);
            r2 = (int)(Math.random()*4);
        }while (!strArray[r1][r2].equals("O"));
        strArray[r1][r2] = "X";
        lbArray[r1][r2].setText("2");
        lbArray[r1][r2].setStyle("-fx-background-color: #DFFF00;");
    }

    @FXML private void onKeyPressed(KeyEvent e){
        //System.out.println(e.getCode());
        if (e.getCode() == KeyCode.W){
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    move(i, j,'U');
                }
            }
            check('U');
        }
        if (e.getCode() == KeyCode.S){
            for (int i = 3; i >= 0; i--) {
                for (int j = 0; j < 4; j++) {
                    move(i,j,'D');
                }
            }
            check('D');
        }
        if (e.getCode() == KeyCode.A){
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    move(i, j,'L');
                }
            }
            check('L');
        }
        if (e.getCode() == KeyCode.D){
            for (int i = 0; i < 4; i++) {
                for (int j = 3; j >= 0; j--) {
                    move(i,j,'R');
                }
            }
            check('R');
        }
        if ("WASD".contains(e.getCode().toString()) && !isFull() && canMove) place2();
        boolean end = true;
        if (isFull()) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (i > 0 && lbArray[i][j].getText() != null && lbArray[i-1][j].getText().equals(lbArray[i][j].getText())) end = false;
                    if (i < 3 && lbArray[i][j].getText() != null && lbArray[i+1][j].getText().equals(lbArray[i][j].getText())) end = false;
                    if (j > 0 && lbArray[i][j].getText() != null && lbArray[i][j-1].getText().equals(lbArray[i][j].getText())) end = false;
                    if (j < 3 && lbArray[i][j].getText() != null && lbArray[i][j+1].getText().equals(lbArray[i][j].getText())) end = false;
                }
            }
            if (end) window("Game Over!");
        }
    }

    private void move(int i, int j, char dir){
        if (i > 3 || i < 0 || j > 3 || j < 0) return;
        if (dir == 'U') if (i > 0 && strArray[i][j].equals("X") && strArray[i-1][j].equals("O")){
            strArray[i][j] = "O";
            strArray[i-1][j] = "X";
            lbArray[i-1][j].setText(lbArray[i][j].getText());
            lbArray[i][j].setText(null);
            lbArray[i-1][j].setStyle(lbArray[i][j].getStyle());
            lbArray[i][j].setStyle("");
            if (!moved) moved = true;
            move(i-1, j, dir);
        }
        if (dir == 'D') if (i < 3 && strArray[i][j].equals("X") && strArray[i+1][j].equals("O")){
            strArray[i][j] = "O";
            strArray[i+1][j] = "X";
            lbArray[i+1][j].setText(lbArray[i][j].getText());
            lbArray[i][j].setText(null);
            lbArray[i+1][j].setStyle(lbArray[i][j].getStyle());
            lbArray[i][j].setStyle("");
            if (!moved) moved = true;
            move(i+1, j, dir);
        }
        if (dir == 'L') if (j > 0 && strArray[i][j].equals("X") && strArray[i][j-1].equals("O")){
            strArray[i][j] = "O";
            strArray[i][j-1] = "X";
            lbArray[i][j-1].setText(lbArray[i][j].getText());
            lbArray[i][j].setText(null);
            lbArray[i][j-1].setStyle(lbArray[i][j].getStyle());
            lbArray[i][j].setStyle("");
            if (!moved) moved = true;
            move(i, j-1, dir);
        }
        if (dir == 'R') if (j < 3 && strArray[i][j].equals("X") && strArray[i][j+1].equals("O")){
            strArray[i][j] = "O";
            strArray[i][j+1] = "X";
            lbArray[i][j+1].setText(lbArray[i][j].getText());
            lbArray[i][j].setText(null);
            lbArray[i][j+1].setStyle(lbArray[i][j].getStyle());
            lbArray[i][j].setStyle("");
            if (!moved) moved = true;
            move(i, j+1, dir);
        }
    }

    private void check(char c){
        if (c == 'U') {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (i < 3 && lbArray[i+1][j].getText() != null && lbArray[i+1][j].getText().equals(lbArray[i][j].getText())) {
                        strArray[i][j] = "X";
                        strArray[i+1][j] = "O";
                        lbArray[i][j].setText(Integer.parseInt(lbArray[i+1][j].getText())*2 + "");
                        lbArray[i+1][j].setText(null);
                        color(i,j);
                        lbArray[i+1][j].setStyle("");
                        if (lbArray[i][j].getText().equals("2048")) window("Well Done!");
                        if (!moved) moved = true;
                    }
                    move(i,j,c);
                }
            }
        }
        if (c == 'D'){
            for (int i = 3; i >= 0; i--) {
                for (int j = 0; j < 4; j++) {
                    if (i > 0 && lbArray[i-1][j].getText() != null && lbArray[i-1][j].getText().equals(lbArray[i][j].getText())) {
                        strArray[i][j] = "X";
                        strArray[i-1][j] = "O";
                        lbArray[i][j].setText(Integer.parseInt(lbArray[i-1][j].getText())*2 + "");
                        lbArray[i-1][j].setText(null);
                        color(i,j);
                        lbArray[i-1][j].setStyle("");
                        if (lbArray[i][j].getText().equals("2048")) window("Well Done!");
                        if (!moved) moved = true;
                    }
                    move(i,j,c);
                }
            }
        }
        if (c == 'L') {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (j < 3 && lbArray[i][j+1].getText() != null && lbArray[i][j+1].getText().equals(lbArray[i][j].getText())) {
                        strArray[i][j] = "X";
                        strArray[i][j+1] = "O";
                        lbArray[i][j].setText(Integer.parseInt(lbArray[i][j+1].getText())*2 + "");
                        lbArray[i][j+1].setText(null);
                        color(i,j);
                        lbArray[i][j+1].setStyle("");
                        if (lbArray[i][j].getText().equals("2048")) window("Well Done!");
                        if (!moved) moved = true;
                    }
                    move(i,j,c);
                }
            }
        }
        if (c == 'R'){
            for (int i = 0; i < 4; i++) {
                for (int j = 3; j >= 0; j--) {
                    if (j > 0 && lbArray[i][j-1].getText() != null && lbArray[i][j-1].getText().equals(lbArray[i][j].getText())) {
                        strArray[i][j] = "X";
                        strArray[i][j-1] = "O";
                        lbArray[i][j].setText(Integer.parseInt(lbArray[i][j-1].getText())*2 + "");
                        lbArray[i][j-1].setText(null);
                        color(i,j);
                        lbArray[i][j-1].setStyle("");
                        if (lbArray[i][j].getText().equals("2048")) window("Well Done!");
                        if (!moved) moved = true;
                    }
                    move(i,j,c);
                }
            }
        }
        if (moved){canMove = true; moved = false;}
        else canMove = false;
    }

    private boolean isFull(){
        int x = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (strArray[i][j].equals("X")) x++;
            }
        }
        return x == 16;
    }

    private void window(String s){
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("2048");
        a.setHeaderText(null);
        a.setContentText(s);
        a.getButtonTypes().clear();
        a.getButtonTypes().add(new ButtonType("Restart"));
        Optional<ButtonType> buttons = a.showAndWait();
        if (buttons.isPresent() && buttons.get().getText().equals("Restart")){
            //canMove = true;
           // moved = false;
            for (int i = 0; i < 4; i++){ for (int j = 0; j < 4; j++){
                strArray[i][j] = "O";
                lbArray[i][j].setText(null);
                lbArray[i][j].setStyle("");
            }
            }
            place2();
            place2();
        }
    }

    private void color(int i, int j){
        String x = lbArray[i][j].getText();
        switch (x) {
            case "4" -> lbArray[i][j].setStyle("-fx-background-color: #FFFF8F;");
            case "8" -> lbArray[i][j].setStyle("-fx-background-color: #FFEA00;");
            case "16" -> lbArray[i][j].setStyle("-fx-background-color: #FCF55F;");
            case "32" -> lbArray[i][j].setStyle("-fx-background-color: #FAFA33;");
            case "64" -> lbArray[i][j].setStyle("-fx-background-color: #FBEC5D;");
            case "128" -> lbArray[i][j].setStyle("-fx-background-color: #FFDB58;");
            case "256" -> lbArray[i][j].setStyle("-fx-background-color: #C4B454;");
            case "512" -> lbArray[i][j].setStyle("-fx-background-color: #FDDA0D;");
            case "1024" -> lbArray[i][j].setStyle("-fx-background-color: #8B8000;");
            case "2048" -> lbArray[i][j].setStyle("-fx-background-color: #FFFFFF;");
        }
    }

    /*private void show(){
        for (int i = 0; i < 4; i++){ for (int j = 0; j < 4; j++) {
            System.out.print(strArray[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }*/
}

