package com.mr235.socketdemo;

import android.app.Application;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

/**
 * Created by Administrator on 2018/4/26.
 */
public class App extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		new Thread() {
			@Override
			public void run() {
				super.run();
				final LocalSocket socket = new LocalSocket();
				try {
					while (true) {
						if (!socket.isConnected()) {
							try {
								socket.connect(new LocalSocketAddress("a.b.c"));
								Thread.sleep(2000);
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							break;
						}
					}
					new Thread() {
						@Override
						public void run() {
							super.run();
							try {
								OutputStream os = socket.getOutputStream();
								BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
								while (true) {
									bw.write("client os:" + new Date().toLocaleString());
									bw.newLine();
									bw.flush();
									try {
										Thread.sleep(2000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}.start();
					new Thread() {
						@Override
						public void run() {
							super.run();
							try {
								InputStream is = socket.getInputStream();
								BufferedReader br = new BufferedReader(new InputStreamReader(is));
								OutputStream os = socket.getOutputStream();
								BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
								String line = null;
								while (true) {
									String s = br.readLine();
									System.out.println("client is:" + s);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}.start();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
}
