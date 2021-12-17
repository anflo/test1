package item.promotion;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import item.Item;

public class Promotion {

	private String name; //assume this name is only useful for unit test (to identity)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	private Calendar promotionStartDateTime;
	private Calendar promotionEndDateTime;
	private List<ArrayList<Object>> promotionItemCombination; //for each node, 0=Item, 1=quantity required
	public static final int promotionItemNodeItemIndex = 0;
	public static final int promotionItemNodeQtyIndex = 1;
	private int discountMode; //0= percentage, 1= direct discount price
	private BigDecimal discountPect; //how many % off from original price, should be between 1-100 only
	private BigDecimal discountedPrice;
	
	private boolean isOriginalTotalPriceCached = false; //cache promotion calculation if calculated already
	private BigDecimal originalTotalPrice;
//	private BigDecimal calculatedDiscountPrice;
//	private BigDecimal calculatedTotalSaved; //wont do this since the calculation is simple already.
	
 	public Promotion(Calendar promotionStartDateTime, Calendar promotionEndDateTime, List<ArrayList<Object>> promotionItemCombination, int discountMode, BigDecimal discountPect, BigDecimal discountedPrice) {
		super();
		this.name = "";
		this.promotionStartDateTime = promotionStartDateTime;
		this.promotionEndDateTime = promotionEndDateTime;
		this.promotionItemCombination = promotionItemCombination;
		
		this.discountMode = discountMode;
		switch (this.discountMode) {
			case 0:
				this.discountPect = discountPect.compareTo(new BigDecimal("100"))>0? new BigDecimal("100"):
					discountPect.compareTo(new BigDecimal("1"))<0? new BigDecimal("1"):
						discountPect;
				break;
			case 1:
				this.discountedPrice = discountedPrice.compareTo(new BigDecimal("0"))<0? new BigDecimal("0"): discountedPrice; //it could be higher then original price
				break;
				
			default:
				discountMode = -1;
				break;
		}
	}
	
	public int getDiscountMode() {
		return discountMode;
	}
	public void setDiscountMode(int discountMode) {
		this.discountMode = discountMode;
	}

	public BigDecimal getDiscountPect() {
		return discountPect;
	}
	public void setDiscountPect(BigDecimal discountPect) {
		this.discountPect = discountPect;
	}

	public BigDecimal getDiscountedPrice() {
		return discountedPrice;
	}
	public void setDiscountedPrice(BigDecimal discountedPrice) {
		this.discountedPrice = discountedPrice;
	}


	public List<ArrayList<Object>> getPromotionItemCombination() {
		return promotionItemCombination;
	}
	/* 
	 * Better not to be altered in any place except DB query
	public void setPromotionItemCombination(List<ArrayList<Object>> promotionItemCombination) {
		this.promotionItemCombination = promotionItemCombination;
	}
	*/



	public boolean isOnPromotion() {
		Calendar today = Calendar.getInstance();
		return isOnPromotion(today);
	}
	public boolean isOnPromotion(Calendar inputDateTime) {
		if (promotionStartDateTime.compareTo(inputDateTime)<=0 && promotionEndDateTime.compareTo(inputDateTime)>=0) {
			return true;
		}
		return false;
	}

	public BigDecimal getDiscountPectForPriceCalculation() {
		if(this.getDiscountMode()==0 && this.getDiscountPect()!=null) {
			return new BigDecimal("100").subtract(this.getDiscountPect()).divide(new BigDecimal("100"));
		}
		return null;
	}
	
	public BigDecimal getOriginalTotalPrice() {
		if(isOriginalTotalPriceCached) {
			return this.originalTotalPrice;
		}
		
		if(promotionItemCombination!=null) {
			BigDecimal totalPrice = new BigDecimal("0");
			for(ArrayList promotionItemNode: promotionItemCombination) {
				Item tempItem = (Item)promotionItemNode.get(promotionItemNodeItemIndex);
				int tempQty = (int)promotionItemNode.get(promotionItemNodeQtyIndex);
				BigDecimal tempTotalPrice = tempItem.getUnitPrice().multiply(new BigDecimal(tempQty));
				totalPrice = totalPrice.add(tempTotalPrice);
			}
			this.originalTotalPrice = totalPrice;
			this.isOriginalTotalPriceCached = true;
			return totalPrice;
		}
		return null;
	}
	public BigDecimal getPromotionPrice() {
		switch (this.discountMode) {
		case 0:
			BigDecimal totalPrice = this.getOriginalTotalPrice();
			if(totalPrice!=null) {
				return totalPrice.multiply(this.getDiscountPectForPriceCalculation());
			}
			break;
		case 1:
			return this.getDiscountedPrice();
			
		default:
			return null;
		}
		
		return null;
	}
	public BigDecimal getTotalSaved() {
		return this.getOriginalTotalPrice().subtract(this.getPromotionPrice());
	}

	
}
