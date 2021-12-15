package item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Item {

	//------ SKU ----------//
	private String sku;
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}

	//------ Price ----------//
	private BigDecimal unitPrice;
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	@Override
	public String toString() {
		return "Item [sku=" + sku + ", unitPrice=" + unitPrice + "]";
	}
	
	//------ Constructor ----------//
	public Item(String inputSKU, BigDecimal initUnitPrice) {
		super();
		this.sku = inputSKU;
		this.unitPrice = initUnitPrice;
	}

	
	
}
