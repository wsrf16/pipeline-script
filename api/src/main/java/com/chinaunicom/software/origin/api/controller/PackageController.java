package com.chinaunicom.software.origin.api.controller;

public class PackageController {

    public static String getPackgeName() {
        return PackageController.class.getPackage().getName();
    }
}
