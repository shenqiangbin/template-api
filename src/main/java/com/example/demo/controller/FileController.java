package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
public class FileController {

    @ResponseBody
    @GetMapping("/file/index")
    public String test() {
        return "this is test";
    }

    /*
    * 将查询列表导出的两种形式：
    *   1、从数据库读取数据后，直接将数据信息以字符串转字节的形式输出（推荐）
    *   2、从数据库读取数据后，先将数据保存在服务器磁盘上，然后将文件输出，相当于下载文件
    *
    * 上面两种又分两种输出形式：
    *   1、将文件所有字节获取后，输出给浏览器
    *   2、部分获取，部分输出（推荐）
    * */

    /*直接将内容输出到浏览器*/
    @GetMapping("file/output")
    public void getFile(HttpServletRequest request, HttpServletResponse response) {
        try {

            String name = "12.csv";
            response.setHeader("Content-Type", "application/octet-stream;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + name);

            ServletOutputStream outputStream = response.getOutputStream();

            byte[] byteArr = ("标题,内容\n").getBytes();
            outputStream.write(byteArr);

            for (int i = 0; i < 10000 * 100; i++) {
                outputStream.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n", i, i + 1, i + 2, i + 3, i + 4, i + 5, i + 6, i + 7, i + 8, i + 9).getBytes());
            }

            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            log(e.toString());
        }
    }

    /*将文件一部分一部分的读取，然后一部分一部分的输出到浏览器*/
    @GetMapping("file/output2")
    public void getFile2(HttpServletRequest request, HttpServletResponse response) {
        try {

            String name = "12.iso";
            response.setHeader("Content-Type", "application/octet-stream;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + name);

            ServletOutputStream outputStream = response.getOutputStream();

            File file = new File("/Users/adminqian/shen/cn_windows_7_enterprise_x64_dvd_x15-70741.iso");
            FileInputStream inputStream = new FileInputStream(file);

            byte[] chunk = new byte[1024];

            int result;
            do {
                result = inputStream.read(chunk);
                outputStream.write(chunk);
            }
            while (result != -1);

//            byte[] byteArr = ("标题,内容\n").getBytes();
//            outputStream.write(byteArr);
//
//            for (int i = 0; i < 10000 * 100; i++) {
//                outputStream.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n", i, i + 1, i + 2, i + 3, i + 4, i + 5, i + 6, i + 7, i + 8, i + 9).getBytes());
//            }

            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            log(e.toString());
        }
    }

    /*一次性将文件所有字节读取出来，并输出出去*/
    @GetMapping("file/output3")
    public void getFile3(HttpServletRequest request, HttpServletResponse response) {
        try {

            String name = "12.iso";
            response.setHeader("Content-Type", "application/octet-stream;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + name);

            ServletOutputStream outputStream = response.getOutputStream();

            File file = new File("/Users/adminqian/shen/cn_windows_7_enterprise_x64_dvd_x15-70741.iso");
            FileInputStream inputStream = new FileInputStream(file);

            int length = inputStream.available();
            byte[] data = new byte[length];
            inputStream.read(data);
            outputStream.write(data);
            /*如果文件过多，会直接提示 java.lang.OutOfMemoryError: Requested array size exceeds VM limit */

            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            log(e.toString());
        }
    }

    private void log(Object obj) {
        System.out.println(obj);
    }

}
