package com.dicoding.mygithubapp

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dicoding.mygithubapp.ui.main.MainActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DarkModeTest {

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testOpenSettingAndToggleSwitch() {
        // Menunggu beberapa saat untuk memastikan aktivitas telah dimuat sepenuhnya
        Thread.sleep(2000)

        // Mengklik menu darkmode
        Espresso.onView(withId(R.id.dark_mode)).perform(click())

        // Menunggu beberapa saat setelah mengklik menu setting
        Thread.sleep(1000)

        // Mengklik switch tema
        Espresso.onView(withId(R.id.switch_theme)).perform(click())

        // Kembali ke halaman utama
        Espresso.pressBack()

        // Menunggu beberapa saat setelah kembali ke halaman utama
        Thread.sleep(1000)

        // Mengklik menu darkmode lagi
        Espresso.onView(withId(R.id.dark_mode)).perform(click())

        // Menunggu beberapa saat setelah mengklik menu setting
        Thread.sleep(1000)

        // Mengklik switch tema lagi untuk mengembalikan ke tema semula
        Espresso.onView(withId(R.id.switch_theme)).perform(click())

        // Kembali ke halaman utama
        Espresso.pressBack()
    }
}
