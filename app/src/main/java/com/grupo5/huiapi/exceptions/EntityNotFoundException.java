package com.grupo5.huiapi.exceptions;

import com.grupo5.huiapi.modules.EntityType;
import com.grupo5.huiapi.utils.StringUtils;
import lombok.Getter;

public class EntityNotFoundException extends CustomException {
    @Getter
    private final EntityType type;
    public EntityNotFoundException(EntityType type) {
        super(StringUtils.capitalize(type.toString()) + " not found.");
        this.type = type;
    }
}
