/*
 * Copyright (C) 2020 The Android Open Source Project
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

package com.android.settings.development.storage;

import static com.google.common.truth.Truth.assertThat;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.app.blob.BlobStoreManager;
import android.content.Context;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.util.ReflectionHelpers;

// TODO: add more detailed tests for the shared data screens
@RunWith(RobolectricTestRunner.class)
public class SharedDataPreferenceControllerTest {

    @Mock
    private Context mContext;
    @Mock
    private BlobStoreManager mBlobStoreManager;
    @Mock
    private Preference mPreference;
    @Mock
    private PreferenceScreen mScreen;

    private SharedDataPreferenceController mController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mController = spy(new SharedDataPreferenceController(mContext));

        when(mScreen.findPreference(mController.getPreferenceKey())).thenReturn(mPreference);
        mController.displayPreference(mScreen);
    }

    @Test
    public void updateState_BlobManagerIsNotNull_preferenceIsEnabled() {
        ReflectionHelpers.setField(mController, "mBlobStoreManager", mBlobStoreManager);
        mController.updateState(mPreference);

        verify(mPreference).setEnabled(true);
        assertThat(mPreference.getSummary())
                .isEqualTo(mContext.getString(R.string.shared_data_summary));
    }

    @Test
    public void updateState_BlobManagerIsNull_preferenceIsDisabled() {
        ReflectionHelpers.setField(mController, "mBlobStoreManager", null);
        mController.updateState(mPreference);

        verify(mPreference).setEnabled(false);
    }
}
