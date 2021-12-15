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
	
	@BeforeEach
	void setUp() throws Exception {
		this.a = new Item("A", new BigDecimal("50"));
		this.b = new Item("B", new BigDecimal("30"));
		this.c = new Item("C", new BigDecimal("20"));
		this.d = new Item("D", new BigDecimal("15"));
		
		
		//promotion set A
		Calendar promotionAStartDateTime = Calendar.getInstance(timeZone);
		promotionAStartDateTime.set(2021, 11, 20, 4, 0); //2021-dec-20 4:00am
		Calendar promotionAEndDateTime = Calendar.getInstance(timeZone);
		promotionAEndDateTime.set(2022, 0, 1, 4, 00); //2022-jan-1 4:00am
		
		List<ArrayList<Object>> promotionAItemCombination = new ArrayList<ArrayList<Object>>();
		ArrayList tempAList = new ArrayList();
		tempAList.add(a);
		tempAList.add(2); //2 items for A
		promotionAItemCombination.add(tempAList);
		
		int promotionADiscountType = 0; //pect
		promotionADiscountPect = new BigDecimal("10"); //10% off
		
		promotionA = new Promotion(promotionAStartDateTime, promotionAEndDateTime, promotionAItemCombination, promotionADiscountType, promotionADiscountPect, null);
		//END: promotion set A
		
	}

	@Test
	void test() {
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
		
		
		assertEquals(df.format(promotionA.getPromotionPrice()), 
				df.format(a.getUnitPrice()
					.multiply(new BigDecimal("2"))
					.multiply(promotionADiscountPect)
					.divide((new BigDecimal("100")
				)))); 
	}

}
