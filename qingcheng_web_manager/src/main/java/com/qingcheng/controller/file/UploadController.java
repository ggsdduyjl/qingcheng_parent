package com.qingcheng.controller.file;

import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private OSSClient ossClient;

    @Value("${oss.bucketName}")
    private String bucketName;
    @Value("${oss.endpoint}")
    private String endpoint;

    /**
     * 上传文件到本地
     *
     * @param file 要上传的文件
     * @return 图片路径
     */
    @RequestMapping("active")
    public String active(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        // 获取文件夹路径
        String path = request.getSession().getServletContext().getRealPath("img");
        // 获取文件后缀名
        String hzName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
        // 新文件名
        String fileName = UUID.randomUUID() + hzName;
        // 新文件的路径
        String pathName = path + "/" + fileName;
        File f = new File(pathName);
        // 判断img文件夹是否存在，不存在则创建
        if (!f.getParentFile().exists()) {
            f.mkdirs();
        }
        // 上传
        file.transferTo(f);
        return "http://localhost:9101/img/" + fileName;
    }

    /**
     * 上传文件到oss
     *
     * @param file 要上传的文件
     * @return 图片路径
     */
    @RequestMapping("oss")
    public String oss(@RequestParam("file") MultipartFile file,String folder) throws IOException {
        // 获取文件后缀名
        String hzName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
        // 新文件名
        String fileName = folder + "/" + UUID.randomUUID() + hzName;
        // 上传
        ossClient.putObject(bucketName, fileName, file.getInputStream());
        return "https://" + bucketName + "." + endpoint + "/" + fileName;
    }

}
