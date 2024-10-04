package com.example.androidsubmission

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class Biodata : AppCompatActivity(), View.OnClickListener {

    lateinit var buttonEmail : Button;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biodata)

        buttonEmail = findViewById(R.id.send_message)

        buttonEmail.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.send_message -> {
                val recipient = "bayufirmansyah.me@gmail.com"
                val subject = "Ajakan Kerja Sama Membangun Aplikasi"
                val message = "hai bayu. saya tertarik untuk melakukan kerjasama dengan anda dalam membangun sebuah aplikasi"

                val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                    var data = Uri.parse("mailto:$recipient")
                    putExtra(Intent.EXTRA_SUBJECT, subject)
                    putExtra(Intent.EXTRA_TEXT, message)
                }

                if (emailIntent.resolveActivity(packageManager) != null) {
                    startActivity(emailIntent)
                } else {
                    Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}