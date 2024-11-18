package es.ies.chandomonte.examensorpresa

import android.content.res.loader.ResourcesLoader
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import es.ies.chandomonte.examensorpresa.fragments.FilterListFragment
import java.io.FileReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.ArrayList

class MainActivity : AppCompatActivity()
{
    lateinit var textView: TextView;

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

        textView = findViewById(R.id.text_view)!!;
    }

    override fun onStart()
    {
        super.onStart()
        val filterListFragment: FilterListFragment<String> =
            supportFragmentManager.findFragmentById(R.id.filter_list_fragment)!! as
                    FilterListFragment<String>

        filterListFragment.setActions(
            loadPalabras(),

            object : FilterListFragment.FilterListActions<String>
            {
                override fun onSelectedItem(item: String, fragment: FilterListFragment<String>)
                {
                    textView.text = item;
                }
            }
        );
    }

    private fun loadPalabras(): List<String>
    {
        println(this.javaClass.getResource("/"))

        val reader = InputStreamReader(resources.openRawResource(R.raw.palabras));

        val list = reader.readLines();

        reader.close();

        return list;
    }
}