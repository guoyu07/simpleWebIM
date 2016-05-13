package lee.ws;

import java.io.IOException;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import lee.context.WSContext;
import lee.mq.ImMQSender;

@ServerEndpoint(value = "/im",configurator=ImWebSocketConfig.class)
public class ImWebSocketHandle {
	
	@OnOpen
    public void onOpen(Session session, EndpointConfig config) {
		Long userId = WSContext.put(session);
		session.getUserProperties().put("userId",userId);
		System.out.println("====用户"+userId+"加入聊天室====");
    }
      
    @OnMessage  
    public void onMessage(String message,Session session) {
    	for (Session s : WSContext.getAllSession()) {
			try {
				Long userId = (Long)s.getUserProperties().get("userId");
				s.getBasicRemote().sendText("用户["+userId+"]:"+message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    	//ImMQSender.send((Long)session.getUserProperties().get("userId"), message);
    }
      
    @OnClose  
    public void onClose(Session session) {
    	Long userId = (Long)session.getUserProperties().get("userId");
    	WSContext.remove(userId);
    	System.out.println("====用户"+userId+"退出聊天室====");
    }
    
    @OnError
    public void onError(Throwable throwable){
    	
    }
}
