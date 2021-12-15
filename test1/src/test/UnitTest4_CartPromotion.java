package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

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
	@BeforeEach
	void setUp() throws Exception {
		cp = new CartPromotion();
		
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
		ArrayList<Promotion> pList1 = cp.getRelatedPromotions(cart1);
		assertEquals(pList1.size(), 1);
		assertEquals(pList1.get(0).getName(), "A");
		
		ArrayList<Promotion> pList2 = cp.getRelatedPromotions(cart2);
		assertEquals(pList2.size(), 2);
		assertEquals(pList2.get(0).getName(), "A");
		assertEquals(pList2.get(1).getName(), "B");
		
		ArrayList<Promotion> pList3 = cp.getRelatedPromotions(cart3);
		assertEquals(pList3.size(), 5);
		assertEquals(pList3.get(0).getName(), "A");
		assertEquals(pList3.get(1).getName(), "B");
		assertEquals(pList3.get(2).getName(), "C");
		assertEquals(pList3.get(3).getName(), "D");
		assertEquals(pList3.get(4).getName(), "F");
	}

}
