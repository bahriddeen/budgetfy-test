package com.budgetfy.app.mapstruct;

import com.budgetfy.app.mapstruct.base.ObjectMapper;
import com.budgetfy.app.model.Template;
import com.budgetfy.app.payload.dto.TemplateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TemplateMapper
        extends ObjectMapper<TemplateDTO, Template> {

    @Override
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "accountId", target = "account")
    @Mapping(source = "categoryId", target = "category")
    Template mapDTOToEntity(TemplateDTO templateDTO);

    @Override
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "category.id", target = "categoryId")
    TemplateDTO mapEntityToDTO(Template template);

    @Override
    List<Template> listMapDTOToEntity(List<TemplateDTO> templateDTOS);

    @Override
    List<TemplateDTO> listMapEntityToDTO(List<Template> templates);


}
