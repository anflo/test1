package cart;
import java.util.ArrayList;
import java.util.List;

import item.Item;
import item.promotion.Promotion;

public class Cart {

	//0=Item, 1=quantity
	private List<ArrayList<Object>> myCart;
	private static int cartItemItemIndex = 0;
	private static int cartItemQtyIndex = 1;
	
	private List<Promotion> promotionGroup = new ArrayList<Promotion>();
	public List<Promotion> getPromotionGroup() {
		return promotionGroup;
	}
	public void setPromotionGroup(List<Promotion> promotionGroup) {
		this.promotionGroup = promotionGroup;
	}
	
	public void updateItem(Item newItem, int newQuantity) {
		if (this.myCart==null || this.myCart.size()<=0) {
			myCart = new ArrayList<ArrayList<Object>>();
			addItemAtTail(newItem, newQuantity);
			
		} else {
			for(int i=0; i<this.myCart.size(); i++) {
				ArrayList tempItemNode = (ArrayList)this.myCart.get(i);
				Item tempItem = (Item)tempItemNode.get(cartItemItemIndex);
				if (tempItem.getSku().equals(newItem.getSku())) {
					if (newQuantity<=0) {
						this.myCart.remove(i);
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

	public int getQuanitiyOfSKU(String inputSKU) {
		if (this.myCart!=null && this.myCart.size()>0) {
			for(int i=0; i<this.myCart.size(); i++) {
				ArrayList tempItemNode = (ArrayList)this.myCart.get(i);
				Item tempItem = (Item)tempItemNode.get(cartItemItemIndex);
				if (tempItem.getSku().equals(inputSKU)) {
					return (int)tempItemNode.get(cartItemQtyIndex);
				}
			}
		}
		
		return 0; //can choose to return -1 to represent item not existing, but this is not important yet 
	}

	
	@Override
	public String toString() {
		return "Cart [myCart=" + myCart + "]";
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

