package application;

import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.Stage;
import item.ControlInventory;
import map.World;
import menu.EventLog;
import menu.MainMenu;
import object.Hero;

public class Main extends Application {
	private static double sceneWidth, sceneHeight;
	public static Stage stage;
	public static Scene gameScene;
	public static Group gameRoot;
	public static ControlInventory controlInventory;
	public static EventLog eventLog;
	public static Hero hero;
	public static World world;
	
	public static final double defaultWidth = 1000;
	public static final double defaultHeight = 650;
	private static final double barHeigth = 30;

	public static void main(String[] args) { 
		launch(args); 
	}
  
	@Override 
	public void start(Stage primaryStage) throws Exception {
		Scene mainMenuScene = new Scene(new MainMenu(), defaultWidth, defaultHeight);
		stage = primaryStage;
		stage.setScene(mainMenuScene);
		stage.setTitle("Hollow Knight");
		stage.setMinHeight(400);
		stage.setMinWidth(600);
		stage.setFullScreenExitHint("");
		stage.show();
		Sound.changeBackgroundMusic(Music.Proof_of_a_Hero);
	}
	
	public static void updateSceneSize() {
		sceneWidth = stage.getWidth();
		if (stage.isFullScreen()) {
			sceneHeight = stage.getHeight();
		} else {
			sceneHeight = stage.getHeight() - barHeigth;
		}
	}

	public static double getSceneWidth() {
		return sceneWidth;
	}

	public static double getSceneHeight() {
		return sceneHeight;
	}

}