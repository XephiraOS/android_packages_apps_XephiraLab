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

public class StatusBarFragment extends PreferenceFragmentCompat
        implements Preference.OnPreferenceChangeListener {

    private static final String KEY_ISLAND = "pref_statusbar_island";
    private static final String KEY_ONEPLUS_RED = "pref_statusbar_oneplus_red";
    private static final String KEY_XEPHIRA_LOGO = "pref_statusbar_xephira_logo";
    private static final String KEY_BATTERY_STYLE = "pref_battery_style";
    private static final String KEY_BATTERY_PERCENT = "pref_battery_percent";
    private static final String KEY_NETWORK_TRAFFIC = "pref_network_traffic";
    private static final String KEY_COMBINED_SIGNAL = "pref_combined_signal";
    private static final String KEY_CORNER_PADDING = "pref_statusbar_corner_padding";

    private SwitchPreferenceCompat mIslandPref;
    private SwitchPreferenceCompat mOnePlusRedPref;
    private SwitchPreferenceCompat mXephiraLogoPref;
    private ListPreference mBatteryStylePref;
    private ListPreference mBatteryPercentPref;
    private SwitchPreferenceCompat mNetworkTrafficPref;
    private SwitchPreferenceCompat mCombinedSignalPref;
    private SeekBarPreference mCornerPaddingPref;

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.xephira_lab_statusbar, rootKey);

        final ContentResolver cr = requireContext().getContentResolver();

        mIslandPref = findPreference(KEY_ISLAND);
        if (mIslandPref != null) {
            mIslandPref.setChecked(SystemProperties.getBoolean("persist.sys.xephira.statusbar_island", true));
            mIslandPref.setOnPreferenceChangeListener(this);
        }

        mOnePlusRedPref = findPreference(KEY_ONEPLUS_RED);
        if (mOnePlusRedPref != null) {
            boolean enabled = Settings.System.getInt(cr, "status_bar_clock_oneplus_red", 1) == 1;
            mOnePlusRedPref.setChecked(enabled);
            mOnePlusRedPref.setOnPreferenceChangeListener(this);
        }

        mXephiraLogoPref = findPreference(KEY_XEPHIRA_LOGO);
        if (mXephiraLogoPref != null) {
            boolean enabled = Settings.System.getInt(cr, "status_bar_xephira_logo_style", 1) == 1;
            mXephiraLogoPref.setChecked(enabled);
            mXephiraLogoPref.setOnPreferenceChangeListener(this);
        }

        mBatteryStylePref = findPreference(KEY_BATTERY_STYLE);
        if (mBatteryStylePref != null) {
            String style = Settings.System.getString(cr, "status_bar_battery_style");
            if (style == null) style = "ios_pill";
            mBatteryStylePref.setValue(style);
            mBatteryStylePref.setOnPreferenceChangeListener(this);
        }

        mBatteryPercentPref = findPreference(KEY_BATTERY_PERCENT);
        if (mBatteryPercentPref != null) {
            String percent = Settings.System.getString(cr, "status_bar_show_battery_percent");
            if (percent == null) percent = "inside";
            mBatteryPercentPref.setValue(percent);
            mBatteryPercentPref.setOnPreferenceChangeListener(this);
        }

        mNetworkTrafficPref = findPreference(KEY_NETWORK_TRAFFIC);
        if (mNetworkTrafficPref != null) {
            boolean traffic = Settings.System.getInt(cr, "network_traffic_state", 1) == 1;
            mNetworkTrafficPref.setChecked(traffic);
            mNetworkTrafficPref.setOnPreferenceChangeListener(this);
        }

        mCombinedSignalPref = findPreference(KEY_COMBINED_SIGNAL);
        if (mCombinedSignalPref != null) {
            boolean combined = Settings.System.getInt(cr, "status_bar_combined_signal_icons", 0) == 1;
            mCombinedSignalPref.setChecked(combined);
            mCombinedSignalPref.setOnPreferenceChangeListener(this);
        }

        mCornerPaddingPref = findPreference(KEY_CORNER_PADDING);
        if (mCornerPaddingPref != null) {
            int pad = Settings.System.getInt(cr, "status_bar_padding_horizontal", 16);
            mCornerPaddingPref.setValue(pad);
            mCornerPaddingPref.setOnPreferenceChangeListener(this);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final ContentResolver cr = requireContext().getContentResolver();
        final String key = preference.getKey();

        if (KEY_ISLAND.equals(key)) {
            boolean enabled = (Boolean) newValue;
            SystemProperties.set("persist.sys.xephira.statusbar_island", enabled ? "1" : "0");
            return true;
        } else if (KEY_ONEPLUS_RED.equals(key)) {
            boolean enabled = (Boolean) newValue;
            Settings.System.putInt(cr, "status_bar_clock_oneplus_red", enabled ? 1 : 0);
            return true;
        } else if (KEY_XEPHIRA_LOGO.equals(key)) {
            boolean enabled = (Boolean) newValue;
            Settings.System.putInt(cr, "status_bar_xephira_logo_style", enabled ? 1 : 0);
            return true;
        } else if (KEY_BATTERY_STYLE.equals(key)) {
            String style = (String) newValue;
            Settings.System.putString(cr, "status_bar_battery_style", style);
            return true;
        } else if (KEY_BATTERY_PERCENT.equals(key)) {
            String percent = (String) newValue;
            Settings.System.putString(cr, "status_bar_show_battery_percent", percent);
            return true;
        } else if (KEY_NETWORK_TRAFFIC.equals(key)) {
            boolean enabled = (Boolean) newValue;
            Settings.System.putInt(cr, "network_traffic_state", enabled ? 1 : 0);
            return true;
        } else if (KEY_COMBINED_SIGNAL.equals(key)) {
            boolean enabled = (Boolean) newValue;
            Settings.System.putInt(cr, "status_bar_combined_signal_icons", enabled ? 1 : 0);
            return true;
        } else if (KEY_CORNER_PADDING.equals(key)) {
            int pad = (Integer) newValue;
            Settings.System.putInt(cr, "status_bar_padding_horizontal", pad);
            return true;
        }
        return false;
    }
}
