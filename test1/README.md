# Test promotion engine
## Objects
### Item
Item object contains SKU (assumed this is an ID), and unit price

### ItemDB
As a data set of items

### Promotion
Promotion object contains the start/end date time of the promotion (with TimeZone), which item(s) and its quantity will be required to fulfill this promotion, and the discount price / percentage of this promotion.

### CartPromotion
Object to calculate which promotion could be applied by providing a shopping cart. An algorithm has been applied on the selection to speed up if there are thousands of promotions and hundreds of items in the cart, which would would be explained in later section.

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
<td>Promotion-A</td>
<td>3 units</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>10% off</td>
</tr>
<tr>
<td>Promotion-B</td>
<td>2 units</td>
<td>3 units</td>
<td>&nbsp;</td>
<td>15% off</td>
</tr>
<tr>
<td>Promotion-C</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>3 units</td>
<td>5% off</td>
</tr>
<tr>
<td>Promotion-D</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>5 units</td>
<td>7% off</td>
</tr>
<tr>
<td>Promotion-E</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td>7 units</td>
<td>10% off</td>
</tr>
<tr>
<td>Promotion-E</td>
<td>3 units</td>
<td>2 units</td>
<td>1 unit</td>
<td>30% off</td>
</tr>
</table>



