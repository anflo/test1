package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cart.Cart;
import cart.CartPromotion;
import item.ItemDB;
import item.promotion.Promotion;
import item.promotion.process.PromotionProcess;

class UnitTest5_Cart_Promotion_Process {

	protected Cart cart1;
	protected Cart cart2;
	protected Cart cart3;
	protected Cart cart4;
	protected Cart cart5;
	protected Cart cart6;
	CartPromotion cp;
	ZoneId zoneId;
	@BeforeEach
	void setUp() throws Exception {
		cp = new CartPromotion();
		zoneId = ZoneId.of("UTC+0");
		
		cart1 = new Cart();
		cart1.updateItem(ItemDB.ITEM_A, 3, false);
		
		cart2 = new Cart();
		cart2.updateItem(ItemDB.ITEM_A, 3, false);
		cart2.updateItem(ItemDB.ITEM_B, 4, false);
		
		cart3 = new Cart();
		cart3.updateItem(ItemDB.ITEM_A, 9, false);
		cart3.updateItem(ItemDB.ITEM_B, 7, false);
		
		cart4 = new Cart();
		cart4.updateItem(ItemDB.ITEM_A, 4, false);
		cart4.updateItem(ItemDB.ITEM_B, 3, false);
		cart4.updateItem(ItemDB.ITEM_C, 12, false); 
		
		cart5 = new Cart();
		cart5.updateItem(ItemDB.ITEM_C, 8, false); 
		
		cart6 = new Cart();
	}

