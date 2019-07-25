package usc.app.eventosusc.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import usc.app.eventosusc.R

class Visor : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.visor)

        val titulo : TextView = findViewById( R.id.eventoTitulo )
        val foto : ImageView = findViewById( R.id.eventoFoto )

        titulo.text = intent.extras?.getString("titulo", "Sin Titulo")

        Picasso
            .get()
            .load( intent.extras?.getString("foto", "" ) )
            .resize(0, 800)
            .into( foto )

    }
}
