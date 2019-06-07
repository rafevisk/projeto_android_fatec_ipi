package com.galeno.drawyourikigai.ToolsFragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.SeekBar;

import com.galeno.drawyourikigai.ManualDraw.ActivityPaintFragmentManual;
import com.galeno.drawyourikigai.ManualDraw.DoodleViewManual;
import com.galeno.drawyourikigai.R;

public class ColorDialogFragment extends DialogFragment {
    //seekbar para alpha
    private SeekBar alphaSeekBar;
    //seekbar para red
    private SeekBar redSeekBar;
    //seekbar para green
    private SeekBar greenSeekBar;
    //seekbar para blue
    private SeekBar blueSeekBar;
    //campo de amostra de cor
    private View colorView;
    //cor atual
    private int color;

    private ActivityPaintFragmentManual getDoodleFragment(){
        return (ActivityPaintFragmentManual)
                getFragmentManager().
                        findFragmentById(R.id.doodleFragmentManual);
    }

    private final SeekBar.OnSeekBarChangeListener colorChangedListener =
            new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean
                        fromUser) {
                    color = Color.argb(
                            alphaSeekBar.getProgress(),
                            redSeekBar.getProgress(),
                            greenSeekBar.getProgress(),
                            blueSeekBar.getProgress()
                    );
                    colorView.setBackgroundColor(color);
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
    };
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //builder para o diálogo
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
        //infla a view do fragment
        View colorDialogView =
                getActivity().
                        getLayoutInflater().
                        inflate(R.layout.fragment_color, null);
        builder.setView(colorDialogView);
        builder.setTitle(R.string.title_color_dialog);

        //busca os componentes visuais na árvore
        alphaSeekBar =
                colorDialogView.findViewById(R.id.alphaSeekBar);
        redSeekBar =
                colorDialogView.findViewById(R.id.redSeekBar);
        greenSeekBar =
                colorDialogView.findViewById(R.id.greenSeekBar);
        blueSeekBar =
                colorDialogView.findViewById(R.id.blueSeekBar);
        colorView = colorDialogView.findViewById(R.id.colorView);

        //registra o observador em todas as SeekBars
        alphaSeekBar.setOnSeekBarChangeListener(colorChangedListener);
        redSeekBar.setOnSeekBarChangeListener(colorChangedListener);
        greenSeekBar.setOnSeekBarChangeListener(colorChangedListener);
        blueSeekBar.setOnSeekBarChangeListener(colorChangedListener);

        //pega o DoodleView para pegar a cor atual e configurar
        //o progresso das barras
        final DoodleViewManual doodleView =
                getDoodleFragment().getDoodleView();
        color = doodleView.getDrawingColor();

        //configura o progresso de cada barra
        alphaSeekBar.setProgress(Color.alpha(color));
        redSeekBar.setProgress(Color.red(color));
        greenSeekBar.setProgress(Color.green(color));
        blueSeekBar.setProgress(Color.blue(color));

        //configura um botão com o texto e o observador
        builder.setPositiveButton(
                R.string.button_set_color,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doodleView.setDrawingColor(color);
                    }
                });
        //devolve o dialogo construído pelo builder
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ActivityPaintFragmentManual fragment = getDoodleFragment();
        if (fragment != null){
            //o diálogo está sendo exibido
            fragment.setDialogOnScreen(true);
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        ActivityPaintFragmentManual fragment = getDoodleFragment();
        if (fragment != null)
            //o diálogo não está mais na tela
            fragment.setDialogOnScreen(false);
    }


}
