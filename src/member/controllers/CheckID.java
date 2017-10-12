package member.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.controllers.AbstractController;
import article.controllers.ModelAndView;

public class CheckID extends AbstractController{

	@Override
	public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {
		String user_id = request.getParameter("user_id");
		//DAO
		//select count(*) AS cnt from member
		//where user_id= ?
		ModelAndView mav = new ModelAndView("/WEB-INF/views/member/checkID.jsp");
		mav.addObject("id", user_id);
		//mav.addObject("cnt", cnt);
		//mav.addObject("msg", "aaa는 이미 사용중인 아이디 입니다.");
		return null;
	}

}
