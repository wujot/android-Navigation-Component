package com.hemmersbach.android

import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.hemmersbach.android.model.Person
import com.hemmersbach.android.service.CommonService
import com.hemmersbach.android.service.ValidatorService
import kotlinx.android.synthetic.main.fragment_first.*
import android.view.*
import android.view.MenuInflater




class FirstFragment : Fragment() {

    private lateinit var service: CommonService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Common helper functions Object
        service = CommonService()

        setButtonOnClickListener(button_next)
    }

    private fun setButtonOnClickListener(button: Button) {
        button.setOnClickListener {
            // Collect data from inputs
            val firstName = input_name.text.toString().trim()
            val surname = input_surname.text.toString().trim()
            // Validate
            if (validate(firstName, surname)) {
                val person = producePerson(firstName, surname)
                sendPersonToNextStep(person)
            }
        }
    }

    private fun validate(name: String, surname: String): Boolean {
        val validator = ValidatorService()
        var isValidated = true

        service.disableErrors(listOf(input_layout_name, input_layout_surname))

        // validate name input
        if (validator.isNullorEmpty(name)) {
            input_layout_name.setError(getString(R.string.error_is_empty))
            isValidated = false
        } else if (!validator.minCharacters(name)) {
            input_layout_name.setError(getString(R.string.error_is_min))
            isValidated = false
        } else if (!validator.isFirstLetterUppercase(name)) {
            input_layout_name.setError(getString(R.string.error_is_first_capital))
            isValidated = false
        }

        // validate surname input
        if (validator.isNullorEmpty(surname)) {
            input_layout_surname.setError(getString(R.string.error_is_empty))
            isValidated = false
        } else if (!validator.minCharacters(surname)) {
            input_layout_surname.setError(getString(R.string.error_is_min))
            isValidated = false
        } else if (!validator.isFirstLetterUppercase(surname)) {
            input_layout_surname.setError(getString(R.string.error_is_first_capital))
            isValidated = false
        }
        return isValidated
    }

    private var producePerson: (String, String) -> Person = {firstName, surname -> Person(firstName, surname) }

    private fun sendPersonToNextStep(person: Person) {
        val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(person)
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId) {
            R.id.clear -> {
                service.clearInputs(listOf(input_name, input_surname))
                service.disableErrors(listOf(input_layout_name, input_layout_surname))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
