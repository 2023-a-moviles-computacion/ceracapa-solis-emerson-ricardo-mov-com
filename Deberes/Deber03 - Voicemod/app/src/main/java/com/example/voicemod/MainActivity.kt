package com.example.voicemod

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private var popupWindow: PopupWindow? = null
    private var darkOverlay: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupLongClickListeners()

        val botonConfiguracion = findViewById<Button>(R.id.Settings)
        botonConfiguracion.setOnClickListener {irActividad(Configuracion::class.java) }
    }

    private fun setupLongClickListeners() {
        val button1 = findViewById<Button>(R.id.btnClean)
        val button2 = findViewById<Button>(R.id.btnCartoon)
        val button3 = findViewById<Button>(R.id.btnRandom)
        val button4 = findViewById<Button>(R.id.btnStopSound)
        val button5 = findViewById<Button>(R.id.btnHearMS)
        val button6 = findViewById<Button>(R.id.btnMute)



        val allButtons: List<Button> = listOf(button1,button2,button3,button4,button5,button6) // Add all your buttons here

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

        darkOverlay = View(this)
        darkOverlay?.setBackgroundColor(resources.getColor(android.R.color.black))
        darkOverlay?.alpha = 0.5f
        container.addView(darkOverlay)

        // Set position of popup
        popupWindow?.showAtLocation(anchorView, Gravity.BOTTOM, 0, 0)

        popupWindow?.setOnDismissListener {
            container.removeView(darkOverlay)
        }
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}
