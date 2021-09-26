package id.ac.telkomuniversity.mrrezki.data.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import id.ac.telkomuniversity.mrrezki.data.model.Todo
import id.ac.telkomuniversity.mrrezki.data.viewmodel.TodoViewModel
import id.ac.telkomuniversity.mrrezki.data.viewmodel.factory.TodoViewModelFactory
import id.ac.telkomuniversity.mrrezki.databinding.ActivityDetailBinding
import id.ac.telkomuniversity.mrrezki.utils.showToast
import id.ac.telkomuniversity.mrrezki.utils.toEditable
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class DetailActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val viewModeFactory: TodoViewModelFactory by instance()
    private lateinit var viewModel: TodoViewModel

    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(
            layoutInflater
        )
    }
    private val key by lazy { intent.getStringExtra("key") }
    private val title by lazy { intent.getStringExtra("title") }
    private val body by lazy { intent.getStringExtra("body") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupView()
        setupViewModel()
    }

    private fun setupView() {
        binding.etTitle.text = title!!.toEditable()
        binding.etBody.text = body!!.toEditable()

        when {
            binding.etTitle.text.isNullOrBlank() -> {
                showToast("Fill title")
            }
            binding.etBody.text.isNullOrBlank() -> {
                showToast("Fill body")
            }
            else -> {
                binding.btnUpdate.setOnClickListener {
                    viewModel.todo = Todo(
                        key = key,
                        title = binding.etTitle.text.toString(),
                        body = binding.etBody.text.toString()
                    )

                    viewModel.updateTodo()
                    showToast("Data has been updated")
                    finish()
                }
            }
        }

    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModeFactory).get(TodoViewModel::class.java)
    }
}