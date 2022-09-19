package sixsalman99.salesrecord;

/**
 * Stores an item along with its quantity
 */
public class TransactionItem {
    Item item;
    int quantity;

    public TransactionItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }
}