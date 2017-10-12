package article.controllers;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
	private String viewName;
	private Map<String, Object> model = new HashMap<String, Object>();

	public ModelAndView() {}

	public ModelAndView(String viewName) {
		this.viewName = viewName;
	}

	public ModelAndView(String viewName, String key, Object obj) {
		this.viewName = viewName;
		model.put(key, obj);
	}

	public Map<String, Object> getModel() {
		return model;
	}

	public void addObject(String key, Object obj) {
		model.put(key, obj);
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	@Override
	public String toString() {
		return "ModelAndView [model=" + model + ", viewName=" + viewName + "]";
	}

}
