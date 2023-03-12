package com.example.myapplication;

        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.fragment.app.Fragment;
        import androidx.navigation.fragment.NavHostFragment;

        import com.example.myapplication.databinding.FragmentFirstBinding;



public class FragmentContinue extends Fragment {

    private FragmentFirstBinding binding;

    TextView showCountTextView;

    TextView userPoints;
    TextView textView3;
    TextView textView5;



    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View fragmentContinueLayout = inflater.inflate(R.layout.fragment_continue, container, false);
        // Get the count text view
        showCountTextView = fragmentContinueLayout.findViewById(R.id.usern_text);
        userPoints = fragmentContinueLayout.findViewById(R.id.textView);

        String s = ((com.example.myapplication.MainActivity) this.getActivity()).getuserName();
        textView3 = fragmentContinueLayout.findViewById(R.id.textView3);
        textView5 = fragmentContinueLayout.findViewById(R.id.textView5);

        showCountTextView.setText(s);


        return fragmentContinueLayout;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String s = ((com.example.myapplication.MainActivity) getActivity()).getuserName();

        showCountTextView.setText(s);

        String p = ((com.example.myapplication.MainActivity) getActivity()).getuserPoints();
        userPoints.setText(p);


        view.findViewById(R.id.next_challenge_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FragmentContinue.this)
                        .navigate(R.id.action_fragmentContinue_to_SecondFragment);
            }
        });

        String[][] wordList = ((com.example.myapplication.MainActivity) getActivity()).getwordsList();
        textView3.setText(String.join(", ", wordList[0]));
        textView5.setText(String.join(", ", wordList[1]));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}