package sixsalman99.salesrecord;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.Locale;

/**
 * This Dialog gets name and price of an item from user and adds it to database
 */
public class AddDialog extends DialogFragment {
    private EditText mEditTextItemName;
    private EditText mEditTextPrice;

    private ItemsFragment mItemsFragment;

    public AddDialog(ItemsFragment itemsFragment) {
        mItemsFragment = itemsFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View dialog = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add, null);

        mEditTextItemName = dialog.findViewById(R.id.edit_text_item_name);
        mEditTextPrice = dialog.findViewById(R.id.edit_text_price);

        return new AlertDialog.Builder(getActivity()).setView(dialog).setPositiveButton(
                "Add Item", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!mEditTextItemName.getText().toString().equals("") &&
                                !mEditTextPrice.getText().toString().equals("")) {
                            DBHelper.addItem(getActivity(), AddDialog.this,
                                    new Item(mEditTextItemName.getText().toString(),
                                            Double.parseDouble(String.format(Locale.US,
                                                    "%.2f", Double.parseDouble
                                                            (mEditTextPrice.getText()
                                                                    .toString())))));
                        }
                    }
                }).create();
    }

    /**
     * Excepts to be called to by DBHelper when an item has been successfully added to database
     */
    void itemAdded() {
        mItemsFragment.updateItems();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mItemsFragment.updateItems();
    }
}