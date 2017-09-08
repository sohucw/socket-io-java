package com.dinwiddy.socketiodemo;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.dinwiddy.socketiodemo.domain.TestEvent;

public class ServerBootstrap {

	private static final int PUSH_MESSAGE_DELAY_MS = 40000;
	private static SocketIOServer server;
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerBootstrap.class);
	
	public static void main(String[] args) {

		new ServerBootstrap().run();
	}

	private void run() {
		
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(3000);
		//config.setAllowCustomRequests(true);
		//config.set

        server = new SocketIOServer(config);
 
        server.addConnectListener(new ConnectListener() {
			
			public void onConnect(SocketIOClient client) {
				LOGGER.info("Client connected: {}", client);
			}
		});
        
        server.addDisconnectListener(new DisconnectListener() {
			
			public void onDisconnect(SocketIOClient client) {
				LOGGER.info("Client disconnected: {}", client);
			}
		});

        server.start();
        
        pushMessages();

        server.stop();
	}

	private void pushMessages() {
		
		while(true) {
			TestEvent e = TestEvent.newRandomEvent();
			LOGGER.info("Pushing message to {} clients: {}", getNumConnectedClients(), e);

			//server.getBroadcastOperations().sendJsonObject(e);
			
			try {
				Thread.sleep(PUSH_MESSAGE_DELAY_MS);
			} catch (InterruptedException ex) {
				// Ignore
			}
		}
	}
	
	private int getNumConnectedClients() {
		
		int i = 0;
		Iterator<SocketIOClient> iter = server.getAllClients().iterator();
		while(iter.hasNext()) {
			i++;
			iter.next();
		}
		return i;
	}
}
