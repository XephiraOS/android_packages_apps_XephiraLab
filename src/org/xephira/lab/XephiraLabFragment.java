/*
 * Copyright (C) 2026 XephiraOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.xephira.lab;

import android.os.Bundle;
import android.os.SystemProperties;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;

public class XephiraLabFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.xephira_lab_main, rootKey);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateHeroTelemetry(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getView() != null) {
            updateHeroTelemetry(getView());
        }
    }

    private void updateHeroTelemetry(View root) {
        final TextView blurView = root.findViewById(R.id.hero_blur_val);
        if (blurView != null) {
            int blur = SystemProperties.getInt("persist.sys.xephira.blur_radius", 28);
            blurView.setText(blur + " dp");
        }

        final TextView springView = root.findViewById(R.id.hero_spring_val);
        if (springView != null) {
            boolean spring = SystemProperties.getBoolean("persist.sys.xephira.spring_dynamics", true);
            springView.setText(spring ? "120 Hz" : "Standard");
        }

        final TextView curvatureView = root.findViewById(R.id.hero_curvature_val);
        if (curvatureView != null) {
            String curv = SystemProperties.get("persist.sys.xephira.curvature", "superellipse_n4");
            curvatureView.setText("superellipse_n4".equals(curv) ? "n=4" : "G2");
        }

        final TextView ramView = root.findViewById(R.id.hero_ram_val);
        if (ramView != null) {
            String boost = SystemProperties.get("persist.sys.xephira.ram_boost", "12");
            ramView.setText("0".equals(boost) ? "Off" : "+" + boost + " GB");
        }
    }
}
