package com.budgetfy.app.service.impl;

import com.budgetfy.app.mapstruct.TemplateMapper;
import com.budgetfy.app.model.Template;
import com.budgetfy.app.payload.dto.TemplateDTO;
import com.budgetfy.app.payload.response.ApiResponse;
import com.budgetfy.app.repository.TemplateRepository;
import com.budgetfy.app.service.TemplateService;
import com.budgetfy.app.service.functionality.Create;
import com.budgetfy.app.service.functionality.Delete;
import com.budgetfy.app.service.functionality.Read;
import com.budgetfy.app.service.functionality.Update;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.budgetfy.app.enums.Message.*;
import static com.budgetfy.app.utils.Utils.getCurrentUser;
import static com.budgetfy.app.utils.constants.Constants.*;

@Service
@RequiredArgsConstructor
public class TemplateServiceImpl
        implements
        TemplateService,
        Create<TemplateDTO>,
        Update<TemplateDTO>,
        Delete<Integer>,
        Read<Integer> {

    private final TemplateMapper templateMapper;
    private final TemplateRepository templateRepository;

    @Override
    public ApiResponse create(TemplateDTO templateDTO) {

        boolean exists = templateRepository.existsByNameAndUserId(templateDTO.name(), templateDTO.userId());

        if (exists)
            return ApiResponse.error(
                    TEMPLATE_ALREADY_EXISTS.message(),
                    HttpStatus.CONFLICT.value()
            );

        Template template = templateMapper.mapDTOToEntity(templateDTO);

        templateRepository.save(template);

        return ApiResponse.success(
                TEMPLATE_CREATED.message(),
                HttpStatus.CREATED.value()
        );

    }

    @Override
    public ApiResponse delete(Integer templateId) {

        templateRepository.deleteById(templateId);

        return ApiResponse.success(
                TEMPLATE_DELETED.message(),
                HttpStatus.NO_CONTENT.value()
        );

    }

    @Override
    public ApiResponse getDataById(Integer templateId) {

        return templateRepository.findById(templateId)
                .map(
                        template ->
                                ApiResponse.success(
                                        templateMapper.mapEntityToDTO(template),
                                        HttpStatus.OK.value()
                                )
                )
                .orElse(
                        ApiResponse.error(
                                TEMPLATE_NOT_FOUND.message(),
                                HttpStatus.NOT_FOUND.value()
                        )
                );

    }

    @Override
    public ApiResponse update(Integer templateId, TemplateDTO templateDTO) {

        return templateRepository.findById(templateId)
                .map(
                        template -> {
                            Template entity = templateMapper.mapDTOToEntity(templateDTO);
                            entity.setId(templateId);

                            templateRepository.save(entity);

                            return ApiResponse.success(
                                    TEMPLATE_UPDATED.message(),
                                    HttpStatus.OK.value()
                            );
                        }
                ).orElse(
                        ApiResponse.error(
                                TEMPLATE_NOT_FOUND.message(),
                                HttpStatus.CONFLICT.value()
                        )
                );
    }

    @Override
    public ApiResponse getUserTemplates(Integer userId) {

        List<TemplateDTO> templates = templateMapper.listMapEntityToDTO(templateRepository.findTemplatesByUserId(userId));

        return ApiResponse.success(
                templates,
                HttpStatus.OK.value()
        );

    }

    @Override
    public ApiResponse searchByName(String templateName) {

        Integer userId = Objects.requireNonNull(getCurrentUser()).getId();
        List<TemplateDTO> templates = templateMapper.listMapEntityToDTO(
                templateRepository.searchByNameContainingIgnoreCaseAndUserId(templateName, userId)
        );

        return ApiResponse.success(
                templates,
                HttpStatus.OK.value()
        );
    }

}
