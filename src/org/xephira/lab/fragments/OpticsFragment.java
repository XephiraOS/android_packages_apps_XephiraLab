/*
 * Copyright (C) 2026 XephiraOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.xephira.lab.fragments;

import android.os.Bundle;
import android.os.SystemProperties;

import androidx.annotation.Nullable;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SeekBarPreference;
import androidx.preference.SwitchPreferenceCompat;

import org.xephira.lab.R;

public class OpticsFragment extends PreferenceFragmentCompat
        implements Preference.OnPreferenceChangeListener {

    private static final String KEY_BLUR_RADIUS = "pref_blur_radius";
    private static final String KEY_BLUR_SAMPLE = "pref_blur_sample_rate";
    private static final String KEY_SPECULAR = "pref_specular_refraction";
    private static final String KEY_SPECULAR_INTENSITY = "pref_specular_intensity";
    private static final String KEY_CHROMATIC = "pref_chromatic_dispersion";
    private static final String KEY_FROSTED_GRAIN = "pref_frosted_grain";

    private SeekBarPreference mBlurRadiusPref;
    private ListPreference mBlurSamplePref;
    private SwitchPreferenceCompat mSpecularPref;
    private SeekBarPreference mSpecularIntensityPref;
    private SwitchPreferenceCompat mChromaticPref;
    private SwitchPreferenceCompat mFrostedGrainPref;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.xephira_lab_optics, rootKey);

        mBlurRadiusPref = findPreference(KEY_BLUR_RADIUS);
        if (mBlurRadiusPref != null) {
            int current = SystemProperties.getInt("persist.sys.xephira.blur_radius", 28);
            mBlurRadiusPref.setValue(current);
            mBlurRadiusPref.setOnPreferenceChangeListener(this);
        }

        mBlurSamplePref = findPreference(KEY_BLUR_SAMPLE);
        if (mBlurSamplePref != null) {
            String sample = SystemProperties.get("persist.sys.xephira.blur_sample", "2");
            mBlurSamplePref.setValue(sample);
            mBlurSamplePref.setOnPreferenceChangeListener(this);
        }

        mSpecularPref = findPreference(KEY_SPECULAR);
        if (mSpecularPref != null) {
            mSpecularPref.setChecked(SystemProperties.getBoolean("persist.sys.xephira.specular", true));
            mSpecularPref.setOnPreferenceChangeListener(this);
        }

        mSpecularIntensityPref = findPreference(KEY_SPECULAR_INTENSITY);
        if (mSpecularIntensityPref != null) {
            int intensity = SystemProperties.getInt("persist.sys.xephira.specular_intensity", 80);
            mSpecularIntensityPref.setValue(intensity);
            mSpecularIntensityPref.setOnPreferenceChangeListener(this);
        }

        mChromaticPref = findPreference(KEY_CHROMATIC);
        if (mChromaticPref != null) {
            mChromaticPref.setChecked(SystemProperties.getBoolean("persist.sys.xephira.chromatic", true));
            mChromaticPref.setOnPreferenceChangeListener(this);
        }

        mFrostedGrainPref = findPreference(KEY_FROSTED_GRAIN);
        if (mFrostedGrainPref != null) {
            mFrostedGrainPref.setChecked(SystemProperties.getBoolean("persist.sys.xephira.frosted_grain", false));
            mFrostedGrainPref.setOnPreferenceChangeListener(this);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final String key = preference.getKey();
        if (KEY_BLUR_RADIUS.equals(key)) {
            int radius = (Integer) newValue;
            SystemProperties.set("persist.sys.xephira.blur_radius", String.valueOf(radius));
            return true;
        } else if (KEY_BLUR_SAMPLE.equals(key)) {
            String sample = (String) newValue;
            SystemProperties.set("persist.sys.xephira.blur_sample", sample);
            return true;
        } else if (KEY_SPECULAR.equals(key)) {
            boolean enabled = (Boolean) newValue;
            SystemProperties.set("persist.sys.xephira.specular", enabled ? "1" : "0");
            return true;
        } else if (KEY_SPECULAR_INTENSITY.equals(key)) {
            int intensity = (Integer) newValue;
            SystemProperties.set("persist.sys.xephira.specular_intensity", String.valueOf(intensity));
            return true;
        } else if (KEY_CHROMATIC.equals(key)) {
            boolean enabled = (Boolean) newValue;
            SystemProperties.set("persist.sys.xephira.chromatic", enabled ? "1" : "0");
            return true;
        } else if (KEY_FROSTED_GRAIN.equals(key)) {
            boolean enabled = (Boolean) newValue;
            SystemProperties.set("persist.sys.xephira.frosted_grain", enabled ? "1" : "0");
            return true;
        }
        return false;
    }
}
