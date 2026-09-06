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

public class AuraFragment extends PreferenceFragmentCompat
        implements Preference.OnPreferenceChangeListener {

    private static final String KEY_ACCENT_PRESET = "pref_accent_preset";
    private static final String KEY_DARK_MODE_DEPTH = "pref_dark_mode_depth";
    private static final String KEY_STATUSBAR_PILL = "pref_statusbar_pill";

    private ListPreference mAccentPresetPref;
    private ListPreference mDarkModeDepthPref;
    private SwitchPreferenceCompat mStatusbarPillPref;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.xephira_lab_aura, rootKey);

        mAccentPresetPref = findPreference(KEY_ACCENT_PRESET);
        if (mAccentPresetPref != null) {
            String accent = SystemProperties.get("persist.sys.xephira.accent_preset", "crimson");
            mAccentPresetPref.setValue(accent);
            mAccentPresetPref.setOnPreferenceChangeListener(this);
        }

        mDarkModeDepthPref = findPreference(KEY_DARK_MODE_DEPTH);
        if (mDarkModeDepthPref != null) {
            String depth = SystemProperties.get("persist.sys.xephira.dark_depth", "obsidian");
            mDarkModeDepthPref.setValue(depth);
            mDarkModeDepthPref.setOnPreferenceChangeListener(this);
        }

        mStatusbarPillPref = findPreference(KEY_STATUSBAR_PILL);
        if (mStatusbarPillPref != null) {
            mStatusbarPillPref.setChecked(SystemProperties.getBoolean("persist.sys.xephira.statusbar_pill", true));
            mStatusbarPillPref.setOnPreferenceChangeListener(this);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final String key = preference.getKey();
        if (KEY_ACCENT_PRESET.equals(key)) {
            String accent = (String) newValue;
            SystemProperties.set("persist.sys.xephira.accent_preset", accent);
            return true;
        } else if (KEY_DARK_MODE_DEPTH.equals(key)) {
            String depth = (String) newValue;
            SystemProperties.set("persist.sys.xephira.dark_depth", depth);
            return true;
        } else if (KEY_STATUSBAR_PILL.equals(key)) {
            boolean enabled = (Boolean) newValue;
            SystemProperties.set("persist.sys.xephira.statusbar_pill", enabled ? "1" : "0");
            return true;
        }
        return false;
    }
}
