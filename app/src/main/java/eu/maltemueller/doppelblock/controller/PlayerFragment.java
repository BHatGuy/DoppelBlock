package eu.maltemueller.doppelblock.controller;


import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.maltemueller.doppelblock.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerFragment extends Fragment {

    private ImageButton deleteBtn;
    private EditText etName;
    private String tempName = "";

    public PlayerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        deleteBtn = getView().findViewById(R.id.btn_delete_player);
        etName = getView().findViewById(R.id.et_pName);
        etName.setText(tempName);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .remove(PlayerFragment.this).commit();

            }
        });

    }

    public void setFocus(Activity activity){
        if(etName.requestFocus()) {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public String getName(){
        return etName.getText().toString();
    }

    public void setName(String name) {
        if (etName != null){
            etName.setText(name);
        } else {
            tempName = name;
        }
    }
}
