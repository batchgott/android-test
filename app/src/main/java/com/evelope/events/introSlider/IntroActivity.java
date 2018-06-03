package com.evelope.events.introSlider;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.evelope.events.MainActivity;
import com.evelope.events.database.AppDatabase;
import com.evelope.events.database.Categorie;
import com.evelope.events.login.LoginActivity;
import com.evelope.events.register.RegisterActivity;
import com.evelope.events.tools.CurrentUser;
import com.github.paolorotolo.appintro.AppIntro;

public class IntroActivity extends AppIntro {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (CurrentUser.getUserID()!=null)
        if (CurrentUser.getUserID()!=-10)
            startActivity(new Intent(IntroActivity.this,MainActivity.class));





        addSlide(new IntroFragment1());
        addSlide(new IntroFragment2());
        addSlide(new IntroFragment4());
        addSlide(new IntroFragment3());


        showSkipButton(false);
        setProgressButtonEnabled(false);

        setVibrate(false);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
            } else {
                super.onPageSelected(4);
            }
        }

        //Clear Database
        if (false)
            AppDatabase.Clear(getApplicationContext());
        if (AppDatabase.getAppDatabase(getApplicationContext()).categorieDao().getAll().size()==0||AppDatabase.getAppDatabase(getApplicationContext()).categorieDao().getAll()==null){
            AppDatabase db=AppDatabase.getAppDatabase(getApplicationContext());
            db.categorieDao().insertCategorie(new Categorie(0l,"drink"));
            db.categorieDao().insertCategorie(new Categorie(1l,"dance"));
            db.categorieDao().insertCategorie(new Categorie(2l,"sing"));
            db.categorieDao().insertCategorie(new Categorie(3l,"smoke"));
            db.categorieDao().insertCategorie(new Categorie(4l,"talk"));
            db.categorieDao().insertCategorie(new Categorie(5l,"play"));
        }
    }


    public void GoToLoginActivity(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
