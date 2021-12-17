package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cart.Cart;
import item.Item;
import item.ItemDB;

class UnitTest1_Item {

	protected Cart cart;
	
	@BeforeEach
	void setUp(){
		cart = new Cart();
	}
	
	@Test
	void simpleCartOperationTest() {
		assertEquals("A", ItemDB.ITEM_A.getSku());
		assertEquals(new BigDecimal("50"), ItemDB.ITEM_A.getUnitPrice());
    	
		cart.updateItem(ItemDB.ITEM_A, 3);
		assertEquals(cart.getItemQuanitiy(ItemDB.ITEM_A), 3);
		cart.updateItem(ItemDB.ITEM_A, 7);
		assertEquals(cart.getItemQuanitiy(ItemDB.ITEM_A), 7);
		
		cart.updateItem(ItemDB.ITEM_B, 2);
		cart.updateItem(ItemDB.ITEM_C, 5);
		cart.updateItem(ItemDB.ITEM_D, 15);
		assertEquals(cart.getItemQuanitiy(ItemDB.ITEM_B), 2);
		assertEquals(cart.getItemQuanitiy(ItemDB.ITEM_C), 5);
		assertEquals(cart.getItemQuanitiy(ItemDB.ITEM_D), 15);
		cart.updateItem(ItemDB.ITEM_C, 0);
		assertEquals(cart.getItemQuanitiy(ItemDB.ITEM_C), 0);
	}

}
