package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements NotesFragment.Controller, OneNoteFragment.Controller{
    public final String NOTES_FRAGMENT_TAG = "NOTES_FRAGMENT_TAG";
    private BottomNavigationView bottomNavigationView;

    static {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this::setBottomNavListener);
    }

    private boolean setBottomNavListener(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_list:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, new NotesFragment(), NOTES_FRAGMENT_TAG)
                        .commit();
                return true;
            case R.id.navigation_add:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, new AddFragment())
                        .commit();
                return true;
            case R.id.navigation_tasks:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, new TasksFragment())
                        .commit();
                return true;
            case R.id.navigation_favorites:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, new FavoritesFragment())
                        .commit();
                return true;
        }
        return false;
    }

    @Override
    public void openNoteScreen(NoteEntity note) {
        //boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        getSupportFragmentManager()
                .beginTransaction()
                //.add(isLandscape ? R.id.layout_one_note : R.id.layout_notes_list, OneNoteFragment.newInstance(note))
                .add(R.id.nav_host_fragment_activity_main, OneNoteFragment.newInstance(note))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu_theme:
                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                break;
            case R.id.action_menu_category:
                View vItem = findViewById(R.id.action_menu_category);
                PopupMenu popupMenu = new PopupMenu(this, vItem);
                popupMenu.inflate(R.menu.category_menu);
                popupMenu.setOnMenuItemClickListener(this::setCategoryMenuListener);
                popupMenu.show();
                break;
        }
        return true;
    }

    private boolean setCategoryMenuListener(MenuItem itemCategory) {
        switch (itemCategory.getItemId()) {
            case R.id.category_menu_no_category:
                Toast.makeText(this,getString(R.string.no_category),Toast.LENGTH_SHORT).show();
                break;
            case R.id.category_menu_work:
                Toast.makeText(this,getString(R.string.work),Toast.LENGTH_SHORT).show();
                break;
            case R.id.category_menu_study:
                Toast.makeText(this,getString(R.string.study),Toast.LENGTH_SHORT).show();
                break;
            case R.id.category_menu_home:
                Toast.makeText(this,getString(R.string.home),Toast.LENGTH_SHORT).show();
                break;
            case R.id.category_menu_add_category:
                Toast.makeText(this,getString(R.string.add_category),Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void saveResult(NoteEntity newNote) {
        NotesFragment notesFragment = (NotesFragment) getSupportFragmentManager()
                .findFragmentByTag(NOTES_FRAGMENT_TAG);
        if (notesFragment != null) {
            notesFragment.saveEditResult(newNote);
        }
    }
}