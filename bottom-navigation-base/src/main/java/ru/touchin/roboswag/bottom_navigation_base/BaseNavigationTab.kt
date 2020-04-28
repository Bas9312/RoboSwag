package ru.touchin.roboswag.bottom_navigation_fragment

import android.os.Parcel
import android.os.Parcelable
import ru.touchin.roboswag.navigation_base.fragments.EmptyState

open class BaseNavigationTab(
        open val cls: Class<*>,
        state: Parcelable,
        /**
         * It can be useful in some cases when it is necessary to create ViewController
         * with initial state every time when tab opens.
         */
        val saveStateOnSwitching: Boolean = true
) {

        /**
         * It is value as class body property instead of value as constructor parameter to specify
         * custom getter of this field which returns copy of Parcelable every time it be called.
         * This is necessary to avoid modifying this value if it would be a value as constructor parameter
         * and every getting of this value would return the same instance.
         */
        val state = state
                get() = field.copy()

        private fun Parcelable.copy(): Parcelable =
                if (this is EmptyState) {
                        EmptyState
                } else {
                        val parcel = Parcel.obtain()

                        parcel.writeParcelable(this, 0)
                        parcel.setDataPosition(0)

                        val result = parcel.readParcelable<Parcelable>(
                                javaClass.classLoader ?: Thread.currentThread().contextClassLoader
                        ) ?: throw IllegalStateException("Failed to copy tab state")

                        parcel.recycle()

                        result
                }

}
