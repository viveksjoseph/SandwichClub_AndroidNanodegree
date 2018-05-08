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

        String mainName;
        List<String> alsoKnownAs = null;
        String placeOfOrigin;
        String description;
        String image;
        List<String> ingredients = null;

        Sandwich sandwichData= null;

        try{
            JSONObject parsedData = new JSONObject(json);

            //parsing data inside name object
            JSONObject nameObject = parsedData.getJSONObject("name");

            mainName = nameObject.getString("mainName");

            JSONArray tempAkaArray = nameObject.getJSONArray("alsoKnownAs");
            alsoKnownAs = new ArrayList<>();  //initialising alsoKnownAs list
            for (int pos = 0; pos < tempAkaArray.length(); pos++) {
                alsoKnownAs.add(tempAkaArray.get(pos).toString());
            }

            placeOfOrigin = parsedData.getString("placeOfOrigin");
            description = parsedData.getString("description");
            image = parsedData.getString("image");

            ingredients = new ArrayList<>();
            JSONArray tempIngredientsArray = parsedData.getJSONArray("ingredients");
            for (int pos = 0; pos < tempIngredientsArray.length(); pos++) {
                ingredients.add(tempIngredientsArray.get(pos).toString());
            }

            //creating a new sandwich object with parsed data
            sandwichData = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        }catch (JSONException e){
            Log.d("JSONUtils", "Exception while parsing JSON : " + e.getMessage());
        }

        return sandwichData;
    }
}
