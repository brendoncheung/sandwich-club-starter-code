package com.udacity.sandwichclub;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.net.URI;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private ActivityDetailBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

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

        // Failed here

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(mBinding.imageIv);

        mBinding.descriptionTv.setText(sandwich.getDescription());

        // Can be refactored but cant figure out an efficient way to do it

        if (sandwich.getAlsoKnownAs().isEmpty()) {
            mBinding.alsoKnownTv.setTypeface(null, Typeface.ITALIC);
            mBinding.alsoKnownTv.setText(R.string.empty_asKnownAs);
            mBinding.alsoKnownTv.append("\n");
        } else {
            for (String s: sandwich.getAlsoKnownAs()) {
                mBinding.alsoKnownTv.append(s);
                mBinding.alsoKnownTv.append("\n");
            }
        }

        if (sandwich.getIngredients().isEmpty()) {
            mBinding.ingredientsTv.setTypeface(null, Typeface.ITALIC);
            mBinding.ingredientsTv.setText(R.string.empty_ingredients);
            mBinding.alsoKnownTv.append("\n");
        } else {
            for (String s: sandwich.getIngredients()) {
                mBinding.ingredientsTv.append(s);
                mBinding.ingredientsTv.append("\n");
            }
        }

        mBinding.originTv.setText(sandwich.getPlaceOfOrigin());




    }

}
