package sixsalman99.salesrecord;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

/**
 * This activity stores and allows user to transition between items, cart and history tabs by either
 * clicking or swiping
 */
public class TabbedActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    ItemsFragment itemsFragment;
    CartFragment cartFragment;
    HistoryFragment historyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);

        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);

        itemsFragment = new ItemsFragment();
        cartFragment = new CartFragment();
        historyFragment = new HistoryFragment();

        mViewPager.setAdapter(new Adapter(getSupportFragmentManager(),
                        FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));

        mTabLayout.setupWithViewPager(findViewById(R.id.view_pager));
    }

    private class Adapter extends FragmentPagerAdapter {

        public Adapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 1:
                    return cartFragment;
                case 2:
                    return historyFragment;
                default:
                    return itemsFragment;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 1:
                    return "Cart";
                case 2:
                    return "History";
                default:
                    return "Items";
            }
        }
    }
}