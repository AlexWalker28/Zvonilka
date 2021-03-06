package kg.kloop.android.zvonilka.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import kg.kloop.android.zvonilka.objects.Campaign;
import kg.kloop.android.zvonilka.R;

public class CreateCampaignActivity extends AppCompatActivity {

    EditText titleEditText;
    EditText descriptionEditText;
    String title;
    String description;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_campaign);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        titleEditText = (EditText)findViewById(R.id.title_edit_text);
        descriptionEditText = (EditText)findViewById(R.id.description_edit_text);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Companies").child("TestCompany").child("Campaigns");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_campaign_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.done_item:
                title = titleEditText.getText().toString();
                description = descriptionEditText.getText().toString();
                String id = databaseReference.push().getKey();
                Campaign campaign = new Campaign(id, title, description);
                databaseReference.child(id).setValue(campaign);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
