package com.example.todolistapp.Settings;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.DropDownPreference;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.todolistapp.Database.TaskDatabase;
import com.example.todolistapp.R;

import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            ListPreference listPreference = findPreference("categories");
            if(listPreference != null) {
                setListPreferenceData(listPreference);

                listPreference.setOnPreferenceClickListener(l -> {
                    setListPreferenceData(listPreference);
                    return false;
                });
            }
        }

        protected void setListPreferenceData(ListPreference dropDownPreference){
            TaskDatabase taskDatabase = TaskDatabase.getDbInstance(this.getContext());
            List<String> categories = taskDatabase.taskDAO().getCategories();
            categories.add("All");
            String[] categoriesArray = categories.toArray(new String[0]);
            dropDownPreference.setEntries(categoriesArray);
            dropDownPreference.setEntryValues(categoriesArray);
        }

    }
}