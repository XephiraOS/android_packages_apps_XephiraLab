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

public class KineticFragment extends PreferenceFragmentCompat
        implements Preference.OnPreferenceChangeListener {

    private static final String KEY_SPRING_DYNAMICS = "pref_spring_dynamics";
    private static final String KEY_SPRING_DAMPING = "pref_spring_damping";
    private static final String KEY_PREDICTIVE_BACK = "pref_predictive_back_fluid";
    private static final String KEY_TOUCH_ELASTICITY = "pref_touch_elasticity";

    private SwitchPreferenceCompat mSpringDynamicsPref;
    private ListPreference mSpringDampingPref;
    private SwitchPreferenceCompat mPredictiveBackPref;
    private SwitchPreferenceCompat mTouchElasticityPref;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.xephira_lab_kinetic, rootKey);

        mSpringDynamicsPref = findPreference(KEY_SPRING_DYNAMICS);
        if (mSpringDynamicsPref != null) {
            mSpringDynamicsPref.setChecked(SystemProperties.getBoolean("persist.sys.xephira.spring_dynamics", true));
            mSpringDynamicsPref.setOnPreferenceChangeListener(this);
        }

        mSpringDampingPref = findPreference(KEY_SPRING_DAMPING);
        if (mSpringDampingPref != null) {
            String damping = SystemProperties.get("persist.sys.xephira.spring_damping", "balanced");
            mSpringDampingPref.setValue(damping);
            mSpringDampingPref.setOnPreferenceChangeListener(this);
        }

        mPredictiveBackPref = findPreference(KEY_PREDICTIVE_BACK);
        if (mPredictiveBackPref != null) {
            mPredictiveBackPref.setChecked(SystemProperties.getBoolean("persist.wm.debug.predictive_back", true));
            mPredictiveBackPref.setOnPreferenceChangeListener(this);
        }

        mTouchElasticityPref = findPreference(KEY_TOUCH_ELASTICITY);
        if (mTouchElasticityPref != null) {
            mTouchElasticityPref.setChecked(SystemProperties.getBoolean("persist.sys.xephira.touch_elasticity", true));
            mTouchElasticityPref.setOnPreferenceChangeListener(this);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final String key = preference.getKey();
        if (KEY_SPRING_DYNAMICS.equals(key)) {
            boolean enabled = (Boolean) newValue;
            SystemProperties.set("persist.sys.xephira.spring_dynamics", enabled ? "1" : "0");
            return true;
        } else if (KEY_SPRING_DAMPING.equals(key)) {
            String damping = (String) newValue;
            SystemProperties.set("persist.sys.xephira.spring_damping", damping);
            return true;
        } else if (KEY_PREDICTIVE_BACK.equals(key)) {
            boolean enabled = (Boolean) newValue;
            SystemProperties.set("persist.wm.debug.predictive_back", enabled ? "1" : "0");
            return true;
        } else if (KEY_TOUCH_ELASTICITY.equals(key)) {
            boolean enabled = (Boolean) newValue;
            SystemProperties.set("persist.sys.xephira.touch_elasticity", enabled ? "1" : "0");
            return true;
        }
        return false;
    }
}
