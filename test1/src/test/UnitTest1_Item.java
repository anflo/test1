package test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cart.Cart;
import item.Item;

class UnitTest1_Item {

	protected Item a;
	protected Item b;
	protected Item c;
	protected Item d;
	protected Cart cart;
	
	@BeforeEach
	void setUp(){
		this.a = new Item("A", new BigDecimal("50"));
		this.b = new Item("B", new BigDecimal("30"));
		this.c = new Item("C", new BigDecimal("20"));
		this.d = new Item("D", new BigDecimal("15"));
		
		cart = new Cart();
	}
	
	@Test
	void simpleCartOperationTest() {
		assertEquals("A", this.a.getSku());
		assertEquals(new BigDecimal("50"), this.a.getUnitPrice());
    	
		cart.updateItem(a, 3);
		assertEquals(cart.getQuanitiyOfSKU(a.getSku()), 3);
		cart.updateItem(a, 7);
		assertEquals(cart.getQuanitiyOfSKU(a.getSku()), 7);
    	cart.updateItem(b, 2);
    	cart.updateItem(c, 5);
    	cart.updateItem(d, 15);
    	assertEquals(cart.getQuanitiyOfSKU(b.getSku()), 2);
    	assertEquals(cart.getQuanitiyOfSKU(c.getSku()), 5);
    	assertEquals(cart.getQuanitiyOfSKU(d.getSku()), 15);
    	cart.updateItem(c, 0);
    	assertEquals(cart.getQuanitiyOfSKU(c.getSku()), 0);
	}

}
