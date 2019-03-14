package com.hemmersbach.android

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.TextView
import com.hemmersbach.android.model.Person
import kotlinx.android.synthetic.main.fragment_summary.*


class SummaryFragment : Fragment() {

    private lateinit var person: Person

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_summary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get data
        arguments?.let {
            val safeArgs = SecondFragmentArgs.fromBundle(it)
            this.person = safeArgs.person
        }

        // Assign data into appropriate text view fields
        assignContent(summary_name_content, summary_surname_content, summary_gender_content, summary_about_content, person)
    }

    private fun assignContent(name: TextView, surname: TextView, gender: TextView, about: TextView, person: Person) {
        name.text = person.firstName
        surname.text = person.surname
        gender.text = person.gender
        val aboutContent = formatAboutContent(person.about)
        about.text = aboutContent
    }

    private fun formatAboutContent(listOfContents: MutableList<String>?): String {
        var about = ""
        listOfContents!!.forEach { content ->
            about += "$content\n\n"
        }
        return about
    }
}
