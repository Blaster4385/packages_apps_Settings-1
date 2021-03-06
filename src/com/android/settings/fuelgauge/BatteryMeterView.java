/*
 * Copyright (C) 2017 The Android Open Source Project
 * Copyright (C) 2021 PixelBlaster-OS 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.fuelgauge;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import com.android.settings.R;
import com.android.settingslib.graph.BatteryMeterDrawableBase;

public class BatteryMeterView extends ImageView {
    ColorFilter mAccentColorFilter;
    BatteryMeterDrawable mDrawable;
    ColorFilter mErrorColorFilter;
    ColorFilter mForegroundColorFilter;

    public static class BatteryMeterDrawable extends BatteryMeterDrawableBase {
        private final int mIntrinsicHeight;
        private final int mIntrinsicWidth;

        public BatteryMeterDrawable(Context context, int i) {
            super(context, i);
            this.mIntrinsicWidth = context.getResources().getDimensionPixelSize(R.dimen.battery_meter_width);
            this.mIntrinsicHeight = context.getResources().getDimensionPixelSize(R.dimen.battery_meter_height);
        }

        public BatteryMeterDrawable(Context context, int i, int i2, int i3) {
            super(context, i);
            this.mIntrinsicWidth = i2;
            this.mIntrinsicHeight = i3;
        }

        public int getIntrinsicWidth() {
            return this.mIntrinsicWidth;
        }

        public int getIntrinsicHeight() {
            return this.mIntrinsicHeight;
        }

        public void setWarningColorFilter(ColorFilter colorFilter) {
            this.mWarningTextPaint.setColorFilter(colorFilter);
        }

        public void setBatteryColorFilter(ColorFilter colorFilter) {
            this.mFramePaint.setColorFilter(colorFilter);
            this.mBatteryPaint.setColorFilter(colorFilter);
            this.mBoltPaint.setColorFilter(colorFilter);
        }
    }

    public BatteryMeterView(Context context) {
        this(context, null, 0);
    }

    public BatteryMeterView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BatteryMeterView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        int color = context.getColor(R.color.meter_background_color);
        this.mAccentColorFilter = new PorterDuffColorFilter(context.getColor(R.color.op_control_accent_color_green), Mode.SRC_IN);
        this.mErrorColorFilter = new PorterDuffColorFilter(context.getColor(R.color.battery_icon_color_error), Mode.SRC_IN);
        BatteryMeterDrawable batteryMeterDrawable = new BatteryMeterDrawable(context, color);
        this.mDrawable = batteryMeterDrawable;
        batteryMeterDrawable.setShowPercent(false);
        this.mDrawable.setBatteryColorFilter(this.mAccentColorFilter);
        this.mDrawable.setWarningColorFilter(new PorterDuffColorFilter(-1, Mode.SRC_IN));
        setBackground(this.mDrawable);
    }

    public void setBatteryLevel(int i) {
        this.mDrawable.setBatteryLevel(i);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("mDrawable.getCriticalLevel() = ");
        stringBuilder.append(this.mDrawable.getCriticalLevel());
        Log.d("BatteryMeterView", stringBuilder.toString());
        if (i < this.mDrawable.getCriticalLevel()) {
            this.mDrawable.setBatteryColorFilter(this.mErrorColorFilter);
        } else {
            this.mDrawable.setBatteryColorFilter(this.mAccentColorFilter);
        }
    }

    public int getBatteryLevel() {
        return this.mDrawable.getBatteryLevel();
    }

    public void setCharging(boolean z) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setCharging = ");
        stringBuilder.append(z);
        Log.d("BatteryMeterView", stringBuilder.toString());
        if (z) {
        } else {
            setImageResource(0);
        }
        this.mDrawable.setCharging(z);
        postInvalidate();
    }

    public boolean getCharging() {
        return this.mDrawable.getCharging();
    }
}
