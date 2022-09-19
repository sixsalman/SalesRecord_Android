package sixsalman99.salesrecord;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Objects of this class store items, total and date and time of a transaction
 */
public class Transaction {
    List<TransactionItem> transactionItems;
    double total;
    String dateTime;

    public Transaction() {
        transactionItems = new ArrayList<>();

        total = 0;
    }

    /**
     * If a corresponding item already exists in 'transactionItems' stored in calling instance of
     * class, its quantity is incremented. Otherwise, a new transaction item with quantity 1 is
     * added to it.
     * @param item the item to add
     */
    void addItem(Item item) {
        boolean found = false;

        for (TransactionItem transactionItem : transactionItems) {
            if (transactionItem.item.equals(item)) {
                transactionItem.quantity++;

                found = true;
            }
        }

        if (!found)
            transactionItems.add(new TransactionItem(item, 1));

        total += item.price;
    }

    /**
     * Assigns current time to 'dateTime' variable of the calling instance of class
     */
    void updateDateTime() {
        dateTime = Calendar.getInstance().getTime().toString();
    }
}