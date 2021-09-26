package id.ac.telkomuniversity.mrrezki.data.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.ac.telkomuniversity.mrrezki.data.model.Todo
import id.ac.telkomuniversity.mrrezki.databinding.AdapterTodoBinding


class TodoAdapter(
    var todos: ArrayList<Todo>,
    val listerner: OnAdapterListener
) : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    class ViewHolder(val binding: AdapterTodoBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnAdapterListener {
        fun onClick(result: Todo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        AdapterTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todo = todos[position]
        holder.binding.tvTitle.text = todo.title
        holder.binding.tvBody.text = todo.body
        holder.binding.container.setOnClickListener {
            listerner.onClick(todo)
        }
    }

    override fun getItemCount() = todos.size

    fun setData(data: List<Todo>) {
        todos.clear()
        todos.addAll(data)
        notifyDataSetChanged()
    }
}