	@Test
	void test() {
		//cart 1: 3A
		// promotion should be applied: A
		ZonedDateTime processDateTime1 = ZonedDateTime.of(2021, 12, 25, 0, 0, 0, 0, zoneId); //2021-dec-25 0:00am
		PromotionProcess pc1 = new PromotionProcess(cart1);
		cart1 = pc1.applyPromotion(processDateTime1);
		ArrayList<Promotion> p1Group = (ArrayList<Promotion>) cart1.getPromotionGroup();
		assertEquals(p1Group.size(), 1);
		assertEquals(p1Group.get(0).getName(), "A");
		assertEquals(cart1.getItemQuanitiy(ItemDB.ITEM_A), 1);
		assertEquals(cart1.getPromotedItemQty(ItemDB.ITEM_A), 2);
		processDateTime1 = ZonedDateTime.of(2022, 12, 25, 0, 0, 0, 0, zoneId); //2022-dec-25 0:00am
		cart1 = pc1.applyPromotion(processDateTime1);
		p1Group = (ArrayList<Promotion>) cart1.getPromotionGroup();
		assertEquals(p1Group.size(), 0);
		assertEquals(cart1.getItemQuanitiy(ItemDB.ITEM_A), 3);
		
		
		//cart 2: 3A +4B
		// promotion should be applied: B 
		ZonedDateTime processDateTime2 = ZonedDateTime.of(2021, 12, 25, 0, 0, 0, 0, zoneId); //2021-dec-25 0:00am
		PromotionProcess pc2 = new PromotionProcess(cart2);
		cart2 = pc2.applyPromotion(processDateTime2);
		ArrayList<Promotion> p2Group = (ArrayList<Promotion>) cart2.getPromotionGroup();
		assertEquals(p2Group.size(), 1);
		assertEquals(p2Group.get(0).getName(), "B");
		assertEquals(cart2.getItemQuanitiy(ItemDB.ITEM_A), 1);
		assertEquals(cart2.getPromotedItemQty(ItemDB.ITEM_A), 2);
		assertEquals(cart2.getItemQuanitiy(ItemDB.ITEM_B), 1);
		assertEquals(cart2.getPromotedItemQty(ItemDB.ITEM_B), 3);	
		
		
		//cart 3: 9A + 7B
		// promotion should be applied: B x 2 + A x 2
		ZonedDateTime processDateTime3 = ZonedDateTime.of(2021, 12, 25, 0, 0, 0, 0, zoneId); //2021-dec-25 0:00am
		PromotionProcess pc3 = new PromotionProcess(cart3);
		cart3 = pc3.applyPromotion(processDateTime3);
		ArrayList<Promotion> p3Group = (ArrayList<Promotion>) cart3.getPromotionGroup();
		assertEquals(p3Group.size(), 4);
		assertEquals(p3Group.get(0).getName(), "B");
		assertEquals(p3Group.get(1).getName(), "B");
		assertEquals(p3Group.get(2).getName(), "A");
		assertEquals(p3Group.get(3).getName(), "A");
		assertEquals(cart3.getItemQuanitiy(ItemDB.ITEM_A), 1);
		assertEquals(cart3.getPromotedItemQty(ItemDB.ITEM_A), 8);
		assertEquals(cart3.getItemQuanitiy(ItemDB.ITEM_B), 1);
		assertEquals(cart3.getPromotedItemQty(ItemDB.ITEM_B), 6);
		
		
		//cart 4: 4A + 3B + 12C
		// promotion should be applied: F + E + C
		ZonedDateTime processDateTime4 = ZonedDateTime.of(2021, 12, 25, 0, 0, 0, 0, zoneId); //2021-dec-25 0:00am
		PromotionProcess pc4 = new PromotionProcess(cart4);
		cart4 = pc4.applyPromotion(processDateTime4);
		ArrayList<Promotion> p4Group = (ArrayList<Promotion>) cart4.getPromotionGroup();
		assertEquals(p4Group.size(), 3);
		assertEquals(p4Group.get(0).getName(), "F");
		assertEquals(p4Group.get(1).getName(), "E");
		assertEquals(p4Group.get(2).getName(), "C");
		assertEquals(cart4.getItemQuanitiy(ItemDB.ITEM_A), 1);
		assertEquals(cart4.getPromotedItemQty(ItemDB.ITEM_A), 3);
		assertEquals(cart4.getItemQuanitiy(ItemDB.ITEM_B), 1);
		assertEquals(cart4.getPromotedItemQty(ItemDB.ITEM_B), 2);
		assertEquals(cart4.getItemQuanitiy(ItemDB.ITEM_C), 1);
		assertEquals(cart4.getPromotedItemQty(ItemDB.ITEM_C), 11);
		
		
		//cart 5: 8C
		// promotion should be applied: E , not C+D
		ZonedDateTime processDateTime5 = ZonedDateTime.of(2021, 12, 25, 0, 0, 0, 0, zoneId); //2021-dec-25 0:00am
		PromotionProcess pc5 = new PromotionProcess(cart5);
		cart5 = pc5.applyPromotion(processDateTime5);
		ArrayList<Promotion> p5Group = (ArrayList<Promotion>) cart5.getPromotionGroup();
		assertEquals(p5Group.size(), 1);
		assertEquals(p5Group.get(0).getName(), "E");
		assertEquals(cart5.getItemQuanitiy(ItemDB.ITEM_C), 1);
		assertEquals(cart5.getPromotedItemQty(ItemDB.ITEM_C), 7);
		
		
		//cart 6: real-time moving along items
		ZonedDateTime processDateTime6 = ZonedDateTime.of(2021, 12, 25, 0, 0, 0, 0, zoneId); //2021-dec-25 0:00am
		//Step 1: add Promotion-C into cart
		cart6.addPromotionGroup(cp.getPromotionByNameFromList("C"));
		PromotionProcess pc6 = new PromotionProcess(cart6);
		cart6 = pc6.applyPromotion(processDateTime6); //re-calculate all possible promotions for bigger save
		ArrayList<Promotion> p6Group = (ArrayList<Promotion>) cart6.getPromotionGroup();
		assertEquals(p6Group.size(), 1);
		assertEquals(p6Group.get(0).getName(), "C");
		assertEquals(cart6.getItemQuanitiy(ItemDB.ITEM_C), 0);
		assertEquals(cart6.getPromotedItemQty(ItemDB.ITEM_C), 3);
		//Step 2: add Promotion-D into cart, and then since there would be 8C, it should be turning into Promotion-E, and remains 1C in non-promotion item cart
		cart6.addPromotionGroup(cp.getPromotionByNameFromList("D"));
		cart6 = pc6.applyPromotion(processDateTime6); //re-calculate all possible promotions for bigger save
		p6Group = (ArrayList<Promotion>) cart6.getPromotionGroup();
		assertEquals(p6Group.size(), 1);
		assertEquals(p6Group.get(0).getName(), "E");
		assertEquals(cart6.getItemQuanitiy(ItemDB.ITEM_C), 1);
		assertEquals(cart6.getPromotedItemQty(ItemDB.ITEM_C), 7);
		//Step 3: Add 3A into cart , 2 of the A should apply Promotion-A
		cart6.updateItem(ItemDB.ITEM_A, 3);
		cart6 = pc6.applyPromotion(processDateTime6); //re-calculate all possible promotions for bigger save
		p6Group = (ArrayList<Promotion>) cart6.getPromotionGroup();
		assertEquals(p6Group.size(), 2);
		assertEquals(p6Group.get(0).getName(), "E");
		assertEquals(cart6.getItemQuanitiy(ItemDB.ITEM_C), 1);
		assertEquals(cart6.getPromotedItemQty(ItemDB.ITEM_C), 7);
		assertEquals(p6Group.get(1).getName(), "A");
		assertEquals(cart6.getItemQuanitiy(ItemDB.ITEM_A), 1);
		assertEquals(cart6.getPromotedItemQty(ItemDB.ITEM_A), 2);
		//Step 4: Add 3B into cart and take-out 1A (to be 2A in total), the Promotion-A should be taken out, and then apply Promotion-B
		cart6.updateItem(ItemDB.ITEM_B, 3);
		cart6.updateItem(ItemDB.ITEM_A, 2);
		cart6 = pc6.applyPromotion(processDateTime6); //re-calculate all possible promotions for bigger save
		p6Group = (ArrayList<Promotion>) cart6.getPromotionGroup();
		assertEquals(p6Group.size(), 2);
		assertEquals(p6Group.get(0).getName(), "B");
		assertEquals(cart6.getItemQuanitiy(ItemDB.ITEM_A), 0);
		assertEquals(cart6.getPromotedItemQty(ItemDB.ITEM_A), 2);
		assertEquals(cart6.getItemQuanitiy(ItemDB.ITEM_B), 0);
		assertEquals(cart6.getPromotedItemQty(ItemDB.ITEM_B), 3);
		assertEquals(p6Group.get(1).getName(), "E");
		assertEquals(cart6.getItemQuanitiy(ItemDB.ITEM_C), 1);
		assertEquals(cart6.getPromotedItemQty(ItemDB.ITEM_C), 7);
		//Step 5: Add 1A into the cart, the promotion-B would be taken out, and apply Promotion-F 
		cart6.updateItem(ItemDB.ITEM_A, 3);
		cart6 = pc6.applyPromotion(processDateTime6); //re-calculate all possible promotions for bigger save
		p6Group = (ArrayList<Promotion>) cart6.getPromotionGroup();
		assertEquals(p6Group.size(), 2);
		assertEquals(p6Group.get(0).getName(), "F");
		assertEquals(p6Group.get(1).getName(), "E");
		assertEquals(cart6.getItemQuanitiy(ItemDB.ITEM_A), 0);
		assertEquals(cart6.getPromotedItemQty(ItemDB.ITEM_A), 3);
		assertEquals(cart6.getItemQuanitiy(ItemDB.ITEM_B), 1);
		assertEquals(cart6.getPromotedItemQty(ItemDB.ITEM_B), 2);
		assertEquals(cart6.getItemQuanitiy(ItemDB.ITEM_C), 0);
		assertEquals(cart6.getPromotedItemQty(ItemDB.ITEM_C), 8);
	}

}

