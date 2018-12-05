package com.wozpi.core.witget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.widget.ImageView
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import com.wozpi.core.WozUtils

class WozImageView : ImageView {

    private var mPaint: Paint? = null
    private var mClipPath: Path? = null

    constructor(context: Context) : super(context){
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        init()
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        clipToOutline = true
        mClipPath = Path()
        mPaint = Paint()
//        val maiDrawable = drawable
//        val bitmapBounds = maiDrawable  tr      tl    br    bl
        val valueRadius = floatArrayOf(WozUtils.convertDpToPx(15f,context),WozUtils.convertDpToPx(15f,context),WozUtils.convertDpToPx(15f,context),WozUtils.convertDpToPx(15f,context),0f,0f,0f,0f)

        val rect = RectF(0f,0f,w.toFloat(),h.toFloat())
        rect.set(0f,0f,w.toFloat(),h.toFloat())
        mClipPath!!.addRoundRect(rect,valueRadius,Path.Direction.CW)

    }


    private fun init(){
        setWillNotDraw(false)
        Log.e("WOW","hello")
    }

    override fun onDraw(canvas: Canvas?) {

        Log.e("WOW","super.onDraw(canvas)")
        if (mClipPath != null){
            canvas!!.clipPath(mClipPath!!)
//            Timber.e("hello")
            Log.e("WOW","onDraw")
        }
        super.onDraw(canvas)
    }


}