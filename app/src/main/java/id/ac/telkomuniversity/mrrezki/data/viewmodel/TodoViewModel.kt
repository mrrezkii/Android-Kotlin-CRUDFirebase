package id.ac.telkomuniversity.mrrezki.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.ac.telkomuniversity.mrrezki.data.model.Todo
import id.ac.telkomuniversity.mrrezki.source.TodoRepository
import id.ac.telkomuniversity.mrrezki.utils.post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(
    val repository: TodoRepository
) : ViewModel() {

    var todo: Todo? = null

    val todos = repository.todo
    val message = repository.message
    val isLoading = repository.isLoading
    val isTodoInserted: LiveData<Boolean> = MutableLiveData()
    val isTodoUpdated: LiveData<Boolean> = MutableLiveData()
    val isTodoDeleted: LiveData<Boolean> = MutableLiveData()

    fun getTodo() = CoroutineScope(Dispatchers.IO).launch {
        repository.getTodo()
    }

    fun insertTodo() = CoroutineScope(Dispatchers.IO).launch {
        todo?.let {
            repository.insertTodo(it) { isSuccess ->
                isTodoInserted.post(isSuccess)
            }
        }
    }

    fun updateTodo() = CoroutineScope(Dispatchers.IO).launch {
        todo?.let {
            repository.updateTodo(it) { isSuccess ->
                isTodoUpdated.post(isSuccess)
            }
        }
    }

    fun deleteNote() = CoroutineScope(Dispatchers.IO).launch {
        todo?.key?.let { key ->
            repository.deleteTodo(key) { isSuccess ->
                isTodoDeleted.post(isSuccess)
            }
        }
    }


}