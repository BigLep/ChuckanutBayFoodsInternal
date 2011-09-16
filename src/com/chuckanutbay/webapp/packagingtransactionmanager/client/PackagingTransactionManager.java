package com.chuckanutbay.webapp.packagingtransactionmanager.client;

import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.H_ALIGN_CENTER;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.H_ALIGN_LEFT;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.V_ALIGN_BOTTOM;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.V_ALIGN_MIDDLE;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.V_ALIGN_TOP;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.newFocusPanel;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.newImage;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.newLabel;
import static com.chuckanutbay.webapp.common.client.IconUtil.LOGO;
import static com.chuckanutbay.webapp.common.client.ServiceUtils.createTrayLabelService;
import static com.chuckanutbay.webapp.packagingtransactionmanager.client.RpcHelper.createGetTrayLabelDtoCallback;
import static com.chuckanutbay.webapp.packagingtransactionmanager.client.RpcHelper.createPersistPackagingTransactionDtoCallback;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.chuckanutbay.webapp.common.client.BarcodeFormulation;
import com.chuckanutbay.webapp.common.client.CbFlexTable;
import com.chuckanutbay.webapp.common.client.CbHorizontalPanel;
import com.chuckanutbay.webapp.common.client.CbVerticalPanel;
import com.chuckanutbay.webapp.common.client.GwtWidgetHelper;
import com.chuckanutbay.webapp.common.client.ScanHandler;
import com.chuckanutbay.webapp.common.shared.OrderTrayLabelDto;
import com.chuckanutbay.webapp.common.shared.PackagingTransactionDto;
import com.chuckanutbay.webapp.common.shared.TrayLabelDto;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class PackagingTransactionManager implements EntryPoint, ScanHandler, PackagingTransactionServerCommunicator {
	
	private BarcodeFormulation barcodeFormulation = new BarcodeFormulation().addScanHandler(this);
	private static final int PAGE_WIDTH_PX = 1366;
	private static final int PAGE_HEIGHT_PX = 750;
	private Label utilityLabel = newLabel("Scan a Tray Label", "welcomeLabel");
	private CbFlexTable flexTable = new CbFlexTable();
	private Label barcodeLabel = newLabel("", "barcodeLabel");
	private Label qtyPackedLabel = newLabel(0);
	private TrayLabelDto trayLabel;
	
	
	@Override
	public void onModuleLoad() {
		initializeGui();
	}

	private void initializeGui() {//Working from the innermost widget to the RootPanel
		
		//Setup headerPanel
		CbHorizontalPanel headerPanel = new CbHorizontalPanel()
			.addWidget(newImage(LOGO, 400), H_ALIGN_LEFT)
			.addWidget(newLabel("Packaging Transaction Manager", "title"), H_ALIGN_CENTER, V_ALIGN_MIDDLE)
			.setWidth(PAGE_WIDTH_PX);
		
		//Setup mainPanel
		CbVerticalPanel mainPanel = new CbVerticalPanel()
			.addWidget(headerPanel)
			.addWidget(utilityLabel, H_ALIGN_CENTER)
			.addWidget(flexTable, H_ALIGN_CENTER)
			.addWidget(newLabel("Last Barcode Scanned", "barcodeLabelBold"), H_ALIGN_LEFT, V_ALIGN_BOTTOM)
			.addWidget(barcodeLabel, H_ALIGN_LEFT, V_ALIGN_TOP)
			.setWidth(PAGE_WIDTH_PX)
			.setHeight(PAGE_HEIGHT_PX)
			.setCellSpacing(10);
		
		//Setup focusPanel
		FocusPanel focusPanel = newFocusPanel(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				GWT.log("Key entered: " + event.getNativeKeyCode());
				barcodeFormulation.addCharacter(event.getNativeKeyCode());
			}
		}, mainPanel);
		
		//Setup simplePanel
		SimplePanel simplePanel = GwtWidgetHelper.newSimplePanel(focusPanel, "backgroundPanel");
		
		//Setup rootPanel
		RootPanel.get("PackagingTransactionManager").add(simplePanel);
		
		focusPanel.setFocus(true);
	}
	
	private void updateMainPanel() {
		/* Test Data
		SalesOrderDto salesOrderDto = new SalesOrderDto();
		salesOrderDto.setCustomerName("TestName");
		QuickbooksItemDto qbItem = new QuickbooksItemDto();
		qbItem.setShortName("TEST 3''");
		SalesOrderLineItemDto lineItemDto = new SalesOrderLineItemDto();
		lineItemDto.setSalesOrderDto(salesOrderDto);
		lineItemDto.setQuickbooksItemDto(qbItem);
		OrderTrayLabelDto trayLabelDto = new OrderTrayLabelDto();
		trayLabelDto.setId(123);
		trayLabelDto.setLotCode("1A123A1");
		trayLabelDto.setSalesOrderLineItemDto(lineItemDto);
		
		this.trayLabel = trayLabelDto;
		*/
		if (trayLabel == null) {
			utilityLabel.setText("You scanned an invalid Tray Label");
		} else {
			qtyPackedLabel.setText("" + trayLabel.getPackagingTransactions().size());
			Map<String, Widget> tableData = new LinkedHashMap<String, Widget>();
			tableData.put("Tray Label:", newLabel(trayLabel.getId()));
			
			String customer;
			if (trayLabel instanceof OrderTrayLabelDto) {
				customer = ((OrderTrayLabelDto) trayLabel).getSalesOrderLineItemDto().getSalesOrderDto().getCustomerName();
			} else {
				customer = "INVENTORY";
			}
			tableData.put("Customer:", newLabel(customer));
			tableData.put("Lot Code:", newLabel(trayLabel.getLotCode()));
			tableData.put("Item:", newLabel(trayLabel.getQbItem().getShortName()));
			tableData.put("Qty Packed:", qtyPackedLabel);
			
			int row = 0; //row	
			int column = 0; //column
			for (Iterator<String> it = tableData.keySet().iterator(); it.hasNext(); ) {
			    String key = it.next();
			    Widget value = tableData.get(key);
			    column = 0;
			    row++;
			    flexTable.setText(key, row, column).setCellStyle("headerColumn", row, column);
			    column++;
			    flexTable.setWidget(value, row, column).setCellStyle("dataColumn", row, column);
			}
			utilityLabel.setText("");
		}
	}

	@Override
	public void onScan(String barcode) {
		barcodeLabel.setText(barcode);
		try {//See if it has just numbers
			Integer trayLabelId = Integer.decode(barcode);
			getTrayLabelDtoFromServer(trayLabelId);
		} catch (NumberFormatException e) {//There is a letter in the code so it is a sierlized label barcode
			if (trayLabel == null) {
				utilityLabel.setText("Please scan a valid Tray Label first!");
			} else {
				String qbItemId = trayLabel.getQbItem().getId();
				int indexOfDash = qbItemId.indexOf("-");
				String qbItemBase = qbItemId.substring(0, indexOfDash);
				if (barcode.indexOf(qbItemBase) == -1) {//The qbItemId and the barcode don't match
					Window.alert("This is not the correct label for this product! If you think these are the correct labels tell Dave!");
				} else {//It is the correct label
					PackagingTransactionDto ptDto = new PackagingTransactionDto();
					ptDto.setBarcode(barcode);
					ptDto.setTrayLabelDto(trayLabel);
					persistPackagingTransactionDtoToServer(ptDto);
				}
			}
		}
	}

	@Override
	public void getTrayLabelDtoFromServer(Integer id) {
		createTrayLabelService().getTrayLabelDto(id, createGetTrayLabelDtoCallback(this));
	}

	@Override
	public void persistPackagingTransactionDtoToServer(
			PackagingTransactionDto ptDto) {
		createTrayLabelService().persistPackagingTransaction(ptDto, createPersistPackagingTransactionDtoCallback(this));
	}

	@Override
	public void onSuccessfulGetTrayLabelDto(TrayLabelDto trayLabelDto) {
		trayLabel = trayLabelDto;
		updateMainPanel();
		
	}

	@Override
	public void onSuccessfulPersistPackaginTransactionDto() {
		qtyPackedLabel.setText("" + (Integer.decode(qtyPackedLabel.getText()) + 1));
	}


}
