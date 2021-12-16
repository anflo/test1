package cart;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TimeZone;

import item.Item;
import item.ItemDB;
import item.promotion.Promotion;

//used to calculate all potential promotion based on the inputted cart
public class CartPromotion {

	ArrayList<Promotion> promotionList;
	
	public CartPromotion() {
		super();
		promotionList = new ArrayList();
		initPromotionList();
	}
	
	//simulate the DB set of Promotion, should use DB calling by providing related Item.SUK to retrieve the related promotion set
	/*
	 * All promotions:
	 * A: 2A = 10% off, $10 saved
	 * B: 2A+3B = 15% off, $25.5 saved
	 * C: 3C = 5% off, $3 saved
	 * D: 5C = 7% off, $7 saved
	 * E: 7C = 10% off, $14 saved
	 * F: 3A+2B+C = 30% off, $69 saved
	 */
	private void initPromotionList() {
		TimeZone timeZone = TimeZone.getTimeZone("GMT");
		
		//promotion set A, 2A = 10% off
		Calendar promotionAStartDateTime = Calendar.getInstance(timeZone);
		promotionAStartDateTime.set(2021, 11, 20, 4, 0); //2021-dec-20 4:00am
		Calendar promotionAEndDateTime = Calendar.getInstance(timeZone);
		promotionAEndDateTime.set(2022, 0, 1, 4, 00); //2022-jan-1 4:00am
		
		List<ArrayList<Object>> promotionAItemCombination = new ArrayList<ArrayList<Object>>();
		ArrayList temp1AList = new ArrayList();
		temp1AList.add(ItemDB.ITEM_A);
		temp1AList.add(2); //2 items for A
		promotionAItemCombination.add(temp1AList);
		
		int promotionADiscountType = 0; //pect
		BigDecimal promotionADiscountPect = new BigDecimal("10"); //10% off
		Promotion promotionA = new Promotion(promotionAStartDateTime, promotionAEndDateTime, promotionAItemCombination, promotionADiscountType, promotionADiscountPect, null);
		promotionA.setName("A");
		promotionList.add(promotionA);
		//END: promotion set A
		
		
		//promotion set B: 2A+3B = 15% off
		Calendar promotionBStartDateTime = Calendar.getInstance(timeZone);
		promotionBStartDateTime.set(2021, 11, 20, 4, 0); //2021-dec-20 4:00am
		Calendar promotionBEndDateTime = Calendar.getInstance(timeZone);
		promotionBEndDateTime.set(2022, 0, 1, 4, 00); //2022-jan-1 4:00am
		
		List<ArrayList<Object>> promotionBItemCombination = new ArrayList<ArrayList<Object>>();
		ArrayList temp2AList = new ArrayList();
		temp2AList.add(ItemDB.ITEM_A);
		temp2AList.add(2); //2 items for A
		promotionBItemCombination.add(temp2AList);
		
		ArrayList temp2BList = new ArrayList();
		temp2BList.add(ItemDB.ITEM_B);
		temp2BList.add(3); //3 items for B
		promotionBItemCombination.add(temp2BList);
		
		int promotionBDiscountType = 0; //pect
		BigDecimal promotionBDiscountPect = new BigDecimal("15"); //15% off
		
		Promotion promotionB = new Promotion(promotionBStartDateTime, promotionBEndDateTime, promotionBItemCombination, promotionBDiscountType, promotionBDiscountPect, null);
		promotionB.setName("B");
		promotionList.add(promotionB);
		//END: promotion set B
		
		
		//promotion set C: 3C = 5% off
		Calendar promotionCStartDateTime = Calendar.getInstance(timeZone);
		promotionCStartDateTime.set(2021, 11, 20, 4, 0); //2021-dec-20 4:00am
		Calendar promotionCEndDateTime = Calendar.getInstance(timeZone);
		promotionCEndDateTime.set(2022, 0, 1, 4, 00); //2022-jan-1 4:00am
		
		List<ArrayList<Object>> promotionCItemCombination = new ArrayList<ArrayList<Object>>();
		ArrayList temp3AList = new ArrayList();
		temp3AList.add(ItemDB.ITEM_C);
		temp3AList.add(3); //3 items for C
		promotionCItemCombination.add(temp3AList);
		
		int promotionCDiscountType = 0; //pect
		BigDecimal promotionCDiscountPect = new BigDecimal("5"); //5% off
		
		Promotion promotionC = new Promotion(promotionCStartDateTime, promotionCEndDateTime, promotionCItemCombination, promotionCDiscountType, promotionCDiscountPect, null);
		promotionC.setName("C");
		promotionList.add(promotionC);
		//END: promotion set C
		
		
		//promotion set D: 5C = 7% off
		Calendar promotionDStartDateTime = Calendar.getInstance(timeZone);
		promotionDStartDateTime.set(2021, 11, 20, 4, 0); //2021-dec-20 4:00am
		Calendar promotionDEndDateTime = Calendar.getInstance(timeZone);
		promotionDEndDateTime.set(2022, 0, 1, 4, 00); //2022-jan-1 4:00am
		
		List<ArrayList<Object>> promotionDItemCombination = new ArrayList<ArrayList<Object>>();
		ArrayList temp4AList = new ArrayList();
		temp4AList.add(ItemDB.ITEM_C);
		temp4AList.add(5); //5 items for C
		promotionDItemCombination.add(temp4AList);
		
		int promotionDDiscountType = 0; //pect
		BigDecimal promotionDDiscountPect = new BigDecimal("7"); //7% off
		
		Promotion promotionD = new Promotion(promotionDStartDateTime, promotionDEndDateTime, promotionDItemCombination, promotionDDiscountType, promotionDDiscountPect, null);
		promotionD.setName("D");
		promotionList.add(promotionD);
		//END: promotion set D
		
		//promotion set E: 7C = 10% off
		Calendar promotionEStartDateTime = Calendar.getInstance(timeZone);
		promotionEStartDateTime.set(2021, 11, 20, 4, 0); //2021-dec-20 4:00am
		Calendar promotionEEndDateTime = Calendar.getInstance(timeZone);
		promotionEEndDateTime.set(2022, 0, 1, 4, 00); //2022-jan-1 4:00am
		
		List<ArrayList<Object>> promotionEItemCombination = new ArrayList<ArrayList<Object>>();
		ArrayList temp5AList = new ArrayList();
		temp5AList.add(ItemDB.ITEM_C);
		temp5AList.add(7); //7 items for C
		promotionEItemCombination.add(temp5AList);
		
		int promotionEDiscountType = 0; //pect
		BigDecimal promotionEDiscountPect = new BigDecimal("10"); //10% off
		
		Promotion promotionE = new Promotion(promotionEStartDateTime, promotionEEndDateTime, promotionEItemCombination, promotionEDiscountType, promotionEDiscountPect, null);
		promotionE.setName("E");
		promotionList.add(promotionE);
		//END: promotion set E
		
		
		//promotion set F: 3A+2B+C = 30% off
		Calendar promotionFStartDateTime = Calendar.getInstance(timeZone);
		promotionFStartDateTime.set(2021, 11, 20, 4, 0); //2021-dec-20 4:00am
		Calendar promotionFEndDateTime = Calendar.getInstance(timeZone);
		promotionFEndDateTime.set(2022, 0, 1, 4, 00); //2022-jan-1 4:00am
		
		List<ArrayList<Object>> promotionFItemCombination = new ArrayList<ArrayList<Object>>();
		ArrayList temp6AList = new ArrayList();
		temp6AList.add(ItemDB.ITEM_A);
		temp6AList.add(3); //3 items for A
		promotionFItemCombination.add(temp6AList);
		
		ArrayList temp6BList = new ArrayList();
		temp6BList.add(ItemDB.ITEM_B);
		temp6BList.add(2); //2 items for B
		promotionFItemCombination.add(temp6BList);
		
		ArrayList temp6CList = new ArrayList();
		temp6CList.add(ItemDB.ITEM_C);
		temp6CList.add(1); //1 item for C
		promotionFItemCombination.add(temp6CList);
		
		int promotionFDiscountType = 0; //pect
		BigDecimal promotionFDiscountPect = new BigDecimal("30"); //30% off
		
		Promotion promotionF = new Promotion(promotionFStartDateTime, promotionFEndDateTime, promotionFItemCombination, promotionFDiscountType, promotionFDiscountPect, null);
		promotionF.setName("F");
		promotionList.add(promotionF);
		//END: promotion set F
		
		
		
	}

