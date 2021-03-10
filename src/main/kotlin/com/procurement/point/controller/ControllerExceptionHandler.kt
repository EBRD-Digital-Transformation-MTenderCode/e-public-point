package com.procurement.point.controller

import com.procurement.point.exception.GetDataException
import com.procurement.point.exception.InternalException
import com.procurement.point.exception.ParamException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

@ControllerAdvice
class ControllerExceptionHandler {

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(::ControllerExceptionHandler::class.java)
    }

    @ResponseBody
    @ExceptionHandler(GetDataException::class)
    fun getDataException(e: GetDataException) = ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ResponseBody
    @ExceptionHandler(ParamException::class)
    fun getParamException(e: ParamException) = ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @ResponseBody
    @ExceptionHandler(InternalException::class)
    fun processInternalException(e: InternalException): ResponseEntity<String> {
        LOG.error(e.message)
        return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
    }

}
