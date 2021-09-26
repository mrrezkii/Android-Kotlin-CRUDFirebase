package id.ac.telkomuniversity.mrrezki.data.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import id.ac.telkomuniversity.mrrezki.data.viewmodel.TodoViewModel
import id.ac.telkomuniversity.mrrezki.data.viewmodel.factory.TodoViewModelFactory
import id.ac.telkomuniversity.mrrezki.databinding.ActivityDetailBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import timber.log.Timber

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
        Timber.e("$key")
        Timber.e("$title")
        Timber.e("$body")
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModeFactory).get(TodoViewModel::class.java)

    }
}