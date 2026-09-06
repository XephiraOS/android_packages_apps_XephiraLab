/*
 * Copyright (C) 2026 XephiraOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.xephira.lab.fragments;

import android.content.ContentResolver;
import android.content.Intent;
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

public class LockscreenFragment extends PreferenceFragmentCompat
        implements Preference.OnPreferenceChangeListener {

    private static final String KEY_DEPTH_ENABLED = "pref_depth_wallpaper_enabled";
    private static final String KEY_LAUNCH_DEPTH_STUDIO = "pref_launch_depth_studio";
    private static final String KEY_DEPTH_OCCLUSION = "pref_depth_occlusion_limit";
    private static final String KEY_DEPTH_PARALLAX = "pref_depth_parallax";
    private static final String KEY_CLOCK_STYLE = "pref_clock_style";
    private static final String KEY_SHORTCUT_LEFT = "pref_shortcut_left";
    private static final String KEY_SHORTCUT_RIGHT = "pref_shortcut_right";
    private static final String KEY_FLUID_MUSIC = "pref_fluid_music";

    private SwitchPreferenceCompat mDepthEnabledPref;
    private Preference mLaunchDepthStudioPref;
    private SeekBarPreference mDepthOcclusionPref;
    private SwitchPreferenceCompat mDepthParallaxPref;
    private ListPreference mClockStylePref;
    private ListPreference mShortcutLeftPref;
    private ListPreference mShortcutRightPref;
    private SwitchPreferenceCompat mFluidMusicPref;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.xephira_lab_lockscreen, rootKey);

        final ContentResolver cr = requireContext().getContentResolver();

        mDepthEnabledPref = findPreference(KEY_DEPTH_ENABLED);
        if (mDepthEnabledPref != null) {
            boolean enabled = Settings.Secure.getInt(cr, "lockscreen_depth_wallpaper_enabled", 1) == 1;
            mDepthEnabledPref.setChecked(enabled);
            mDepthEnabledPref.setOnPreferenceChangeListener(this);
        }

        mLaunchDepthStudioPref = findPreference(KEY_LAUNCH_DEPTH_STUDIO);
        if (mLaunchDepthStudioPref != null) {
            mLaunchDepthStudioPref.setOnPreferenceClickListener(pref -> {
                Intent intent = new Intent("com.android.customization.action.DEPTH_WALLPAPER_PICKER");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            });
        }

        mDepthOcclusionPref = findPreference(KEY_DEPTH_OCCLUSION);
        if (mDepthOcclusionPref != null) {
            int limit = Settings.Secure.getInt(cr, "lockscreen_depth_occlusion_limit", 30);
            mDepthOcclusionPref.setValue(limit);
            mDepthOcclusionPref.setOnPreferenceChangeListener(this);
        }

        mDepthParallaxPref = findPreference(KEY_DEPTH_PARALLAX);
        if (mDepthParallaxPref != null) {
            boolean parallax = Settings.Secure.getInt(cr, "lockscreen_depth_parallax_gyro", 1) == 1;
            mDepthParallaxPref.setChecked(parallax);
            mDepthParallaxPref.setOnPreferenceChangeListener(this);
        }

        mClockStylePref = findPreference(KEY_CLOCK_STYLE);
        if (mClockStylePref != null) {
            String style = Settings.Secure.getString(cr, "lockscreen_clock_style");
            if (style == null) style = "XEPHIRA_IOS_DEPTH";
            mClockStylePref.setValue(style);
            mClockStylePref.setOnPreferenceChangeListener(this);
        }

        mShortcutLeftPref = findPreference(KEY_SHORTCUT_LEFT);
        if (mShortcutLeftPref != null) {
            String left = Settings.Secure.getString(cr, "lockscreen_shortcut_left");
            if (left == null) left = "flashlight";
            mShortcutLeftPref.setValue(left);
            mShortcutLeftPref.setOnPreferenceChangeListener(this);
        }

        mShortcutRightPref = findPreference(KEY_SHORTCUT_RIGHT);
        if (mShortcutRightPref != null) {
            String right = Settings.Secure.getString(cr, "lockscreen_shortcut_right");
            if (right == null) right = "camera";
            mShortcutRightPref.setValue(right);
            mShortcutRightPref.setOnPreferenceChangeListener(this);
        }

        mFluidMusicPref = findPreference(KEY_FLUID_MUSIC);
        if (mFluidMusicPref != null) {
            boolean fluid = Settings.Secure.getInt(cr, "lockscreen_fluid_music_artwork", 1) == 1;
            mFluidMusicPref.setChecked(fluid);
            mFluidMusicPref.setOnPreferenceChangeListener(this);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final ContentResolver cr = requireContext().getContentResolver();
        final String key = preference.getKey();

        if (KEY_DEPTH_ENABLED.equals(key)) {
            boolean enabled = (Boolean) newValue;
            Settings.Secure.putInt(cr, "lockscreen_depth_wallpaper_enabled", enabled ? 1 : 0);
            SystemProperties.set("persist.sys.xephira.lockscreen_depth", enabled ? "1" : "0");
            return true;
        } else if (KEY_DEPTH_OCCLUSION.equals(key)) {
            int limit = (Integer) newValue;
            Settings.Secure.putInt(cr, "lockscreen_depth_occlusion_limit", limit);
            return true;
        } else if (KEY_DEPTH_PARALLAX.equals(key)) {
            boolean enabled = (Boolean) newValue;
            Settings.Secure.putInt(cr, "lockscreen_depth_parallax_gyro", enabled ? 1 : 0);
            return true;
        } else if (KEY_CLOCK_STYLE.equals(key)) {
            String style = (String) newValue;
            Settings.Secure.putString(cr, "lockscreen_clock_style", style);
            return true;
        } else if (KEY_SHORTCUT_LEFT.equals(key)) {
            String shortcut = (String) newValue;
            Settings.Secure.putString(cr, "lockscreen_shortcut_left", shortcut);
            return true;
        } else if (KEY_SHORTCUT_RIGHT.equals(key)) {
            String shortcut = (String) newValue;
            Settings.Secure.putString(cr, "lockscreen_shortcut_right", shortcut);
            return true;
        } else if (KEY_FLUID_MUSIC.equals(key)) {
            boolean enabled = (Boolean) newValue;
            Settings.Secure.putInt(cr, "lockscreen_fluid_music_artwork", enabled ? 1 : 0);
            return true;
        }
        return false;
    }
}
