package com.example.caiocalado.eventreceiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.OrientationEventListener;
import android.widget.ImageView;


public class OrientationNotification extends ActionBarActivity {


    private OrientationEventListener orientationLst;
    private ImageView phoneView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orientation_notification);

        phoneView = (ImageView)findViewById(R.id.phoneView);

        orientationLst = new OrientationEventListener(this, SensorManager.SENSOR_DELAY_NORMAL){
            @Override
            public void onOrientationChanged(int args){
                phoneView.setRotation(Float.parseFloat(String.valueOf(args)));
            }};

        if(orientationLst.canDetectOrientation()){
            orientationLst.enable();
        }else{
            finish();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


        String orientation = "Your orientation is ";
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            orientation += "landscape!";
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            orientation += "portrait!";
        }

        //Estou construindo o builder para chamar a notificação depois!
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.phone)
                .setContentTitle("Hey!")
                .setContentText(orientation);

        //Intent que vai fazer a notificação em si ser chamada
        Intent resultIntent = new Intent(this, OrientationNotification.class);

        //pilha para empilhar todas as notificações possíveis
        //No meu caso, apenas vou chamar para alterações de orientações
        //portrait e landscape
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        stackBuilder.addParentStack(OrientationNotification.class);

        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //criando a notificação em si!
        mNotificationManager.notify(0, mBuilder.build());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_orientation_notification, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
