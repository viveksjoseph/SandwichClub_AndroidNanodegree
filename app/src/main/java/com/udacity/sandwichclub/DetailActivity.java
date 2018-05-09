package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        //also known as
        TextView akaTv = (TextView) findViewById(R.id.also_known_tv);
        if (sandwich.getAlsoKnownAs().isEmpty()) {
            akaTv.setText(R.string.no_name);
        } else if (sandwich.getAlsoKnownAs().size() == 1) {
            akaTv.setText(sandwich.getAlsoKnownAs().get(0));
        } else {
            StringBuffer akaText = new StringBuffer();
            for (String eachString : sandwich.getAlsoKnownAs()) {
                akaText.append("> " + eachString + "\n");
            }
            akaTv.setText(akaText.substring(0, akaText.length() - 1));
        }

        //place of origin
        TextView originTv = (TextView) findViewById(R.id.origin_tv);
        if (sandwich.getPlaceOfOrigin().length() == 0) {
            originTv.setText(R.string.no_name);
        } else {
            originTv.setText(sandwich.getPlaceOfOrigin());
        }

        //description
        TextView descriptionTv = (TextView) findViewById(R.id.description_tv);
        descriptionTv.setText(sandwich.getDescription());

        //ingredients
        TextView ingredientsTv = (TextView) findViewById(R.id.ingredients_tv);
        if (sandwich.getIngredients().isEmpty()) {
            ingredientsTv.setText(R.string.no_name);
        } else if (sandwich.getIngredients().size() == 1) {
            ingredientsTv.setText(sandwich.getIngredients().get(0));
        } else {
            StringBuffer ingredientsText = new StringBuffer();
            for (String eachString : sandwich.getIngredients()) {
                ingredientsText.append("> " + eachString + "\n");
            }
            ingredientsTv.setText(ingredientsText.substring(0, ingredientsText.length() - 1));
        }
    }
}
