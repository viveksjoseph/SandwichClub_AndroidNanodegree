package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final String NAME = "name";
    public static final String MAIN_NAME = "mainName";
    public static final String ALSO_KNOWN_AS = "alsoKnownAs";
    public static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    public static final String DESCRIPTION = "description";
    public static final String IMAGE = "image";
    public static final String INGREDIENTS = "ingredients";

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
            JSONObject nameObject = parsedData.getJSONObject(NAME);

            mainName = nameObject.getString(MAIN_NAME);

            JSONArray tempAkaArray = nameObject.getJSONArray(ALSO_KNOWN_AS);
            alsoKnownAs = new ArrayList<>();  //initialising alsoKnownAs list
            for (int pos = 0; pos < tempAkaArray.length(); pos++) {
                alsoKnownAs.add(tempAkaArray.get(pos).toString());
            }

            placeOfOrigin = parsedData.getString(PLACE_OF_ORIGIN);
            description = parsedData.getString(DESCRIPTION);
            image = parsedData.getString(IMAGE);

            ingredients = new ArrayList<>();
            JSONArray tempIngredientsArray = parsedData.getJSONArray(INGREDIENTS);
            for (int pos = 0; pos < tempIngredientsArray.length(); pos++) {
                ingredients.add(tempIngredientsArray.get(pos).toString());
            }

            //creating a new sandwich object with parsed data
            sandwichData = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch(JSONException e) {
            Log.d("JSONUtils", "Exception while parsing JSON : " + e.getMessage());
        }

        return sandwichData;
    }
}
