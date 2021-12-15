package cart;
import java.util.ArrayList;
import java.util.List;

import item.Item;
import item.promotion.Promotion;

public class Cart {

	//0=Item, 1=quantity
	private List<ArrayList<Object>> myCart; //each node, 0=item, 1=qty
	//myCart stores for non-promotion items only
	public final static int cartItemItemIndex = 0;
	public final static int cartItemQtyIndex = 1;
	public List<ArrayList<Object>> getMyCart(){
		return this.myCart;
	}
	
	
	private List<Promotion> promotionGroup = new ArrayList<Promotion>();
	public List<Promotion> getPromotionGroup() {
		return promotionGroup;
	}
	public void setPromotionGroup(List<Promotion> promotionGroup) {
		this.promotionGroup = promotionGroup;
	}
	
	public void updateItem(Item newItem, int newQuantity) {
		if(promotionGroup!=null && promotionGroup.size()>0) {
			dismissAllAppliedPromotionGroup();
		}
		
		if (this.myCart==null || this.myCart.size()<=0) {
			myCart = new ArrayList<ArrayList<Object>>();
			addItemAtTail(newItem, newQuantity);
			
		} else {
			for(int i=0; i<this.myCart.size(); i++) {
				ArrayList tempItemNode = (ArrayList)this.myCart.get(i);
				Item tempItem = (Item)tempItemNode.get(cartItemItemIndex);
				int tempItemQty = (int)tempItemNode.get(cartItemQtyIndex);
				if (tempItem.getSku().equals(newItem.getSku())) {
					if (newQuantity<=0) {
						this.myCart.remove(i);
						//TO-DO 
					} else {
						tempItemNode.set(cartItemQtyIndex, newQuantity);
						this.myCart.set(i, tempItemNode);
					}
					return;
				}
			}
			
			//loop all existing item and cant find the new one, insert at the end
			addItemAtTail(newItem, newQuantity);
		}
	}
	private void addItemAtTail(Item newItem, int newQuantity) {
		ArrayList newItemNode = new ArrayList<Object>();
		newItemNode.add(newItem);
		newItemNode.add(newQuantity);
		myCart.add(newItemNode);
	}

	public int getItemQuanitiy(Item newItem) {
		if (this.myCart!=null && this.myCart.size()>0) {
			for(int i=0; i<this.myCart.size(); i++) {
				ArrayList tempItemNode = (ArrayList)this.myCart.get(i);
				Item tempItem = (Item)tempItemNode.get(cartItemItemIndex);
				if (tempItem.getSku().equals(newItem.getSku())) {
					return (int)tempItemNode.get(cartItemQtyIndex);
				}
			}
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
				List<ArrayList<Object>> promotionCombination = p.getPromotionItemCombination();
				if (promotionCombination!=null) {
					for(ArrayList<Object> promotionItemNode: promotionCombination) {
						Item tempItem = (Item)promotionItemNode.get(0);
						int tempQty = (int)promotionItemNode.get(1);
						int currentNonPromotedItemInCart = getNonPromotedItemQty(tempItem);
						int newQty = currentNonPromotedItemInCart+tempQty;
						updateItem(tempItem, newQty);
					}
				}
			}
		}
	}
	
	//mainly for dismissAllAppliedPromotionGroup(). 
	public int getNonPromotedItemQty(Item item) {
		if (this.myCart.size()>0) {
			for(int i=0; i<this.myCart.size(); i++) {
				ArrayList tempItemNode = (ArrayList)this.myCart.get(i);
				Item tempItem = (Item)tempItemNode.get(cartItemItemIndex);
				int tempItemQty = (int)tempItemNode.get(cartItemQtyIndex);
				if (tempItem.getSku().equals(item.getSku())) {
					return tempItemQty;
				}
			}
		}
		return 0;
	}

	public int getPromotedItemQty(Item item) {
		int totalQty = 0;
		if(promotionGroup!=null) {
			List<Promotion> tempPromotionGroup = this.promotionGroup;
			
			for(Promotion p: tempPromotionGroup) {
				List<ArrayList<Object>> promotionCombination = p.getPromotionItemCombination();
				if (promotionCombination!=null) {
					for(ArrayList<Object> promotionItemNode: promotionCombination) {
						Item tempItem = (Item)promotionItemNode.get(0);
						int tempQty = (int)promotionItemNode.get(1);
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
		if(promotionGroup!=null && promotionGroup.size()>0) {
			dismissAllAppliedPromotionGroup(); //make sure all promotion has been removed
		}
		
		
		
	}
	
	@Override
	public String toString() {
		return "Cart [myCart=" + myCart + ", promotionGroup=" + promotionGroup + "]";
	}
	public Cart() {
		this(new ArrayList<ArrayList<Object>>(), new ArrayList<Promotion>());
	}
	public Cart(List<ArrayList<Object>> myCart, List<Promotion> promotionGroup) {
		super();
		this.myCart = myCart;
		this.promotionGroup = promotionGroup;
	}

}

