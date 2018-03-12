package philip.com.dogstagram.di;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import philip.com.dogstagram.mvvm.view.login.LoginActivity;
import philip.com.dogstagram.mvvm.view.main.MainActivity;
import philip.com.dogstagram.mvvm.view.main.MainModule;
import philip.com.dogstagram.mvvm.view.profile.ProfileActivity;

@Module
abstract class ActivityBindingModule {
    @Binds
    abstract Context bindContext(Application application);

    @ActivityScoped
    @ContributesAndroidInjector
    abstract LoginActivity loginActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity mainActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract ProfileActivity profileActivity();
}