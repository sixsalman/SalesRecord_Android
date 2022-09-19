package sixsalman99.salesrecord;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

/**
 * This fragment gets past transactions and adds them to its recycler view
 */
public class HistoryFragment extends Fragment {
    private RecyclerView mRecyclerView;

    private List<Transaction> mHistory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        mRecyclerView = view.findViewById(R.id.recycler_view_history);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        updateHistory();
    }

    /**
     * Gets latest list of past transactions from database
     */
    void updateHistory() {
        mHistory = DBHelper.getHistory(getActivity());
    }

    /**
     * Excepts to be called to by DBHelper when past transactions loaded from database are ready to
     * use
     */
    void historyLoaded() {
        mRecyclerView.setAdapter(new Adapter());
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getActivity()).inflate(
                    R.layout.history_entry, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.textViewId.setText(mHistory.get(position).dateTime);
            holder.textViewTotal.setText(String.format(Locale.US, "%.2f",mHistory
                    .get(position).total));

            holder.transaction = mHistory.get(position);
        }

        @Override
        public int getItemCount() {
            return mHistory.size();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewId;
        TextView textViewTotal;

        Transaction transaction;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewId = itemView.findViewById(R.id.text_view_history_id);
            textViewTotal = itemView.findViewById(R.id.text_view_history_total);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new HistoryDetailsDialog(transaction).show(getActivity()
                                    .getSupportFragmentManager(), "HistoryDetailsDialog");
                }
            });
        }
    }
}