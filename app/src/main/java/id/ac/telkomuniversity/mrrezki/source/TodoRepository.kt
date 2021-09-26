package id.ac.telkomuniversity.mrrezki.source

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.ac.telkomuniversity.mrrezki.data.model.Todo
import id.ac.telkomuniversity.mrrezki.utils.post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TodoRepository {
    companion object {
        const val TABLE_TODO = "Todo"
    }

    private val firebaseDatabase by lazy { FirebaseDatabase.getInstance() }

    val todo: LiveData<List<Todo>> = MutableLiveData()

    val isLoading: LiveData<Boolean> = MutableLiveData()
    val message: LiveData<String> = MutableLiveData()


    suspend fun getTodo() = withContext(Dispatchers.IO) {
        isLoading.post(true)
        val ref = firebaseDatabase
            .reference
            .child(TABLE_TODO)
        ref.addValueEventListener(getTodoValueListener())
        ref.keepSynced(true)
    }

    private fun getTodoValueListener() = object : ValueEventListener {
        override fun onCancelled(error: DatabaseError) {
            isLoading.post(false)
            message.post("$error")
        }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun onDataChange(snapshot: DataSnapshot) {
            isLoading.post(false)

            val todoResult = mutableListOf<Todo>()
            snapshot.children.forEach { dataSnapshot ->
                dataSnapshot.getValue(Todo::class.java)?.let { todo ->
                    todo.key = dataSnapshot.key
                    todo.title = dataSnapshot.child("title").value.toString()
                    todo.body = dataSnapshot.child("body").value.toString()
                    todoResult.add(
                        Todo(
                            key = todo.key,
                            title = todo.title,
                            body = todo.body
                        )
                    )
                }
            }
            todo.post(todoResult)
        }
    }

    suspend fun insertTodo(todo: Todo, isTodoInserted: (Boolean) -> Unit) =
        withContext(Dispatchers.IO) {
            isLoading.post(true)
            firebaseDatabase.reference.child(TABLE_TODO).push()
                .setValue(todo)
                .addOnSuccessListener {
                    isLoading.post(false)
                    isTodoInserted(true)
                }
                .addOnFailureListener {
                    isLoading.post(false)
                    isTodoInserted(false)
                    message.post("$it")
                }
        }

    suspend fun updateTodo(todo: Todo, isTodoUpdated: (Boolean) -> Unit) =
        withContext(Dispatchers.IO) {
            isLoading.post(true)
            todo.key?.let { key ->
                firebaseDatabase.reference.child(TABLE_TODO).child(key)
                    .setValue(todo)
                    .addOnSuccessListener {
                        isLoading.post(true)
                        isTodoUpdated(true)
                    }
                    .addOnFailureListener {
                        isLoading.post(true)
                        isTodoUpdated(false)
                        message.post("$it")
                    }
            }
        }

    suspend fun deleteTodo(key: String, isTodoDeleted: (Boolean) -> Unit) =
        withContext(Dispatchers.IO) {
            isLoading.post(true)

            firebaseDatabase.reference.child(TABLE_TODO).child(key)
                .removeValue()
                .addOnSuccessListener {
                    isLoading.post(false)
                    isTodoDeleted(true)
                }
                .addOnFailureListener {
                    isLoading.post(false)
                    isTodoDeleted(false)
                    message.post("$it")
                }
        }

}