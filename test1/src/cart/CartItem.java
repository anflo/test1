package cart;

import java.util.ArrayList;

import item.Item;

public class CartItem {

	private Item item;
	private int quantity;
	
	public void setItem(Item item) {
		this.item = item;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public Item getItem() {
		return item;
	}
	public int getQuantity() {
		return quantity;
	}
	public CartItem(Item item, int quantity) {
		super();
		this.item = item;
		this.quantity = quantity;
	}

	
	
	
}
