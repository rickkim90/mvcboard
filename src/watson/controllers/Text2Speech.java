package watson.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;

import article.controllers.AbstractController;
import article.controllers.ModelAndView;

public class Text2Speech extends AbstractController{

	@Override
	public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {
		TextToSpeech service = new TextToSpeech();
		service.setUsernameAndPassword("b0883724-4f87-456c-991b-c07bde6fc85b", "gPr35i2TgMpX");		
		ServiceCall<List<Voice>> call = service.getVoices();
		List<Voice> voices = call.execute();
		
		return new ModelAndView("/WEB-INF/views/text2speech/text2speech.jsp", "voices", voices);
	}

}
