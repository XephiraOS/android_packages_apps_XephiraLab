/*
 * Copyright (C) 2026 XephiraOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.xephira.lab;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemProperties;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SeekBarPreference;
import androidx.preference.SwitchPreferenceCompat;

public class XephiraLabFragment extends PreferenceFragmentCompat
        implements Preference.OnPreferenceChangeListener {

    private static final String KEY_BLUR_RADIUS = "pref_blur_radius";
    private static final String KEY_SPECULAR = "pref_specular_refraction";
    private static final String KEY_CHROMATIC = "pref_chromatic_dispersion";
    private static final String KEY_SPRING_DYNAMICS = "pref_spring_dynamics";
    private static final String KEY_PREDICTIVE_BACK = "pref_predictive_back_fluid";
    private static final String KEY_CURVATURE = "pref_curvature_style";
    private static final String KEY_RAM_BOOST = "pref_ram_boost";
    private static final String KEY_SOC_GOVERNOR = "pref_soc_governor";

    private SeekBarPreference mBlurRadiusPref;
    private SwitchPreferenceCompat mSpecularPref;
    private SwitchPreferenceCompat mChromaticPref;
    private SwitchPreferenceCompat mSpringDynamicsPref;
    private SwitchPreferenceCompat mPredictiveBackPref;
    private ListPreference mCurvaturePref;
    private ListPreference mRamBoostPref;
    private SwitchPreferenceCompat mSocGovernorPref;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.xephira_lab_preferences, rootKey);

        mBlurRadiusPref = findPreference(KEY_BLUR_RADIUS);
        if (mBlurRadiusPref != null) {
            int current = SystemProperties.getInt("persist.sys.xephira.blur_radius", 28);
            mBlurRadiusPref.setValue(current);
            mBlurRadiusPref.setOnPreferenceChangeListener(this);
        }

        mSpecularPref = findPreference(KEY_SPECULAR);
        if (mSpecularPref != null) {
            mSpecularPref.setChecked(SystemProperties.getBoolean("persist.sys.xephira.specular", true));
            mSpecularPref.setOnPreferenceChangeListener(this);
        }

        mChromaticPref = findPreference(KEY_CHROMATIC);
        if (mChromaticPref != null) {
            mChromaticPref.setChecked(SystemProperties.getBoolean("persist.sys.xephira.chromatic", true));
            mChromaticPref.setOnPreferenceChangeListener(this);
        }

        mSpringDynamicsPref = findPreference(KEY_SPRING_DYNAMICS);
        if (mSpringDynamicsPref != null) {
            mSpringDynamicsPref.setChecked(SystemProperties.getBoolean("persist.sys.xephira.spring_dynamics", true));
            mSpringDynamicsPref.setOnPreferenceChangeListener(this);
        }

        mPredictiveBackPref = findPreference(KEY_PREDICTIVE_BACK);
        if (mPredictiveBackPref != null) {
            mPredictiveBackPref.setChecked(SystemProperties.getBoolean("persist.wm.debug.predictive_back", true));
            mPredictiveBackPref.setOnPreferenceChangeListener(this);
        }

        mCurvaturePref = findPreference(KEY_CURVATURE);
        if (mCurvaturePref != null) {
            String style = SystemProperties.get("persist.sys.xephira.curvature", "superellipse_n4");
            mCurvaturePref.setValue(style);
            mCurvaturePref.setOnPreferenceChangeListener(this);
        }

        mRamBoostPref = findPreference(KEY_RAM_BOOST);
        if (mRamBoostPref != null) {
            String boost = SystemProperties.get("persist.sys.xephira.ram_boost", "12");
            mRamBoostPref.setValue(boost);
            mRamBoostPref.setOnPreferenceChangeListener(this);
        }

        mSocGovernorPref = findPreference(KEY_SOC_GOVERNOR);
        if (mSocGovernorPref != null) {
            mSocGovernorPref.setChecked(SystemProperties.getBoolean("persist.sys.xephira.trinity_engine", true));
            mSocGovernorPref.setOnPreferenceChangeListener(this);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final String key = preference.getKey();
        if (KEY_BLUR_RADIUS.equals(key)) {
            int radius = (Integer) newValue;
            SystemProperties.set("persist.sys.xephira.blur_radius", String.valueOf(radius));
            return true;
        } else if (KEY_SPECULAR.equals(key)) {
            boolean enabled = (Boolean) newValue;
            SystemProperties.set("persist.sys.xephira.specular", enabled ? "1" : "0");
            return true;
        } else if (KEY_CHROMATIC.equals(key)) {
            boolean enabled = (Boolean) newValue;
            SystemProperties.set("persist.sys.xephira.chromatic", enabled ? "1" : "0");
            return true;
        } else if (KEY_SPRING_DYNAMICS.equals(key)) {
            boolean enabled = (Boolean) newValue;
            SystemProperties.set("persist.sys.xephira.spring_dynamics", enabled ? "1" : "0");
            return true;
        } else if (KEY_PREDICTIVE_BACK.equals(key)) {
            boolean enabled = (Boolean) newValue;
            SystemProperties.set("persist.wm.debug.predictive_back", enabled ? "1" : "0");
            return true;
        } else if (KEY_CURVATURE.equals(key)) {
            String style = (String) newValue;
            SystemProperties.set("persist.sys.xephira.curvature", style);
            return true;
        } else if (KEY_RAM_BOOST.equals(key)) {
            String boost = (String) newValue;
            SystemProperties.set("persist.sys.xephira.ram_boost", boost);
            return true;
        } else if (KEY_SOC_GOVERNOR.equals(key)) {
            boolean enabled = (Boolean) newValue;
            SystemProperties.set("persist.sys.xephira.trinity_engine", enabled ? "1" : "0");
            return true;
        }
        return false;
    }
}
