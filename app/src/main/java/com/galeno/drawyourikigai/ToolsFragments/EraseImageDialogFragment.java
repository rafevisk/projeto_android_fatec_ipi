package com.galeno.drawyourikigai.ToolsFragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.galeno.drawyourikigai.ManualDraw.ActivityPaintFragmentManual;
import com.galeno.drawyourikigai.R;

public class EraseImageDialogFragment extends DialogFragment {
    private ActivityPaintFragmentManual getDoodleFragment (){
        return (ActivityPaintFragmentManual)
                (getFragmentManager().findFragmentById(R.id.doodleFragmentManual));
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ActivityPaintFragmentManual fragment = getDoodleFragment();
        if (fragment != null){
            fragment.setDialogOnScreen(true);
        }

    }
    @Override
    public void onDetach() {
        super.onDetach();
        ActivityPaintFragmentManual fragment = getDoodleFragment();
        if (fragment != null){
            fragment.setDialogOnScreen(false);
        }
    }

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.message_erase);

        builder.setPositiveButton(R.string.button_erase, new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getDoodleFragment().getDoodleView().clear();
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, null);
        return builder.create();
    }
}
