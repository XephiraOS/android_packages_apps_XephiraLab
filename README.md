# Xephira Lab (`packages/apps/XephiraLab`)

Flagship OxygenOS 17 Aquamorphic & Apple iOS 18 Spatial Liquid Glass modular customization suite for **XephiraOS** (Android 16 / LineageOS 23.2).

---

## 💎 Modular Sub-Menu Hub Architecture

Xephira Lab is organized into a clean, modern, multi-tier customization architecture with live telemetry and 5 dedicated sub-menus:

```text
Xephira Lab (Main Dashboard)
├── 🌟 Live Telemetry Hero Preview (Blur, 120Hz Spring, Curvature, RAM Boost status)
├── 🔮 Sub-Menu 1: Liquid Glass & Optics (OpticsFragment)
│   ├── Gaussian Blur Intensity (0 - 48 dp live slider)
│   ├── Blur Downscaling & Sample Rate (Optimized 2x, Quality 1x, Ultra 0.5x)
│   ├── Specular Edge Reflection & Stroke Brightness (10% - 100%)
│   ├── Prism Chromatic Aberration
│   └── Frosted Glass Micro-Grain Simulation
├── ⚡ Sub-Menu 2: Spatial Kinetic Physics (KineticFragment)
│   ├── 120Hz ProMotion Spring Dynamics
│   ├── Spring Damping Profiles (Fluid iOS Bouncy, Balanced Natural, Snappy OxygenOS)
│   ├── Spatial Predictive Back fluid morphing & scaling
│   └── Edge Over-scroll Rubber Banding elasticity
├── 📐 Sub-Menu 3: Continuous Curvature Studio (CurvatureFragment)
│   ├── Superellipse Geometry (Lamé curve n=4 Squircle, Apple G2 Continuity, Precision)
│   ├── Framework Global Corner Radius (24dp, 28dp, 32dp, 36dp)
│   └── Grouped Card Lateral Separation (12dp, 16dp, 20dp)
├── 🚀 Sub-Menu 4: Flux Silicon & Memory Turbo (FluxFragment)
│   ├── Liquid RAM Boost Virtual Expansion (Disabled, +4GB, +8GB, +12GB)
│   ├── Trinity Engine Frame-Pacing Governor for locked 120 FPS
│   ├── Hyper-Touch Sampling Rate (240Hz Standard, 360Hz Gaming, 480Hz Esports)
│   └── Silicon Thermal & Power Profile (Dynamic Liquid, Performance, Extreme Turbo)
└── 🎨 Sub-Menu 5: Aura Accent & Theming Studio (AuraFragment)
    ├── Signature Aura Presets (OnePlus Crimson #E60026, Aether Cyan #00F2FE, Amethyst #A855F7, Emerald #10B981, Amber #F59E0B)
    ├── Spatial Dark Depth (Deep Titanium Obsidian #0D111A, Pure OLED Midnight #000000, Frosted Slate #161C28)
    └── Liquid Glass Status Bar Capsule Pill
```

---

## 🛠 Integration into Settings

Symlinked into LineageOS / XephiraOS Settings:
```bash
ln -s ../../../packages/apps/XephiraLab packages/apps/Settings/packages/XephiraLab
```
Exposed on Settings homepage under the Aether Core hero card at order `-180`.

---

## 📄 License

Licensed under the Apache License, Version 2.0. Copyright (C) 2026 XephiraOS Project.
