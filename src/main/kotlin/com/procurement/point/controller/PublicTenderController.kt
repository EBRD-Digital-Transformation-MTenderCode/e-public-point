package com.procurement.point.controller

import com.procurement.point.model.dto.offset.OffsetDto
import com.procurement.point.model.dto.record.RecordPackageDto
import com.procurement.point.model.dto.release.ReleasePackageDto
import com.procurement.point.service.PublicTenderService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/tenders")
class PublicTenderController(private val publicService: PublicTenderService) {

    @GetMapping("/{cpid}")
    fun getRecordPackage(@PathVariable(value = "cpid") cpid: String,
                         @RequestParam(value = "offset", required = false)
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                         offset: LocalDateTime?): ResponseEntity<RecordPackageDto> {
        return ResponseEntity(publicService.getRecordPackage(cpid, offset), HttpStatus.OK)
    }

    @GetMapping("/{cpid}/{ocid}")
    fun getReleasePackage(@PathVariable(value = "cpid") cpid: String,
                          @PathVariable(value = "ocid") ocid: String,
                          @RequestParam(value = "offset", required = false)
                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                          offset: LocalDateTime?): ResponseEntity<ReleasePackageDto> {
        return ResponseEntity(publicService.getRecord(cpid, ocid, offset), HttpStatus.OK)
    }

    @GetMapping
    fun getByOffset(@RequestParam(value = "offset")
                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                    offset: LocalDateTime,
                    @RequestParam(value = "limit", required = false) limitParam: Int?): ResponseEntity<OffsetDto> {
        return ResponseEntity(publicService.getByOffset(offset, limitParam), HttpStatus.OK)
    }
}

