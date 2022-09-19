package sixsalman99.salesrecord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * This activity gets a record id from user and stores it in DBHelper
 */
public class LogInActivity extends AppCompatActivity {
    private EditText mEditTextRecordId;
    private Button mButtonRetrieve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mEditTextRecordId = findViewById(R.id.edit_text_record_id);
        mButtonRetrieve = findViewById(R.id.button_retrieve);

        mButtonRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mEditTextRecordId.getText().toString().equals("")) {
                    DBHelper.recordId = mEditTextRecordId.getText().toString();

                    startActivity(new Intent(LogInActivity.this,
                            TabbedActivity.class));
                }
            }
        });
    }
}