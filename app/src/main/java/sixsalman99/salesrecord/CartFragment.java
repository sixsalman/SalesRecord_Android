package sixsalman99.salesrecord;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

/**
 * This fragment shows the items added to cart along with quantities, total and an option to save
 * current contents of cart
 */
public class CartFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private TextView mTextViewTotal;
    private Button mButtonCartSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        mRecyclerView = view.findViewById(R.id.recycler_view_cart);
        mTextViewTotal = view.findViewById(R.id.text_view_cart_total);
        mButtonCartSave = view.findViewById(R.id.button_cart_save);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mButtonCartSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper.addHistoryEntry(getActivity());
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        mRecyclerView.setAdapter(new TransactionsAdapter(getActivity(), DBHelper.cart));

        mTextViewTotal.setText(String.format(Locale.US, "%.2f", DBHelper.cart.total));
    }

    /**
     * Excepts to be called to by DBHelper when current contents of cart have been successfully
     * added to database and cart has been emptied
     */
    void historyEntryAdded() {
        mRecyclerView.setAdapter(new TransactionsAdapter(getActivity(), DBHelper.cart));

        mTextViewTotal.setText("0.00");
    }
}