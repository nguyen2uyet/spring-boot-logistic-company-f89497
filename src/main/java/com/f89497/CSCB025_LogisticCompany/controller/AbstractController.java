package com.f89497.CSCB025_LogisticCompany.controller;

import org.springframework.ui.Model;

abstract class AbstractController<T> {
    public abstract String add(T t,Model model);
    public abstract String update(T t,Model model);
    public abstract String delete(String stdId,Model model);
    public abstract String edit(String stdId,Model model);

}
