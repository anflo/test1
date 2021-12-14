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
	
	//------ new price period ----------//
	//0=promotion start date time, 1=promotion end date time, 2=discount price in BigDecimal
	private List<ArrayList<Object>> promotionInfoList = new ArrayList<ArrayList<Object>>();
	private static int promoteInfoStartDateIndex = 0;
	private static int promoteInfoEndDateIndex = 1;
	private static int promoteInfoPriceIndex = 2;
	public List<ArrayList<Object>> getPromotionInfoList() {
		return promotionInfoList;
	}
	public void setPromotionInfoList(List<ArrayList<Object>> promotionInfoList) {
		this.promotionInfoList = promotionInfoList;
		
		Comparator<ArrayList<Object>> comp = (ArrayList<Object> a, ArrayList<Object> b) -> {
			Calendar aPromotionStartDateTime = (Calendar)a.get(promoteInfoStartDateIndex);
			Calendar bPromotionStartDateTime = (Calendar)b.get(promoteInfoStartDateIndex);
			
			//https://docs.oracle.com/javase/7/docs/api/java/util/Calendar.html#compareTo(java.util.Calendar)
		    return aPromotionStartDateTime.compareTo(bPromotionStartDateTime);
		};

		Collections.sort(this.promotionInfoList, comp);
	}

	public boolean isOnPromotion() {
		Calendar today = Calendar.getInstance();
		return isOnPromotion(today);
	}
	public boolean isOnPromotion(Calendar inputDateTime) {
		if (this.promotionInfoList!=null) {
			for(ArrayList promotionList: this.promotionInfoList) {
				Calendar startPeriod = (Calendar)promotionList.get(promoteInfoStartDateIndex);
				Calendar endPeriod = (Calendar)promotionList.get(promoteInfoEndDateIndex);
				
				if (startPeriod.compareTo(inputDateTime)>=0 && endPeriod.compareTo(inputDateTime)>=0) {
					return true;
				}
				
				//if the sorting is confirmed as good, return false directly when the period is not yet reach
				if (startPeriod.compareTo(inputDateTime)<0) {
					break;
				}
			}
		}
		return false;
	}

	
	
	@Override
	public String toString() {
		return "Item [sku=" + sku + ", unitPrice=" + unitPrice + ", promotionInfoList=" + promotionInfoList + "]";
	}
	
	//------ Constructor ----------//
	public Item(String inputSKU, BigDecimal initUnitPrice) {
		this.sku = inputSKU;
		this.unitPrice = initUnitPrice;
	}

	
	
}
