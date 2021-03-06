package com.example.trainingzonev4;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.example.trainingzonev4.controllers.homeControllers.HomeMenuController;
import com.example.trainingzonev4.network.NetworkService;
import com.example.trainingzonev4.network.dataClasses.InstagramDataPOJO;
import com.example.trainingzonev4.realmDatabase.RealmDatabase;
import com.example.trainingzonev4.util.ActionBarProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ActionBarProvider {

    @BindView(R.id.controller_container)
    ViewGroup container;

    private Router router;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        RealmDatabase realmDatabase =new RealmDatabase(this,getResources());
        realmDatabase.createDatabase();


        router = Conductor.attachRouter(this, container, savedInstanceState);
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(new HomeMenuController()));
        }

    }

    @Override
    public void onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed();
        }
    }

    public Router getRouter(){
        return router;
    }

}
