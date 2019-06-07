package com.galeno.drawyourikigai.ManualDraw;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.galeno.drawyourikigai.MainActivity;
import com.galeno.drawyourikigai.ManualDraw.DoodleViewManual;
import com.galeno.drawyourikigai.R;
import com.galeno.drawyourikigai.ToolsFragments.ColorDialogFragment;
import com.galeno.drawyourikigai.ToolsFragments.EraseImageDialogFragment;
import com.galeno.drawyourikigai.ToolsFragments.LineWidthDialogFragment;

public class ActivityPaintFragmentManual extends Fragment {
    private DoodleViewManual doodleView; //para desenhar e lidar com os eventos de toque na tela
    private float acceleration;
    private float currentAcceleration;
    private float lastAcceleration;
    //para evitar que mais de um diálogo seja exibido
    private boolean dialogOnScreen = false;
    //limiar que deve ser superado para considerar
    //que o usuário balançou o dispositivo
    private static final int ACCELERATION_THRESHOLD = 1000000;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_paint_manual, container, false);
        setHasOptionsMenu(true);

        doodleView = v.findViewById(R.id.doodleViewManual);
        //aceleração atual
        currentAcceleration = SensorManager.GRAVITY_EARTH;
        //última conhecida
        lastAcceleration = SensorManager.GRAVITY_EARTH;
        //será atualizada quando uma nova aceleração for detectada
        acceleration = 0.00f;

        return v;
    }

    private final SensorEventListener sensorEventListener =
            new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    //verifica se um diálogo já não está sendo exibido
                    if (!dialogOnScreen){
                        //pega a aceleração nos três eixos
                        float x = event.values[0];
                        float y = event.values[1];
                        float z = event.values [2];

                        lastAcceleration = currentAcceleration;
                        //atualiza a aceleração atual
                        currentAcceleration = x * x + y * y + z * z;
                        //diferença entre a aceleração anterior e atual
                        acceleration = currentAcceleration *
                                (currentAcceleration - lastAcceleration);
                        //passou do limiar?
                        if (acceleration > ACCELERATION_THRESHOLD){
                            confirmErase();
                        }
                    }

                }
                @Override
                public void onAccuracyChanged(Sensor sensor, int
                        accuracy) {
                }
    };

    private void confirmErase (){
        EraseImageDialogFragment eraseFragment = new EraseImageDialogFragment();
        eraseFragment.show(getFragmentManager(),"erase dialog");
    }

    private void enableAccelerometerListening(){
        //obtém o serviço
        SensorManager sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        //registra o observer
        sensorManager.registerListener(sensorEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void disableAccelerometerListening(){
        //obtém o serviço
        SensorManager sensorManager =
                (SensorManager) getActivity().
                        getSystemService(Context.SENSOR_SERVICE);
        //desregistra o observer
        sensorManager.unregisterListener(
                sensorEventListener,

                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
    }

    @Override
    public void onResume() {
        super.onResume();
        enableAccelerometerListening();
    }

    @Override
    public void onPause() {
        super.onPause();
        disableAccelerometerListening();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.doodle_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.color:
                ColorDialogFragment colorDialog =
                        new ColorDialogFragment();
                colorDialog.show(getFragmentManager(), "color dialog");
                return true;
            case R.id.line_width:
                LineWidthDialogFragment widthDialog =  new LineWidthDialogFragment();
                widthDialog.show(getFragmentManager(), "line width dialog");
                return true;
            case R.id.delete:
                confirmErase();
                return true;
            case android.R.id.home:
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finishAffinity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }




    public DoodleViewManual getDoodleView(){
        return this.doodleView;
    }
    public void setDialogOnScreen(boolean dialogOnScreen) {
        this.dialogOnScreen = dialogOnScreen;
    }






}
