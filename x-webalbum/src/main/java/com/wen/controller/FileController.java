package com.wen.controller;

import com.alibaba.fastjson.JSON;
import com.wen.annotation.PassToken;
import com.wen.pojo.FileFolder;
import com.wen.pojo.FileStore;
import com.wen.pojo.MyFile;
import com.wen.pojo.User;
import com.wen.utils.FileUtil;
import com.wen.utils.ResponseUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * FileController类
 *
 * @author Mr.文
 */
@RestController
@RequestMapping("/file")
public class FileController extends BaseController {

    @GetMapping("/queryFiles/p/{page}")
    public String queryFiles(@PathVariable String page) {
        List<MyFile> myFiles;
        try {
            User user = tokenService.getTokenUser();
            myFiles = fileService.queryFilesByUid(user.getId(), Integer.parseInt(page));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return ResponseUtil.error("执行异常");
        }
        return ResponseUtil.success(JSON.toJSONString(myFiles));

    }


    @PassToken
    @GetMapping("/queryFile_data/p/{page}")
    public List<?> queryFilesData(@PathVariable String page) {
        User user = tokenService.getTokenUser();
        try {
            return fileService.queryFilesByUid(user.getId(), Integer.parseInt(page), true);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam("fatherFileFolderId") String fatherFileFolderId) {

        if (file.isEmpty()) {
            return ResponseUtil.uploadFileError("文件为空");
        }
        // 判断上传文件大小
        if (!FileUtil.checkFileSize(file)) {
            return ResponseUtil.uploadFileError("上传文件大于2GB ");
        }

        User user = tokenService.getTokenUser();
        int userId = user.getId();
        if (fileService.uploadFile(file, userId, fatherFileFolderId)) {
            return ResponseUtil.success(file.getOriginalFilename() + " 上传文件成功");
        }
        return ResponseUtil.error("未知错误!");
    }

    @GetMapping("/queryMyFiles")
    public String queryMyFiles(@RequestParam("parentFolderId") String parentFolderId) {
        List<MyFile> myFiles;
        List<FileFolder> fileFolders;
        List fileAndFolds = new ArrayList();
        try {
            User user = tokenService.getTokenUser();
            FileStore store = fileStoreService.queryStoreByUserId(user.getId());

            myFiles = fileService.queryMyFiles(user.getId(), Integer.parseInt(parentFolderId), -1);
            fileFolders = fileFolderService.queryFoldersByPId(store.getFileStoreId(), Integer.parseInt(parentFolderId));
            fileAndFolds.addAll(fileFolders);
            fileAndFolds.addAll(myFiles);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return ResponseUtil.error("执行异常");
        }
        return ResponseUtil.success(JSON.toJSONString(fileAndFolds));
    }


    @DeleteMapping("/delByFileIds")
    public String delByFileIds(@RequestParam("IdList") String fileIdList) {
        List<String> list = JSON.parseArray(fileIdList, String.class);
        try {
            int count = 0;
            for (String fileId : list) {
                if (fileService.deleteByMyFileId(Integer.parseInt(fileId))) {
                    count++;
                }
            }
            if (count == list.size()) {
                return ResponseUtil.success("所选文件已删除");
            } else {
                return ResponseUtil.error((list.size() - count) + " 个文件删除失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.error("执行异常");
        }
    }

    @PassToken
    @GetMapping("/downByFileIds")
    public Object downByFileIds(@RequestParam("fileIdList") String fileIdList) {

        List<String> list = JSON.parseArray(fileIdList, String.class);
        try {
            return fileService.downloadByMyFileId(Integer.parseInt(list.get(0)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.downloadFileError("下载失败");
        }
    }

    @PutMapping("/updateFileName")
    public String updateFileName(@RequestParam("fileId") String fileId,
                                 @RequestParam("newName") String newName) {

        try {
            if (fileService.updateFileName(Integer.parseInt(fileId), newName)) {
                return ResponseUtil.success("重命名成功");
            } else {
                return ResponseUtil.error("重命名失败");
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            return ResponseUtil.error("重命名失败");
        }
    }

    @GetMapping("/shareFile")
    public String shareFile(@RequestParam("fileId") String fileId) {
        String shareCode = fileService.shareFile(Integer.parseInt(fileId));
        if (shareCode == null) {
            return ResponseUtil.error("分享失败");
        }
        return ResponseUtil.success(shareCode);
    }

    @PassToken
    @GetMapping("/getShareFile")
    public String getShareFile(@RequestParam("shareCode") String shareCode) {
        MyFile file = null;
        try {
            file = fileService.getShareFile(shareCode);
            if (file == null) {
                return ResponseUtil.fileMiss("分享的文件不存在或过期!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseUtil.success(JSON.toJSONString(file));
    }

}
