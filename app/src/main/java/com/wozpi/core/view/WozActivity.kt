package com.wozpi.core.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.wozpi.core.model.KindException
import com.wozpi.core.model.WozException
import com.wozpi.core.viewmodel.WozViewModel
import kotlinx.android.synthetic.main.toolbar_woz.*

abstract class WozActivity<T : ViewDataBinding?> : AppCompatActivity() {

    protected enum class STATUS{
        FIRST_LOAD,
        REFRESH,
        LOAD_MORE
    }

    private var mListViewModel = ArrayList<WozViewModel>()
    protected var mStatusLoading = STATUS.FIRST_LOAD

    protected open var mBiding: T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayoutView())
        mBiding = DataBindingUtil.setContentView<T>(this,setLayoutView())

        mBiding!!.setLifecycleOwner(this)

        initToolbar()

        initData()

        loadData()
    }

    abstract fun setLayoutView():Int


    abstract fun initData()

    override fun onDestroy() {
        for (e in mListViewModel){
            e.onDestroy()
        }
        super.onDestroy()
    }

    fun <V:WozViewModel> setViewModel(name: Int, viewModelClass: Class<out V>):V{
        val viewModel = ViewModelProviders.of(this).get(viewModelClass)
        mBiding!!.setVariable(name,viewModel)
        mListViewModel.add(viewModel)

        viewModel.getKindException().observe(this, Observer {
           if(it != null) {
               when (it.mKindException){
                   KindException.NETWORK-> {
                       onRetryConnection(it.mTag)
                   }
                   else-> onException(viewModel, it, it.mTag)
               }

           }
        })

        return viewModel
    }

    open fun <V:WozViewModel> onException(viewModel:V,ex: WozException?,tag:Int){
        if(ex != null) {
            ErrorDialog(this)
                .setMessage(ex.mMessage)
                .show()
        }
    }


    protected open fun loadData(){

    }

    /**
     * @tag
     * Handle error retry connection when call api
     * */
    protected open fun  onRetryConnection(tag: Int){

    }

    /**
     * Hide keyboard when touch outside
     * */


    protected open fun isHideSoftKeyBoardTouchOutSide():Boolean{
        return true
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if(isHideSoftKeyBoardTouchOutSide()) {
            val viewFocus = window.currentFocus

            if (viewFocus is EditText && ev != null) {
                val screenLocation = IntArray(2)
                viewFocus.getLocationOnScreen(screenLocation)
                val x = ev.rawX + viewFocus.left - screenLocation[0]
                val y = ev.rawY + viewFocus.right - screenLocation[1]

                if (ev.action == MotionEvent.ACTION_DOWN) {
                    if (x < viewFocus.left || x >= viewFocus.right || y < viewFocus.top || y >= viewFocus.bottom) {
                        val iMM: InputMethodManager =
                            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        iMM.hideSoftInputFromWindow(viewFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                    }
                }
            }
        }

        return super.dispatchTouchEvent(ev)
    }


    /**
     * Init toolbar
     * */

    private fun initToolbar(){
        if(toolbarBack != null){
            setSupportActionBar(toolbarBack)
            val actionBar = supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(false)
        }

    }


}
