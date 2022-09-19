package sixsalman99.salesrecord;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains methods that get info from PHP files stored on server
 */
public class DBHelper {
    final static String DIRECTORY = "http://thecloset.myartsonline.com/SalesRecord/";

    static String recordId;

    static Transaction cart = new Transaction();

    /**
     * Gets details of all items corresponding with the 'recordId' stored in class and uses them to
     * create Item objects
     * @param context context of the application
     * @return a list of items
     */
    static List<Item> getItems(Context context) {
        List<Item> items = new ArrayList<>();

        StringRequest request = new StringRequest(Request.Method.GET, DIRECTORY +
                "GetItems.php?record_id=" + recordId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        items.add(new Item(object.getString("name"),
                                object.getDouble("price")));
                    }
                } catch (Exception e) {
                    Toast.makeText(context, "An error occurred.", Toast.LENGTH_SHORT).show();
                }

                ((TabbedActivity) context).itemsFragment.itemsLoaded();
            }
        }, null);

        Volley.newRequestQueue(context).add(request);

        return items;
    }

    /**
     * Gets details of all past transactions corresponding with the 'recordId' stored in class and
     * uses them to create TransactionItem and Transaction objects objects
     * @param context context of the application
     * @return a list of transactions
     */
    static List<Transaction> getHistory(Context context) {
        List<Transaction> history = new ArrayList<>();

        StringRequest request = new StringRequest(Request.Method.GET, DIRECTORY +
                "GetHistory.php?record_id=" + recordId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray transactionsArray = new JSONArray(response);

                    for (int i = 0; i < transactionsArray.length(); i++) {
                        JSONObject transactionObject = transactionsArray.getJSONObject(i);

                        Transaction transaction = new Transaction();

                        transaction.dateTime = transactionObject.getString("date_time");

                        transaction.total = transactionObject.getDouble("total");

                        JSONArray transactionItemsArray = transactionObject
                                .getJSONArray("transaction_items");

                        for (int j = 0; j < transactionItemsArray.length(); j++) {
                            JSONObject transactionItemObject = transactionItemsArray
                                    .getJSONObject(j);

                            transaction.transactionItems.add(new TransactionItem(new
                                    Item(transactionItemObject.getString("name"),
                                    transactionItemObject.getDouble("price")),
                                    transactionItemObject.getInt("quantity")));
                        }

                        history.add(transaction);
                    }
                } catch (Exception e) {
                    Toast.makeText(context, "An error occurred.", Toast.LENGTH_SHORT).show();
                }

                ((TabbedActivity) context).historyFragment.historyLoaded();
            }
        }, null);

        Volley.newRequestQueue(context).add(request);

        return history;
    }

    /**
     * Adds received 'item' to the database associating it with the the 'recordId' stored in class
     * @param context context of the application
     * @param addDialog instance of AddDialog that called this method
     * @param item the item to add
     */
    static void addItem(Context context, AddDialog addDialog, Item item) {
        StringRequest request = new StringRequest(Request.Method.GET, DIRECTORY +
                "AddItem.php?record_id=" + recordId + "&name=" + item.name + "&price=" + item.price,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        addDialog.itemAdded();
                    }
                }, null);

        Volley.newRequestQueue(context).add(request);
    }

    /**
     * Adds and empties current contents of 'cart' variable of class to the database associating it
     * with the 'recordId' stored in class
     * @param context context of the application
     */
    static void addHistoryEntry(Context context) {
        cart.updateDateTime();

        if (cart.transactionItems.size() > 0) {
            StringRequest request = new StringRequest(Request.Method.GET, DIRECTORY +
                    "AddHistoryEntry.php?date_time=" + cart.dateTime + "&total=" + cart.total,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            int entryId = Integer.parseInt(response);

                            for (int i = 0; i < cart.transactionItems.size(); i++) {
                                addHistoryDetail(context, entryId, cart.transactionItems.get(i));
                            }

                            cart = new Transaction();

                            ((TabbedActivity) context).cartFragment.historyEntryAdded();
                        }
                    }, null);

            Volley.newRequestQueue(context).add(request);
        }
    }

    /**
     * Helps 'addHistoryEntry()' method in adding individual transaction items to database
     * @param context context of the application
     * @param entryId id of the transaction to which this transaction item should be associated
     * @param transactionItem the transaction item to add
     */
    static void addHistoryDetail(Context context, int entryId, TransactionItem transactionItem) {
        StringRequest request = new StringRequest(Request.Method.GET, DIRECTORY +
                "AddHistoryDetail.php?record_id=" + recordId + "&name=" + transactionItem.item.name
                + "&price=" + transactionItem.item.price + "&quantity=" + transactionItem.quantity
                + "&entry_id=" + entryId, null, null);

        Volley.newRequestQueue(context).add(request);
    }
}