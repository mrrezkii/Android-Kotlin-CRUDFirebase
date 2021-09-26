package id.ac.telkomuniversity.mrrezki.data.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import id.ac.telkomuniversity.mrrezki.data.viewmodel.TodoViewModel
import id.ac.telkomuniversity.mrrezki.data.viewmodel.factory.TodoViewModelFactory
import id.ac.telkomuniversity.mrrezki.databinding.ActivityDetailBinding
import id.ac.telkomuniversity.mrrezki.utils.showToast
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
    private val data by lazy { intent.getStringExtra("data") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupView()
        setupViewModel()
    }

    private fun setupView() {
        showToast(data.toString())
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModeFactory).get(TodoViewModel::class.java)

    }
}