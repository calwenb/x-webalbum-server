package com.wen.xwebalbum.servcie;

import com.wen.xwebalbum.pojo.MyFile;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * FileService业务类
 * 对File进行上传、下载、删除、查询、修改、分享、清除无效文件
 * 并操作服务器I/O
 *
 * @author Mr.文
 */
public interface FileService {

    boolean uploadFile(MultipartFile file, int userId, String fatherFileFolderId);

    List<MyFile> queryMyFiles(int userId, int parentFolderId, int pageNum);

    List<MyFile> queryFilesByUid(int userId, int pageNum);

    List<Map<String, String>> queryFilesByUid(int userId, int pageNum, boolean preview) throws IOException;
    /* List<MyFile> queryFilesByType(int userId, String type, int pageNum);*/


    boolean deleteByMyFileId(int fileId);

    /**
     * 文件下载业务
     */
    ResponseEntity<InputStreamResource> downloadByMyFileId(int fileId) throws IOException;

    ResponseEntity<InputStreamResource> downloadUtil(String path) throws IOException;

    /* ResponseEntity<InputStreamResource> previewImage(List<String> list) throws IOException;*/
    //ArrayList<?> previewImage(List<String> list) throws IOException;

    boolean updateFileName(int fileId, String newName);

    /**
     * 生成分享码，保存在redis中
     * {key：fid，value：code}
     *
     * @param fileId 文件id
     * @return 文件分享码
     */
    String shareFile(int fileId);

    /**
     * 通过分享码在redis中找到fid，在通过数据库返回文件信息
     *
     * @param shareCode 分享码
     * @return
     */
    MyFile getShareFile(String shareCode);

    List<String> clearBadFile();

    boolean uploadFileComm(MultipartFile file, String path);
}
