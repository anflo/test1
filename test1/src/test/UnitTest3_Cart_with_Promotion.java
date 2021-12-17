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
import item.promotion.PromotionItem;

class UnitTest3_Cart_with_Promotion {
	protected Cart cart;
	DecimalFormat df = new DecimalFormat("#,###.00");
	TimeZone timeZone = TimeZone.getTimeZone("GMT");
	Promotion promotionA;
	BigDecimal promotionADiscountPect;
	
	Promotion promotionB;
	BigDecimal promotionBDiscountPect;
	
	@BeforeEach
	void setUp() throws Exception {
		
		//promotion set A
		Calendar promotionAStartDateTime = Calendar.getInstance(timeZone);
		promotionAStartDateTime.set(2021, 11, 20, 4, 0); //2021-dec-20 4:00am
		Calendar promotionAEndDateTime = Calendar.getInstance(timeZone);
		promotionAEndDateTime.set(2022, 0, 1, 4, 00); //2022-jan-1 4:00am
		
		List<PromotionItem> promotionAItemCombination = new ArrayList<PromotionItem>();
		PromotionItem p1A = new PromotionItem(ItemDB.ITEM_A, 2);
		promotionAItemCombination.add(p1A);
		
		DiscountMode promotionADiscountType = DiscountMode.DiscountPect; //pect
		promotionADiscountPect = new BigDecimal("10"); //10% off
		
		promotionA = new Promotion(promotionAStartDateTime, promotionAEndDateTime, promotionAItemCombination, promotionADiscountType, promotionADiscountPect, null);
		//END: promotion set A
		
		
		//promotion set B
		Calendar promotionBStartDateTime = Calendar.getInstance(timeZone);
		promotionBStartDateTime.set(2021, 11, 20, 4, 0); //2021-dec-20 4:00am
		Calendar promotionBEndDateTime = Calendar.getInstance(timeZone);
		promotionBEndDateTime.set(2022, 0, 1, 4, 00); //2022-jan-1 4:00am
		
		List<PromotionItem> promotionBItemCombination = new ArrayList<PromotionItem>();
		PromotionItem p2A = new PromotionItem(ItemDB.ITEM_A, 2);
		PromotionItem p2B = new PromotionItem(ItemDB.ITEM_B, 3);
		promotionBItemCombination.add(p2A);
		promotionBItemCombination.add(p2B);
		
		DiscountMode promotionBDiscountType = DiscountMode.DiscountPect; //pect
		promotionBDiscountPect = new BigDecimal("15"); //15% off
		
		promotionB = new Promotion(promotionBStartDateTime, promotionBEndDateTime, promotionBItemCombination, promotionBDiscountType, promotionBDiscountPect, null);
		//END: promotion set B
		
		
		cart = new Cart();
	}

	@Test
	void cartTestOnChangingQtyWithPromotion() {
		cart.updateItem(ItemDB.ITEM_A, 3, false); //3A in non-promotion cart
		assertEquals(cart.getItemQuanitiy(ItemDB.ITEM_A), 3);
		cart.addPromotionGroup(promotionA); //3A in non-promotion cart + 2A in promotion cart
		assertEquals(cart.getItemQuanitiy(ItemDB.ITEM_A), 3); //get 3 on fucntion's purpose
		assertEquals(cart.getPromotedItemQty(ItemDB.ITEM_A), 2); //get 2 on fucntion's purpose
		
		cart.updateItem(ItemDB.ITEM_A, 7, true); //dismiss promotion items
		assertEquals(cart.getItemQuanitiy(ItemDB.ITEM_A), 7); //updated to 7A
		assertEquals(cart.getItemQuanitiy(ItemDB.ITEM_B), 0); 
		
		cart.addPromotionGroup(promotionB); //7A in non-promotion cart + (2A & 3B in promotion cart)
		assertEquals(cart.getItemQuanitiy(ItemDB.ITEM_A), 7);
		assertEquals(cart.getPromotedItemQty(ItemDB.ITEM_A), 2); 
		assertEquals(cart.getPromotedItemQty(ItemDB.ITEM_B), 3);
		cart.updateItem(ItemDB.ITEM_B, 4, true); //dismiss promotion items all promotions
		assertEquals(cart.getItemQuanitiy(ItemDB.ITEM_A), 9);
		assertEquals(cart.getItemQuanitiy(ItemDB.ITEM_B), 4);
		assertEquals(cart.getPromotedItemQty(ItemDB.ITEM_A), 0); 
		assertEquals(cart.getPromotedItemQty(ItemDB.ITEM_B), 0);
		
	}

}
