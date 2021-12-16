package test;

import static org.junit.jupiter.api.Assertions.*;

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

class UnitTest5_Cart_Promotion_Process {

	protected Cart cart1;
	protected Cart cart2;
	protected Cart cart3;
	CartPromotion cp;
	TimeZone timeZone;
	@BeforeEach
	void setUp() throws Exception {
		cp = new CartPromotion();
		timeZone = TimeZone.getTimeZone("GMT");
		
		cart1 = new Cart();
		cart1.updateItem(ItemDB.ITEM_A, 3, false);
		
		cart2 = new Cart();
		cart2.updateItem(ItemDB.ITEM_A, 3, false);
		cart2.updateItem(ItemDB.ITEM_B, 4, false);
		
		cart3 = new Cart();
		cart3.updateItem(ItemDB.ITEM_A, 9, false);
		cart3.updateItem(ItemDB.ITEM_B, 7, false);
		
//		cart3 = new Cart();
//		cart3.updateItem(ItemDB.ITEM_A, 3, false);
//		cart3.updateItem(ItemDB.ITEM_B, 4, false);
//		cart3.updateItem(ItemDB.ITEM_C, 5, false);
	}

	@Test
	void test() {
		/*
		 * All promotion:
		 * A: 2A = 10% off, $10 saved
		 * B: 2A+3B = 15% off, $25.5 saved
		 * C: 3C = 5% off, $3 saved
		 * D: 5C = 7% off, $7 saved
		 * E: 7C = 10% off, $14 saved
		 * F: 3A+2B+C = 30% off, $69 saved
		 */
		
		//cart 1: 3A
		// promotion should be applied: A
		Calendar processDateTime1 = Calendar.getInstance(timeZone);
		processDateTime1.set(2021, 11, 25, 0, 0); //2021-dec-25 0:00am
		cart1.applyPromotion(processDateTime1);
		ArrayList<Promotion> p1Group = (ArrayList<Promotion>) cart1.getPromotionGroup();
		assertEquals(p1Group.size(), 1);
		assertEquals(p1Group.get(0).getName(), "A");
		assertEquals(cart1.getItemQuanitiy(ItemDB.ITEM_A), 1);
		assertEquals(cart1.getPromotedItemQty(ItemDB.ITEM_A), 2);
		processDateTime1.set(2022, 11, 25, 0, 0); //2022-dec-25 0:00am
		cart1.applyPromotion(processDateTime1);
		p1Group = (ArrayList<Promotion>) cart1.getPromotionGroup();
		assertEquals(p1Group.size(), 0);
		assertEquals(cart1.getItemQuanitiy(ItemDB.ITEM_A), 3);
		
		
		//cart 2: 3A +4B
		// promotion should be applied: B 
		Calendar processDateTime2 = Calendar.getInstance(timeZone);
		processDateTime2.set(2021, 11, 25, 0, 0); //2021-dec-25 0:00am
		cart2.applyPromotion(processDateTime2);
		ArrayList<Promotion> p2Group = (ArrayList<Promotion>) cart2.getPromotionGroup();
		assertEquals(p2Group.size(), 1);
		assertEquals(p2Group.get(0).getName(), "B");
		assertEquals(cart2.getItemQuanitiy(ItemDB.ITEM_A), 1);
		assertEquals(cart2.getPromotedItemQty(ItemDB.ITEM_A), 2);
		assertEquals(cart2.getItemQuanitiy(ItemDB.ITEM_B), 1);
		assertEquals(cart2.getPromotedItemQty(ItemDB.ITEM_B), 3);	
		
		
		//cart 3: 9A + 7B
		// promotion should be applied: B x 2 + A x 2
		Calendar processDateTime3 = Calendar.getInstance(timeZone);
		processDateTime3.set(2021, 11, 25, 0, 0); //2021-dec-25 0:00am
		cart3.applyPromotion(processDateTime3);
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
		
		
	}

}
