package com.cfpj.tiktaktoe;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.EditText;

public class SetNamesDialogFragment extends DialogFragment {

    private EditText etPlayer1Name, etPlayer2Name;

    public interface SetNamesDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
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
    public Dialog onCreateDialog( Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //Get the layout inflater
        final LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setView(R.layout.fragment_setnamesdialog)
                .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onDialogPositiveClick(SetNamesDialogFragment.this);
                    }
                });

        return builder.create();

    }
}
