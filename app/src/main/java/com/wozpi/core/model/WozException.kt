package com.wozpi.core.model

import com.wozpi.core.service.ServerError

class WozException {

    var mKindException:KindException
    var mMessage:String? = null
    var mErrorData:ServerError? = null
    var mTag:Int

    constructor(kind:KindException,message:String?,tag:Int){
        mKindException = kind
        mMessage = message
        mTag = tag
    }

    constructor(kind:KindException,data:ServerError?,tag:Int){
        mKindException = kind
        mErrorData = data
        mTag = tag
    }



}