package id.ac.telkomuniversity.mrrezki.data.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import id.ac.telkomuniversity.mrrezki.data.view.adapter.TodoAdapter
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
    private lateinit var adapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupView()
        setupViewModel()
        setupObserve()
    }

    private fun setupView() {
        adapter = TodoAdapter(arrayListOf())
        binding.listTodo.adapter = adapter

        binding.fab.setOnClickListener {
            startActivity(Intent(this, FormActivity::class.java))
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
            adapter.setData(it)
        }
        observe(viewModel.message) { message ->
            showToast(message)
        }
    }

}