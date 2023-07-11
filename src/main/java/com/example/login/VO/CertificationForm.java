package com.example.login.VO;

import lombok.Getter;

@Getter
public class CertificationForm {
    private String name;
    private String phone;

    public CertificationForm(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}
