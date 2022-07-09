package com.wen.xwebalbum.utils;

import com.wen.xwebalbum.mapper.FileFolderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * FolderUtil类
 *
 * @author Mr.文
 */
@Component
public class FolderUtil {
    @Resource
    FileFolderMapper fileFolderMapper;

    /**
     * 异步任务 处理无效文件夹以及文件
     *
     * @param a
     */
    @Async
    public void delErrorFolAndFile(int a) {
        //fileFolderMapper.
    }

}
