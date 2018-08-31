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

import java.nio.charset.Charset;
import java.util.Random;

import me.Cronin.Keith.Passbench.Data.Data;

/* CrackerThread.java
 * 
 * This is a Thread class that handles the generation of passwords, which are then sent to the Data class to be evaluated.
 */
public class CrackerThread extends Thread {
	
	private byte[] passwordByteBuffer;
	
	private String generatedPassword;
	
	private Random password = new Random();
	
	public CrackerThread(String name) {
		this.setName(name);
		this.setPriority(MAX_PRIORITY);
		this.setDaemon(false);
	}
	
	private Random r = new Random();
	private byte[] ba = new byte[1];
	
	public void run() {
		//If Better Cracker Integrity is set... This shuffles a seed for the generator 25 times before beginning, otherwise it does it just once.
		if((boolean)Data.configBuffer[1]) {

			Random salt = new Random();
			salt.setSeed(new Random().nextLong());
		    for(int i = 1; i < 25; i++) {
				salt.nextLong();
			}
			password.setSeed(salt.nextLong());	
		} else {
		password.setSeed(new Random().nextLong());
		}
		//Lets grab the password char length
		
		//Creating the byte array to be the specified length.
		passwordByteBuffer = new byte[(int)Data.configBuffer[2]];
		while(Data.threadsStayAlive) 
		{
			password.nextBytes(passwordByteBuffer);
			//Removes bytes that aren't UTF-8, and generates a byte to fill that index that is greater than decimal 33
			//and less than decimal 126, therefore, it can be any sequence of UTF-8 characters without any spaces.

			//We need to choose a generation method based off Password Specifications
			/* The bytes have already been generated past this point, these
			 * blocks of conditional for loops are here to verify that the
			 * generated passwordByteBuffer match the password specifications
			 * otherwise we iterate over the buffer with new random bytes until
			 * it does.
			 */
			//Letters,Symbols,Numbers
			if(Data.passCrackMethod == Data.controller.RDOButton2) {
			for(int i = 0; i < passwordByteBuffer.length; i++)
			{
				while(!(passwordByteBuffer[i] < 127 & passwordByteBuffer[i] > 32)) {
					r.nextBytes(ba);
					passwordByteBuffer[i] = ba[0];
				}
			  }
			}
			//Letters,Numbers
			else if (Data.passCrackMethod == Data.controller.RDOButton1) {
				for(int i2 = 0; i2 < passwordByteBuffer.length; i2++)
				{
					while(!((passwordByteBuffer[i2] < 91 && passwordByteBuffer[i2] > 64) || (passwordByteBuffer[i2] > 96 && passwordByteBuffer[i2] < 123) || (passwordByteBuffer[i2] > 47 && passwordByteBuffer[i2] < 58))) {
						r.nextBytes(ba);
						passwordByteBuffer[i2] = ba[0];
					}
				 }
			}
			//Only Letters
			else if (Data.passCrackMethod == Data.controller.RDOButton3) {
				for(int i3 = 0; i3 < passwordByteBuffer.length; i3++)
				{
					while(!((passwordByteBuffer[i3] < 91 && passwordByteBuffer[i3] > 64) || (passwordByteBuffer[i3] > 96 && passwordByteBuffer[i3] < 123))) {
						r.nextBytes(ba);
						passwordByteBuffer[i3] = ba[0];
					}	
				 }
		    }
			//Only Numbers
			else {
				for(int i4 = 0; i4 < passwordByteBuffer.length; i4++)
				{
					while(!(passwordByteBuffer[i4] > 47 && passwordByteBuffer[i4] < 58)) {
						r.nextBytes(ba);
						passwordByteBuffer[i4] = ba[0];
					}	
				 }
				
			}
			
			//check the password and do the appropriate thing
			generatedPassword = new String(passwordByteBuffer, Charset.forName("UTF-8"));
			//For debugging!!!!
			//System.out.println("Password generated: (" + generatedPassword + ")");
			Data.checkCrackThis(generatedPassword);
			Data.NumAttempts++;
	}

  }
}
