package com.abank.IDCard.data.repository.server.interceptor

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class BaseAuthenticator : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        // todo: do something on 401
        return null
    }
}