package com.chuckanutbay.webapp.packagingtransactionmanager.client;

import static com.chuckanutbay.webapp.common.client.CbListBox.newCbListBox;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.H_ALIGN_CENTER;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.H_ALIGN_LEFT;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.V_ALIGN_MIDDLE;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.largeButtonStyles;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.newImage;
import static com.chuckanutbay.webapp.common.client.GwtWidgetHelper.newLabel;
import static com.chuckanutbay.webapp.common.client.IconUtil.ADD_LARGE;
import static com.chuckanutbay.webapp.common.client.IconUtil.LOGO;
import static com.chuckanutbay.webapp.common.client.ServiceUtils.createPackagingTransactionService;
import static com.chuckanutbay.webapp.packagingtransactionmanager.client.RpcHelper.createGetDamageCodeDtosCallback;
import static com.chuckanutbay.webapp.packagingtransactionmanager.client.RpcHelper.createGetEmployeeDtosCallback;
import static com.chuckanutbay.webapp.packagingtransactionmanager.client.RpcHelper.createPersistPackagingTransactionDtoCallback;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newLinkedHashMap;
import static java.util.Collections.sort;

import java.util.List;
import java.util.Map;

import com.chuckanutbay.webapp.common.client.CbDateBox;
import com.chuckanutbay.webapp.common.client.CbFlexTable;
import com.chuckanutbay.webapp.common.client.CbHorizontalPanel;
import com.chuckanutbay.webapp.common.client.CbIconButton;
import com.chuckanutbay.webapp.common.client.CbListBox;
import com.chuckanutbay.webapp.common.client.CbTextBox;
import com.chuckanutbay.webapp.common.client.CbVerticalPanel;
import com.chuckanutbay.webapp.common.client.GwtWidgetHelper;
import com.chuckanutbay.webapp.common.shared.DamageCodeDto;
import com.chuckanutbay.webapp.common.shared.EmployeeDto;
import com.chuckanutbay.webapp.common.shared.PackagingTransactionDto;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class PackagingTransactionManager implements EntryPoint, PackagingTransactionServerCommunicator {
	private static final int PAGE_WIDTH_PX = Window.getClientWidth();
	private static final int PAGE_HEIGHT_PX = Window.getClientHeight();
	private CbFlexTable flexTable = new CbFlexTable();
	
	//Fields
	private CbListBox<EmployeeDto> packedByLb = newCbListBox();
	private CbDateBox dateBox = new CbDateBox(60);
	private CbTextBox trayLabelTb = new CbTextBox(100);
	private CbTextBox startLabelTb = new CbTextBox(100);
	private CbTextBox endLabelTb = new CbTextBox(100);
	private CbTextBox startLabel2Tb = new CbTextBox(100);
	private CbTextBox endLabel2Tb = new CbTextBox(100);
	private CbTextBox testWeightTb = new CbTextBox(100);
	private CbTextBox damagedQtyTb = new CbTextBox(100);
	private CbListBox<DamageCodeDto> damageCodeLb = newCbListBox();
	private CbIconButton submitButton = new CbIconButton(ADD_LARGE, "Submit", largeButtonStyles);
	
	
	@Override
	public void onModuleLoad() {
		initializeGui();
		getDamageCodeDtosFromDatabase();
		getEmployeeDtosFromDatabase();
	}

	private void initializeGui() {//Working from the innermost widget to the RootPanel
		
		//Setup flexTable
		flexTable.setStyleName("flexTable");
		flexTable.setWidth(PAGE_WIDTH_PX + "px");
		
		Map<String, Widget> labelWidgetPairsRow1 = newLinkedHashMap();
		labelWidgetPairsRow1.put("Packed By", packedByLb);
		labelWidgetPairsRow1.put("Date", dateBox);
		labelWidgetPairsRow1.put("Tray Label", trayLabelTb);
		labelWidgetPairsRow1.put("Start Label", startLabelTb);
		labelWidgetPairsRow1.put("End Label", endLabelTb);
		labelWidgetPairsRow1.put("Test Weight", testWeightTb);
		labelWidgetPairsRow1.put("Qty Damaged", damagedQtyTb);
		labelWidgetPairsRow1.put("Damage Code", damageCodeLb);
		addLabelWidgetPairs(labelWidgetPairsRow1, 0, 0);
		
		Map<String, Widget> labelWidgetPairsRow2 = newLinkedHashMap();
		labelWidgetPairsRow2.put("Start Label 2", startLabel2Tb);
		labelWidgetPairsRow2.put("End Label 2", endLabel2Tb);
		addLabelWidgetPairs(labelWidgetPairsRow2, 2, 3);
		
		int buttonRow = 4; int buttonColumn = 0;
		flexTable.getFlexCellFormatter().setColSpan(buttonRow, buttonColumn, 8);
		flexTable.setWidget(submitButton, buttonRow, buttonColumn).setHorizontalAlignement(buttonRow, buttonColumn, H_ALIGN_CENTER);
		
		//Setup Tab Links
		packedByLb.setNextWidget(
		dateBox.setNextWidget(
		trayLabelTb.setNextWidget(
		startLabelTb.setNextWidget(
		endLabelTb.setNextWidget(
		testWeightTb.setNextWidget(
		damagedQtyTb.setNextWidget(
		damageCodeLb.setNextWidget(
		submitButton.setNextWidget(
		trayLabelTb
		)))))))));
		
		startLabel2Tb.setNextWidget(
		endLabel2Tb.setNextWidget(
		damagedQtyTb
		));
		
		//Set TextBox values
		damagedQtyTb.setText("0");
		
		//Setup submitButton
		submitButton.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				submitButton.click();
			}
		}); 
		submitButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				PackagingTransactionDto ptDto = new PackagingTransactionDto();
				
				makeBorderRed(); //This is to green when a good return comes
				
				//setEmployee
				if (packedByLb.getSelected() == null) {
					Window.alert("Please select an employee");
					packedByLb.setFocus(true);
					return;
				} else {
					ptDto.setEmployee(packedByLb.getSelected());
				}
				
				//setDate
				if (dateBox.getValue() == null) {
					Window.alert("Please select a date");
					dateBox.setFocus(true);
					return;
				} else {
					ptDto.setDate(dateBox.getValue());
				}
				
				//setTrayLabelId
				try {
					ptDto.setTrayLabelId(Integer.valueOf(trayLabelTb.getText()));
				} catch (NumberFormatException e) {
					Window.alert("Please enter a proper Tray Label");
					trayLabelTb.setFocus(true);
					return;
				}
				
				//setLabels
				List<CbTextBox> textBoxes = newArrayList(startLabelTb, endLabelTb, startLabel2Tb, endLabel2Tb);
				List<String> labels = newArrayList();
				for (CbTextBox tb : textBoxes) {
					try {
						Integer.valueOf(tb.getText());
						Window.alert("Please enter a proper Label Code");
						tb.setFocus(true);
						return;
					} catch (NumberFormatException e) {//Labels should have a letter in them
						if (tb.getText().length() >= 10 && tb.getText().length() <= 15) {
							labels.add(tb.getText());
						}
					}
				}
				sort(labels, String.CASE_INSENSITIVE_ORDER);
				if (labels.size() == 0) {//It is probably a case where the new labels aren't being used
					
				} else if (labels.size() % 2 == 1) {//An odd number of labels were scanned. Probably a mistake
					if (Window.confirm("You have scanned an odd number of label barcodes!")) {//It doesn't matter, move on!
						ptDto.setStartLabel1(labels.get(0));
						if (labels.size() == 3) {
							ptDto.setEndLabel1(labels.get(1));
							ptDto.setStartLabel2(labels.get(2));
							
						}
					} else {//The user wants to fix the mistake
						startLabelTb.setFocus(true);
						return;
					}
				} else {//They scanned a pair or two of label barcodes. Yay!
					ptDto.setStartLabel1(labels.get(0));
					ptDto.setEndLabel1(labels.get(1));
					if (labels.size() == 4) {//There are 2 pairs
						ptDto.setStartLabel2(labels.get(2));
						ptDto.setEndLabel2(labels.get(3));
					}
				}
				
				//setTestWeight
				try {
					ptDto.setTestWeight(Double.valueOf(testWeightTb.getText()));
				} catch (NumberFormatException e) {
					Window.alert("Please enter a proper Weight");
					testWeightTb.setFocus(true);
					return;
				}
				
				//setDamagedQty
				try {
					ptDto.setDamagedQty(Integer.valueOf(damagedQtyTb.getText()));
				} catch (NumberFormatException e) {
					Window.alert("Please enter a proper Damaged Qty");
					damagedQtyTb.setFocus(true);
					return;
				}
				
				//setDamageCode
				if (ptDto.getDamagedQty() != 0 && damageCodeLb.getSelected() != null) {
					ptDto.setDamageCode(damageCodeLb.getSelected());
				}
				
				persistPackagingTransactionDtoToDatabase(ptDto);
				
			}
			
		});
		
		//Setup headerPanel
				CbHorizontalPanel headerPanel = new CbHorizontalPanel()
					.addWidget(newImage(LOGO, 400), H_ALIGN_LEFT)
					.addWidget(newLabel("Packaging Transaction Manager", "title"), H_ALIGN_CENTER, V_ALIGN_MIDDLE)
					.setWidth(PAGE_WIDTH_PX);
				
				//Setup mainPanel
				CbVerticalPanel mainPanel = new CbVerticalPanel()
					.addWidget(headerPanel)
					.addWidget(flexTable, H_ALIGN_CENTER)
					.setWidth(PAGE_WIDTH_PX)
					.setHeight(PAGE_HEIGHT_PX)
					.setCellSpacing(10);
		
		//Setup simplePanel
		SimplePanel simplePanel = GwtWidgetHelper.newSimplePanel(mainPanel, "backgroundPanel");
		
		//Setup rootPanel
		RootPanel.get("PackagingTransactionManager").add(simplePanel);
	}	
	
	private void addLabelWidgetPairs(Map<String, Widget> pairs, int row, int column) {
		for (Map.Entry<String, Widget> pair : pairs.entrySet()) {
			flexTable.setText(pair.getKey(), row, column).setCellStyle("flexTableLabel", row, column);
			flexTable.setWidget(pair.getValue(), row + 1, column);
			column++;
		}
	}

	@Override
	public void getDamageCodeDtosFromDatabase() {
		createPackagingTransactionService().getDamageCodeDtos(createGetDamageCodeDtosCallback(this));
	}

	@Override
	public void getEmployeeDtosFromDatabase() {
		createPackagingTransactionService().getEmployeeDtos(createGetEmployeeDtosCallback(this));
	}

	@Override
	public void persistPackagingTransactionDtoToDatabase(
			PackagingTransactionDto packagingTransaction) {
		createPackagingTransactionService().persistPackagingTransactionDto(packagingTransaction, createPersistPackagingTransactionDtoCallback(this));
	}

	@Override
	public void onSuccessfulGetDamageCodeDtos(List<DamageCodeDto> damageCodes) {
		Map<String, DamageCodeDto> map = newLinkedHashMap();
		for (DamageCodeDto damageCode : damageCodes) {
			map.put(damageCode.getId() + " - " + damageCode.getCode(), damageCode);
		}
		damageCodeLb.setData(map);
	}

	@Override
	public void onSuccessfulGetEmployeeDtos(List<EmployeeDto> employees) {
		Map<String, EmployeeDto> map = newLinkedHashMap();
		for (EmployeeDto employee : employees) {
			map.put(employee.getFirstName() + " " + employee.getLastName(), employee);
		}
		packedByLb.setData(map);
	}

	@Override
	public void onSuccessfulPersistPackagingTransactionDto() {
		makeBorderGreen();
		trayLabelTb.setText("");
		startLabelTb.setText("");
		endLabelTb.setText("");
		startLabel2Tb.setText("");
		endLabel2Tb.setText("");
		testWeightTb.setText("");
		damagedQtyTb.setText("0");
		
		trayLabelTb.setFocus(true);
	}

	private void makeBorderGreen() {
		flexTable.setStyleName("greenBorder");
		Timer timer = new Timer() {

			@Override
			public void run() {
				flexTable.setStyleName("flexTable");
			}
			
		};
		timer.schedule(5000);
	}
	
	private void makeBorderRed() {
		flexTable.setStyleName("redBorder");
	}
	

}
