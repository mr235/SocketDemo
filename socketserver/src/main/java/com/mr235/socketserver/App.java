package com.mr235.socketserver;

import android.app.Application;
import android.net.LocalServerSocket;
import android.net.LocalSocket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

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

				try {
					LocalServerSocket server = new LocalServerSocket("a.b.c");
					while (true) {
						final LocalSocket socket = server.accept();

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
										System.out.println("server is:" + s);
										bw.write("server receiver");
										bw.newLine();
										bw.flush();
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}.start();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}
