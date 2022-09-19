package sixsalman99.salesrecord;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

/**
 * This dialog shows details including items, quantities, total and date and time of the transaction
 * received by constructor
 */
public class HistoryDetailsDialog extends DialogFragment {
    private RecyclerView mRecyclerView;
    private TextView mTextViewTotal;
    private TextView mTextViewTime;

    Transaction mTransaction;

    public HistoryDetailsDialog(Transaction transaction) {
        mTransaction = transaction;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View dialog = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_history_details,
                null);

        mRecyclerView = dialog.findViewById(R.id.recycler_view_history_details);
        mTextViewTotal = dialog.findViewById(R.id.text_view_history_details_total);
        mTextViewTime = dialog.findViewById(R.id.text_view_history_details_time);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new TransactionsAdapter(getActivity(), mTransaction));

        mTextViewTotal.setText(String.format(Locale.US, "%.2f", mTransaction.total));

        mTextViewTime.setText(mTransaction.dateTime);

        return new AlertDialog.Builder(getActivity()).setView(dialog).create();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        ((TabbedActivity) getActivity()).historyFragment.updateHistory();
    }
}