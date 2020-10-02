package ru.touchin.roboswag.pagination

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.touchin.roboswag.recyclerview_adapters.adapters.AdapterDelegate
import ru.touchin.roboswag.recyclerview_adapters.adapters.DelegationListAdapter

/**
 * Adapter for showing [Paginator].
 *
 * @param nextPageCallback - callback to load data for next page (if not all data loaded);
 * @param itemIdDiff - compares whether two elements are equal;
 * @param delegate - list of delegates to add to adapter of RecyclerView.
 *
 */
class PaginationAdapter(
        private val nextPageCallback: () -> Unit,
        private val itemIdDiff: (old: Any, new: Any) -> Boolean,
        vararg delegate: AdapterDelegate<out RecyclerView.ViewHolder>
) : DelegationListAdapter<Any>(
        object : DiffUtil.ItemCallback<Any>() {
            override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean = itemIdDiff(oldItem, newItem)

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean = oldItem == newItem
        }
) {

    // TODO: перенести в список
    // Переменная, которая отвечает за отображение лоадера в конце списка. Если переменная равна true, лоадер не будет показан
    internal var fullData = false

    init {
        addDelegate(ProgressAdapterDelegate())
        delegate.forEach(this::addDelegate)
    }

    // TODO: перенести в Paginator
    fun update(data: List<Any>, updateState: UpdateState) {
        submitList(data + listOfNotNull(when (updateState) {
            is UpdateState.Common -> null
            is UpdateState.Progress -> ProgressItem
            is UpdateState.Error -> ErrorItem
        }))
    }

    // При байндинге одного из последних элементов списка запускается загрузка следующей страницы
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: List<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        if (!fullData && position >= itemCount - 10) nextPageCallback.invoke()
    }

    sealed class UpdateState {
        object Common : UpdateState()
        object Progress : UpdateState()
        object Error : UpdateState()
    }

}