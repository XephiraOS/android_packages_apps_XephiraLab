/*
 * Copyright (C) 2026 XephiraOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.xephira.lab.fragments;

import android.content.ContentResolver;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import org.xephira.lab.R;

public class GesturesFragment extends PreferenceFragmentCompat
        implements Preference.OnPreferenceChangeListener {

    private static final String KEY_THREE_FINGER = "pref_three_finger_screenshot";
    private static final String KEY_DOUBLE_TAP = "pref_double_tap_sleep";
    private static final String KEY_TORCH_POWER = "pref_torch_power";
    private static final String KEY_HIDE_NAV_PILL = "pref_hide_nav_pill";
    private static final String KEY_SCREEN_OFF_MUSIC = "pref_screen_off_music";

    private SwitchPreferenceCompat mThreeFingerPref;
    private SwitchPreferenceCompat mDoubleTapPref;
    private SwitchPreferenceCompat mTorchPowerPref;
    private SwitchPreferenceCompat mHideNavPillPref;
    private SwitchPreferenceCompat mScreenOffMusicPref;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.xephira_lab_gestures, rootKey);

        final ContentResolver cr = requireContext().getContentResolver();

        mThreeFingerPref = findPreference(KEY_THREE_FINGER);
        if (mThreeFingerPref != null) {
            boolean enabled = Settings.System.getInt(cr, "three_finger_gesture", 1) == 1;
            mThreeFingerPref.setChecked(enabled);
            mThreeFingerPref.setOnPreferenceChangeListener(this);
        }

        mDoubleTapPref = findPreference(KEY_DOUBLE_TAP);
        if (mDoubleTapPref != null) {
            boolean enabled = Settings.System.getInt(cr, "double_tap_sleep_gesture", 1) == 1;
            mDoubleTapPref.setChecked(enabled);
            mDoubleTapPref.setOnPreferenceChangeListener(this);
        }

        mTorchPowerPref = findPreference(KEY_TORCH_POWER);
        if (mTorchPowerPref != null) {
            boolean enabled = Settings.System.getInt(cr, "torch_long_press_power_gesture", 1) == 1;
            mTorchPowerPref.setChecked(enabled);
            mTorchPowerPref.setOnPreferenceChangeListener(this);
        }

        mHideNavPillPref = findPreference(KEY_HIDE_NAV_PILL);
        if (mHideNavPillPref != null) {
            boolean enabled = Settings.System.getInt(cr, "navigation_bar_hide_pill", 0) == 1;
            mHideNavPillPref.setChecked(enabled);
            mHideNavPillPref.setOnPreferenceChangeListener(this);
        }

        mScreenOffMusicPref = findPreference(KEY_SCREEN_OFF_MUSIC);
        if (mScreenOffMusicPref != null) {
            boolean enabled = Settings.System.getInt(cr, "volbtn_music_controls", 1) == 1;
            mScreenOffMusicPref.setChecked(enabled);
            mScreenOffMusicPref.setOnPreferenceChangeListener(this);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final ContentResolver cr = requireContext().getContentResolver();
        final String key = preference.getKey();

        if (KEY_THREE_FINGER.equals(key)) {
            boolean enabled = (Boolean) newValue;
            Settings.System.putInt(cr, "three_finger_gesture", enabled ? 1 : 0);
            return true;
        } else if (KEY_DOUBLE_TAP.equals(key)) {
            boolean enabled = (Boolean) newValue;
            Settings.System.putInt(cr, "double_tap_sleep_gesture", enabled ? 1 : 0);
            return true;
        } else if (KEY_TORCH_POWER.equals(key)) {
            boolean enabled = (Boolean) newValue;
            Settings.System.putInt(cr, "torch_long_press_power_gesture", enabled ? 1 : 0);
            return true;
        } else if (KEY_HIDE_NAV_PILL.equals(key)) {
            boolean enabled = (Boolean) newValue;
            Settings.System.putInt(cr, "navigation_bar_hide_pill", enabled ? 1 : 0);
            return true;
        } else if (KEY_SCREEN_OFF_MUSIC.equals(key)) {
            boolean enabled = (Boolean) newValue;
            Settings.System.putInt(cr, "volbtn_music_controls", enabled ? 1 : 0);
            return true;
        }
        return false;
    }
}
