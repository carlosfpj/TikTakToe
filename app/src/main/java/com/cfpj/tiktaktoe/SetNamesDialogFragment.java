package com.cfpj.tiktaktoe;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SetNamesDialogFragment extends DialogFragment {

    private TextView tvAlert;
    private EditText etPlayer1Name, etPlayer2Name;
    private Button btnAccept;

    public interface SetNamesDialogListener {
        void onDialogPositiveClick(DialogFragment dialog, String name1, String name2);
    }

    SetNamesDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (SetNamesDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(GameActivity.class.toString() + " must implement SetNamesDialogFragment");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_setnamesdialog, null);
        etPlayer1Name = view.findViewById(R.id.et_player_1_name);
        etPlayer2Name = view.findViewById(R.id.et_player_2_name);
        tvAlert = view.findViewById(R.id.tv_alert);
        tvAlert.setVisibility(View.GONE);
        btnAccept = view.findViewById(R.id.btn_accept);
        builder.setView(view);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name1 = etPlayer1Name.getText().toString();
                String name2 = etPlayer2Name.getText().toString();

                if(name1.length() < 1 || name2.length() < 1){
                    tvAlert.setVisibility(View.VISIBLE);
                    tvAlert.setText(R.string.noNameAlertMessage);
                }else {
                    listener.onDialogPositiveClick(SetNamesDialogFragment.this, name1, name2);
                    dismiss();
                }
            }
        });
        return builder.create();
    }
}
