package lee.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Session;

public class WSContext {
	private static Map<Long,Session> context = new ConcurrentHashMap<Long, Session>();
	private static Long count = 1L;
	
	public static Session get(Long sessionId){
		return context.get(sessionId);
	}
	
	public synchronized static Long put(Session session){
		context.put(count,session);
		return count++;
	}
	
	public static List<Session> getAllSession(){
		return new ArrayList<Session>(context.values());
	}
	
	public synchronized static Session remove(Long sessionId){
		return context.remove(sessionId);
	}
	
}
