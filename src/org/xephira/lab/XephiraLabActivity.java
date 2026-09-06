/*
 * Copyright (C) 2026 XephiraOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.xephira.lab;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class XephiraLabActivity extends AppCompatActivity implements
        PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.xephira_lab_title);
            actionBar.setSubtitle(R.string.xephira_lab_summary);
        }

        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            if (actionBar != null) {
                final int count = getSupportFragmentManager().getBackStackEntryCount();
                if (count == 0) {
                    actionBar.setTitle(R.string.xephira_lab_title);
                    actionBar.setSubtitle(R.string.xephira_lab_summary);
                }
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(android.R.id.content, new XephiraLabFragment())
                    .commit();
        }
    }

    @Override
    public boolean onPreferenceStartFragment(@NonNull PreferenceFragmentCompat caller, @NonNull Preference pref) {
        final Bundle args = pref.getExtras();
        final Fragment fragment = getSupportFragmentManager().getFragmentFactory().instantiate(
                getClassLoader(),
                pref.getFragment());
        fragment.setArguments(args);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null && pref.getTitle() != null) {
            actionBar.setTitle(pref.getTitle());
            actionBar.setSubtitle(null);
        }

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(
                        android.R.anim.fade_in,
                        android.R.anim.fade_out,
                        android.R.anim.fade_in,
                        android.R.anim.fade_out)
                .replace(android.R.id.content, fragment)
                .addToBackStack(pref.getKey())
                .commit();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
                return true;
            }
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
