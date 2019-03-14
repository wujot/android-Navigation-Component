package com.hemmersbach.android

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.navigation.fragment.findNavController
import com.hemmersbach.android.model.Person
import com.hemmersbach.android.service.CommonService
import kotlinx.android.synthetic.main.fragment_second.*

class SecondFragment : Fragment() {

    private lateinit var service: CommonService
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
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set default gender
        input_gender.check(input_gender_male.id)

        // Common helper functions Object
        service = CommonService()

        // get data from previous fragment
        arguments?.let {
            val safeArgs = SecondFragmentArgs.fromBundle(it)
            person = Person(safeArgs.person.firstName, safeArgs.person.surname)
        }

        setButtonOnClickListener(button_summary)
    }

    private fun setButtonOnClickListener(button: Button) {
        button.setOnClickListener {
            val checkBoxes = listOf(input_about_poland, input_about_developer, input_about_sing, input_about_cook, input_about_tired)
            service.disableErrors(listOf(input_layout_gender))
            val gender = collectGender(input_gender)
            val about = collectAbout(checkBoxes)
            upgradePerson(person, gender, about)
            // Pass person to summary
            sendPersonToNextStep(person)
        }
    }

    private fun collectGender(input: RadioGroup?): String? {
        val gender = view?.findViewById<RadioButton>(input!!.checkedRadioButtonId)
        return gender?.text.toString().trim()
    }


    private fun collectAbout(listOfCheckBoxes: List<CheckBox>): MutableList<String> {
        val about = mutableListOf<String>()
        listOfCheckBoxes.forEach { checkbox ->
            if (checkbox.isChecked) {
                about.add(checkbox.text.toString().trim())
            }
        }
        return about
    }

    private fun upgradePerson(person: Person, gender: String?, about: MutableList<String>): Person {
        person.gender = gender
        person.about = about
        return person
    }

    private fun sendPersonToNextStep(person: Person) {
        val action = SecondFragmentDirections.actionSecondFragmentToSummaryFragment(person)
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.clear -> {
                input_gender.check(input_gender_male.id)
                service.disableInputs(listOf(input_about_poland, input_about_developer, input_about_sing, input_about_cook, input_about_tired))
                service.disableErrors(listOf(input_layout_gender))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
