package com.wen.xwebalbum.controller;

import com.wen.xwebalbum.servcie.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * 同时给Controller注入多个Service
 *
 * @author Mr.文
 */
@Repository
public abstract class BaseController {
    @Resource
    UserService userService;
    @Resource
    FileService fileService;
    @Resource
    FileFolderService fileFolderService;
    @Resource
    FileStoreService fileStoreService;
    @Resource
    TokenService tokenService;

}
