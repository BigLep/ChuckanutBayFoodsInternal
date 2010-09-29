package com.chuckanutbay.LotCodeManager.client;

import static com.chuckanutbay.LotCodeManager.client.LotCodeUtil.*;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

public class LotCodeManagerDialogBox extends DialogBox {
	HorizontalPanel buttonPanel = new HorizontalPanel(); 
	VerticalPanel dialogBoxContents = new VerticalPanel();
	
	LotCodeManagerDialogBox() {
		this.setGlassEnabled(true);
		this.setAnimationEnabled(true);
		this.center();
	}
	LotCodeManagerDialogBox(final LotCodeManagerPanel panel, String caption, Boolean isVisible, Boolean isPrintable) {
		this.setGlassEnabled(true);
		this.setAnimationEnabled(true);
			//Set up scrollPanel
			ScrollPanel scrollPanel = new ScrollPanel(panel.getPanel());
			scrollPanel.setSize("625px", "400px");
			scrollPanel.setAlwaysShowScrollBars(true);
			//Set up buttonPanel
				buttonPanel.setSpacing(5);
				//Set up printButton
				Button printButton = new Button();
				makeButtonWithIcon(printButton, icons.printIcon(), "Print");
				printButton.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
			            Window.print();

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
			buttonPanel.add(saveButton);
			if (isPrintable) {
				buttonPanel.add(printButton);
			}
			buttonPanel.add(cancelButton);
			//Set up dialogBoxContents
			dialogBoxContents.add(scrollPanel);
			dialogBoxContents.add(buttonPanel);
			dialogBoxContents.setCellHorizontalAlignment(buttonPanel, HasHorizontalAlignment.ALIGN_RIGHT);
		this.setWidget(dialogBoxContents);
		this.setText(caption);
		this.center();
		if (isVisible) {
			this.show();
		}
	}
}
