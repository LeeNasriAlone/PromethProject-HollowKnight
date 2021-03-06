package menu;

import application.Controller;
import application.Main;
import application.Music;
import application.Sound;
import item.ControlInventory;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import map.World;
import object.Hero;

public class MainMenu extends VBox {
	
	public MainMenu() {
		setAlignment(Pos.CENTER);
		setSpacing(30);
		setBackground(new Background(
				new BackgroundImage(new Image(ClassLoader.getSystemResource("Background/Great_Grey_Wolf.png").toString()), 
						BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
		Button newGameButton = new Button("New Game");
		newGameButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				createNewGame();
			}
		});
		newGameButton.setPrefSize(100, 30);
		newGameButton.setFocusTraversable(false);
		Button exitButton = new Button("Exit");
		exitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Main.stage.close();
			}
		});
		exitButton.setPrefSize(100, 30);
		exitButton.setFocusTraversable(false);
		getChildren().addAll(newGameButton, exitButton);
		Sound.changeBackgroundMusic(Music.Proof_of_a_Hero);
	}
	
	private void createNewGame() {
		startLoadingScene();
		Thread createGame = new Thread() {
			@Override
			public void run() {
				Main.controlInventory = new ControlInventory();
				Main.eventLog = new EventLog();
				Main.hero = new Hero();
				Main.world = new World();
				Main.hero.spawn();
				Main.gameRoot = new Group(Main.world, Main.hero.getHpBar(), Main.eventLog);
				Controller.setGameKey();
				Controller.startGameLoop();
				Platform.runLater(new Runnable(){
					@Override
					public void run() {
						Main.gameScene.setRoot(Main.gameRoot);
					}
				});
			}
		};
		createGame.start();
	}
	
	private void startLoadingScene() {
		Label text = new Label("now loading...");
		text.setFont(Font.font(30));
		StackPane loading = new StackPane(text);
		loading.setBackground(new Background(
				new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
		Scene loadingScene = new Scene(loading, Main.defaultWidth, Main.defaultHeight);
		Main.gameScene = loadingScene;
		Main.stage.setScene(Main.gameScene);
		Main.stage.setFullScreen(true);
	}

}
