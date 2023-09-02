package com.android.simpleday.ui.update

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.simpleday.R
import com.android.simpleday.data.local.TaskEntry
import com.android.simpleday.databinding.FragmentUpdateBinding
import com.android.simpleday.ui.task.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateFragment : Fragment() {

    private val viewModel: TaskViewModel by viewModels()
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<UpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUpdateBinding.inflate(inflater, container, false)

        binding.apply {
            updateEdtTask.setText(args.task.titulo)
            updateSpinner.setSelection(args.task.prioridad)

            btnUpdate.setOnClickListener {
                if(TextUtils.isEmpty(updateEdtTask.text)){
                    Toast.makeText(requireContext(), "¡Esta vacio!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val taskTitle = updateEdtTask.text.toString()
                val priority = updateSpinner.selectedItemPosition

                val taskEntry = TaskEntry(
                    args.task.id,
                    taskTitle,
                    priority,
                    args.task.timestamp
                )

                viewModel.update(taskEntry)
                Toast.makeText(requireContext(), "Tarea actualizada exitosamente.", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_updateFragment_to_taskFragment)
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}