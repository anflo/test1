package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cart.Cart;
import cart.CartPromotion;
import item.ItemDB;
import item.promotion.Promotion;

class UnitTest4_CartPromotion {

	protected Cart cart1;
	protected Cart cart2;
	protected Cart cart3;
	CartPromotion cp;
	ZoneId zoneId;
	@BeforeEach
	void setUp() throws Exception {
		cp = new CartPromotion();
		
		zoneId = ZoneId.of("UTC+0");
		
		cart1 = new Cart();
		cart1.updateItem(ItemDB.ITEM_A, 3);
		
		cart2 = new Cart();
		cart2.updateItem(ItemDB.ITEM_A, 3);
		cart2.updateItem(ItemDB.ITEM_B, 4);
		
		cart3 = new Cart();
		cart3.updateItem(ItemDB.ITEM_A, 3);
		cart3.updateItem(ItemDB.ITEM_B, 4);
		cart3.updateItem(ItemDB.ITEM_C, 5);
	}

	@Test
	void simpleTestOnCardPromotionAlgorithm() {
		ZonedDateTime processDateTime1 = ZonedDateTime.of(2021, 12, 25, 0, 0, 0, 0, zoneId); //2021-dec-25 0:00am
		ArrayList<Promotion> pList1 = cp.getRelatedPromotionsFromList(cart1, processDateTime1);
		assertEquals(pList1.size(), 1);
		assertEquals(pList1.get(0).getName(), "A");
		processDateTime1 = ZonedDateTime.of(2022, 12, 25, 0, 0, 0, 0, zoneId); //2022-dec-25 0:00am
		pList1 = cp.getRelatedPromotionsFromList(cart1, processDateTime1);
		assertEquals(pList1.size(), 0);
		

		ZonedDateTime processDateTime2 = ZonedDateTime.of(2021, 12, 25, 0, 0, 0, 0, zoneId); //2021-dec-25 0:00am
		ArrayList<Promotion> pList2 = cp.getRelatedPromotionsFromList(cart2, processDateTime2);
		assertEquals(pList2.size(), 2);
		assertEquals(pList2.get(0).getName(), "B");
		assertEquals(pList2.get(1).getName(), "A");
		processDateTime2 = ZonedDateTime.of(2022, 12, 25, 0, 0, 0, 0, zoneId); //2022-dec-25 0:00am
		pList2 = cp.getRelatedPromotionsFromList(cart2, processDateTime2);
		assertEquals(pList2.size(), 0);
		
		
		ZonedDateTime processDateTime3 = ZonedDateTime.of(2021, 12, 25, 0, 0, 0, 0, zoneId); //2021-dec-25 0:00am
		ArrayList<Promotion> pList3 = cp.getRelatedPromotionsFromList(cart3, processDateTime3);
		assertEquals(pList3.size(), 5);
		assertEquals(pList3.get(0).getName(), "F");
		assertEquals(pList3.get(1).getName(), "B");
		assertEquals(pList3.get(2).getName(), "A");
		assertEquals(pList3.get(3).getName(), "D");
		assertEquals(pList3.get(4).getName(), "C");
		
		processDateTime3 = ZonedDateTime.of(2022, 12, 25, 0, 0, 0, 0, zoneId); //2022-dec-25 0:00am
		pList3 = cp.getRelatedPromotionsFromList(cart3, processDateTime3);
		assertEquals(pList3.size(), 0);
		
		
	}

}
