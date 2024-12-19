package es.ies.chandomonte.examensorpresa.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.fragment.app.Fragment
import es.ies.chandomonte.examensorpresa.R

class FilterListFragment<T> : Fragment()
{
    private lateinit var filterText: EditText;
    private lateinit var filterList: ListView;

    private lateinit var items: List<T>;

    private var actions: FilterListActions<T> = object : FilterListActions<T>
    {
        override fun onSelectedItem(item: T, fragment: FilterListFragment<T>) {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_filter_list, container, false)

        filterText = view.findViewById(R.id.filter_text)!!;
        filterList = view.findViewById(R.id.filter_list)!!;

        filterText.addTextChangedListener(object : TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?)
            {
                val filteredItems = items.filter { item ->
                    item
                        .toString()
                        .contains(s.toString())
                };

                filterList.adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    filteredItems
                );
            }
        })

        filterList.setOnItemClickListener { _, _, position, _ ->
            actions.onSelectedItem(filterList.adapter.getItem(position) as T, this);
        };

        return view;
    }

    fun setActions(items: List<T>, actions: FilterListActions<T>)
    {
        this.actions = actions;

        this.items = items;

        filterList.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            items
        );
    }

    interface FilterListActions<T>
    {
        fun onSelectedItem(item: T, fragment: FilterListFragment<T>);
    }
}