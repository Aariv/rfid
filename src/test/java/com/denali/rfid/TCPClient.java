package com.denali.rfid;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author zentere
 *
 */
public class TCPClient {

	public static void main(String[] args) throws InterruptedException {
		try {
			String data = "A0 13 01 8A 0C 30 00 54 30 30 38 30 39 30 30 32 37 D8 DB 30 8D A0 05 01 8A 01 22 AD A0 05 01 8A 02 22 AC A0 05 01 8A 03 22 AB A0 05 01 8A 01 22 AD A0 05 01 8A 02 22 AC A0 05 01 8A 03";
			@SuppressWarnings("resource")
			Socket socket = new Socket("192.168.2.203", 4001);
			OutputStream output = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(output, true);
			while (true) {
				writer.println(data);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}