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
import androidx.preference.SwitchPreferenceCompat;

import org.xephira.lab.R;

public class FluxFragment extends PreferenceFragmentCompat
        implements Preference.OnPreferenceChangeListener {

    private static final String KEY_RAM_BOOST = "pref_ram_boost";
    private static final String KEY_SOC_GOVERNOR = "pref_soc_governor";
    private static final String KEY_TOUCH_SAMPLING = "pref_touch_sampling";
    private static final String KEY_THERMAL_PROFILE = "pref_thermal_profile";

    private ListPreference mRamBoostPref;
    private SwitchPreferenceCompat mSocGovernorPref;
    private ListPreference mTouchSamplingPref;
    private ListPreference mThermalProfilePref;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.xephira_lab_flux, rootKey);

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

        mTouchSamplingPref = findPreference(KEY_TOUCH_SAMPLING);
        if (mTouchSamplingPref != null) {
            String rate = SystemProperties.get("persist.sys.xephira.touch_sampling", "240");
            mTouchSamplingPref.setValue(rate);
            mTouchSamplingPref.setOnPreferenceChangeListener(this);
        }

        mThermalProfilePref = findPreference(KEY_THERMAL_PROFILE);
        if (mThermalProfilePref != null) {
            String profile = SystemProperties.get("persist.sys.xephira.thermal_profile", "dynamic");
            mThermalProfilePref.setValue(profile);
            mThermalProfilePref.setOnPreferenceChangeListener(this);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final String key = preference.getKey();
        if (KEY_RAM_BOOST.equals(key)) {
            String boost = (String) newValue;
            SystemProperties.set("persist.sys.xephira.ram_boost", boost);
            return true;
        } else if (KEY_SOC_GOVERNOR.equals(key)) {
            boolean enabled = (Boolean) newValue;
            SystemProperties.set("persist.sys.xephira.trinity_engine", enabled ? "1" : "0");
            return true;
        } else if (KEY_TOUCH_SAMPLING.equals(key)) {
            String rate = (String) newValue;
            SystemProperties.set("persist.sys.xephira.touch_sampling", rate);
            return true;
        } else if (KEY_THERMAL_PROFILE.equals(key)) {
            String profile = (String) newValue;
            SystemProperties.set("persist.sys.xephira.thermal_profile", profile);
            return true;
        }
        return false;
    }
}