	public Promotion getPromotionByName(String name) {
		for(Promotion p: promotionList) {
			if (p.getName().equals(name)) {
				return p;
			}
		}
		return null;
	}
	
	// Below process would be a lot faster by querying from DB using all items' SKU directly.
	// For e.g.: A one-to-many relationship of Promotion & PromotionItems structure. I can just
	// gather all items' SKU, query the PromotionItems.SKU (or PromotionItems.item_id.SKU). I'll
	// have a list of Promotion set immediately. The speed is fast by adding index on PromotionItems.SKU (or PromotionItems.item_id.SKU).
	// However, since there is DB and I assume there could be more then a thousand promotions and over a hundred items in the cart,
	// I made below algorithm to query the related promotion.
	// -----
	// - the cart input should be taken off all promotions already
	public ArrayList<Promotion> getRelatedPromotions(Cart cart){
		return getRelatedPromotions(cart, Calendar.getInstance()); //current timestamp with server timezone if no datetime is provided
	}
	public ArrayList<Promotion> getRelatedPromotions(Cart cart, Calendar processDateCalendar){
		ArrayList<Promotion> returnPromotionList = promotionList; //put all the promotion list in here, and then eliminate one-by-one //new ArrayList<Promotion>();
		
		int maxItemSetSizeOfRelatedPromotionCombination = -1;
		//For example, [3A = 10% off] contains 1 item, maxItemSetSizeOfRelatedPromotionCombination=1.
		//[3A+2B = 15% off] contains 2 items, maxItemSetSizeOfRelatedPromotionCombination=2
		//[3A+2B+1C = 20% off] contains 3 items, maxItemSetSizeOfRelatedPromotionCombination=3
		int promotionItemSetIndex = 0;
		do {
			ArrayList<Promotion> tempNewPromotionList = new ArrayList<Promotion>();
			for(Promotion p: returnPromotionList) {
				if (!p.isOnPromotion(processDateCalendar)) {
					continue;
				}
				List<ArrayList<Object>> pComb = p.getPromotionItemCombination();
				if (pComb.size()>maxItemSetSizeOfRelatedPromotionCombination) maxItemSetSizeOfRelatedPromotionCombination=pComb.size(); //should just useful in the first loop
				
				if (pComb.size()-1<promotionItemSetIndex) {
					tempNewPromotionList.add(p);
					continue;
				}
				
				ArrayList<Object> promotionItemNode = pComb.get(promotionItemSetIndex);
				Item pItem = (Item)promotionItemNode.get(Promotion.promotionItemNodeItemIndex);
				int pQty = (int)promotionItemNode.get(Promotion.promotionItemNodeQtyIndex);
				
				int qtyInCart = cart.getItemQuanitiy(pItem);
				if (qtyInCart>=pQty) {
					tempNewPromotionList.add(p);
				}
			}
			returnPromotionList = tempNewPromotionList;
			promotionItemSetIndex++;
		} while (promotionItemSetIndex<maxItemSetSizeOfRelatedPromotionCombination);
		
		returnPromotionList = prioritisedPromotion(returnPromotionList);
		return returnPromotionList;
	}
	
