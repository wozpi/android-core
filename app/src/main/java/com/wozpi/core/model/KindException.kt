package com.wozpi.core.model

enum class KindException {
    /** An [IO Exception] occurred while communicating to the server.  */
    NETWORK,
    /** A non-200 HTTP status code was received from the server.  */
    HTTP,
    HTTP_422_WITH_DATA,
    /**
     * An internal error occurred while attempting to execute a request. It is best practice to
     * re-throw this exception so your application crashes.
     */
    UNEXPECTED
}