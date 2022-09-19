package sixsalman99.salesrecord;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

/**
 * This fragment gets items and adds them to its recycler view along with add to cart buttons
 */
public class ItemsFragment extends Fragment {
    private RecyclerView mRecyclerView;

    private List<Item> mItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items, container, false);

        mRecyclerView = view.findViewById(R.id.recycler_view_items);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.fragment_items, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_item) {
            new AddDialog(this).show(getActivity().getSupportFragmentManager(),
                    "AddDialog");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();

        updateItems();
    }

    /**
     * Gets latest list of items from database
     */
    void updateItems() {
        mItems = DBHelper.getItems(getActivity());
    }

    /**
     * Excepts to be called to by DBHelper when items loaded from database are ready to use
     */
    void itemsLoaded() {
        mRecyclerView.setAdapter(new Adapter());
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getActivity()).inflate(
                    R.layout.items_entry, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.textViewName.setText(mItems.get(position).name);
            holder.textViewPrice.setText(String.format(Locale.US, "%.2f", mItems
                    .get(position).price));

            holder.item = mItems.get(position);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewPrice;
        private ImageButton mButtonAdd;

        Item item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_items_name);
            textViewPrice = itemView.findViewById(R.id.text_view_items_price);
            mButtonAdd = itemView.findViewById(R.id.image_button_add);

            mButtonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DBHelper.cart.addItem(item);
                }
            });
        }
    }
}