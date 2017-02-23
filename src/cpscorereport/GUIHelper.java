/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpscorereport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;

/**
 *
 * @author Vinay
 */
public class GUIHelper {

    public static MenuBar getMenu(Text textbox) {
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        Menu helpMenu = new Menu("Help");

        MenuItem startServer = new MenuItem("Start Server");
        startServer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                //System.out.println("Server started!");
                textbox.setText(ServerHelper.startServer());
            }
        });

        MenuItem stopServer = new MenuItem("Stop Server");
        stopServer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                textbox.setText(ServerHelper.stopServer());
            }
        });

        MenuItem export = new MenuItem("Export to xls");
        export.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                ServerHelper.exportToExcel(textbox);
                //textbox.setText("Exported!");
            }
        });

        MenuItem quit = new MenuItem("Exit");
        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                System.exit(0);
            }
        });

        MenuItem about = new MenuItem("About...");
        about.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                //System.out.println("Created by @billwi and @hexalellogram for the VHS CyberPatriot team!\nShoutout to Mr. Osborne for running CyberPatriot, Irvin for guiding us, and Mr. Parker for giving us the knowledge to run this!\n\nThis is licensed under the GNU/GPL V3 Public license!");
                Dialog aboutDiag = new Dialog();
                aboutDiag.setContentText("Created by @billwi and @hexalellogram for the VHS CyberPatriot team!\nShoutout to Mr. Osborne for running CyberPatriot, Irvin for guiding us, and Mr. Parker for giving us the knowledge to run this!\n\nThis is licensed under the GNU/GPL V3 Public license!");
                aboutDiag.setTitle("About CPScoreboard");
                aboutDiag.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                Node closeButton = aboutDiag.getDialogPane().lookupButton(ButtonType.CLOSE);
                closeButton.managedProperty().bind(closeButton.visibleProperty());
                closeButton.setVisible(false);
                aboutDiag.showAndWait();
            }
        });

        fileMenu.getItems().addAll(startServer, stopServer, export, quit);
        helpMenu.getItems().addAll(about);
        menuBar.getMenus().addAll(fileMenu, helpMenu);

        return menuBar;
    }

    public static void updateScores(ArrayList<Team> teams) {
        Scanner inFile;
        try {
            inFile = new Scanner(new File("rawData.txt"));
            while (inFile.hasNextLine()) {
                String teamname = inFile.next();
                int score = inFile.nextInt();
                String time = inFile.nextLine();
                int loc = -1;
                for (int i = 0; i < teams.size(); i++) {
                    if (teams.get(i).getTeamName().equals(teamname)) {
                        loc = i;
                    }
                }
                if (loc != -1) {
                    teams.add(new Team(teamname, new Score(time, score)));
                } else {
                    teams.get(loc).addScore(new Score(time, score));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }
}