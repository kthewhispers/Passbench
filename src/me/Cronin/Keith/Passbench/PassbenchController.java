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

import java.util.Date;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import me.Cronin.Keith.Passbench.Data.Data;
import me.Cronin.Keith.Passbench.Threading.CrackerThread;
import me.Cronin.Keith.Passbench.Threading.UpdaterThread;

public class PassbenchController {
	@FXML
	public TextField ThreadCounter;
	@FXML
	public CheckBox AdvancedCalcBool;
	@FXML
	public TextField BenchmarkPasswordBox;
	@FXML
	public Label NumAttempts;
	@FXML
	public Label NumAttemptsPerSecond;
	@FXML
	public TextField CrackedPasswordBox;
	@FXML
	public Label NumSameGens;
	@FXML
	public RadioButton RDOButton1;
	@FXML
	public RadioButton RDOButton2;
	@FXML
	public Label ThreadCounterLabel;
	@FXML
	public Button BtnCrackn;
	@FXML
	public Pane GreetingContainer;
	@FXML
	public Button BtnBenchStart;
	@FXML
	public Button BtnBenchStop;
	@FXML
	public Label CrackFinishTime;
	@FXML
	public RadioButton RDOButton3;
	@FXML
	public RadioButton RDOButton4;
	
	public PassbenchController() {

	}
	
	//This fires whenever the user enters Passhack! Do start stuff here.
	public void CracknClick(MouseEvent e) {
		GreetingContainer.setVisible(false);
		Platform.runLater(new UpdaterThread("Passhack-GUI-Thread"));
	}
	//Begins cracking a password-to-benchmark from the user
	public void BStartButtonClick(MouseEvent e) {
		if(CrackFinishTime.isVisible())
		{
			CrackFinishTime.setVisible(false);
		}
		CrackedPasswordBox.setText("");
		Data.crackThis = BenchmarkPasswordBox.getText();
		Data.threadsStayAlive = true;
		Data.passCrackMethod = RDOButton1.getToggleGroup().getSelectedToggle();
		AdvancedCalcBool.setDisable(true);
		ThreadCounter.setDisable(true);
		BenchmarkPasswordBox.setDisable(true);
		RDOButton1.setDisable(true);
		RDOButton2.setDisable(true);
		RDOButton3.setDisable(true);
		RDOButton4.setDisable(true);
		BtnBenchStart.setDisable(true);
		//First we validate all the configuration information so it doesn't blow everything up XD
		//This is where we grab all the configurations to save in memory, and begin.
		Data.setConfigData(Integer.valueOf(ThreadCounter.getText()), AdvancedCalcBool.isSelected(), BenchmarkPasswordBox.getText().length(), true);
		//Grabbed current configurations and saved them in memory. Begin creating the cracker threads.
		//Made this variable to be able to start each thread as it's created.
		CrackerThread threadAddress;
		int threads = (int)Data.configBuffer[0];
		for(int i = 0; i < threads; i++) {
			threadAddress = new CrackerThread("CT" + (i + 1));
			//Here threads CTx - CTxx are created, but not yet started!
			Data.storeThreadInstance(threadAddress);
			//Here the thread is started as soon as it's created.
			threadAddress.start();
		}
		Data.timeBegan[0] = new Date().getTime();
		
	}
	public void BPauseButtonClick(MouseEvent e) {
		Data.threadsStayAlive = false;
		AdvancedCalcBool.setDisable(false);
		ThreadCounter.setDisable(false);
		BenchmarkPasswordBox.setDisable(false);
		RDOButton1.setDisable(false);
		RDOButton2.setDisable(false);
		RDOButton3.setDisable(false);
		RDOButton4.setDisable(false);
		BtnBenchStart.setDisable(false);
		Data.disposeAllData();
		Data.isBenchmarking = false;
		Data.hasBenchmarkBeenCracked = false;
		Data.timeBegan[0] = 0L;
	}
	public void TypedInCrackBox(KeyEvent e) {
		if(!Data.crackedPassword.isEmpty()) {
		CrackedPasswordBox.setText(Data.crackedPassword);
		} else {
			CrackedPasswordBox.setText(Data.crackedPassword);
		}
	}
	public void OnInsideCrackedPassBox(MouseEvent e) {
		if(Data.hasBenchmarkBeenCracked && Data.crackedPassword != null && !Data.crackedPassword.isEmpty()) {
			CrackedPasswordBox.setText(Data.crackedPassword);
		} else {
			CrackedPasswordBox.setText("");
		}
	}
	
}
