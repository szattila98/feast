package ren.practice.feast.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import ren.practice.feast.R
import ren.practice.feast.databinding.FragmentRecordUserBinding
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

class RecordUserFragment : Fragment() {

    companion object {
        lateinit var currentUser: User
    }

    private var _binding: FragmentRecordUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecordUserBinding.inflate(inflater, container, false)

        // TODO validation object / correct validation
        // TODO strings to strings xml, constants too
        // TODO private methods
        // TODO BMI
        binding.firstNameEditText.addTextChangedListener { text ->
            if (text.isNullOrBlank()) binding.firstNameEditText.error = "Cannot be blank!"
            else binding.confirmButton.isEnabled = true
        }
        binding.lastNameEditText.addTextChangedListener { text ->
            if (text.isNullOrBlank()) binding.lastNameEditText.error = "Cannot be blank!"
            else binding.confirmButton.isEnabled = true
        }
        binding.heightEditText.addTextChangedListener { text ->
            if (text.isNullOrBlank()) binding.heightEditText.error = "Cannot be blank!"
            else if (!text.isNullOrBlank() && text.toString()
                    .toInt() < 50
            ) binding.heightEditText.error = "Cannot be less than 50 centimeters!"
            else if (!text.isNullOrBlank() && text.toString()
                    .toInt() > 280
            ) binding.heightEditText.error = "Cannot be more than 280 centimeters!"
            else binding.confirmButton.isEnabled = true
        }
        binding.weightEditText.addTextChangedListener { text ->
            if (text.isNullOrBlank()) binding.weightEditText.error = "Cannot be blank!"
            else if (!text.isNullOrBlank() && text.toString()
                    .toInt() < 30
            ) binding.weightEditText.error = "Cannot be less than 30 kilograms!"
            else if (!text.isNullOrBlank() && text.toString().toInt() > 200
            ) binding.weightEditText.error = "Cannot be more than 200 kilograms!"
            else binding.confirmButton.isEnabled = true
        }
        binding.birthDateShowTextView.addTextChangedListener {
            binding.confirmButton.isEnabled = true
        }

        var birthDate: Date? = null
        val materialDateBuilder = MaterialDatePicker.Builder.datePicker()
        val materialDatePicker = materialDateBuilder.build()
        binding.birthDateSelectButton.setOnClickListener {
            materialDatePicker.showNow(parentFragmentManager, "MATERIAL_DATE_PICKER")
        }
        materialDatePicker.addOnPositiveButtonClickListener {
            birthDate = Date(it)
            val localBirthDate =
                birthDate!!.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            val age = ChronoUnit.YEARS.between(
                localBirthDate,
                LocalDate.now()
            )
            binding.birthDateShowTextView.text =
                localBirthDate.format(DateTimeFormatter.ofPattern("dd/MMMM/yyyy"))
            binding.ageTextView.text = "Age: ${age}"
        }

        val sexType = when (binding.sexOptions.checkedRadioButtonId) {
            R.id.male_option -> SexType.FEMALE
            else -> SexType.MALE
        }

        binding.confirmButton.setOnClickListener {
            currentUser = User(
                binding.firstNameEditText.text.toString(),
                binding.lastNameEditText.text.toString(),
                birthDate!!,
                sexType,
                binding.heightEditText.text.toString().toFloat(),
                binding.weightEditText.text.toString().toFloat()
            )
            val action = RecordUserFragmentDirections.actionRecordUserFragmentToHomeFragment()
            binding.root.findNavController().navigate(action)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

data class User(
    private var firstName: String,
    private var lastName: String,
    private var birthDate: Date,
    private var sex: SexType,
    private var height: Float,
    private var weight: Float,
)

enum class SexType {
    MALE, FEMALE
}