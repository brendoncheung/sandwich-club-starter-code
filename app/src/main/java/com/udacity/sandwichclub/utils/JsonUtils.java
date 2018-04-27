package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = new Sandwich();

        Log.d("JSON", json);

        JSONObject jsonObject = null;

        try {

            jsonObject = new JSONObject(json);

            JSONObject name = jsonObject.getJSONObject("name");
            JSONArray alsoKnowAs = name.getJSONArray("alsoKnownAs");
            JSONArray ingredients = jsonObject.getJSONArray("ingredients");

            sandwich.setMainName(name.getString("mainName"));
            sandwich.setAlsoKnownAs(toArray(alsoKnowAs));
            sandwich.setPlaceOfOrigin(jsonObject.getString("placeOfOrigin"));
            sandwich.setDescription(jsonObject.getString("description"));
            sandwich.setImage(jsonObject.getString("image"));
            sandwich.setIngredients(toArray(ingredients));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }

    private static List<String> toArray (JSONArray array) throws JSONException {

        List<String> list = new ArrayList<String>();

        for (int i = 0; i < array.length(); i++) {
            list.add(array.getString(i));
        }
        return list;
    }
}
