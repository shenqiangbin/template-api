package com.example.demo.controller;

import com.example.demo.base.Response;
import com.example.demo.model.Chunk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class UploadController {

    private Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Value("${upload.uploadFolder}")
    private String uploadFolder;

    /*项目初始化的时候就创建好目录*/
    @PostConstruct
    private void checkUploadFolder() throws Exception {
        if (StringUtils.isEmpty(uploadFolder)) {
            throw new Exception("uploadFolder没有设置");
        }

        File file = new File(uploadFolder);
        if (!file.exists()) {
            if (!file.mkdirs())
                throw new Exception(new StringBuilder(uploadFolder).append("没有创建成功").toString());
        }
    }

    @PostMapping("/uploader/chunk")
    public Response uploadChunk(Chunk chunk) {
        MultipartFile file = chunk.getFile();

        Response<String> response = new Response<>();

        logger.debug("OriginalFilename:{},name:{},chunkInfo:{}", file.getOriginalFilename(), file.getName(), chunk.toString());

        try {
            byte[] bytes = file.getBytes();
            Path folder = Paths.get(uploadFolder, chunk.getIdentifier());
            if (!Files.exists(folder))
                Files.createDirectories(folder);
            Path path = Paths.get(folder.toString(), chunk.getFilename() + "-" + chunk.getChunkNumber().toString());
            Files.write(path, bytes);

            /*
        https://github.com/simple-uploader/Uploader/blob/develop/README_zh-CN.md#%E9%85%8D%E7%BD%AE
        200, 201, 202: 当前块上传成功，不需要重传。
        404, 415. 500, 501: 当前块上传失败，会取消整个文件上传。
        其他状态码: 出错了，但是会自动重试上传
         */

            response.set(200, "上传成功");
        } catch (Exception e) {
            logger.error(e.getMessage() + e.getStackTrace());
            response.set(500, "上传失败");
        }

        return response;
    }

    /*上传分片和检测分片是一个接口，一个是Post请求一个是Get请求*/
    @GetMapping("/uploader/chunk")
    public Object checkChunk(Chunk chunk, HttpServletResponse response) {

        /*
        https://github.com/simple-uploader/Uploader/blob/develop/README_zh-CN.md#%E9%85%8D%E7%BD%AE
        200, 201, 202: 当前块上传成功，不需要重传。
        404, 415. 500, 501: 当前块上传失败，会取消整个文件上传。
        如果是其他状态吗，那么就认为服务端还没有这个块，需要按照标准模式上传
         */

        Path folder = Paths.get(uploadFolder, chunk.getIdentifier());
        Path path = Paths.get(folder.toString(), chunk.getFilename() + "-" + chunk.getChunkNumber().toString());
        if(!Files.exists(path)){
            response.setStatus(222);
        }
        return chunk;
    }

    @PostMapping("/uploader/mergeFile")
    public Response mergeFile(String identifier, String filename) {

        Response<String> response = new Response<>();
        try {
            String folder = uploadFolder + "/" + identifier;
            String targetFile = uploadFolder + "/" + identifier + "_" + new Date().getTime() + "_" + filename;
            Files.createFile(Paths.get(targetFile));

            Stream<Path> paths =
                    Files.list(Paths.get(folder))
                            .filter(path -> path.getFileName().toString().contains("-"))
                            .sorted((o1, o2) -> {
                                String p1 = o1.getFileName().toString();
                                String p2 = o2.getFileName().toString();
                                int i1 = p1.lastIndexOf("-");
                                int i2 = p2.lastIndexOf("-");
                                return Integer.valueOf(p2.substring(i2)).compareTo(Integer.valueOf(p1.substring(i1)));
                            });

            for (Path path : paths.collect(Collectors.toList())) {
                //以追加的形式写入文件
                Files.write(Paths.get(targetFile), Files.readAllBytes(path), StandardOpenOption.APPEND);
                //合并后删除该块
                Files.delete(path);
            }

            response.set(200, "success");
        } catch (Exception e) {
            logger.error(e.toString());
            response.set(500, "success");
        }

        return response;
    }


}
