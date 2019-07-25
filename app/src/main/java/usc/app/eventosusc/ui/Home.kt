package usc.app.eventosusc.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.jsoup.Jsoup
import usc.app.eventosusc.volley.MySingleton
import usc.app.eventosusc.R
import usc.app.eventosusc.adaptador.INTERFACE_click
import usc.app.eventosusc.adaptador.RvEvento
import usc.app.eventosusc.objetos.Eventos

class Home : AppCompatActivity() {

    var recyclerView : RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.lista)

        recyclerView?.layoutManager = GridLayoutManager( this, 2 )
        recyclerView?.setHasFixedSize(true)

        doAsync {
            val html = Jsoup
                .connect("https://usc.edu.co/index.php/noticias/itemlist/category/59-banner-eventos")
                .get()

            val data = html.select("div[class=catItemView catItemBlogView groupLeading]")

            val lista : ArrayList<Eventos> = ArrayList()

            for( item in data )
            {
                val obj = Eventos()
                obj.titulo = item.select("h3[class=catItemTitle]").select("a").text()
                if ( item.select("img").size > 1 )
                    obj.foto = "https://usc.edu.co" + item.select("img").eq( 1 ).attr("src")
                else
                    obj.foto = "https://usc.edu.co" + item.select("img").attr("src")
                lista.add( obj )
            }

            uiThread {
                adaptadorRV( lista )
            }
        }

    }

    private fun adaptadorRV(lista : ArrayList<Eventos> )
    {
        val adaptador = RvEvento(
            lista,
            INTERFACE_click { _: View, i: Int ->
                run {
                    val visor = Intent( this, Visor::class.java )
                    visor.putExtra("foto", lista[ i ].foto )
                    visor.putExtra("titulo", lista[ i ].titulo )
                    startActivity( visor )
                }
            }
        )
        recyclerView?.adapter = adaptador
        ConsolaDebug("usc_debug", "size: " + lista.size )
    }

    fun ConsolaDebug(str : String, msg : String )
    {
        Log.d("usc_debug", "$str -> $msg");
    }

}
