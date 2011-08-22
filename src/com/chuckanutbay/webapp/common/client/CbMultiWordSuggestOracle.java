package com.chuckanutbay.webapp.common.client;

import java.util.Collection;

import com.google.gwt.user.client.ui.MultiWordSuggestOracle;

public class CbMultiWordSuggestOracle extends MultiWordSuggestOracle {
	
	public CbMultiWordSuggestOracle(Collection<String> suggestions) {
		super();
		super.addAll(suggestions);
	}
	
}
