package com.dhruti.project_1

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class PlaceholderFragment(private val title: String) : Fragment(R.layout.fragment_placeholder) {

    // Default constructor for Fragment Restoration
    constructor() : this("Placeholder")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.txtPlaceholder).text = "$title Screen Coming Soon!"
    }
}
