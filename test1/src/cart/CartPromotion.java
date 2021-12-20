package cart;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import item.Item;
import item.ItemDB;
import item.promotion.DiscountMode;
import item.promotion.Promotion;
import item.promotion.PromotionItem;

//used to calculate all potential promotion based on the inputted cart
public class CartPromotion {

	private ArrayList<Promotion> promotionList;
	private HashMap<String, ArrayList<Promotion>> promotionMap;
	
	public CartPromotion() {
		super();
		promotionList = new ArrayList();
		promotionMap = new HashMap<String, ArrayList<Promotion>>();
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
		ZoneId zoneId = ZoneId.of("UTC+0");
		promotionList = new ArrayList();
		
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

	//start over to recalculate all promotion into the map
	public void refreshPromotionMap() {
		refreshPromotionMap(ZonedDateTime.now());
	}
	public void refreshPromotionMap(ZonedDateTime processDateCalendar) {
		initPromotionList(); //simulate reload all promotions from DB
		promotionMap = new HashMap<String, ArrayList<Promotion>>();
		for(Promotion p: promotionList) {
			if (!p.isOnPromotion(processDateCalendar)) {
				continue;
			}
			ArrayList<PromotionItem> promotionItemCombination = (ArrayList<PromotionItem>)p.getPromotionItemCombination();
			if (promotionItemCombination!=null && promotionItemCombination.size()>0) {
				for(PromotionItem pi: promotionItemCombination) {
					Item i = pi.getItem();
					String keyOfMap = getMapKeyFromItem(i);
					if(keyOfMap!=null && keyOfMap.length()>0) {
						ArrayList<Promotion> existingPromotionList = promotionMap.get(keyOfMap);
						if (existingPromotionList==null || existingPromotionList.size()<=0) {
							existingPromotionList = new ArrayList<Promotion>();
						}	
						existingPromotionList.add(p);
						promotionMap.put(keyOfMap, existingPromotionList);	
						
					}
				}
			}
		}
		
		//re-calculate the priority
		for (String keyOfMap : promotionMap.keySet()) {
			ArrayList<Promotion> newPromotionList = prioritisedPromotion(promotionMap.get(keyOfMap));
			promotionMap.put(keyOfMap, newPromotionList);
		}
	}
	
	private String getMapKeyFromItem(Item p) {
		if (p!=null && p.getSku()!=null && p.getSku().length()>0) {
			return p.getSku();
		}
		return null;
	}
	
	public Promotion getPromotionByNameFromList(String name) {
		for(Promotion p: promotionList) {
			if (p.getName().equals(name)) {
				return p;
			}
		}
		return null;
	}
	
	//Update from 20/Dec/2021, no longer useful by getting the related promotion from List, as it could be using Hashmap to speed up
	// Below process would be a lot faster by querying from DB using all items' SKU directly.
	// For e.g.: A one-to-many relationship of Promotion & PromotionItems structure. I can just
	// gather all items' SKU, query the PromotionItems.SKU (or PromotionItems.item_id.SKU). I'll
	// have a list of Promotion set immediately. The speed is fast by adding index on PromotionItems.SKU (or PromotionItems.item_id.SKU).
	// However, since there is DB and I assume there could be more then a thousand promotions and over a hundred items in the cart,
	// -----
	// - the cart input should be taken off all promotions already
	public ArrayList<Promotion> getRelatedPromotionsFromMap(Cart cart){
		ArrayList<Promotion> returnPromotionList = new ArrayList<Promotion>();
		
		HashMap<String, CartItem> myCart = cart.getMyCart();
		for (String keyOfCartMap : myCart.keySet()) {
			CartItem ci = myCart.get(keyOfCartMap);
			Item i = ci.getItem();
			int cartItemQty = ci.getQuantity();
			String keyOfPromotionHashMap = this.getMapKeyFromItem(i);
			ArrayList<Promotion> pList = this.promotionMap.get(keyOfPromotionHashMap); 
			if (pList!=null && pList.size()>0) {
				returnPromotionList.addAll(pList);
			}
		}
		returnPromotionList = (ArrayList<Promotion>) returnPromotionList.stream().distinct().collect(Collectors.toList());
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