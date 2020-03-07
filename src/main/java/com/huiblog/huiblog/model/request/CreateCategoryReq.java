package com.huiblog.huiblog.model.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CreateCategoryReq {
    @NotNull(message = "Name is required")
    @NotEmpty(message = "Name must be not Empty")
    private String name;
}
