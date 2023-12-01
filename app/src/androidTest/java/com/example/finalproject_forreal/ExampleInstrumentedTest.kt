package com.example.finalproject_forreal

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.finalproject_forreal.data.repository.AppPreferences

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(MockitoJUnitRunner::class)
class ExampleInstrumentedTest {

    @Mock val mockContext: Context = mock()

    @Mock val mockPrefs: SharedPreferences = mock()

    @Mock val mockEditor: SharedPreferences.Editor = mock()

    @Before
    @Throws(Exception ::class)
    fun before() {
        Mockito.`when`(mockContext.getSharedPreferences(anyString(), anyInt()))
            .thenReturn(mockPrefs)
        Mockito.`when`(mockContext.getSharedPreferences(anyString(), anyInt()).edit())
            .thenReturn(mockEditor)
        Mockito.`when`(mockPrefs.getString("favorite", "2m2"))
            .thenReturn("2m2")
        Mockito.`when`(mockPrefs.contains("2m2"))
            .thenReturn(true)
        Mockito.`when`(mockPrefs.contains("abc"))
            .thenReturn(false)
    }
    @Test
    fun checkIsInList() {
        assertTrue(mockPrefs.contains(mockPrefs.getString("favorite", "2m2")))
    }
    @Test
    fun checkIsNotInList() {
        assertFalse(mockPrefs.contains(mockPrefs.getString("favorite", "abc")))
    }
}