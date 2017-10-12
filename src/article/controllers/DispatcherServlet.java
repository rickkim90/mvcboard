package article.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// log4j 변수 설정
	/*private static Logger logger = 
			Logger.getLogger(DispatcherServlet.class);*/
	private static Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

	private static Map<String, AbstractController> controllerMap =
			new HashMap<String, AbstractController>();
	
	@Override
	public void init() throws ServletException {
		logger.info("DispatcherServlet.init() 수행중...");
		InputStream is = null;
		Properties pr = new Properties();
		String filePath = this.getClass().getResource("").getPath();
		try {
			is = new FileInputStream(
					filePath + "dispatcher-servlet.properties");
			pr.load(is);
			for(Object obj:pr.keySet()) {
				String key = ((String)obj).trim();
				String classPath = (pr.getProperty(key)).trim();
				
				try {
					@SuppressWarnings("rawtypes")
					Class className = Class.forName(classPath);
					controllerMap.put(key, (AbstractController)className.newInstance());
					logger.info("loaded : " + key);
				} catch(Exception e) {
					e.printStackTrace();
					logger.info("failure : " + key);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) try{is.close();} catch(Exception e){};
		}
		
	}
	
	
	@Override
	protected void service(
		HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String contextPath = request.getContextPath();
		String action = 
				request.getRequestURI().trim()
				.substring(contextPath.length());
		logger.info(action);

		AbstractController controller = null;
		ModelAndView mav = null;

		controller = controllerMap.get(action);
		if (controller == null) {
			logger.info("수행할 액션이 없거나 AbstractController의 서브타입이 아닙니다.");
			return;
		}
		mav = controller.handleRequestInternal(request, response);
		
		if (mav != null) {
			for(String key : mav.getModel().keySet()){
				request.setAttribute(key, mav.getModel().get(key));
			}
			RequestDispatcher dispatcher = 
					request.getRequestDispatcher(mav.getViewName());
			dispatcher.forward(request, response);
			return;
		} else {
			logger.info("DispatcherServlet에서 길을 잃었다네");
		}
	}	
}
