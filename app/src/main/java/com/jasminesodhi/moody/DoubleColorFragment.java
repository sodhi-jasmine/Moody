package com.jasminesodhi.moody;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class DoubleColorFragment extends Fragment {

    Button colorIndicator_1, colorIndicator_2;
    FancyButton colorSelector_1, colorSelector_2, okay;

    String red1_double, green1_double, blue1_double, red2_double, green2_double, blue2_double;
    String url;

    RequestQueue queue;

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_double_color, container, false);

        colorSelector_1 = (FancyButton) view.findViewById(R.id.color_button_1);
        colorSelector_2 = (FancyButton) view.findViewById(R.id.color_button_2);

        colorIndicator_1 = (Button) view.findViewById(R.id.color_indicator_1);
        colorIndicator_2 = (Button) view.findViewById(R.id.color_indicator_2);

        okay = (FancyButton) view.findViewById(R.id.okay);

        red1_double = green1_double = blue1_double = "1";
        red2_double = green2_double = blue2_double="1";

        queue = Volley.newRequestQueue(getActivity());

        colorSelector_1.setOnClickListener(new View.OnClickListener() {
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
                                        setFirstColor(selectedColor);
                            }

                            private void setFirstColor(int selectedColor) {

                                red1_double = Integer.toString(Color.red(selectedColor));
                                green1_double = Integer.toString(Color.green(selectedColor));
                                blue1_double = Integer.toString(Color.blue(selectedColor));

                                colorIndicator_1.setBackgroundColor(selectedColor);

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

        colorSelector_2.setOnClickListener(new View.OnClickListener() {
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
                                        setSecondColor(selectedColor);
                            }

                            private void setSecondColor(int selectedColor) {

                                red2_double = Integer.toString(Color.red(selectedColor));
                                green2_double = Integer.toString(Color.green(selectedColor));
                                blue2_double = Integer.toString(Color.blue(selectedColor));

                                colorIndicator_2.setBackgroundColor(selectedColor);

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

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "" + red1_double + "&g1=" +
                        green1_double + "&b1=" + blue1_double + "&r2=" + red2_double + "&g2=" + green2_double + "&b2=" + blue2_double;

                JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getActivity(),"The lamp has been set", Toast.LENGTH_SHORT).show();
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
