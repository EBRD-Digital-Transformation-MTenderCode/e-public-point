package com.procurement.point.controller

import com.procurement.point.exception.ParamException
import com.procurement.point.model.dto.OffsetDto
import com.procurement.point.model.dto.RecordPackageDto
import com.procurement.point.model.dto.ReleasePackageDto
import com.procurement.point.service.PublicBudgetService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/budgets")
class PublicBudgetController(private val publicService: PublicBudgetService) {

    @GetMapping
    fun getByOffset(@RequestParam(value = "offset", required = false)
                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                    offset: LocalDateTime?,
                    @RequestParam(value = "limit", required = false) limitParam: Int?,
                    request: HttpServletRequest): ResponseEntity<OffsetDto> {
        checkParams(request.parameterNames.toList(), listOf("offset", "limit"))
        return ResponseEntity(publicService.getByOffset(offset, limitParam), HttpStatus.OK)
    }

    @GetMapping("/{cpid}")
    fun getRecordPackage(@PathVariable(value = "cpid") cpid: String,
                         @RequestParam(value = "offset", required = false)
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                         offset: LocalDateTime?,
                         request: HttpServletRequest): ResponseEntity<RecordPackageDto> {
        checkParams(request.parameterNames.toList(), listOf("offset"))
        return ResponseEntity(publicService.getRecordPackage(cpid, offset), HttpStatus.OK)
    }

    @GetMapping("/{cpid}/{ocid}")
    fun getReleasePackage(@PathVariable(value = "cpid") cpid: String,
                          @PathVariable(value = "ocid") ocid: String,
                          @RequestParam(value = "offset", required = false)
                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                          offset: LocalDateTime?,
                          request: HttpServletRequest): ResponseEntity<ReleasePackageDto> {
        checkParams(request.parameterNames.toList(), listOf("offset"))
        return ResponseEntity(publicService.getRecord(cpid, ocid, offset), HttpStatus.OK)
    }

    private fun checkParams(parameterNames: List<String>, prams: List<String>) {
        parameterNames.forEach { paramRq ->
            if (!prams.contains(paramRq)) throw ParamException("Invalid parameter:'$paramRq'. Valid params: $prams")
        }
    }
}

