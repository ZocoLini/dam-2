package com.example

import android.app.Person
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fragments.CniSensorIAFragment


class MainActivity : AppCompatActivity()
{
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>;

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        resultLauncher = this.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode != RESULT_OK) return@registerForActivityResult;
        }

        val fragments = supportFragmentManager;

        val emailReceiver = fragments.findFragmentById(R.id.email_receiver) as CniSensorIAFragment;
        val emailSubject = fragments.findFragmentById(R.id.email_subject) as CniSensorIAFragment;
        val emailBody = fragments.findFragmentById(R.id.email_body) as CniSensorIAFragment;

        val alertsListView = findViewById<ListView>(R.id.past_alerts);
    }

    //class Adapter(context: Context, datos: ArrayAdapter<Person>) : ArrayAdapter<Person>(
    //    context,
    //    R.layout.activity_main
    //)
    //{
//
    //    private class ViewHolder
    //    {
    //        lateinit var foto: ImageView;
    //        lateinit var tvNombre: TextView;
    //        lateinit var mute: ImageView;
    //    }
//
    //    var context: Context = context
    //        get() = field
    //        set(value)
    //        {
    //            field = value
    //        }
//
    //    var datos: ArrayAdapter<Person> = datos
    //        get() = field
    //        set(value)
    //        {
    //            field = value
    //        }
//
    //    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View
    //    {
    //        var viewHolder: ViewHolder = ViewHolder();
//
    //        val contacto = datos.getItem(position);
//
    //        var view: View? = convertView;
//
    //        if (view == null)
    //        {
    //            view = LayoutInflater
    //                .from(context)
    //                .inflate(R.layout.activity_main, null);
    //            // viewHolder = RecyclerView.ViewHolder();
    //            // viewHolder.foto = view.findViewById(R.id.foto);
    //            // viewHolder.tvNombre = view.findViewById(R.id.nombre);
    //            // viewHolder.mute = view.findViewById(R.id.mute);
    //            view!!.tag = viewHolder;
    //        }
    //        else
    //        {
    //            viewHolder = view.tag as ViewHolder;
    //        }
//
    //        // String strNombre = contacto . getId () + ".jpg";
    //        // // Creamos una carpeta fotos en la carpeta /data/data/paquete/files/
    //        // String strRuta = contexto . getFilesDir ().getAbsolutePath() + "/fotos/" +
        // strNombre;
    //        // File ruta = new File(strRuta);
    //        // if (ruta.exists())
    //        // {
    //        //     Bitmap bitmap = BitmapFactory . decodeFile (strRuta);
    //        //     viewHolder.foto.setImageBitmap(bitmap);
    //        // }
    //        // else
    //        //     viewHolder.foto.setImageResource(R.drawable.contacto);
    //        // viewHolder.tvNombre.setText(contacto.toString());
    //        // if (contacto.isMute())
    //        //     viewHolder.mute.setImageResource(R.drawable.mute);
    //        // else
    //        //     viewHolder.mute.setImageDrawable(null);
//
    //        return convertView!!;
    //    }
    //}

}