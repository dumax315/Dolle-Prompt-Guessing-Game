package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SecondFragment extends Fragment {

//    private FragmentSecondBinding binding;

    ImageView dalleImage;

    JSONArray words;
    JSONArray correctWords;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View fragmentSecondLayout = inflater.inflate(R.layout.fragment_second, container, false);
        // Get the count text view
        dalleImage = fragmentSecondLayout.findViewById(R.id.imageView);

        return fragmentSecondLayout;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("api get image res","here it should come");
        RequestQueue ExampleRequestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "https://FastAPI-Template.theohal.repl.co/get-image";
        StringRequest ExampleStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    Log.d("api get image res",obj.toString());
                    new ImageLoadTask(obj.getString("url"), dalleImage).execute();
                    words = obj.getJSONArray("allWords");
                    correctWords = obj.getJSONArray("usedWords");
                    int[] buttons = {R.id.toggleButton0,R.id.toggleButton1,R.id.toggleButton2,R.id.toggleButton3,R.id.toggleButton4,R.id.toggleButton5,R.id.toggleButton6,R.id.toggleButton7,R.id.toggleButton8,R.id.toggleButton9};
                    for (int i = 0; i < words.length(); i++) {
                        String word = words.getString(i);
                        ToggleButton toggleButton = view.findViewById(buttons[i]);
                        toggleButton.setText(word);
                        // Sets the text for when the button is first created.

                        toggleButton.setTextOff(word);
                        // Sets the text for when the button is not in the checked state.

                        toggleButton.setTextOn(word);
                        // Sets the text for when the button is in the checked state.
                        toggleButton.setChecked(false);
                    }
                }catch (JSONException e) {
                    Log.e("api get image res error",e.toString());
                    //some exception handler code.
                }


                //This code is executed if the server responds, whether or not the response contains data.
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("api get image res error",error.toString());
                //This code is executed if there is an error.
            }
        });

        ExampleRequestQueue.add(ExampleStringRequest);

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String s = ((com.example.myapplication.MainActivity) getActivity()).getuserName();

                int points = -2;

                int[] buttons = {R.id.toggleButton0,R.id.toggleButton1,R.id.toggleButton2,R.id.toggleButton3,R.id.toggleButton4,R.id.toggleButton5,R.id.toggleButton6,R.id.toggleButton7,R.id.toggleButton8,R.id.toggleButton9};
                try {
                    for (int i = 0; i < words.length(); i++) {
                        String word = words.getString(i);
                        ToggleButton toggleButton = getActivity().findViewById(buttons[i]);
//                        if (toggleButton.isChecked() == hasValue(correctWords, word)) {
//                            points += 1;
//                        } else {
//                            points -= 2;
//                        }
                        if (toggleButton.isChecked() && hasValue(correctWords, word)) {
                            points += 1;
                        }

                    }
                }catch (JSONException e) {
                    Log.e("api get image res error",e.toString());
                    //some exception handler code.
                }

                RequestQueue ExampleRequestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                String url = "https://fastapi-template.theohal.repl.co/update-user/"+s+"?points="+String.valueOf(points);
                StringRequest ExampleStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ((com.example.myapplication.MainActivity) getActivity()).setuserPoints(response);
                        Log.d("points:",response);
                        String[] wordsToSend = new String[10];
                        try{
                            for (int i = 0; i < words.length(); i++) {
                                String word = words.getString(i);
                                wordsToSend[i] = word;
                            }
                        }catch (JSONException e) {
                            Log.e("api get image res error",e.toString());
                            //some exception handler code.
                        }
                        String[] correctWordsToSend = new String[4];
                        try{
                            for (int i = 0; i < correctWords.length(); i++) {
                                String word = correctWords.getString(i);
                                correctWordsToSend[i] = word;
                            }
                        }catch (JSONException e) {
                            Log.e("api get image res error",e.toString());
                            //some exception handler code.
                        }
                        String[][] arrayToSend = {correctWordsToSend,wordsToSend};
                        ((com.example.myapplication.MainActivity) getActivity()).setwordsList(arrayToSend);
                        NavHostFragment.findNavController(SecondFragment.this)
                                .navigate(R.id.action_SecondFragment_to_fragmentContinue);

                        //This code is executed if the server responds, whether or not the response contains data.
                    }
                }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("didn't happer: ",error.toString());
                        //This code is executed if there is an error.
                    }
                });
                ExampleRequestQueue.add(ExampleStringRequest);

            }
        });
//
//        Integer count = SecondFragmentArgs.fromBundle(getArguments()).getMyArg();
//        String countText = getString(R.string.hello_second_fragment, count);
//        TextView headerView = view.getRootView().findViewById(R.id.textview_header);
//        headerView.setText(countText);

//        Random random = new java.util.Random();
//        Integer randomNumber = 0;
//        if (59 > 0) {
//            randomNumber = random.nextInt(14 + 1);
//        }
//
//        TextView randomView = view.getRootView().findViewById(R.id.textview_header);
//        randomView.setText(randomNumber.toString());
//
//        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(SecondFragment.this)
//                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
//            }
//        });
    }


    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }

    }

    public boolean hasValue(JSONArray json, String key) {
        try {
            for (int i = 0; i < json.length(); i++) {  // iterate through the JsonArray
                // first I get the 'i' JsonElement as a JsonObject, then I get the key as a string and I compare it with the value
                if (json.getString(i).equals(key)) return true;
            }
        }catch (JSONException e) {
            Log.e("api get image res error",e.toString());

            //some exception handler code.
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        binding = null;
    }

}