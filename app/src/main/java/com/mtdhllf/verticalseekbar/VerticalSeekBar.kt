package com.mtdhllf.verticalseekbar

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatSeekBar

/**
 * author: mtdhllf
 * desc  :
 */
class VerticalSeekBar : AppCompatSeekBar {

    private var mOnSeekBarChangeListener: OnSeekBarChangeListener? = null

    override fun setOnSeekBarChangeListener(l: OnSeekBarChangeListener) {
        mOnSeekBarChangeListener = l
        super.setOnSeekBarChangeListener(l)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (!isEnabled) {
            return false
        }
        parent.requestDisallowInterceptTouchEvent(true)
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                mOnSeekBarChangeListener?.onStartTrackingTouch(this)
                val i = max - (max * event.y * 1f / height).toInt()
                progress = i
                if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) {
                    parent.requestDisallowInterceptTouchEvent(false)
                    mOnSeekBarChangeListener?.onStopTrackingTouch(this)
                }
            }
            MotionEvent.ACTION_MOVE, MotionEvent.ACTION_UP -> {
                val i = max - (max * event.y * 1f / height).toInt()
                progress = i
                if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) {
                    parent.requestDisallowInterceptTouchEvent(false)
                    mOnSeekBarChangeListener?.onStopTrackingTouch(this)
                }
            }
        }
        return true
    }

    constructor(ctx: Context) : super(ctx)

    constructor(ctx: Context, attrs: AttributeSet?) : this(ctx, attrs, 0)

    constructor(ctx: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(ctx, attrs, defStyleAttr)

    init {
        thumb = null
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mOnSeekBarChangeListener = null
    }

}