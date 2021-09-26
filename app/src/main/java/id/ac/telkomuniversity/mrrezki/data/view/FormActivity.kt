package id.ac.telkomuniversity.mrrezki.data.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import id.ac.telkomuniversity.mrrezki.data.model.Todo
import id.ac.telkomuniversity.mrrezki.data.viewmodel.TodoViewModel
import id.ac.telkomuniversity.mrrezki.data.viewmodel.factory.TodoViewModelFactory
import id.ac.telkomuniversity.mrrezki.databinding.ActivityFormBinding
import id.ac.telkomuniversity.mrrezki.utils.showToast
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class FormActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val viewModeFactory: TodoViewModelFactory by instance()
    private lateinit var viewModel: TodoViewModel

    private val binding: ActivityFormBinding by lazy {
        ActivityFormBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupView()
        setupViewModel()
    }

    private fun setupView() {
        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text
            val body = binding.etBody.text

            when {
                title.isNullOrBlank() -> {
                    showToast("Fill title")
                }
                body.isNullOrBlank() -> {
                    showToast("Fill body")
                }
                else -> {
                    viewModel.todo = Todo(title = title.toString(), body = body.toString())

                    if (viewModel.todo?.key.isNullOrBlank()) {
                        viewModel.insertTodo()
                        showToast("Data has been created")
                    } else {
                        viewModel.updateTodo()
                        showToast("Data has been updated")
                    }
                    finish()
                }
            }
        }
        binding.btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModeFactory).get(TodoViewModel::class.java)

    }
}
