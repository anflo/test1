package item.promotion;

public enum DiscountMode {

	NoDiscount(-1),
	DiscountPect(0),
	DiscountPrice(1);
	public final int discountType;
	
	private DiscountMode(int value) {
		this.discountType = value;
	}
}
