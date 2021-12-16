package test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cart.Cart;
import item.Item;
import item.ItemDB;

class UnitTest1_Item {

	protected Item a;
	protected Item b;
	protected Item c;
	protected Item d;
	protected Cart cart;
	
	@BeforeEach
	void setUp(){
		this.a = ItemDB.ITEM_A; //new Item("A", new BigDecimal("50"));
		this.b = ItemDB.ITEM_B; //new Item("B", new BigDecimal("30"));
		this.c = ItemDB.ITEM_C; //new Item("C", new BigDecimal("20"));
		this.d = ItemDB.ITEM_D; //new Item("D", new BigDecimal("15"));
		
		cart = new Cart();
	}
	
	@Test
	void simpleCartOperationTest() {
		assertEquals("A", this.a.getSku());
		assertEquals(new BigDecimal("50"), this.a.getUnitPrice());
    	
		cart.updateItem(a, 3, false);
		assertEquals(cart.getItemQuanitiy(a), 3);
		cart.updateItem(a, 7, false);
		assertEquals(cart.getItemQuanitiy(a), 7);
    	cart.updateItem(b, 2, false);
    	cart.updateItem(c, 5, false);
    	cart.updateItem(d, 15, false);
    	assertEquals(cart.getItemQuanitiy(b), 2);
    	assertEquals(cart.getItemQuanitiy(c), 5);
    	assertEquals(cart.getItemQuanitiy(d), 15);
    	cart.updateItem(c, 0, false);
    	assertEquals(cart.getItemQuanitiy(c), 0);
	}

}
