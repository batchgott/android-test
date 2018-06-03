package com.evelope.events.introSlider;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;

import com.evelope.events.MainActivity;
import com.evelope.events.database.AppDatabase;
import com.evelope.events.database.Categorie;
import com.github.paolorotolo.appintro.AppIntro;

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(new IntroFragment1());
        addSlide(new IntroFragment2());
        addSlide(new IntroFragment4());
        addSlide(new IntroFragment3());


        showSkipButton(false);
        setProgressButtonEnabled(false);

        setVibrate(false);

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
