package com.pipi.common.thread;

import java.io.File;

import org.apache.commons.io.FileUtils;

/**
 * 移动文件线程
 * Created by yahto on 07/05/2017.
 */
public class MoveFile extends Thread {

    /**
     * 移动文件的旧路径
     */
    private String oldPath;

    /**
     * 移动文件的新路径
     */
    private String newPath;

    public MoveFile(String oldPath, String newPath) {
        this.oldPath = oldPath;
        this.newPath = newPath;
    }

    @Override
    public void run() {
        while (true) {
            try {
                File oldFile = new File(oldPath);
                File newFile = new File(newPath);
                FileUtils.copyFile(oldFile, newFile);
                FileUtils.forceDelete(oldFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
            break;
        }
    }
}
