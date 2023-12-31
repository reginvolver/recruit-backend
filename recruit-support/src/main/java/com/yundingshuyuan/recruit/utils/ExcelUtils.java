package com.yundingshuyuan.recruit.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Excel 工具类
 * <p>参考文档：<a href="https://easyexcel.opensource.alibaba.com/docs/current/api/">
 * Easy Excel</a><a href="https://blog.csdn.net/qq_48922459/article/details/124032490">
 * * HeadsDo 注解</a></p>
 */
@Slf4j
public class ExcelUtils {

    /**
     * 导出-单表写入
     * <p>头-数据</p>
     *
     * @param items        表数据
     * @param sheetName    表名
     * @param outputStream
     * @return
     */
    public static <T> void writeOneSheet(Collection<T> items, String sheetName, OutputStream outputStream) {
        if (!items.isEmpty()) {
            writeOneSheet(items.toArray()[0], items, sheetName, outputStream);
        } else {
            log.info("空");
        }
    }

    /**
     * 导出-单表写入
     * <p>头-数据 (分离)</p>
     *
     * @param headsDo      列名 Data Object对象
     * @param items        表数据
     * @param sheetName    表名
     * @param outputStream
     * @return
     */
    public static <T> void writeOneSheet(T headsDo, Collection items, String sheetName, OutputStream outputStream) {
        EasyExcel.write(outputStream)
                .head(headsDo.getClass())
                .sheet(sheetName)
                .doWrite(items);
    }

    /**
     * 导入-单表读入
     *
     * @param headsDo      接收的列名对象
     * @param inputStream
     * @param readListener 读取 excel 时调用其中方法
     */
    public static <T> void readOneExcel(T headsDo, InputStream inputStream, ReadListener readListener) {
        EasyExcel.read(inputStream, readListener)
                .head(headsDo.getClass())
                .sheet()
                .doRead();
    }

    /**
     * 导入-单表读入
     *
     * @param headsDo     接收的列名对象
     * @param inputStream
     */
    public static <T> List<T> readOneExcel(T headsDo, InputStream inputStream) {
        InnerReadListener<T> listener = new InnerReadListener<>();
        readOneExcel(headsDo, inputStream, listener);
        return listener.getList();
    }

    /**
     * 内部监听器
     *
     * @param <T>
     */
    private static class InnerReadListener<T> implements ReadListener<T> {
        // 存放解析后的数据
        private List<T> list = new ArrayList<>();

        @Override
        public void invoke(T item, AnalysisContext analysisContext) {
            list.add(item);
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            log.info("{}条数据读入完毕", list.size());
        }

        public List<T> getList() {
            return list;
        }
    }

}


