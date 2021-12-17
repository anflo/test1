package item.promotion;

import item.Item;

public class PromotionItem {
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
	public PromotionItem(Item item, int quantity) {
		super();
		this.item = item;
		this.quantity = quantity;
	}
}
