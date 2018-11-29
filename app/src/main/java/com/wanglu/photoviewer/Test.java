package com.wanglu.photoviewer;

import com.wanglu.photoviewerlibrary.PhotoViewer;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class Test {
    public static void main(String[] args){
        PhotoViewer
                .INSTANCE
                .setOnPhotoViewerCreatedListener(new Function0<Unit>() {
                    @Override
                    public Unit invoke() {
                        System.out.println("okok");
                        return null;
                    }
                });
    }
}
