package com.jasminesodhi.moody;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Random;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by jasminesodhi on 14/06/17.
 */

public class Randomize extends Fragment {

    Button colorIndicator_1, colorIndicator_2;
    FancyButton randomize;

    String red1, green1, blue1;
    String red2, green2, blue2;

    String url;
    Random random;

    RequestQueue queue;

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_randomize, container, false);

        queue = Volley.newRequestQueue(getActivity());

        colorIndicator_1 = (Button) view.findViewById(R.id.color_indicator_1);
        colorIndicator_2 = (Button) view.findViewById(R.id.color_indicator_2);

        randomize = (FancyButton) view.findViewById(R.id.color_button);

        random = new Random();

        randomize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                red1 = Integer.toString(random.nextInt(255));
                green1 = Integer.toString(random.nextInt(255));
                blue1 = Integer.toString(random.nextInt(255));

                red2 = Integer.toString(random.nextInt(255));
                green2 = Integer.toString(random.nextInt(255));
                blue2 = Integer.toString(random.nextInt(255));

                url = "" + red1 + "&g1=" +
                        green1 + "&b1=" + blue1 + "&r2=" + red2 + "&g2=" + green2 + "&b2=" + blue2;

                JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        colorIndicator_1.setBackgroundColor(android.graphics.Color.rgb(Integer.decode(red1), Integer.decode(green1), Integer.decode(blue1)));
                        colorIndicator_2.setBackgroundColor(android.graphics.Color.rgb(Integer.decode(red2), Integer.decode(green2), Integer.decode(blue2)));
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Response", error.toString());
                            }
                        });
                queue.add(getRequest);
            }
        });

        return view;
    }
}
