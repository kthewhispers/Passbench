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
package me.Cronin.Keith.Passbench.Data;



import java.util.Date;

import javafx.scene.control.Toggle;
import me.Cronin.Keith.Passbench.Passbench;
import me.Cronin.Keith.Passbench.PassbenchController;
import me.Cronin.Keith.Passbench.Threading.CrackerThread;

/* Data.java
 * 
 * This abstract class acts as a class for storing data, and housing the functions that all of the classes 
 * in Passbench make calls to. It acts as a bridge between actual Passbench classes (like CrackerThread) and the JavaFX GUI
 * 
 */

public abstract class Data {
	
	public static boolean isBenchmarking = false;
	public static boolean hasBenchmarkBeenCracked = false;
	public static String crackedPassword;
	public static Toggle passCrackMethod;
	public static boolean miscThreadsPrioritized = false;
	//A pointer to the JavaFX controller. Data.controller gives access to the JavaFX controller, from anywhere.
	public static PassbenchController controller = Passbench.controller;
	//A pointer to the string that needs to be cracked.
	public static String crackThis = controller.BenchmarkPasswordBox.getText();
	
	public static Long NumAttempts = 0L;
	
	//A buffer for storing global data. Can be referenced at any time to get access to current cracker configurations.
	public static Object[] configBuffer;
	
	//A saved instance of CrackerThread(s) for global reference.
	public static CrackerThread[] threadBuffer;
	
	public static boolean threadsStayAlive;
	
	//sets the configuration data, this has to be done in the controller's class.
	public static void setConfigData(int numThreads, boolean calcAdvanced, int passCharCount, boolean benchmarkPass)
	{
		configBuffer = new Object[4];
		threadBuffer = new CrackerThread[numThreads];
		configBuffer[0] = numThreads;
		configBuffer[1] = calcAdvanced;
		configBuffer[2] = passCharCount;
		configBuffer[3] = benchmarkPass;
	}
	//stores the sent thread instance into the global data buffer.
	public static void storeThreadInstance(CrackerThread thread) {
		//If nothing is in the buffer just put it in the first index, otherwise, push the thread on the stack.
		if(threadBuffer.length == 0)
		{
			threadBuffer[0] = thread;
		} else {
			threadBuffer[threadBuffer.length - 1] = thread;
		}
	}
	//allows to get a CrackerThread instance as a CrackerThread by it's name.
	public static CrackerThread getCrackerThreadByName(String name) {
		
		if(threadBuffer != null) {
			int threads = (int)configBuffer[0];
		for(int i = 0; i < threads; i++)
		{
			if(threadBuffer[i].getName() == name)
			{
				return threadBuffer[i];
			} else {
				
			}
		}
		return null;
		} else {
			return null;
		}
	}
	
	private static long timestamp = new Date().getTime();
	private static long lastResult;
	private static long numTicks = 1;
	
	public static long currentAttemptsPerSecond() {
		//What happens after one second has passed
		if(new Date().getTime() - timestamp >= 1000){
			//update timestamp
			timestamp = new Date().getTime();
			lastResult = NumAttempts / numTicks;
			numTicks++;
			return lastResult;
		}
		else
		{
			return lastResult;
		}
	}
	
	public static void disposeAllData() {
		NumAttempts = 0L;
		lastResult = 0L;
		numTicks = 1;
		timeBegan[0] = 0L;
	}
	public static long[] timeBegan = new long[1];
	public static String getElapsedTimeSinceCrackBegin(boolean formatted) {
		long elapsedMS = new Date().getTime() - timeBegan[0];
		long days;
		long hours;
		long minutes;
		long seconds;
		if(formatted) {
			//checks to see if it took at least 1 day
			if(elapsedMS / (1000 * 60 * 60 * 24) < 1) {
				days = 0;
			} else {
				//store how many days it took
				days = elapsedMS / (1000 * 60 * 60 * 24);
				//subtract the number of days in milliseconds
				elapsedMS = elapsedMS - (days * (1000 * 60 * 60 * 24));
			}
			//checks to see if it took at least one hour
			if(elapsedMS / (1000*60*60) < 1) {
				hours = 0;
			} else {
				//store how many hours it took
				hours = elapsedMS / (1000 * 60 * 60);
				//subtract the hours from milliseconds
				elapsedMS = elapsedMS - (hours * (1000 * 60 * 60));
			}
			//checks to see if it took at least 1 minute
			if(elapsedMS / (1000*60) < 1) {
				minutes = 0;
			} else {
				//store how many minutes it took
				minutes = elapsedMS / (1000 * 60);
				//subtract minutes from milliseconds
				elapsedMS = elapsedMS - (minutes * (1000 * 60));
			}
			//checks to see if it took at least 1 second
			if(elapsedMS/1000 < 1)
			{
				return elapsedMS + " Milliseconds";
			} else {
				//store how many seconds it took
				seconds = elapsedMS / 1000;
				//subtract seconds from milliseconds
				elapsedMS = elapsedMS - (seconds * 1000);
			}
			return("Days: " + days + " Hours: " + hours + " Minutes: " + minutes + " Seconds: " + seconds);
		} else {
			return String.valueOf(elapsedMS+"ms");
		}
	}
	
	public static void checkCrackThis(String gPass) {
		if(gPass.equals(crackThis))
		{
			hasBenchmarkBeenCracked = true;
			threadsStayAlive = false;
			isBenchmarking = false;
			crackedPassword = gPass;
			Data.controller.CrackedPasswordBox.setText(gPass);
		} else { }
	}
}
