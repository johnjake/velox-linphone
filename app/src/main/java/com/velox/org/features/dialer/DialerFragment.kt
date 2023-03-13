package com.velox.org.features.dialer

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.velox.org.R
import com.velox.org.bases.BaseFragment
import com.velox.org.databinding.FragmentDialerBinding
import com.velox.org.features.main.MainActivity
import kotlinx.coroutines.flow.collectLatest

class DialerFragment : BaseFragment<FragmentDialerBinding>(FragmentDialerBinding::inflate) {

    private val viewModel: DialerViewModel by viewModels()
    override fun setUpView() {
        super.setUpView()
        val act = requireActivity() as MainActivity
        act.showNavigation()
        padListener()
        observePad()
    }

    private fun observePad() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.padShared.collectLatest { state ->
                when (state) {
                    0 -> handleZero()
                    1 -> handleOne()
                    2 -> handleTwo()
                    3 -> handleThree()
                    4 -> handleFour()
                    5 -> handleFive()
                    6 -> handleSix()
                    7 -> handleSeven()
                    8 -> handleEight()
                    9 -> handleNine()
                    10 -> handleTen()
                    11 -> handleEleven()
                    12 -> handleTwelve()
                    else -> defaultPad()
                }
            }
        }
    }

    private fun handleOne() {
        binding.apply {
            defaultPad()
            includedPad.padOne.setImageResource(R.drawable.ic_number_one_chk)
        }
    }
    private fun handleTwo() {
        binding.apply {
            defaultPad()
            includedPad.padTwo.setImageResource(R.drawable.ic_number_two_chk)
        }
    }

    private fun handleThree() {
        binding.apply {
            defaultPad()
            includedPad.padThree.setImageResource(R.drawable.ic_number_three_chk)
        }
    }

    private fun handleFour() {
        binding.apply {
            defaultPad()
            includedPad.padFour.setImageResource(R.drawable.ic_number_four_chk)
        }
    }

    private fun handleFive() {
        binding.apply {
            defaultPad()
            includedPad.padFive.setImageResource(R.drawable.ic_number_five_chk)
        }
    }

    private fun handleSix() {
        binding.apply {
            defaultPad()
            includedPad.padSix.setImageResource(R.drawable.ic_number_six_chk)
        }
    }

    private fun handleSeven() {
        binding.apply {
            defaultPad()
            includedPad.padSeven.setImageResource(R.drawable.ic_number_seven_chk)
        }
    }

    private fun handleEight() {
        binding.apply {
            defaultPad()
            includedPad.padEight.setImageResource(R.drawable.ic_number_eight_chk)
        }
    }

    private fun handleNine() {
        binding.apply {
            defaultPad()
            includedPad.padNine.setImageResource(R.drawable.ic_number_nine_chk)
        }
    }

    private fun handleZero() {
        binding.apply {
            defaultPad()
            includedPad.padZero.setImageResource(R.drawable.ic_number_zero_chk)
        }
    }
    private fun handleTen() {
        binding.apply {
            defaultPad()
            includedPad.padStar.setImageResource(R.drawable.ic_star_digit_chk)
        }
    }

    private fun handleEleven() {
        binding.apply {
            defaultPad()
            includedPad.padSharp.setImageResource(R.drawable.ic_sharp_digit_chk)
        }
    }

    private fun handleTwelve() {
        binding.apply {
            defaultPad()
            padErase.setImageResource(R.drawable.ic_back_space_chk)
        }
    }

    private fun padListener() {
        binding.apply {
            includedPad.padOne.setOnClickListener {
                viewModel.clickPadNumber(1)
            }
            includedPad.padTwo.setOnClickListener {
                viewModel.clickPadNumber(2)
            }
            includedPad.padThree.setOnClickListener {
                viewModel.clickPadNumber(3)
            }
            includedPad.padFour.setOnClickListener {
                viewModel.clickPadNumber(4)
            }
            includedPad.padFive.setOnClickListener {
                viewModel.clickPadNumber(5)
            }
            includedPad.padSix.setOnClickListener {
                viewModel.clickPadNumber(6)
            }
            includedPad.padSeven.setOnClickListener {
                viewModel.clickPadNumber(7)
            }
            includedPad.padEight.setOnClickListener {
                viewModel.clickPadNumber(8)
            }
            includedPad.padNine.setOnClickListener {
                viewModel.clickPadNumber(9)
            }
            includedPad.padZero.setOnClickListener {
                viewModel.clickPadNumber(0)
            }
            includedPad.padStar.setOnClickListener {
                viewModel.clickPadNumber(10)
            }
            includedPad.padSharp.setOnClickListener {
                viewModel.clickPadNumber(11)
            }
            padErase.setOnClickListener {
                viewModel.clickPadNumber(12)
            }
        }
    }

    private fun defaultPad() {
        binding.apply {
            includedPad.padOne.setImageResource(R.drawable.ic_number_one_unc)
            includedPad.padTwo.setImageResource(R.drawable.ic_number_two_unc)
            includedPad.padThree.setImageResource(R.drawable.ic_number_three_unc)
            includedPad.padFour.setImageResource(R.drawable.ic_number_four_unc)
            includedPad.padFive.setImageResource(R.drawable.ic_number_five_unc)
            includedPad.padSix.setImageResource(R.drawable.ic_number_six_unc)
            includedPad.padSeven.setImageResource(R.drawable.ic_number_seven_unc)
            includedPad.padEight.setImageResource(R.drawable.ic_number_eight_unc)
            includedPad.padNine.setImageResource(R.drawable.ic_number_nine_unc)
            includedPad.padZero.setImageResource(R.drawable.ic_number_zero_unc)
            includedPad.padStar.setImageResource(R.drawable.ic_star_digit_unc)
            includedPad.padSharp.setImageResource(R.drawable.ic_sharp_digit_unc)
            padErase.setImageResource(R.drawable.ic_back_space_unc)
        }
    }
}
