package com.example.mae_project.bottomsheets

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.HtmlCompat
import com.example.mae_project.R
import com.example.mae_project.databinding.InfoLayoutBinding
import com.example.mae_project.dataclasses.MajorDataClass
import com.example.mae_project.dataclasses.ScholarDataClass
import com.example.mae_project.dataclasses.UniversityDataClass
import com.example.mae_project.mvvm.viewmodels.DataBaseViewModel
import com.example.mae_project.mvvm.viewmodels.SharedHomeViewModel
import com.example.mae_project.utils.beGone
import com.example.mae_project.utils.beVisible
import com.example.mae_project.utils.setImage
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class InfoBottomFragment : BottomSheetDialogFragment() {
    private lateinit var binding: InfoLayoutBinding
    private val sharedHomeViewModel: SharedHomeViewModel by sharedViewModel()
    private val dataBaseViewModel: DataBaseViewModel by sharedViewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = InfoLayoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSave.setOnClickListener {
            if (this.tag?.contains("Save") == true) {
                dataBaseViewModel.deleteData(sharedHomeViewModel.selectedItem.value)
                this.dismiss()
            } else {
                dataBaseViewModel.insertData(sharedHomeViewModel.selectedItem.value)
                Toast.makeText(requireContext(), "Saved successful", Toast.LENGTH_SHORT).show()
            }
        }
        sharedHomeViewModel.selectedItem.observe(viewLifecycleOwner) {
            if (this.tag?.contains("University") == true) {
                bindUniversityData(it as UniversityDataClass)
            } else if (this.tag?.contains("Majors") == true) {
                bindMajorData(it as MajorDataClass)
            } else if (this.tag?.contains("Scholar") == true) {
                bindScholarData(it as ScholarDataClass)
            }

            if (this.tag?.contains("Save") == true) {
                binding.btnSave.setImage(R.drawable.ic_baseline_delete_24)
            }
        }
    }


    private fun bindMajorData(majorDataClass: MajorDataClass) {
        with(binding) {
            headerImage.setImage(majorDataClass.majImage)
            buttonLayout.beGone()
            mImage.beGone()
            tvDetail.beVisible()
            tvHeader.text = majorDataClass.name
            tvDetail.text = HtmlCompat.fromHtml(
                majorDataClass.description.toString(),
                HtmlCompat.FROM_HTML_MODE_COMPACT
            )
        }
    }

    private fun bindScholarData(scholarDataClass: ScholarDataClass) {
        with(binding) {
            headerImage.setImage(R.drawable.graduation)
            buttonLayout.beGone()
            mImage.beGone()
            tvHeader.text = scholarDataClass.title
            tvDetail.beVisible()
            tvDetail.text = HtmlCompat.fromHtml(
                scholarDataClass.content.toString(),
                HtmlCompat.FROM_HTML_MODE_COMPACT
            )
        }
    }

    private fun bindUniversityData(universityDataClass: UniversityDataClass) {

        with(binding) {
            headerImage.setImage(R.drawable.school)
            tvHeader.text = universityDataClass.uniName
            tvLayout.beVisible()
            tvName.text = universityDataClass.uniName
            tvContact.text = universityDataClass.contactNo
            tvFee.text = universityDataClass.fee
            tvCountry.text = universityDataClass.uniCountry
            tvlocation.text = universityDataClass.uniLocation
            tvEmail.text = universityDataClass.emailAddress
            mImage.setImage(universityDataClass.uniImage)
            btnCall.setOnClickListener {
                callNow(universityDataClass.contactNo)
            }
            btnText.setOnClickListener {
                sendMessage(universityDataClass.emailAddress)
            }
        }
    }

    private fun sendMessage(emailAddress: String?) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, emailAddress)
        }
        startActivity(intent)
    }

    private fun callNow(contactNo: String?) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", contactNo, null))
        startActivity(intent)
    }

    private fun Int.getBoldText(appendValue: String?): String {
        return Html.fromHtml(getString(this), Html.FROM_HTML_MODE_COMPACT).toString() + "\n" +
                "$appendValue"
    }

}