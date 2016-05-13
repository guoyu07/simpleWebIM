package lee.servelt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import lee.mq.ImMQReciver;

@WebServlet(urlPatterns={"/"},loadOnStartup=0)
public class ReciveServelt extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	public void destroy() {
	}

	@Override
	public void init() throws ServletException {
		System.out.println("-------初始化-----------");
		ImMQReciver.recive();
	}
	
}
