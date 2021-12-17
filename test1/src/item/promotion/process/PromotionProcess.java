package item.promotion.process;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cart.Cart;
import cart.CartPromotion;
import item.Item;
import item.promotion.Promotion;
import item.promotion.PromotionItem;

public class PromotionProcess {

	private Cart cart;
	public PromotionProcess(Cart cart) {
		super();
		this.cart = cart;
	}
	
	public Cart applyPromotion() {
		return applyPromotion(Calendar.getInstance());
	}
	
	public Cart applyPromotion(Calendar processDateCalendar) {
		ArrayList<Promotion> promotionGroup = (ArrayList<Promotion>)this.cart.getPromotionGroup();
		if(promotionGroup!=null && promotionGroup.size()>0) {
			this.cart.dismissAllAppliedPromotionGroup(); //make sure all promotion has been removed for re-calculation
		}
		
		CartPromotion cp = new CartPromotion();
		ArrayList<Promotion> pList = cp.getRelatedPromotions(this.cart, processDateCalendar);
		if(pList!=null) {
			for(int i=0; i<pList.size();) {
				Promotion p = pList.get(i);
				List<PromotionItem> pItemCombination = p.getPromotionItemCombination();
				boolean promotionCanStillApply = true;
				for(PromotionItem promotionItemNode: pItemCombination) {
					Item pItem = promotionItemNode.getItem();
					int pQty = promotionItemNode.getQuantity();
					int currentItemNonPromotedQty = this.cart.getItemQuanitiy(pItem);
					if (currentItemNonPromotedQty<pQty) {
						promotionCanStillApply = false;
						i++; //if the current promotion is not applicable, move to next promotion. Otherwise, keep applying
						break;
					}
				}
				
				if(promotionCanStillApply) {
					for(PromotionItem promotionItemNode: pItemCombination) {
						Item pItem = promotionItemNode.getItem();
						int pQty = promotionItemNode.getQuantity();
						int currentItemNonPromotedQty = this.cart.getItemQuanitiy(pItem);
						this.cart.updateItem(pItem, currentItemNonPromotedQty-pQty, false);
					}
					this.cart.addPromotion(p);
				}
			}
		}
		
		
		return this.cart;
	}
}