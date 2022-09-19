package sixsalman99.salesrecord;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionItemViewHolder> {
    private Context mContext;
    private Transaction mTransaction;

    public TransactionsAdapter(Context context, Transaction transaction) {
        mContext = context;
        mTransaction = transaction;
    }

    @NonNull
    @Override
    public TransactionItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionItemViewHolder(LayoutInflater.from(mContext).inflate(
                R.layout.transaction_item_entry, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionItemViewHolder holder, int position) {
        holder.textViewName.setText(mTransaction.transactionItems.get(position).item.name);
        holder.textViewPrice.setText(String.format(Locale.US, "%.2f",mTransaction
                .transactionItems.get(position).item.price));
        holder.textViewQuantity.setText(String.format("x%s", mTransaction.transactionItems.
                get(position).quantity));
        holder.textViewItemTotal.setText(String.format(Locale.US, "%.2f", mTransaction
                .transactionItems.get(position).item.price * mTransaction.transactionItems
                .get(position).quantity));
    }

    @Override
    public int getItemCount() {
        return mTransaction.transactionItems.size();
    }
}
