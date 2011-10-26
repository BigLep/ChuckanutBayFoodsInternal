package com.chuckanutbay.webapp.common.client;

import static com.google.common.collect.Maps.newLinkedHashMap;

import java.util.Map;

import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.ListBox;

public class CbListBox<T> extends ListBox {
	private Map<String, T> data;
	private FocusWidgetLinker linker; 
	
	public CbListBox() {
		super();
		data = newLinkedHashMap();
		linker = new FocusWidgetLinker(this);
	}
	
	public static <T> CbListBox<T> newCbListBox() {
		return new CbListBox<T>();
	}
	
	public static <T> CbListBox<T> newCbListBox(int width) {
		CbListBox<T> cbListBox = newCbListBox();
		cbListBox.setWidth(width);
		return cbListBox;
	}
	
	public static <T> CbListBox<T> newCbListBox(Map<String, T> data) {
		CbListBox<T> cbListBox = newCbListBox();
		cbListBox.data = data;
		cbListBox.updateListBoxItems();
		return cbListBox;
	}
	
	public static <T> CbListBox<T> newCbListBox(Map<String, T> data, FocusWidget nextWidget) {
		CbListBox<T> cbListBox = newCbListBox(data);
		cbListBox.linker.setNextWidget(nextWidget);
		return cbListBox;
	}
	
	public CbListBox<T> addData(String key, T value) {
		data.put(key, value);
		updateListBoxItems();
		return this;
	}

	public CbListBox<T> setData(Map<String, T> data) {
		this.data = data;
		updateListBoxItems();
		return this;
	}
	
	public CbListBox<T> addData(Map<String, T> data) {
		if (this.data != null) {
			this.data.putAll(data);
		} else {
			this.data = data;
		}
		updateListBoxItems();
		return this;
	}
	
	public T getSelected() {
		return data.get(this.getItemText(this.getSelectedIndex()));
	}
	
	private void updateListBoxItems() {
		this.clear();
		for (Map.Entry<String, T> entry : data.entrySet()) {
			this.addItem(entry.getKey());
		}
	}
	
	public CbListBox<T> setNextWidget(FocusWidget nextWidget) {
		linker.setNextWidget(nextWidget);
		return this;
	}	
	
	public CbListBox<T> setWidth(int width) {
		this.setWidth(width + "px");
		return this;
	}
	
	
	
	
}
