package com.github.inoles.reviewpreference

import android.app.Activity
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.google.android.play.core.review.ReviewManagerFactory

/**
 * A helper class to manage in-app review prompts using Google Play Core Library.
 *
 * @param activity The activity used to show the review flow and manage preferences.
 */
class ReviewPreference(
    private val activity: Activity,
) {
    // Shared preferences instance
    private val preferences = PreferenceManager.getDefaultSharedPreferences(activity)

    // Preference keys
    companion object {
        private const val KEY_OPEN_TIMES = "openTimes"
        private const val KEY_RATING_SHOWN = "ratingShown"
    }

    /**
     * Tracks app opens and triggers the review prompt if the threshold is reached.
     *
     * @param threshold The number of app opens required to show the review prompt.
     */
    fun trackAppOpens(
        threshold: Int = 5,
        resetAfterPrompt: Boolean = true,
    ) {
        // Validate the threshold to avoid invalid configurations
        if (threshold <= 0) {
            throw IllegalArgumentException("Threshold must be greater than 0.")
        }

        // Check if the review prompt has already been shown
        if (preferences.getBoolean(KEY_RATING_SHOWN, false)) {
            return
        }

        // Increment and store the open times count
        val openTimes = preferences.getInt(KEY_OPEN_TIMES, 0) + 1
        preferences.edit { putInt(KEY_OPEN_TIMES, openTimes) }

        // Check if the threshold has been reached
        if (openTimes >= threshold) {
            showReviewPrompt()

            // Optionally reset the open count after showing the review prompt
            if (resetAfterPrompt) {
                preferences.edit { putInt(KEY_OPEN_TIMES, 0) }
            }
        }
    }

    /**
     * Initiates the in-app review prompt if conditions are met.
     */
    private fun showReviewPrompt() {
        val reviewManager = ReviewManagerFactory.create(activity)

        // Request the review flow
        reviewManager.requestReviewFlow().addOnCompleteListener { requestTask ->
            if (requestTask.isSuccessful) {
                val reviewInfo = requestTask.result
                // Launch the review flow
                reviewManager.launchReviewFlow(activity, reviewInfo).addOnCompleteListener {
                    if (it.isSuccessful) {
                        // Mark review as shown after completing the flow
                        preferences.edit { putBoolean(KEY_RATING_SHOWN, true) }
                    } else {
                        it.exception?.printStackTrace()
                    }
                }
            } else {
                // Handle the case where the review flow request fails
                requestTask.exception?.printStackTrace()
            }
        }
    }
}
