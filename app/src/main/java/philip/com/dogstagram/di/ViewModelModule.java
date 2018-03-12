package philip.com.dogstagram.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import philip.com.dogstagram.mvvm.view.main.ViewPagerViewModel;
import philip.com.dogstagram.mvvm.view.profile.ProfileViewModel;
import philip.com.dogstagram.mvvm.viewmodel.DogStagramViewModelFactory;
import philip.com.dogstagram.mvvm.view.main.MainViewModel;

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel mainViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    abstract ViewModel bindProfileViewModel(ProfileViewModel profileViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ViewPagerViewModel.class)
    abstract ViewModel bindViewPagerViewModel(ViewPagerViewModel viewPagerViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(DogStagramViewModelFactory factory);
}
