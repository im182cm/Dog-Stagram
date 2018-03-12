package philip.com.dogstagram.mvvm.view.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import philip.com.dogstagram.R;

public class MainActivity extends DaggerAppCompatActivity {
    @Inject
    public PostFragment mPostFragment;
    private PhotoFragment mPhotoFragment;
    @Inject
    public ViewpagerFragment mViewpagerFragment;

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    switchToPostFragment();
                    break;
                case R.id.navigation_dashboard:
                    switchToPhotoFragment();
                    break;
                case R.id.navigation_notifications:
                    switchToViewPagerFragment();
                    break;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        switchToPostFragment();
    }

    private void switchToPostFragment() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.layout_contentFrame, mPostFragment).commit();
    }

    private void switchToPhotoFragment() {
        if (mPhotoFragment == null){
            mPhotoFragment = new PhotoFragment();
        }

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.layout_contentFrame, mPhotoFragment).commit();
    }

    private void switchToViewPagerFragment() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.layout_contentFrame, mViewpagerFragment).commit();
    }
}
