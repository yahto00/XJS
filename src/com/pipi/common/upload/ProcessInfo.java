package com.pipi.common.upload;

/**
 * 实现进度条的相应属性
 * @author liuyang
 */
public class ProcessInfo {
	public long totalSize = 1;
    public long readSize = 0;
    public String show = "";
    public int itemNum = 0;
    public int rate = 0;
}
