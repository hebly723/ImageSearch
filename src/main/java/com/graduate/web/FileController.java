package com.graduate.web;

import com.graduate.algorithm.Algorithm;
import com.graduate.algorithm.impl.CVA;
import com.graduate.algorithm.impl.PHA;
import com.graduate.dao.HashcvaDao;
import com.graduate.dao.HashphaDao;
import com.graduate.dao.ImageDao;
import com.graduate.entity.*;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import static org.opencv.imgcodecs.Imgcodecs.imread;

@RequestMapping("/Image/file")
@Controller
public class FileController {
    @Autowired
    HashphaDao haphaDao;
    @Autowired
    ImageDao imageDao;
    @Autowired
    HashcvaDao hashcvaDao;
    /**
     * 文件上传功能
     * @param file
     * @return
     * @throws IOException
     */
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    @RequestMapping(value="/upload",method= RequestMethod.POST)
    @ResponseBody
    public String upload(Model model, MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileName = file.getOriginalFilename();
//        File dir = new File(path);
        File dir = new File("/home/hebly723/temp/",fileName);
        if(!dir.exists()){
            dir.mkdirs();
        }
        //MultipartFile自带的解析方法
        file.transferTo(dir);
        request.getSession().setAttribute("image", "/home/hebly723/temp/"+file.getOriginalFilename());
        response.sendRedirect("answer");
        return "answer";
    }
    /**
     * 文件上传功能
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping(value="/answer")
    public String answer(Model model, HttpServletRequest request) throws IOException {
        Mat mat = imread((String)request.getSession().getAttribute("image"));
        File file = new File((String)request.getSession().getAttribute("image"));
        HashPack hashPack = new HashPack();
        Algorithm algorithm = new PHA();
        hashPack.setHashPha(algorithm.hashString(mat));
        algorithm = new CVA();
        hashPack.setHashCva(algorithm.hashString(mat));

        List<Image> images = haphaDao.selectHash(hashPack);
        model.addAttribute("image", (String)request.getSession().getAttribute("image"));
        model.addAttribute("list", images);
//        file.delete();
        return "answer";
    }

    /**
     * 文件下载功能
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/down")
    public void down(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //模拟文件，myfile.txt为需要下载的文件
        String fileName = request.getSession().getServletContext().getRealPath("upload")+"/myfile.txt";
        //获取输入流
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
        //假如以中文名下载的话
        String filename = "下载文件.txt";
        //转码，免得文件名中文乱码
        filename = URLEncoder.encode(filename,"UTF-8");
        //设置文件下载头
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        int len = 0;
        while((len = bis.read()) != -1){
            out.write(len);
            out.flush();
        }
        out.close();
    }

}
