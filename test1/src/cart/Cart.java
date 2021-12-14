package cart;
import java.util.ArrayList;
import java.util.List;

import item.Item;

public class Cart {

	//0=Item, 1=quantity
	private List<ArrayList<Object>> myCart;
	private static int cartItemItemIndex = 0;
	private static int cartItemQtyIndex = 1;
	
	
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

	
	
}

