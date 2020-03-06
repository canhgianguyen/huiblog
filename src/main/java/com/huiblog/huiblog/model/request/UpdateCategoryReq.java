package com.huiblog.huiblog.model.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.huiblog.huiblog.extension.CustomDateDeserializer;
import com.huiblog.huiblog.extension.CustomDateSerializer;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UpdateCategoryReq {
    @NotNull(message = "Name is required")
    @NotEmpty(message = "Name must be not Empty")
    private String name;
}
