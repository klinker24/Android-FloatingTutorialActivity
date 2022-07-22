package xyz.klinker.android.floating_tutorial

import android.view.Gravity
import android.view.ViewGroup
import xyz.klinker.android.floating_tutorial.util.DensityConverter

abstract class BottomSheetTutorialPage(activity: FloatingTutorialActivity) : TutorialPage(activity) {
    override val rootLayoutParams: LayoutParams
        get() = LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT).apply {
            gravity = Gravity.BOTTOM

            val dp = DensityConverter.toDp(getActivity(), 12)
            setMargins(dp, 0, dp, dp)
        }
}