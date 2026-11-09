package br.com.fiap.greenstock.ui.onboarding

import android.content.Context

class OnboardingPreferences(context: Context) {

    private val preferences = context.getSharedPreferences(
        "greenstock_preferences",
        Context.MODE_PRIVATE
    )

    fun isOnboardingCompleted(): Boolean {
        return preferences.getBoolean("onboarding_completed", false)
    }

    fun setOnboardingCompleted() {
        preferences.edit()
            .putBoolean("onboarding_completed", true)
            .apply()
    }
}