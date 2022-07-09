package com.wen.xwebalbum.servcie;

import com.wen.xwebalbum.pojo.FileStore;

/**
 * FileStoreService业务类
 * 对仓库初始化、查询业务，并操作服务器I/O
 * @author Mr.文
 */
public interface FileStoreService {

    boolean initStore(int userId);

    FileStore queryStoreByUserId(int userId);
}
