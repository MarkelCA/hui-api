package com.grupo5.huiapi.exceptions;

import com.grupo5.huiapi.modules.EntityType;
import com.grupo5.huiapi.utils.StringUtils;

public class EntityNotFoundException extends CustomException {
    private final EntityType type;
    public EntityNotFoundException(EntityType type) {
        super(StringUtils.capitalize(type.toString()) + " not found.");
        this.type = type;
    }
}
