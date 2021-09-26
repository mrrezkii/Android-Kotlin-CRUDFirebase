package id.ac.telkomuniversity.mrrezki.data.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import id.ac.telkomuniversity.mrrezki.data.model.Todo
import id.ac.telkomuniversity.mrrezki.data.viewmodel.TodoViewModel
import id.ac.telkomuniversity.mrrezki.data.viewmodel.factory.TodoViewModelFactory
import id.ac.telkomuniversity.mrrezki.databinding.ActivityMainBinding
import id.ac.telkomuniversity.mrrezki.utils.observe
import id.ac.telkomuniversity.mrrezki.utils.showToast
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import timber.log.Timber

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val viewModeFactory: TodoViewModelFactory by instance()
    private lateinit var viewModel: TodoViewModel

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupView()
        setupViewModel()
        setupObserve()
    }

    private fun setupView() {
        binding.tvHello.setOnClickListener {
            createSampleTodo()
        }
    }


    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModeFactory).get(TodoViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getTodo()
    }

    private fun setupObserve() {
        observe(viewModel.todos) {
            Timber.e("$it")
        }
        observe(viewModel.message) { message ->
            showToast(message)
        }
    }


    private fun createSampleTodo() {
        viewModel.todo = Todo(title = "Dummy title", body = "Dummy body")

        if (viewModel.todo?.key.isNullOrBlank()) {
            viewModel.insertTodo()
        } else {
            viewModel.updateTodo()
        }
        Timber.e("${viewModel.todo}")
    }
}