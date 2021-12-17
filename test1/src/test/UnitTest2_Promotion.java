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

import item.Item;
import item.ItemDB;
import item.promotion.DiscountMode;
import item.promotion.Promotion;

class UnitTest2_Promotion {
	protected Item a;
	protected Item b;
	protected Item c;
	protected Item d;
	
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
	}

	@Test
	void simplePromotionOperationTest() {
		//promotion set A
		Calendar promotionATest1DateTime = Calendar.getInstance(timeZone);
		promotionATest1DateTime.set(2021, 11, 20, 4, 0); //2021-dec-20 4:00am
		assertTrue(promotionA.isOnPromotion(promotionATest1DateTime));
		
		Calendar promotionATest2DateTime = Calendar.getInstance(timeZone);
		promotionATest2DateTime.set(2021, 11, 20, 3, 59); //2021-dec-20 3:59am
		assertFalse(promotionA.isOnPromotion(promotionATest2DateTime));
		
		
		Calendar promotionATest3DateTime = Calendar.getInstance(timeZone);
		promotionATest3DateTime.set(2022, 0, 1, 3, 59); //2022-jan-1 3:59am
		assertTrue(promotionA.isOnPromotion(promotionATest3DateTime));
		
		Calendar promotionATest4DateTime = Calendar.getInstance(timeZone);
		promotionATest4DateTime.set(2022, 0, 1, 4, 0); //2022-jan-1 4:00am
		assertFalse(promotionA.isOnPromotion(promotionATest4DateTime));
		
		assertEquals(promotionA.getDiscountPect(),new BigDecimal("10"));
		assertEquals(promotionA.getDiscountPectForPriceCalculation(), new BigDecimal("0.9"));
		
		assertEquals(df.format(promotionA.getPromotionPrice()), 
				df.format(a.getUnitPrice()
					.multiply(new BigDecimal("2"))
					.multiply(promotionA.getDiscountPectForPriceCalculation())
				)); 
		//END: promotion set A
		
		
		//promotion set B
		BigDecimal itemAOriginalTotalPrice = a.getUnitPrice().multiply(new BigDecimal("2"));
		BigDecimal itemBOriginalTotalPrice = b.getUnitPrice().multiply(new BigDecimal("3"));
		BigDecimal itemABOriginalTotalPrice = itemAOriginalTotalPrice.add(itemBOriginalTotalPrice);
		BigDecimal itemABDiscountedTotalPrice = itemABOriginalTotalPrice.multiply(promotionB.getDiscountPectForPriceCalculation());
		
		assertEquals(df.format(promotionB.getPromotionPrice()), 
				df.format(itemABDiscountedTotalPrice)); 
		
		assertEquals(df.format(promotionB.getTotalSaved()),
				df.format(itemABOriginalTotalPrice.subtract(itemABDiscountedTotalPrice))
		); 
				
		//END: promotion set B
	}

}
