package org.CSLab.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@RequestMapping("/image")
public class ImageController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/getPicture")
    public void getPicture(String path, HttpServletResponse response) {
        if(path.contains(".jpg")){
            response.setContentType("image/jpg");
        }else if(path.contains(".png")){
            response.setContentType("image/png");
        }
        try{
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            long size = file.length();
            byte [] picture = new byte[(int)size];
            fis.read(picture);
            fis.close();
            OutputStream fos = response.getOutputStream();
            fos.write(picture);
            fos.flush();
            fos.close();
        }catch(IOException ioException){
            logger.error("获取图片失败");
        }
    }

}
