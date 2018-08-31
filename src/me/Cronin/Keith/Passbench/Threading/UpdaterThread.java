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
package me.Cronin.Keith.Passbench.Threading;

import javafx.application.Platform;
import me.Cronin.Keith.Passbench.Data.Data;

/* UpdaterThread.java
 * 
 * For updating the UI as needed.
 */

public class UpdaterThread extends Thread{
		
	
	public UpdaterThread(String name) {
		this.setName(name);
		this.setPriority(MIN_PRIORITY);
		this.setDaemon(false);
	}
	
	public void run() {
		//maybe have a timer config to tell it to make updates at a interval the user specifies.
		Data.controller.NumAttempts.setText(String.valueOf(Data.NumAttempts));
		Data.controller.NumAttemptsPerSecond.setText(String.valueOf(Data.currentAttemptsPerSecond()));
		if(Data.controller.BenchmarkPasswordBox.getText().isEmpty() && !Data.isBenchmarking) {
			//Turn everything back on because the user isn't going to benchmark?
			Data.controller.RDOButton1.setDisable(false);
			Data.controller.RDOButton2.setDisable(false);
			Data.controller.RDOButton3.setDisable(false);
			Data.controller.RDOButton4.setDisable(false);
		} else {
			//Here we disable everything in the spec box because the user isn't going to try and crack a password. But benchmark
			Data.controller.RDOButton1.setDisable(true);
			Data.controller.RDOButton2.setDisable(true);
			Data.controller.RDOButton3.setDisable(true);
			Data.controller.RDOButton4.setDisable(true);
		}
		if(Data.hasBenchmarkBeenCracked) {
			
			if(Data.controller.CrackFinishTime.isVisible()) {
				
			} else {
				Data.controller.CrackFinishTime.setVisible(true);
				Data.controller.CrackFinishTime.setText(Data.getElapsedTimeSinceCrackBegin(true));
			}
			
		}
		
		//This turns down the priority of all Threads not directly related to Passbench tasks.
		if(!Data.miscThreadsPrioritized) {
		for (Thread t: Thread.getAllStackTraces().keySet()) {
			
			if(t.getName().charAt(0) == 'C' && t.getName().charAt(1) == 'T') {
				
			} else { t.setPriority(MIN_PRIORITY); }
		  }
		}
		Data.miscThreadsPrioritized = true;
		Platform.runLater(this);
	
  }
}
