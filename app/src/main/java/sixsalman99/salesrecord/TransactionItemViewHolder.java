package sixsalman99.salesrecord;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionItemViewHolder extends RecyclerView.ViewHolder {
    TextView textViewName;
    TextView textViewPrice;
    TextView textViewQuantity;
    TextView textViewItemTotal;

    public TransactionItemViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewName = itemView.findViewById(R.id.text_view_cart_name);
        textViewPrice = itemView.findViewById(R.id.text_view_cart_price);
        textViewQuantity = itemView.findViewById(R.id.text_view_cart_quantity);
        textViewItemTotal = itemView.findViewById(R.id.text_view_cart_item_total);
    }
}