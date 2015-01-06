package info.androidhive.projetandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SingleContactActivity  extends Activity {

    // JSON node keys
    private static final String TAG_NAME = "name";
    private static final String TAG_CATEGORY = "category";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_COUNTRY_NAME = "name";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_contact);

        // getting intent data
        Intent in = getIntent();

        // Get JSON values from previous intent
        String name = in.getStringExtra(TAG_NAME);
        String category = in.getStringExtra(TAG_CATEGORY);
        String description = in.getStringExtra(TAG_DESCRIPTION);
        String country = in.getStringExtra(TAG_COUNTRY_NAME);
        // Displaying all values on the screen
        TextView lblName = (TextView) findViewById(R.id.name_label);
        TextView lblCategory = (TextView) findViewById(R.id.category_label);
        TextView lblDescription = (TextView) findViewById(R.id.description_label);
        TextView lblCountry = (TextView) findViewById(R.id.country_label);

        lblName.setText(name);
        lblCategory.setText(category);
        lblDescription.setText(description);
        lblCountry.setText(country);
    }
}
