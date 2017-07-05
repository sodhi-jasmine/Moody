package com.jasminesodhi.moody;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import org.json.JSONObject;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by jasminesodhi on 14/06/17.
 */

public class SingleColorFragment extends Fragment {

    Button colorIndicator_1, colorIndicator_2;
    FancyButton colorSelector;

    String red1_single, green1_single, blue1_single, red2_single, green2_single, blue2_single;
    String url;

    RequestQueue queue;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_single_color, container, false);

        queue = Volley.newRequestQueue(getActivity());

        red1_single = green1_single = blue1_single = "1";
        red2_single = green2_single = blue2_single ="1";

        colorSelector = (FancyButton) view.findViewById(R.id.color_button);

        colorIndicator_1 = (Button) view.findViewById(R.id.color_indicator_1);
        colorIndicator_2 = (Button) view.findViewById(R.id.color_indicator_2);

        colorSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    ColorPickerDialogBuilder
                            .with(getActivity())
                            .lightnessSliderOnly()
                            .setTitle("Choose color")
                            .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                            .setOnColorSelectedListener(new OnColorSelectedListener() {

                                @Override
                                public void onColorSelected(int selectedColor) {
                                    //Toast.makeText(getActivity(), "onColorSelected: 0x" + Integer.toHexString(selectedColor), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setPositiveButton("ok", new ColorPickerClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                    changeLampColor(selectedColor);
                                }

                                private void changeLampColor(final int selectedColor) {
                                    red1_single = red2_single = Integer.toString(Color.red(selectedColor));
                                    green1_single = green2_single = Integer.toString(Color.green(selectedColor));
                                    blue1_single = blue2_single =Integer.toString(Color.blue(selectedColor));

                                    url = "" + red1_single + "&g1=" +
                                            green1_single + "&b1=" + blue1_single + "&r2=" + red2_single + "&g2=" + green2_single + "&b2=" + blue2_single;


                                    final JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {

                                            colorIndicator_1.setBackgroundColor(selectedColor);
                                            colorIndicator_2.setBackgroundColor(selectedColor);

                                            Toast.makeText(getActivity(), "The lamp has been set", Toast.LENGTH_SHORT).show();
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
                            })
                            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .build()
                            .show();
                }
        });

        return view;
    }
}

