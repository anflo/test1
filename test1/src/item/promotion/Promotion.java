package item.promotion;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cart.Cart;
import item.Item;

public class Promotion {

	private Calendar promotionStartDateTime;
	private Calendar promotionEndDateTime;
	private List<ArrayList<Object>> promotionItemCombination; //for each node, 0=Item, 1=quantity required
	private int discountMode; //0= percentage, 1= direct discount price
	private BigDecimal discountPect; //how many % off from original price, should be between 1-100 only
	private BigDecimal discountedPrice;
	
	public Promotion(Calendar promotionStartDateTime, Calendar promotionEndDateTime, List<ArrayList<Object>> promotionItemCombination, int discountMode, BigDecimal discountPect, BigDecimal discountedPrice) {
		super();
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

	public BigDecimal getPromotionPrice() {
		switch (this.discountMode) {
		case 0:
			BigDecimal totalPrice = new BigDecimal("0");
			if(promotionItemCombination!=null) {
				for(ArrayList promotionItemNode: promotionItemCombination) {
					Item tempItem = (Item)promotionItemNode.get(0);
					int tempQty = (int)promotionItemNode.get(1);
					BigDecimal tempTotalPrice = tempItem.getUnitPrice().multiply(new BigDecimal(tempQty));
					totalPrice = totalPrice.add(tempTotalPrice);
				}
				return totalPrice.multiply(this.getDiscountPect().divide(new BigDecimal("100")));
			}
			break;
		case 1:
			return this.getDiscountedPrice();
			
		default:
			return null;
		}
		
		return null;
	}
	
	
 	public boolean promoteValidOnThisCart(Cart cart) {
		
		return false;
	}
	
	public BigDecimal discountedPrice(){
		
		return null;
	}
	
}
