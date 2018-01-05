package com.procurement.point.service;

import com.procurement.point.model.dto.ResponseDto;
import com.procurement.point.repository.PublicRepository;
import com.procurement.point.utils.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PublicServiceImpl implements PublicService {

    private final PublicRepository PublicRepository;

    private final DateUtil dateUtil;

    @Value("${upload.file.path}")
    private String uploadFilePath;

    @Value("${upload.file.folder}")
    private String uploadFileFolder;

    @Value("${upload.file.extensions}")
    private String[] fileExtensions;

    @Value("${upload.file.max-weight}")
    private Integer maxFileWeight;

    public PublicServiceImpl(final PublicRepository PublicRepository,
                             final DateUtil dateUtil) {
        this.PublicRepository = PublicRepository;
        this.dateUtil = dateUtil;
    }

    @Override
    public ResponseDto get() {
        return null;
    }
}
