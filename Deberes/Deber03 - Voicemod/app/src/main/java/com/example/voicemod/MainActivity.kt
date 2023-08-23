package com.example.voicemod

import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private var popupWindow: PopupWindow? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupLongClickListeners()
    }

    private fun setupLongClickListeners() {
        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)

        val allButtons: List<Button> = listOf(button1, button2, button3) // Add all your buttons here

        allButtons.forEach { button ->
            button.setOnLongClickListener {
                showPopupMenu(it)
                true
            }
        }
    }

    private fun showPopupMenu(anchorView: View) {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.menu_layout, null)

        popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        // Darken the background behind the popup
        val container = findViewById<ViewGroup>(android.R.id.content)
        val darkOverlay = View(this)
        darkOverlay.setBackgroundColor(resources.getColor(android.R.color.black))
        darkOverlay.alpha = 0.5f
        container.addView(darkOverlay)

        // Set position of popup
        popupWindow?.showAtLocation(anchorView, Gravity.BOTTOM, 0, 0)

        // Remove the popup and dark overlay after 3 seconds
        Handler().postDelayed({
            popupWindow?.dismiss()
            container.removeView(darkOverlay)
        }, 3000)
    }
}
