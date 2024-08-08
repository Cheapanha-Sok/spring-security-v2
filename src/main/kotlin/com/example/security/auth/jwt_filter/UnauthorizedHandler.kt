package com.example.security.auth.jwt_filter

import jakarta.servlet.http.HttpServletResponse
import com.google.gson.Gson

class UnauthorizedHandler(
    var responseCode: Int? = null,
    var responseMessage: String? = null,
    var responseType: String? = null,
    var errorCode: String? = null,
    var hashData: String? = "",
    var data: String? = "",
) {

    companion object {
        fun invalidX_Api_Key(response: HttpServletResponse) {

            val res = UnauthorizedHandler(
                responseCode = 403,
                responseMessage = "Request Not Accept",
                responseType = "X-API-KEY INVALID",
                errorCode = "MERCHANT_REQUEST_INVALID"
            )

            val json = Gson().toJson(res)
            response.status = 403
            response.contentType = "application/json"
            response.characterEncoding = "UTF-8"
            response.writer.write(json)
            return
        }

        fun requiredX_Api_Key(response: HttpServletResponse) {

            val res = UnauthorizedHandler(
                responseCode = 401,
                responseMessage = "Request Not Accept",
                responseType = "X-API-KEY REQUIRE",
                errorCode = "MERCHANT_REQUEST_INVALID"
            )

            val json = Gson().toJson(res)
            response.status = 401
            response.contentType = "application/json"
            response.characterEncoding = "UTF-8"
            response.writer.write(json)
            return
        }

        fun invalidToken(response: HttpServletResponse) {

            val res = UnauthorizedHandler(
                responseCode = 401,
                responseMessage = "Request Not Accept",
                responseType = "JWT TOKEN INVALID",
                errorCode = "MERCHANT_REQUEST_INVALID"
            )

            val json = Gson().toJson(res)
            response.status = 401
            response.contentType = "application/json"
            response.characterEncoding = "UTF-8"
            response.writer.write(json)
            return
        }

        fun invalidToken(): UnauthorizedHandler {
            return UnauthorizedHandler(
                responseCode = 401,
                responseMessage = "Request Not Accept",
                responseType = "JWT TOKEN INVALID",
                errorCode = "MERCHANT_REQUEST_INVALID"
            )
        }

        fun expiredToken(response: HttpServletResponse) {

            val res = UnauthorizedHandler(
                responseCode = 401,
                responseMessage = "Request Not Accept",
                responseType = "JWT TOKEN WAS EXPIRED",
                errorCode = "MERCHANT_REQUEST_INVALID"
            )

            val json = Gson().toJson(res)
            response.status = 401
            response.contentType = "application/json"
            response.characterEncoding = "UTF-8"
            response.writer.write(json)
            return
        }

        fun requiredToken(response: HttpServletResponse) {

            val res = UnauthorizedHandler(
                responseCode = 401,
                responseMessage = "Request Not Accept",
                responseType = "JWT Token REQUIRE",
                errorCode = "MERCHANT_REQUEST_INVALID"
            )

            val json = Gson().toJson(res)
            response.status = 401
            response.contentType = "application/json"
            response.characterEncoding = "UTF-8"
            response.writer.write(json)
            return
        }
    }
}