package com.jonathansteele.reviewpreference

import android.app.Activity
import androidx.preference.PreferenceManager
import com.google.android.play.core.review.ReviewManagerFactory

class ReviewPreference(private val activity: Activity) {
    private val preferences = PreferenceManager.getDefaultSharedPreferences(activity)

    fun openTimes(times: Int = 5) {
        if (preferences.getBoolean("ratingShown", false)) {
            return
        }
        val openTimes = preferences.getInt("openTimes", 0) + 1
        preferences.edit().putInt("openTimes", openTimes).apply()
        showRating(openTimes >= times)
    }

    fun showRating(boolean: Boolean) {
        if (boolean) {
            val reviewManager = ReviewManagerFactory.create(activity)
            reviewManager.requestReviewFlow().addOnCompleteListener {
                if (it.isSuccessful) {
                    val flow = reviewManager.launchReviewFlow(activity, it.result)
                    flow.addOnCompleteListener {
                        preferences.edit().putBoolean("ratingShown", true).apply()
                    }
                }
            }
        }
    }
}