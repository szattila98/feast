package ren.practice.feast.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import ren.practice.feast.R
import ren.practice.feast.databinding.FragmentRecordUserBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.math.pow

const val MIN_YEAR_OFFSET = -100
const val MAX_YEAR_OFFSET = 84

class RecordUserFragment : Fragment() {

    private var _binding: FragmentRecordUserBinding? = null
    private val binding get() = _binding!!

    private var firstNameValid = false
    private var lastNameValid = false
    private var genderValid = false
    private var birthDateValid = false
    private var heightValid = false
    private var weightValid = false

    companion object {
        val currentUser = User()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecordUserBinding.inflate(inflater, container, false)
        // TODO refactor interface validator or generify
        initTextWatchers()
        initCalendar()
        initConfirmButton()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initTextWatchers() {
        binding.firstNameEditText.addTextChangedListener { text ->
            if (text.isNullOrBlank()) {
                binding.firstNameEditText.error = getString(R.string.no_blank)
                firstNameValid = false
            } else {
                firstNameValid = true
                currentUser.firstName = binding.firstNameEditText.text.toString()
            }
            canConfirm()
        }
        binding.lastNameEditText.addTextChangedListener { text ->
            if (text.isNullOrBlank()) {
                binding.lastNameEditText.error = getString(R.string.no_blank)
                lastNameValid = false
            } else {
                lastNameValid = true
                currentUser.lastName = binding.lastNameEditText.text.toString()
            }
            canConfirm()
        }
        binding.genderOptions.setOnCheckedChangeListener { _, i ->
            genderValid = true
            when (i) {
                R.id.female_option -> currentUser.gender = GenderType.FEMALE
                else -> currentUser.gender = GenderType.MALE
            }
            canConfirm()
        }
        binding.heightEditText.addTextChangedListener { text ->
            if (text.isNullOrBlank()) {
                binding.heightEditText.error = getString(R.string.no_blank)
                heightValid = false
            } else if (!text.isNullOrBlank() && text.toString().toInt() < 50) {
                binding.heightEditText.error = getString(R.string.no_less_height)
                heightValid = false
            } else if (!text.isNullOrBlank() && text.toString().toInt() > 280) {
                binding.heightEditText.error = getString(R.string.no_more_height)
                heightValid = false
            } else {
                heightValid = true
                currentUser.height = binding.heightEditText.text.toString().toFloat() / 100f
            }
            canConfirm()
        }
        binding.weightEditText.addTextChangedListener { text ->
            if (text.isNullOrBlank()) {
                binding.weightEditText.error = getString(R.string.no_blank)
                weightValid = false
            } else if (!text.isNullOrBlank() && text.toString().toInt() < 30) {
                binding.weightEditText.error = getString(R.string.no_less_weight)
                weightValid = false
            } else if (!text.isNullOrBlank() && text.toString().toInt() > 200) {
                binding.weightEditText.error = getString(R.string.no_more_weight)
                weightValid = false
            } else {
                weightValid = true
                currentUser.weight = binding.weightEditText.text.toString().toFloat()
            }
            canConfirm()
        }
    }

    private fun initCalendar() {
        val calendar = Calendar.getInstance()
        val dpd = DatePickerDialog(
            binding.root.context, R.style.MySpinnerDatePickerStyle,
            { _, y, m, d ->
                currentUser.birthDate = LocalDate.of(y, m, d)
                currentUser.age = ChronoUnit.YEARS.between(
                    currentUser.birthDate,
                    LocalDate.now()
                ).toInt()
                binding.birthDateShowTextView.text =
                    currentUser.birthDate.format(DateTimeFormatter.ofPattern("dd/MMMM/yyyy"))
                binding.ageTextView.text = getString(R.string.age_val, currentUser.age)
                birthDateValid = true
                canConfirm()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        calendar.add(Calendar.YEAR, MIN_YEAR_OFFSET)
        dpd.datePicker.minDate = calendar.timeInMillis
        calendar.add(Calendar.YEAR, MAX_YEAR_OFFSET)
        dpd.datePicker.maxDate = calendar.timeInMillis
        binding.birthDateSelectButton.setOnClickListener {
            dpd.show()
        }
    }

    private fun canConfirm() {
        if (firstNameValid && lastNameValid && birthDateValid && heightValid && weightValid && genderValid) {
            binding.confirmButton.isEnabled = true
            binding.bfiTextView.text = calcBfp().toString()
        }
    }

    private fun initConfirmButton() {
        binding.confirmButton.setOnClickListener {
            // TODO save user
            val action = RecordUserFragmentDirections.actionRecordUserFragmentToHomeFragment()
            binding.root.findNavController().navigate(action)
        }
    }

    private fun calcBfp(): Float {
        val bmi = currentUser.weight / currentUser.height.pow(2)
        return when (currentUser.gender) {
            GenderType.FEMALE -> (1.20f * bmi) + (0.23f * currentUser.age) - 5.4f
            else -> (1.20f * bmi) + (0.23f * currentUser.age) - 16.2f
        }
    }

}

data class User(
    var firstName: String = "",
    var lastName: String = "",
    var birthDate: LocalDate = LocalDate.now(),
    var age: Int = 0,
    var gender: GenderType = GenderType.MALE,
    var height: Float = 1f, // m
    var weight: Float = 1f, // kg
)

enum class GenderType {
    MALE, FEMALE
}