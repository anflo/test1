package cart;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import item.Item;
import item.promotion.Promotion;
import item.promotion.PromotionItem;

public class Cart {

	private HashMap<String, CartItem> myCart;
	public HashMap<String, CartItem> getMyCart() {
		return myCart;
	}
	
	private List<Promotion> promotionGroup = new ArrayList<Promotion>();
	public List<Promotion> getPromotionGroup() {
		return promotionGroup;
	}
	public void setPromotionGroup(List<Promotion> promotionGroup) {
		this.promotionGroup = promotionGroup;
	}
	
	public void updateItem(Item newItem, int newQuantity) {
		updateItem(newItem, newQuantity, true);
	}
	//shouldDismissAllAppliedPromotionGroup: should only be false when calling inside applyPromotion()
	// The rest of time should be true.
	// Reason: When you try to update the quantity of the cart, you should dismiss all promotions, change the qty, and then check again any new promotion could be applied for bigger save.
	// Therefore, it should always be true.
	// The reason of false is: applyPromotion() is used to move items from non-promoted item to promoted
	public void updateItem(Item newItem, int newQuantity, boolean shouldDismissAllAppliedPromotionGroup) {
		if(shouldDismissAllAppliedPromotionGroup /*&& promotionGroup!=null && promotionGroup.size()>0*/) {
			dismissAllAppliedPromotionGroup();
		}
		
		if (newItem!=null && newItem.getSku()!=null && newItem.getSku().length()>0 &&
			newQuantity>0
		) {
			CartItem cartItem = new CartItem(newItem, newQuantity);
			myCart.put(newItem.getSku(), cartItem);
		} else if (newQuantity<=0) {
			myCart.remove(newItem.getSku());
		}
	}

	//item non-promoted qty
	public int getItemQuanitiy(Item newItem) {
		if (newItem!=null && newItem.getSku()!=null && newItem.getSku().length()>0 &&
			myCart!=null && myCart.size()>0 && myCart.containsKey(newItem.getSku())
		) {
			CartItem cartItem = myCart.get(newItem.getSku());
			return cartItem.getQuantity();
		}
		
		return 0; //can choose to return -1 to represent item not existing, but this is not important yet 
	}

	//for every-qty-change on any of the item, the promotion should be re-calculated as a whole.
	// for example it could be a set of promotion like:
	// 1. 3A = 10% off
	// 2. 5A = 15% off
	// 3. 7A = 20% off
	// The change of its quantity might change of the promotion applied. 
	// Therefore, recalculate is the best
	private void dismissAllAppliedPromotionGroup() {
		if(promotionGroup!=null) {
			List<Promotion> tempPromotionGroup = this.promotionGroup;
			this.promotionGroup = new ArrayList<Promotion>(); //reset the promotion group to prevent deadloop when call updateItem() below 

			for(Promotion p: tempPromotionGroup) {
				List<PromotionItem> promotionCombination = p.getPromotionItemCombination();
				if (promotionCombination!=null) {
					for(PromotionItem promotionItemNode: promotionCombination) {
						Item tempItem = promotionItemNode.getItem();
						int tempQty = promotionItemNode.getQuantity();
						int currentNonPromotedItemInCart = getNonPromotedItemQty(tempItem);
						int newQty = currentNonPromotedItemInCart+tempQty;
						updateItem(tempItem, newQty, false); //prevent deadloop
					}
				}
			}
		}
	}
	
	//mainly for dismissAllAppliedPromotionGroup(). 
	public int getNonPromotedItemQty(Item item) {
		if (item!=null && item.getSku()!=null && item.getSku().length()>0 && 
			this.myCart.size()>0 && this.myCart.containsKey(item.getSku())
		) {
			CartItem tempItem = this.myCart.get(item.getSku());
			int tempItemQty = tempItem.getQuantity();
			return tempItemQty;
		}
		return 0;
	}

	public int getPromotedItemQty(Item item) {
		int totalQty = 0;
		if(promotionGroup!=null) {
			List<Promotion> tempPromotionGroup = this.promotionGroup;
			
			for(Promotion p: tempPromotionGroup) {
				List<PromotionItem> promotionCombination = p.getPromotionItemCombination();
				if (promotionCombination!=null) {
					for(PromotionItem promotionItemNode: promotionCombination) {
						Item tempItem = promotionItemNode.getItem();
						int tempQty = promotionItemNode.getQuantity();
						if (tempItem.getSku().equals(item.getSku())){
							totalQty += tempQty;
						}
					}
				}
			}
		}
		return totalQty;
	}
	
	//customer can directly add the promotion bundle into the cart
	public void addPromotionGroup(Promotion p) {
		promotionGroup.add(p);
	}

	//calculate promotion based on the cart
	public void applyPromotion() {
		applyPromotion(Calendar.getInstance());
	}
	public void applyPromotion(Calendar processDateCalendar) {
		if(promotionGroup!=null && promotionGroup.size()>0) {
			dismissAllAppliedPromotionGroup(); //make sure all promotion has been removed for re-calculation
		}
		
		CartPromotion cp = new CartPromotion();
		ArrayList<Promotion> pList = cp.getRelatedPromotions(this, processDateCalendar);
		if(pList!=null) {
			for(int i=0; i<pList.size();) {
				Promotion p = pList.get(i);
				List<PromotionItem> pItemCombination = p.getPromotionItemCombination();
				boolean promotionCanStillApply = true;
				for(PromotionItem promotionItemNode: pItemCombination) {
					Item pItem = promotionItemNode.getItem();
					int pQty = promotionItemNode.getQuantity();
					int currentItemNonPromotedQty = this.getItemQuanitiy(pItem);
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
						int currentItemNonPromotedQty = this.getItemQuanitiy(pItem);
						this.updateItem(pItem, currentItemNonPromotedQty-pQty, false);
					}
					this.promotionGroup.add(p);
				}
			}
		}
		
		
	}
	
	@Override
	public String toString() {
		return "Cart [myCart=" + myCart + ", promotionGroup=" + promotionGroup + "]";
	}
	public Cart() {
		this(new HashMap<String, CartItem>(), new ArrayList<Promotion>());
	}
	public Cart(HashMap<String, CartItem> myCart, List<Promotion> promotionGroup) {
		super();
		this.myCart = myCart;
		this.promotionGroup = promotionGroup;
	}
}


