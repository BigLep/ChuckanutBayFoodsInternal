package com.chuckanutbay.LotCodeManager.client;

import static com.chuckanutbay.LotCodeManager.client.LotCodeUtil.*;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

public class LotCodeManagerDialogBox extends DialogBox {
	ScrollPanel scrollPanel;
	LotCodeManagerPanel panel;
	String title;
	HorizontalPanel buttonPanel = new HorizontalPanel(); 
	VerticalPanel dialogBoxContents = new VerticalPanel();
	DialogBox printBox = null;
	
	LotCodeManagerDialogBox() {
		//Default LotCodeManagerDialogBox
		this.setGlassEnabled(true);
		this.setAnimationEnabled(true);
		this.center();
	}
	LotCodeManagerDialogBox(final LotCodeManagerPanel argPanel, final String argTitle, Boolean isSaveable, Boolean isPrintable) {
		//Set panel and title
		setPanel(argPanel);
		setTitle(argTitle);
		//Set Up DialogBox
		this.setGlassEnabled(true);
		this.setAnimationEnabled(true);
			//Set up scrollPanel
			scrollPanel = new ScrollPanel(panel.getPanel());
			scrollPanel.setSize("625px", "400px");
			scrollPanel.setAlwaysShowScrollBars(true);
			//Set up buttonPanel
				buttonPanel.setSpacing(5);
				//Set up printButton
				Button printButton = new Button();
				makeButtonWithIcon(printButton, icons.printIcon(), "Print");
				printButton.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						printDialogBox();
					}
				});
				//Set up saveButton
				Button saveButton = new Button();
				makeButtonWithIcon(saveButton, icons.saveIcon(), "Save");
				saveButton.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						panel.updateDB();
						LotCodeManagerDialogBox.this.clear();
						LotCodeManagerDialogBox.this.hide();
					}
				});
				//Set up cancelButton
				Button cancelButton = new Button();
				makeButtonWithIcon(cancelButton, icons.cancelIcon(), "Cancel");
				cancelButton.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						LotCodeManagerDialogBox.this.clear();
						LotCodeManagerDialogBox.this.hide();
					}
				});
			//Add Buttons to buttonPanel
			if (isSaveable) {
				buttonPanel.add(saveButton);
			}
			if (isPrintable) {
				buttonPanel.add(printButton);
			}
			buttonPanel.add(cancelButton);
			//Set up dialogBoxContents
			dialogBoxContents.add(scrollPanel);
			dialogBoxContents.add(buttonPanel);
			dialogBoxContents.setCellHorizontalAlignment(buttonPanel, HasHorizontalAlignment.ALIGN_RIGHT);
		this.setWidget(dialogBoxContents);
		this.setText(title);
		this.center();
		this.show();
	}
	
	private void printDialogBox() {
		this.clear();
		this.setWidget(panel.getPanel());
		Window.print();
		this.clear();
			//Reassemble dialogBoxContents
			scrollPanel = new ScrollPanel(panel.getPanel());
			scrollPanel.setSize("625px", "400px");
			scrollPanel.setAlwaysShowScrollBars(true);
			dialogBoxContents.clear();
			dialogBoxContents.add(scrollPanel);
			dialogBoxContents.add(buttonPanel);
			dialogBoxContents.setCellHorizontalAlignment(buttonPanel, HasHorizontalAlignment.ALIGN_RIGHT);
		this.setWidget(dialogBoxContents);
	}
	
	public void setPanel(LotCodeManagerPanel argPanel) {
		panel = argPanel;
	}
	
	public LotCodeManagerPanel getPanel() {
		return panel;
	}
	
	public void setTitle(String argTitle) {
		title = argTitle;
	}
	
	public String getTitle() {
		return title;
	}
}
