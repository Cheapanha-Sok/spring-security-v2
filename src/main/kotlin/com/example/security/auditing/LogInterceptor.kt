package com.example.security.auditing

import com.example.security.utilities.annotation.Sl4JLogger.Companion.log
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView


@Component
class LogInterceptor : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, dataObject: Any): Boolean {
        val st1 = """ip [${request.remoteAddr}] method [${request.method}] url [${request.requestURI}] params [${request.parameterMap.map { "${it.key}: [${it.value.joinToString()}]" }.joinToString(", ")}]"""
        log.info(st1)
        return true
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        dataObject: Any,
        model: ModelAndView?
    ) {
        val st1 = """http status [${response.status}]"""
        log.info(st1)
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        dataObject: Any,
        e: Exception?
    ) {
        try {

//            val userAgent: UserAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"))
//            var currentUser: UserPrinc? = null
//
//            val auth = SecurityContextHolder.getContext()?.authentication?.principal
//
//            if (auth != "anonymousUser") {
//                currentUser = auth as UserPrinc?
//            }
            //val audit = auditTrailRepository.save(mapAuditTrail(request, userAgent, dataObject, currentUser))


        } catch (_: Exception) {

        }

    }
}