package com.wen.controller;

import com.alibaba.fastjson.JSON;
import com.wen.pojo.FileFolder;
import com.wen.pojo.FileStore;
import com.wen.pojo.User;
import com.wen.utils.ResponseUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * FileFolderController类
 *
 * @author Mr.文
 */
@RestController
@RequestMapping("/fileFolder")
public class FileFolderController extends BaseController {


    @PostMapping("addFileFolder/{folderName}")
    public String addFileFolder(@RequestParam("parentFolderId") String pFolderId,
                                @PathVariable String folderName) {

        try {
            User user = tokenService.getTokenUser();
            FileStore store = fileStoreService.queryStoreByUserId(user.getId());
            FileFolder fileFolder = new FileFolder(-1, folderName, Integer.parseInt(pFolderId), store.getFileStoreId(), "");
            if (!fileFolderService.addFileFolder(fileFolder)) {
                return ResponseUtil.error("新建文件夹失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.error("新建文件夹失败");
        }
        return ResponseUtil.success("新建文件夹成功");
    }

    @DeleteMapping("/delFileFolder")
    public String delFileFolder(@RequestParam("IdList") String folderId) {

        List<String> list = JSON.parseArray(folderId, String.class);
        try {
            int count = 0;
            for (String fileId : list) {
                if (fileFolderService.delFolder(Integer.parseInt(fileId))) {
                    count++;
                }
            }
            if (count == list.size()) {
                return ResponseUtil.success("所选文件夹已删除");
            } else {
                return ResponseUtil.error((list.size() - count) + " 个文件夹删除失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.error("执行异常");
        }
    }

    @PutMapping("/updateFolderName")
    public String updateFolderName(@RequestParam("fileId") String folderId,
                                   @RequestParam("newName") String newName) {

        try {
            if (fileFolderService.updateFolderName(Integer.parseInt(folderId), newName)) {
                return ResponseUtil.success("重命名成功");
            } else {
                return ResponseUtil.error("重命名失败");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return ResponseUtil.error("重命名失败");
        }
    }
}
