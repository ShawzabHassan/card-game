package game;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			final FXMLLoader ldr = new FXMLLoader(getClass().getResource("PeliMain.fxml"));
			final Pane root = (Pane)ldr.load();
			final Scene scene = new Scene(root);

			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle (KeyEvent event){
					if (event.getCode() == KeyCode.F1) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Game instructions");
						alert.setHeaderText(null);
						try {
							alert.setContentText(new String(Files.readAllBytes(Paths.get("src/game/Instructions.txt"))));
						} catch (IOException e) {
							alert.setContentText("ERROR: instructions not found");
						}

						alert.showAndWait();
					}
				}
			});

			primaryStage.setScene(scene);
			primaryStage.setTitle("Paskahousu");
			primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("cards/icon.png")));
			primaryStage.setResizable(false);


		} catch(Exception e) {
			e.printStackTrace();
		}
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
