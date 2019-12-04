package com.acasema.inventory.iu.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.acasema.inventory.R;

public class BaseDialogoFragment extends DialogFragment {
    public static final String TITLE="title";
    public static final String MESSAGE="mesage";
    public static final String TAG="BaseDialogFragment";

    //Metodo callBack del lisener del DialogFragment
    //Cuando se pulsa ACEPTAR
    public interface OnAcceptDialogLisener{
        void onAcceptDialog();
    }
    public static  BaseDialogoFragment newIntace(Bundle bundle){
        BaseDialogoFragment dialogoFragment = new BaseDialogoFragment();
        if (bundle != null)
            dialogoFragment.setArguments(bundle);
        return  dialogoFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String title = getArguments().getString(TITLE);
        String message = getArguments().getString(MESSAGE);
        //se creael cuadro de dialogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(getString(android.R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OnAcceptDialogLisener lisener = (OnAcceptDialogLisener)getTargetFragment();
                lisener.onAcceptDialog();
            }
        });
        builder.setNegativeButton(getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }
}
