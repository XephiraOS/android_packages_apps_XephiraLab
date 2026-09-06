/*
 * Copyright (C) 2026 XephiraOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.xephira.lab.fragments;

import android.content.ContentResolver;
import android.os.Bundle;
import android.os.SystemProperties;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SeekBarPreference;
import androidx.preference.SwitchPreferenceCompat;

import org.xephira.lab.R;

public class QuickSettingsFragment extends PreferenceFragmentCompat
        implements Preference.OnPreferenceChangeListener {

    private static final String KEY_COLUMNS = "pref_qs_columns";
    private static final String KEY_BLUR = "pref_qs_blur";
    private static final String KEY_SPECULAR = "pref_qs_specular";
    private static final String KEY_VERTICAL_SLIDERS = "pref_qs_vertical_sliders";
    private static final String KEY_AUTO_BRIGHTNESS_BTN = "pref_qs_brightness_auto_btn";
    private static final String KEY_TILE_ANIM = "pref_qs_tile_anim";
    private static final String KEY_RUNNING_SERVICES = "pref_qs_running_services";

    private ListPreference mColumnsPref;
    private SeekBarPreference mBlurPref;
    private SwitchPreferenceCompat mSpecularPref;
    private SwitchPreferenceCompat mVerticalSlidersPref;
    private SwitchPreferenceCompat mAutoBrightnessBtnPref;
    private ListPreference mTileAnimPref;
    private SwitchPreferenceCompat mRunningServicesPref;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.xephira_lab_quicksettings, rootKey);

        final ContentResolver cr = requireContext().getContentResolver();

        mColumnsPref = findPreference(KEY_COLUMNS);
        if (mColumnsPref != null) {
            String cols = Settings.System.getString(cr, "qs_tile_columns");
            if (cols == null) cols = "3";
            mColumnsPref.setValue(cols);
            mColumnsPref.setOnPreferenceChangeListener(this);
        }

        mBlurPref = findPreference(KEY_BLUR);
        if (mBlurPref != null) {
            int blur = Settings.System.getInt(cr, "qs_background_blur_radius", 32);
            mBlurPref.setValue(blur);
            mBlurPref.setOnPreferenceChangeListener(this);
        }

        mSpecularPref = findPreference(KEY_SPECULAR);
        if (mSpecularPref != null) {
            boolean specular = Settings.System.getInt(cr, "qs_tile_specular_highlight", 1) == 1;
            mSpecularPref.setChecked(specular);
            mSpecularPref.setOnPreferenceChangeListener(this);
        }

        mVerticalSlidersPref = findPreference(KEY_VERTICAL_SLIDERS);
        if (mVerticalSlidersPref != null) {
            boolean vert = Settings.System.getInt(cr, "qs_vertical_sliders", 1) == 1;
            mVerticalSlidersPref.setChecked(vert);
            mVerticalSlidersPref.setOnPreferenceChangeListener(this);
        }

        mAutoBrightnessBtnPref = findPreference(KEY_AUTO_BRIGHTNESS_BTN);
        if (mAutoBrightnessBtnPref != null) {
            boolean auto = Settings.System.getInt(cr, "qs_brightness_auto_button", 1) == 1;
            mAutoBrightnessBtnPref.setChecked(auto);
            mAutoBrightnessBtnPref.setOnPreferenceChangeListener(this);
        }

        mTileAnimPref = findPreference(KEY_TILE_ANIM);
        if (mTileAnimPref != null) {
            String anim = Settings.System.getString(cr, "qs_tile_animation_style");
            if (anim == null) anim = "spring_pulse";
            mTileAnimPref.setValue(anim);
            mTileAnimPref.setOnPreferenceChangeListener(this);
        }

        mRunningServicesPref = findPreference(KEY_RUNNING_SERVICES);
        if (mRunningServicesPref != null) {
            boolean run = Settings.System.getInt(cr, "qs_footer_running_services", 1) == 1;
            mRunningServicesPref.setChecked(run);
            mRunningServicesPref.setOnPreferenceChangeListener(this);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final ContentResolver cr = requireContext().getContentResolver();
        final String key = preference.getKey();

        if (KEY_COLUMNS.equals(key)) {
            String cols = (String) newValue;
            Settings.System.putString(cr, "qs_tile_columns", cols);
            return true;
        } else if (KEY_BLUR.equals(key)) {
            int blur = (Integer) newValue;
            Settings.System.putInt(cr, "qs_background_blur_radius", blur);
            SystemProperties.set("persist.sys.xephira.qs_blur", String.valueOf(blur));
            return true;
        } else if (KEY_SPECULAR.equals(key)) {
            boolean enabled = (Boolean) newValue;
            Settings.System.putInt(cr, "qs_tile_specular_highlight", enabled ? 1 : 0);
            return true;
        } else if (KEY_VERTICAL_SLIDERS.equals(key)) {
            boolean enabled = (Boolean) newValue;
            Settings.System.putInt(cr, "qs_vertical_sliders", enabled ? 1 : 0);
            return true;
        } else if (KEY_AUTO_BRIGHTNESS_BTN.equals(key)) {
            boolean enabled = (Boolean) newValue;
            Settings.System.putInt(cr, "qs_brightness_auto_button", enabled ? 1 : 0);
            return true;
        } else if (KEY_TILE_ANIM.equals(key)) {
            String anim = (String) newValue;
            Settings.System.putString(cr, "qs_tile_animation_style", anim);
            return true;
        } else if (KEY_RUNNING_SERVICES.equals(key)) {
            boolean enabled = (Boolean) newValue;
            Settings.System.putInt(cr, "qs_footer_running_services", enabled ? 1 : 0);
            return true;
        }
        return false;
    }
}