	//After calculate what promotion could be applied, it should prioritise which promotion should be applied first.
	// The rule should be either :
	// 	1: The one which is higher discount value goes first
	// 	2: The one which is higher discount rate goes first
	// For maximise benefit of customer, I would take first rule. However, below code provide rooms to switch to use rule 2.
	// PS: Although the question said the promotions are mutually exclusive, the same Scenario B & C shows there are could have multiple promotion applied at the same time.
	// Therefore, I would define the [mutually exclusive] means each unit of the item could be used to applied promotion once and once only. In fact, the same unit of an item is only countable for 1 promotion only.
	// E.g. 
	// - Promotion #1 is 2A = 10%
	// - Cart: 3A
	// - The promotion #1 could be only applied on the first and second A. The third is alone. 
	// - However, if the cart has 4A, the promotion could be used twice.
	private ArrayList<Promotion> prioritisedPromotion(ArrayList<Promotion> inputPromotionList){
		ArrayList<Promotion> returnPromotionList = inputPromotionList;
		Collections.sort(returnPromotionList, new SortPromotionByHighestDiscountValueComparator());
		return returnPromotionList;
	}
}


class SortPromotionByHighestDiscountValueComparator implements Comparator<Promotion> {
    @Override
    public int compare(Promotion p1, Promotion p2) {
//    	System.out.println("Total Price: p1: "+p1.getOriginalTotalPrice()+", p2: "+p2.getOriginalTotalPrice());
//    	System.out.println("Total Save: p1: "+p1.getTotalSaved()+", p2: "+p2.getTotalSaved());
//        return p1.getTotalSaved().compareTo(p2.getTotalSaved());
    	return p2.getTotalSaved().compareTo(p1.getTotalSaved());
    }
}
class SortPromotionByHighestDiscountPectComparator implements Comparator<Promotion> {
    @Override
    public int compare(Promotion p1, Promotion p2) {
        return p2.getDiscountPect().compareTo(p1.getDiscountPect());
    }
}