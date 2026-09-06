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

import org.xephira.lab.R;

public class CurvatureFragment extends PreferenceFragmentCompat
        implements Preference.OnPreferenceChangeListener {

    private static final String KEY_CURVATURE = "pref_curvature_style";
    private static final String KEY_CORNER_RADIUS = "pref_corner_radius";
    private static final String KEY_CARD_MARGIN = "pref_card_lateral_margin";

    private ListPreference mCurvaturePref;
    private ListPreference mCornerRadiusPref;
    private ListPreference mCardMarginPref;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.xephira_lab_curvature, rootKey);

        mCurvaturePref = findPreference(KEY_CURVATURE);
        if (mCurvaturePref != null) {
            String curv = SystemProperties.get("persist.sys.xephira.curvature", "superellipse_n4");
            mCurvaturePref.setValue(curv);
            mCurvaturePref.setOnPreferenceChangeListener(this);
        }

        mCornerRadiusPref = findPreference(KEY_CORNER_RADIUS);
        if (mCornerRadiusPref != null) {
            String radius = SystemProperties.get("persist.sys.xephira.corner_radius", "32");
            mCornerRadiusPref.setValue(radius);
            mCornerRadiusPref.setOnPreferenceChangeListener(this);
        }

        mCardMarginPref = findPreference(KEY_CARD_MARGIN);
        if (mCardMarginPref != null) {
            String margin = SystemProperties.get("persist.sys.xephira.card_margin", "16");
            mCardMarginPref.setValue(margin);
            mCardMarginPref.setOnPreferenceChangeListener(this);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final String key = preference.getKey();
        if (KEY_CURVATURE.equals(key)) {
            String curv = (String) newValue;
            SystemProperties.set("persist.sys.xephira.curvature", curv);
            return true;
        } else if (KEY_CORNER_RADIUS.equals(key)) {
            String radius = (String) newValue;
            SystemProperties.set("persist.sys.xephira.corner_radius", radius);
            return true;
        } else if (KEY_CARD_MARGIN.equals(key)) {
            String margin = (String) newValue;
            SystemProperties.set("persist.sys.xephira.card_margin", margin);
            return true;
        }
        return false;
    }
}
