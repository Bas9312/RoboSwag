package ru.touchin.roboswag.bottom_navigation_fragment

import android.content.Context
import android.util.SparseArray
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentManager

class BottomNavigationController(
        context: Context,
        fragments: SparseArray<NavigationTab>,
        fragmentManager: FragmentManager,
        wrapWithNavigationContainer: Boolean = false,
        @IdRes private val defaultTabId: Int = 0, // If it zero back press with empty fragment back stack would close the app
        @IdRes private val contentContainerViewId: Int,
        @LayoutRes private val contentContainerLayoutId: Int,
        private val onReselectListener: (() -> Unit)? = null
) : BaseBottomNavigationController<NavigationTab>(
        tabs = fragments,
        context = context,
        fragmentManager = fragmentManager,
        defaultTabId = defaultTabId,
        contentContainerViewId = contentContainerViewId,
        contentContainerLayoutId = contentContainerLayoutId,
        wrapWithNavigationContainer = wrapWithNavigationContainer
) {

    override fun getNavigationContainerClass() = NavigationContainerFragment::class.java

    override fun onTabReselected() {
        onReselectListener?.invoke()
    }

}
