package com.swxy.common;

public enum ExcelMeta {

    FUNCTIONNAME("testFunctionName"),
    INPUTJSON("inputJson"),
    EXPECTED("expected"),
    COMMON("备注");

    private int id;
    private String name;

    ExcelMeta() {
    }

    private ExcelMeta(String name) {
        this.name=name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
