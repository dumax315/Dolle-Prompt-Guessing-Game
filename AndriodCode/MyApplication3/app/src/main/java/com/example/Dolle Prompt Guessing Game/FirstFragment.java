package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.databinding.FragmentFirstBinding;



public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    TextView showCountTextView;

    EditText usernameBox;



    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View fragmentFirstLayout = inflater.inflate(R.layout.fragment_first, container, false);
        // Get the count text view
        showCountTextView = fragmentFirstLayout.findViewById(R.id.usern_text);

        String s = ((com.example.myapplication.MainActivity) this.getActivity()).getuserName();

        showCountTextView.setText(s);

        usernameBox = fragmentFirstLayout.findViewById(R.id.username_textbox);

        return fragmentFirstLayout;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.next_challenge_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(showCountTextView.getText() == "username" ){
                    Toast myToast = Toast.makeText(getActivity(), "Please set a username", Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
        //try catching errer
        view.findViewById(R.id.set_username).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((com.example.myapplication.MainActivity) getActivity()).setuserName(usernameBox.getText().toString());

                String s = ((com.example.myapplication.MainActivity) getActivity()).getuserName();

                showCountTextView.setText(s);
                RequestQueue ExampleRequestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                String url = "https://fastapi-template.theohal.repl.co/create-user/"+s+"?points=10";
                StringRequest ExampleStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        showCountTextView.setText(response.substring(0,500));

                        //This code is executed if the server responds, whether or not the response contains data.
                    }
                }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //This code is executed if there is an error.
                    }
                });

                ExampleRequestQueue.add(ExampleStringRequest);
            }

        });


//
//        view.findViewById(R.id.random_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int currentCount = Integer.parseInt(showCountTextView.getText().toString());
//                FirstFragmentDirections.ActionFirstFragmentToSecondFragment action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(currentCount);
//                NavHostFragment.findNavController(FirstFragment.this).navigate(action);
//            }
//        });
//
//        view.findViewById(R.id.toast_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast myToast = Toast.makeText(getActivity(), "Hello toast!", Toast.LENGTH_SHORT);
//                myToast.show();
//            }
//        });
//
//        view.findViewById(R.id.count_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                countMe(view);
//
//            }
//        });



    }

    private void countMe(View view) {
        // Get the value of the text view
        String countString = showCountTextView.getText().toString();
        // Convert value to a number and increment it
        Integer count = Integer.parseInt(countString);
        count++;
        // Display the new value in the text view.
        showCountTextView.setText(count.toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}