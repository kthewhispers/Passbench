/* License & Copyright Notice
 * 
 * Copyright (C) 2018  Keith Cronin
 * 
 * This file is part of Passbench.

    Passbench is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, only version 3 of the license, also known
    as GNU GPLv3.

    Passbench is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Passbench.  If not, see <https://www.gnu.org/licenses/>.
    
    E-mail Address: k dot cronin1100010 at gmail dot com
 */
package me.Cronin.Keith.Passbench;
	
import java.io.IOException;
import me.Cronin.Keith.Passbench.PassbenchController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

public class Passbench extends Application {
	
	public static PassbenchController controller;
	@Override
	public void start(Stage primaryStage) throws IOException {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Passbench.fxml"));
		    Parent root = loader.load();
		    //Now we have access to getController() through the instance... don't forget the type cast
		    controller = (PassbenchController)loader.getController();
			Scene scene = new Scene(root,600,400);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Passbench: The Password Benchmark Tool");
			primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
