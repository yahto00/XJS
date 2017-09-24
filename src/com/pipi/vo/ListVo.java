package com.pipi.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装List数据
 * Created by yahto on 07/05/2017.
 */
public class ListVo<T> {

    /**
     * 记录总条数
     */
    private int totalSize;

    /**
     * 记录列表
     */
    private List<T> list;

    /**
     * 初始化构造方法
     */
    public ListVo() {
        this.totalSize = 0;
        list = new ArrayList<T>();
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

}
