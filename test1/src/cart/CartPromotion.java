package cart;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TimeZone;

import item.Item;
import item.ItemDB;
import item.promotion.DiscountMode;
import item.promotion.Promotion;
import item.promotion.PromotionItem;

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
		ZoneId zoneId = ZoneId.of("UTC+0");;
		
		//promotion set A, 2A = 10% off
		ZonedDateTime promotionAStartDateTime = ZonedDateTime.of(2021, 12, 20, 4, 0, 0, 0, zoneId); //2021-dec-20 4:00am
		ZonedDateTime promotionAEndDateTime = ZonedDateTime.of(2022, 1, 1, 4, 0, 0, 0, zoneId); //2022-jan-1 4:00am
		 
		
		List<PromotionItem> promotionAItemCombination = new ArrayList<PromotionItem>();
		PromotionItem p1A = new PromotionItem(ItemDB.ITEM_A, 2); //2 items for A
		promotionAItemCombination.add(p1A);
		
		DiscountMode promotionADiscountType = DiscountMode.DiscountPect;
		BigDecimal promotionADiscountPect = new BigDecimal("10"); //10% off
		Promotion promotionA = new Promotion(promotionAStartDateTime, promotionAEndDateTime, promotionAItemCombination, promotionADiscountType, promotionADiscountPect, null);
		promotionA.setName("A");
		promotionList.add(promotionA);
		//END: promotion set A
		
		
		//promotion set B: 2A+3B = 15% off
		ZonedDateTime promotionBStartDateTime = ZonedDateTime.of(2021, 12, 20, 4, 0, 0, 0, zoneId); //2021-dec-20 4:00am
		ZonedDateTime promotionBEndDateTime = ZonedDateTime.of(2022, 1, 1, 4, 0, 0, 0, zoneId); //2022-jan-1 4:00am
		List<PromotionItem> promotionBItemCombination = new ArrayList<PromotionItem>();
		PromotionItem p2A = new PromotionItem(ItemDB.ITEM_A, 2); //2 items for A	
		PromotionItem p2B = new PromotionItem(ItemDB.ITEM_B, 3); //3 items for B
		promotionBItemCombination.add(p2A);
		promotionBItemCombination.add(p2B);
		
		DiscountMode promotionBDiscountType = DiscountMode.DiscountPect;
		BigDecimal promotionBDiscountPect = new BigDecimal("15"); //15% off
		
		Promotion promotionB = new Promotion(promotionBStartDateTime, promotionBEndDateTime, promotionBItemCombination, promotionBDiscountType, promotionBDiscountPect, null);
		promotionB.setName("B");
		promotionList.add(promotionB);
		//END: promotion set B
		
		
		//promotion set C: 3C = 5% off
		ZonedDateTime promotionCStartDateTime = ZonedDateTime.of(2021, 12, 20, 4, 0, 0, 0, zoneId); //2021-dec-20 4:00am
		ZonedDateTime promotionCEndDateTime = ZonedDateTime.of(2022, 1, 1, 4, 0, 0, 0, zoneId); //2022-jan-1 4:00am
		
		List<PromotionItem> promotionCItemCombination = new ArrayList<PromotionItem>();
		PromotionItem p3C = new PromotionItem(ItemDB.ITEM_C, 3);
		promotionCItemCombination.add(p3C);
		
		DiscountMode promotionCDiscountType = DiscountMode.DiscountPect;
		BigDecimal promotionCDiscountPect = new BigDecimal("5"); //5% off
		Promotion promotionC = new Promotion(promotionCStartDateTime, promotionCEndDateTime, promotionCItemCombination, promotionCDiscountType, promotionCDiscountPect, null);
		promotionC.setName("C");
		promotionList.add(promotionC);
		//END: promotion set C
		
		
		//promotion set D: 5C = 7% off
		ZonedDateTime promotionDStartDateTime = ZonedDateTime.of(2021, 12, 20, 4, 0, 0, 0, zoneId); //2021-dec-20 4:00am
		ZonedDateTime promotionDEndDateTime = ZonedDateTime.of(2022, 1, 4, 4, 0, 0, 0, zoneId); //2022-jan-1 4:00am
		List<PromotionItem> promotionDItemCombination = new ArrayList<PromotionItem>();
		PromotionItem p4C = new PromotionItem(ItemDB.ITEM_C, 5);
		promotionDItemCombination.add(p4C);
		
		DiscountMode promotionDDiscountType = DiscountMode.DiscountPect; //pect
		BigDecimal promotionDDiscountPect = new BigDecimal("7"); //7% off
		Promotion promotionD = new Promotion(promotionDStartDateTime, promotionDEndDateTime, promotionDItemCombination, promotionDDiscountType, promotionDDiscountPect, null);
		promotionD.setName("D");
		promotionList.add(promotionD);
		//END: promotion set D
		
		//promotion set E: 7C = 10% off
		ZonedDateTime promotionEStartDateTime = ZonedDateTime.of(2021, 12, 20, 4, 0, 0, 0, zoneId); //2021-dec-20 4:00am
		ZonedDateTime promotionEEndDateTime = ZonedDateTime.of(2022, 1, 4, 4, 0, 0, 0, zoneId); //2022-jan-1 4:00am
		List<PromotionItem> promotionEItemCombination = new ArrayList<PromotionItem>();
		PromotionItem p5C = new PromotionItem(ItemDB.ITEM_C, 7);
		promotionEItemCombination.add(p5C);
		
		DiscountMode promotionEDiscountType = DiscountMode.DiscountPect;
		BigDecimal promotionEDiscountPect = new BigDecimal("10"); //10% off
		Promotion promotionE = new Promotion(promotionEStartDateTime, promotionEEndDateTime, promotionEItemCombination, promotionEDiscountType, promotionEDiscountPect, null);
		promotionE.setName("E");
		promotionList.add(promotionE);
		//END: promotion set E
		
		
		//promotion set F: 3A+2B+C = 30% off
		ZonedDateTime promotionFStartDateTime = ZonedDateTime.of(2021, 12, 20, 4, 0, 0, 0, zoneId); //2021-dec-20 4:00am
		ZonedDateTime promotionFEndDateTime = ZonedDateTime.of(2022, 1, 4, 4, 0, 0, 0, zoneId); //2022-jan-1 4:00am
		List<PromotionItem> promotionFItemCombination = new ArrayList<PromotionItem>();
		PromotionItem p6A = new PromotionItem(ItemDB.ITEM_A, 3);
		PromotionItem p6B = new PromotionItem(ItemDB.ITEM_B, 2);
		PromotionItem p6C = new PromotionItem(ItemDB.ITEM_C, 1);
		promotionFItemCombination.add(p6A);
		promotionFItemCombination.add(p6B);
		promotionFItemCombination.add(p6C);
		
		DiscountMode promotionFDiscountType = DiscountMode.DiscountPect;
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
		return getRelatedPromotions(cart, ZonedDateTime.now()); //current timestamp with server timezone if no datetime is provided
	}
	public ArrayList<Promotion> getRelatedPromotions(Cart cart, ZonedDateTime processDateCalendar){
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
				List<PromotionItem> pComb = p.getPromotionItemCombination();
				if (pComb.size()>maxItemSetSizeOfRelatedPromotionCombination) maxItemSetSizeOfRelatedPromotionCombination=pComb.size(); //should just useful in the first loop
				
				if (pComb.size()-1<promotionItemSetIndex) {
					tempNewPromotionList.add(p);
					continue;
				}
				
				PromotionItem promotionItemNode = pComb.get(promotionItemSetIndex);
				Item pItem = promotionItemNode.getItem();
				int pQty = promotionItemNode.getQuantity();
				
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