package ru.touchin.roboswag.bottom_navigation_fragment

abstract class BottomNavigationFragment : BaseBottomNavigationFragment<NavigationTab>() {

    override fun createNavigationController() = BottomNavigationController(
            context = requireContext(),
            fragments = tabs,
            fragmentManager = childFragmentManager,
            defaultTabId = defaultTabId,
            contentContainerViewId = contentContainerViewId,
            contentContainerLayoutId = contentContainerLayoutId,
            wrapWithNavigationContainer = wrapWithNavigationContainer,
            onReselectListener = reselectListener
    )

}
