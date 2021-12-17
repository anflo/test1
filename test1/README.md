# Test promotion engine
## Objects
### Item
Item object contains SKU (assumed this is an ID), and unit price

### ItemDB
As a data set of items

### Promotion
Promotion object contains the start/end date time of the promotion (with TimeZone), which item(s) and its quantity will be required to fulfill this promotion, and the discount price / percentage of this promotion.

### CartPromotion
Object to calculate which promotion could be applied by providing a shopping cart. It would be easier and faster if a relational DB would be applied (one-to-many relationship of Promotion & PromotionItem). In fact, just query the PromotionItem by all item's id (name, in this case) and you will find out all the related Promotion already. However, there is no DB in time question.

This object stores several different kind of promotions as a DB. An algorithm has been applied on the selection to speed up if there are thousands of promotions and hundreds of items in the cart, which would would be explained in later section.

### Cart
Shopping Cart object. There are 2 attributes represeting the selected items: <i>myCart</i>, and <i>promotionGroup</i>.
- <i>myCart</i>: it contains all the non-promoted items, which mean it's on regular unit price.
- <i>promotionGroup</i>: it contains all the promotion that eligable and had been put in this cart.

For example, the customer selected 3 units of item A, and there is a bundle discount on 10% off if buying 2 units of item A. The quantity of item A in <i>myCart</i> would be 1; while the <i>promotionGroup</i> stores a promotion offer of buying 2 units of item A.

## Eligable Promotion Algorithm in CartPromtion
Imagine if there are thousands promotions and hundreds of selected item in the cart, using a typical horizontal loop, a loop on each promotion and then check if the cart fulfill all the required item and quantity, would be extremely slow. To speed up, we use vertical loop. For exmaple, below are some of the promotions:
<table>
<tr>
<th>Name</th>
<th>Item A</th>
<th>Item B</th>
<th>Item C</th>
<th>Discount</th>
</tr>
<tr>
<td>Promo-A</td>
<td>2 units</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>10% off</td>
</tr>
<tr>
<td>Promo-B</td>
<td>2 units</td>
<td>3 units</td>
<td>&nbsp;</td>
<td>15% off</td>
</tr>
<tr>
<td>Promo-C</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>3 units</td>
<td>5% off</td>
</tr>
<tr>
<td>Promo-D</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>5 units</td>
<td>7% off</td>
</tr>
<tr>
<td>Promo-E</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>7 units</td>
<td>10% off</td>
</tr>
<tr>
<td>Promo-F</td>
<td>3 units</td>
<td>2 units</td>
<td>1 unit</td>
<td>30% off</td>
</tr>
</table>
Case 1: The cart contains 3 units of Item A.
+ 1st round loop:
Checking with each promotion's first required item and its quantity, which are 2A, 2A, 3C, 5C, 7C and 3A. We keep Promo-A, B, & F for the next round. Also, it saves the max number of required individual items in this round in order to see how many times of the loop should be. In this case, it is 3 (there are 3 individual items required in Promo-F).
+ 2nd round loop:
Checking with the promotion kept from last round by using its 2nd required item and its quantity (if any), We keep Promo-A (since there is no more requirement) only and ignore B & F. And the loop.
+ 3rd round loop:
The loop keeps Promo-A and ends here.

It founds only Promo-A is eligable in this cart.

Case 2: The cart contains 3 units of A, 2 units of B, and 5 units of C.
+ 1st round loop:
It keeps Promo-A, B, C, D, & F. The max times of the loop should be 3 (Promo-F).
+ 2nd round loop:
It keeps Promo-A, C, D & F. The Promo-B is taken out since it requires 3 units of B, and the cart has 2 units only.
+ 3rd round loop:
It keeps Promo-A, C, D & F as all of them are fulfilled, and the loop ends.

### Priority of the Promotion
There are 2 ways to prioritise the promotion: apply first on the highest total saved money, and apply first on the highest saved percentage from the original price. Although both are implemented, the current engine is using the first one as to maxmize customer's savings. The above algorithm will returns a priortised eligable promotion list.

## Apply promotion to the cart
By calling Cart.applyPromotion(), it starts retrieve which promotion could be used in this cart by calling the above algorithm. Afterward, the engine will try applying the promotion and see if it works.

Although the question stated the promtions are mutually exclusive, the sample Scenarios are applying different promotion at the same time. I would assume the [mutually exclsuive] represents each unit of the item is only availble to 1 promotion only. For example, there are 3 units of item A, and buying 2 units of item A could have a 10% off discount. The promotion could only be applied once (1st+2nd unit), but not twice (1st+2nd & 2nd+3rd unit). It makes more sense to the [mutually exclusive] means.

### Usage
Check with the 5th unit test - <i>UnitTest5_Cart_Promotion_Process.java</i> - would find the usage of the engine and how it works. There is a complex case simulating a real-time shopping cart movement of a customer.