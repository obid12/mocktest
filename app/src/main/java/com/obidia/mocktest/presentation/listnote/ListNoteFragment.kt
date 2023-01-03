package com.obidia.mocktest.presentation.listnote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.obidia.mocktest.databinding.FragmentListNoteBinding
import com.obidia.mocktest.presentation.NoteViewModel
import com.obidia.mocktest.presentation.inputdata.InputDataFragment
import com.obidia.mocktest.presentation.update.UpdateDataFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ListNoteFragment : Fragment() {

    private lateinit var binding: FragmentListNoteBinding
    private val noteViewModel: NoteViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListNoteBinding.inflate(inflater, container, false)
        val adapter = ListAdapter(ListAdapter.OnClick {
            val dialogFragment = UpdateDataFragment(it)
            val fragmentManager = childFragmentManager
            dialogFragment.show(fragmentManager, dialogFragment::class.java.simpleName)
        })
        binding.rv.adapter = adapter
        noteViewModel.readAllData().flowWithLifecycle(lifecycle).onEach {
            adapter.submitData(it)
        }.launchIn(lifecycleScope)



        binding.floatBtn.setOnClickListener {
            val dialogFragment = InputDataFragment()
            val fragmentManager = childFragmentManager
            dialogFragment.show(fragmentManager, dialogFragment::class.java.simpleName)
        }

        return binding.root
    }


}