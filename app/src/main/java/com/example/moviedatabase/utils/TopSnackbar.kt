package com.example.moviedatabase.utils

import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.res.ResourcesCompat
import com.example.moviedatabase.R
import com.google.android.material.snackbar.Snackbar

object TopSnackbar {
    fun make(view: View, text: String, duration: Int): Snackbar {
        val parent = findSuitableParent(view) ?: throw IllegalArgumentException(
            "No suitable parent found from the given view. Please provide a valid view."
        )

        val snackbar = Snackbar.make(parent, text, duration)


        val snackbarView = snackbar.view
        val context = view.context

        val topMarginDp = 36
        val marginSideDp = 8
        val scale = context.resources.displayMetrics.density

        val topMarginPx = (topMarginDp * scale + 0.5f).toInt()
        val marginSidePx = (marginSideDp * scale + 0.5f).toInt()

        val params = snackbarView.layoutParams as ViewGroup.MarginLayoutParams
        if (params is CoordinatorLayout.LayoutParams) {
            params.gravity = Gravity.TOP
        }
        params.setMargins(marginSidePx, topMarginPx, marginSidePx, 0)
        snackbarView.layoutParams = params

        val textView = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        val typeface: Typeface? = ResourcesCompat.getFont(context, R.font.dmsans_regular)
        textView.typeface = typeface
        textView.setTextColor(Color.WHITE)

        return snackbar
    }

    private fun findSuitableParent(view: View): ViewGroup? {
        var currentView: View? = view
        var fallback: ViewGroup? = null

        do {
            if (currentView is CoordinatorLayout) {
                return currentView
            }

            if (currentView is FrameLayout) {
                fallback = currentView
            }

            if (currentView != null) {
                val parent = currentView.parent
                currentView = if (parent is View) parent else null
            }
        } while (currentView != null)

        return fallback
    }
}