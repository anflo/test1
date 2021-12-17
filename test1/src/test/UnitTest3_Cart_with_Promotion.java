package test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cart.Cart;
import item.Item;
import item.ItemDB;
import item.promotion.DiscountMode;
import item.promotion.Promotion;

class UnitTest3_Cart_with_Promotion {
	protected Item a;
	protected Item b;
	protected Item c;
	protected Item d;
	protected Cart cart;
	DecimalFormat df = new DecimalFormat("#,###.00");
	TimeZone timeZone = TimeZone.getTimeZone("GMT");
	Promotion promotionA;
	BigDecimal promotionADiscountPect;
	
	Promotion promotionB;
	BigDecimal promotionBDiscountPect;
	
	@BeforeEach
	void setUp() throws Exception {
		this.a = ItemDB.ITEM_A; //new Item("A", new BigDecimal("50"));
		this.b = ItemDB.ITEM_B; //new Item("B", new BigDecimal("30"));
		this.c = ItemDB.ITEM_C; //new Item("C", new BigDecimal("20"));
		this.d = ItemDB.ITEM_D; //new Item("D", new BigDecimal("15"));
		
		
		
		//promotion set A
		Calendar promotionAStartDateTime = Calendar.getInstance(timeZone);
		promotionAStartDateTime.set(2021, 11, 20, 4, 0); //2021-dec-20 4:00am
		Calendar promotionAEndDateTime = Calendar.getInstance(timeZone);
		promotionAEndDateTime.set(2022, 0, 1, 4, 00); //2022-jan-1 4:00am
		
		List<ArrayList<Object>> promotionAItemCombination = new ArrayList<ArrayList<Object>>();
		ArrayList temp1AList = new ArrayList();
		temp1AList.add(a);
		temp1AList.add(2); //2 items for A
		promotionAItemCombination.add(temp1AList);
		
		DiscountMode promotionADiscountType = DiscountMode.DiscountPect; //pect
		promotionADiscountPect = new BigDecimal("10"); //10% off
		
		promotionA = new Promotion(promotionAStartDateTime, promotionAEndDateTime, promotionAItemCombination, promotionADiscountType, promotionADiscountPect, null);
		//END: promotion set A
		
		
		//promotion set B
		Calendar promotionBStartDateTime = Calendar.getInstance(timeZone);
		promotionBStartDateTime.set(2021, 11, 20, 4, 0); //2021-dec-20 4:00am
		Calendar promotionBEndDateTime = Calendar.getInstance(timeZone);
		promotionBEndDateTime.set(2022, 0, 1, 4, 00); //2022-jan-1 4:00am
		
		List<ArrayList<Object>> promotionBItemCombination = new ArrayList<ArrayList<Object>>();
		ArrayList temp2AList = new ArrayList();
		temp2AList.add(a);
		temp2AList.add(2); //2 items for A
		promotionBItemCombination.add(temp2AList);
		
		ArrayList temp2BList = new ArrayList();
		temp2BList.add(b);
		temp2BList.add(3); //3 items for B
		promotionBItemCombination.add(temp2BList);
		
		DiscountMode promotionBDiscountType = DiscountMode.DiscountPect; //pect
		promotionBDiscountPect = new BigDecimal("15"); //15% off
		
		promotionB = new Promotion(promotionBStartDateTime, promotionBEndDateTime, promotionBItemCombination, promotionBDiscountType, promotionBDiscountPect, null);
		//END: promotion set B
		
		
		cart = new Cart();
	}

	@Test
	void cartTestOnChangingQtyWithPromotion() {
		cart.updateItem(a, 3, false); //3A in non-promotion cart
		assertEquals(cart.getItemQuanitiy(a), 3);
		cart.addPromotionGroup(promotionA); //3A in non-promotion cart + 2A in promotion cart
		assertEquals(cart.getItemQuanitiy(a), 3); //get 3 on fucntion's purpose
		assertEquals(cart.getPromotedItemQty(a), 2); //get 2 on fucntion's purpose
		
		cart.updateItem(a, 7, true); //dismiss promotion items
		assertEquals(cart.getItemQuanitiy(a), 7); //updated to 7A
		assertEquals(cart.getItemQuanitiy(b), 0); 
		
		cart.addPromotionGroup(promotionB); //7A in non-promotion cart + (2A & 3B in promotion cart)
		assertEquals(cart.getItemQuanitiy(a), 7);
		assertEquals(cart.getPromotedItemQty(a), 2); 
		assertEquals(cart.getPromotedItemQty(b), 3);
		cart.updateItem(b, 4, true); //dismiss promotion items all promotions
		assertEquals(cart.getItemQuanitiy(a), 9);
		assertEquals(cart.getItemQuanitiy(b), 4);
		assertEquals(cart.getPromotedItemQty(a), 0); 
		assertEquals(cart.getPromotedItemQty(b), 0);
		
	}

}
