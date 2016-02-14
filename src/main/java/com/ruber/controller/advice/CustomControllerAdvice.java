package com.ruber.controller.advice;

import com.ruber.controller.GroupsController;
import com.ruber.util.CaseInsensitiveEnumEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class CustomControllerAdvice {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
//        binder.registerCustomEditor(GroupsController.FieldValue.class,
//            new CaseInsensitiveEnumEditor<>(GroupsController.FieldValue.class));
    }
}
