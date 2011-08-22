package com.chuckanutbay.webapp.common.client;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;

/**
 * 
 * Class copied from http://blog.js-development.com/2010/03/gwt-button-with-image-and-text.html
 *
 */
public class CbIconButton extends Button {
	private String text;
	private String styleRules = "padding-left:3px; vertical-align:middle;";
	
	public CbIconButton(String imageResource, String text, ClickHandler handler, String...styleRules){
		super();
		setResource(imageResource);
		setText(text);
		this.addClickHandler(handler);
		setTextStyleRule(styleRules);
	}
	
	public CbIconButton(String imageResource, String text){
		super();
		setResource(imageResource);
		setText(text);
	}
 
	public CbIconButton(){
	 	super();
	 }

	public void setResource(String imageResource){
		Image img = new Image(imageResource);
		String definedStyles = img.getElement().getAttribute("style");
		img.getElement().setAttribute("style", definedStyles + "; vertical-align:middle; padding: 4px;");
		DOM.insertBefore(getElement(), img.getElement(), DOM.getFirstChild(getElement()));
	}

	@Override
	public void setText(String text) {
		this.text = text;
		Element span = DOM.createElement("span");
		span.setInnerText(text);
		span.setAttribute("style", styleRules);
		
		if (DOM.getChildCount(getElement()) > 1) {
			DOM.removeChild(getElement(), DOM.getChild(getElement(), 1));
		}
		DOM.insertChild(getElement(), span, 1);
	}

	@Override
	public String getText() {
		return this.text;
	}

	public void setTextStyleRule(String...styleRules) {
		for (String styleRule : styleRules) {
			this.styleRules = this.styleRules.concat(" " + styleRule + ";");
		}
		setText(text);
	}
}