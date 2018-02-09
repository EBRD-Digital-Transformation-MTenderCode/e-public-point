package com.procurement.point.controller;

import com.procurement.point.model.dto.offset.OffsetDto;
import com.procurement.point.model.dto.record.RecordPackageDto;
import com.procurement.point.model.dto.release.ReleasePackageDto;
import com.procurement.point.service.PublicTenderService;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping(value = "/tender")
public class PublicTenderController {

    private final PublicTenderService publicService;

    public PublicTenderController(final PublicTenderService publicService) {
        this.publicService = publicService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{cpid}")
    public ResponseEntity<RecordPackageDto> getRecordPackage(@PathVariable(value = "cpid") final String cpid,
                                                             @RequestParam(value = "offset", required = false)
                                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                             final LocalDateTime
                                                                 offset) {
        return new ResponseEntity<>(publicService.getRecordPackage(cpid, offset), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{cpid}/{ocid}")
    public ResponseEntity<ReleasePackageDto> getReleasePackage(@PathVariable(value = "cpid") final String cpid,
                                                               @PathVariable(value = "ocid") final String ocid,
                                                               @RequestParam(value = "offset", required = false)
                                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                   final LocalDateTime
                                                                   offset) {
        return new ResponseEntity<>(publicService.getReleasePackage(cpid, ocid, offset), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<OffsetDto> getByOffset(@RequestParam(value = "offset")
                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final
                                                 LocalDateTime offset,
                                                 @RequestParam(value = "limit") final Integer limit) {
        return new ResponseEntity<>(publicService.getByOffset(offset, limit), HttpStatus.OK);
    }
}

