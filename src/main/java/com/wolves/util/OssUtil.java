package com.wolves.util;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import com.wolves.common.OSSClientConstants;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 上传
 * @author gf
 * @date 2019/3/26
 */
public class OssUtil {

    static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(OssUtil.class);

    /**
     * 阿里云API的内或外网域名
     */
    private static String ENDPOINT;
    /**
     * 阿里云API的密钥Access Key ID
     */
    private static String ACCESS_KEY_ID;
    /**
     * 阿里云API的密钥Access Key Secret
     */
    private static String ACCESS_KEY_SECRET;
    /**
     * 阿里云API的bucket名称
     */
    private static String BACKET_NAME;
    /**
     * 阿里云API的文件夹名称
     */
    private static String FOLDER;
    /**
     * 访问路径
     */
    private static String FILE_URL;
    /**
     * 初始化属性
     */
    static{
        ENDPOINT = OSSClientConstants.ENDPOINT;
        ACCESS_KEY_ID = OSSClientConstants.ACCESS_KEY_ID;
        ACCESS_KEY_SECRET = OSSClientConstants.ACCESS_KEY_SECRET;
        BACKET_NAME = OSSClientConstants.BACKET_NAME;
        FOLDER = OSSClientConstants.FOLDER;
        FILE_URL = OSSClientConstants.FILE_URL;
    }
    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     *
     * @param fileName
     *            文件名
     * @return 文件的contentType
     */
    public static String getContentType(String fileName) {
        // 文件的后缀名
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        if (".bmp".equalsIgnoreCase(fileExtension)) {
            return "image/bmp";
        }
        if (".gif".equalsIgnoreCase(fileExtension)) {
            return "image/gif";
        }
        if (".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension)
                || ".png".equalsIgnoreCase(fileExtension)) {
            return "image/jpeg";
        }
        if (".html".equalsIgnoreCase(fileExtension)) {
            return "text/html";
        }
        if (".txt".equalsIgnoreCase(fileExtension)) {
            return "text/plain";
        }
        if (".vsd".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.visio";
        }
        if (".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if (".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
            return "application/msword";
        }
        if (".xml".equalsIgnoreCase(fileExtension)) {
            return "text/xml";
        }
        if (".mp4".equalsIgnoreCase(fileExtension)) {
            return "video/mp4";
        }
        if (".xls".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.ms-excel";
        }
        if (".xlsx".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        }
        if (".zip".equals(fileExtension)) {
            return "application/zip";
        }
        if (".pdf".equals(fileExtension)) {
            return "application/pdf";
        }
        // 默认返回类型
        return "image/jpeg";
    }


    /**
     * 获取阿里云OSS客户端对象
     * @return ossClient
     */
    private static OSSClient getOSSClient() {
        OSSClient ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        BucketInfo f = ossClient.getBucketInfo(BACKET_NAME);
        return ossClient;
    }

    /**
     * 创建存储空间
     * @param ossClient      OSS连接
     * @param bucketName 存储空间
     * @return
     */
    public  static String createBucketName(OSSClient ossClient,String bucketName){
        //存储空间
        final String bucketNames=bucketName;
        if(!ossClient.doesBucketExist(bucketName)){
            //创建存储空间
            Bucket bucket=ossClient.createBucket(bucketName);
            logger.info("创建存储空间成功");
            return bucket.getName();
        }
        return bucketNames;
    }

    /**
     * 删除存储空间buckName
     * @param ossClient  oss对象
     * @param bucketName  存储空间
     */
    public static  void deleteBucket(OSSClient ossClient, String bucketName){
        ossClient.deleteBucket(bucketName);
        logger.info("删除" + bucketName + "Bucket成功");
    }

    /**
     * 创建模拟文件夹
     * @param ossClient oss连接
     * @param bucketName 存储空间
     * @param folder   模拟文件夹名如"qj_nanjing/"
     * @return  文件夹名
     */
    public  static String createFolder(OSSClient ossClient,String bucketName,String folder){
        //文件夹名
        final String keySuffixWithSlash =folder;
        //判断文件夹是否存在，不存在则创建
        if(!ossClient.doesObjectExist(bucketName, keySuffixWithSlash)){
            //创建文件夹
            ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
            logger.info("创建文件夹成功");
            //得到文件夹名
            OSSObject object = ossClient.getObject(bucketName, keySuffixWithSlash);
            String fileDir=object.getKey();
            return fileDir;
        }
        return keySuffixWithSlash;
    }

    /**
     * 根据key删除OSS服务器上的文件
     * @param ossClient  oss连接
     * @param bucketName  存储空间
     * @param folder  模拟文件夹名 如"qj_nanjing/"
     * @param key Bucket下的文件的路径名+文件名 如："upload/cake.jpg"
     */
    public static void deleteFile(OSSClient ossClient, String bucketName, String folder, String key){
        ossClient.deleteObject(bucketName, folder + key);
        logger.info("删除" + bucketName + "下的文件" + folder + key + "成功");
    }

    /**
     * 上传图片至OSS
     * @param file 上传文件（文件全路径如：D:\\image\\cake.jpg）
     * @param bucketName  存储空间
     * @param folder 模拟文件夹名 如"qj_nanjing/"
     * @return String 返回访问路径
     * */
    public static String uploadObject2OSS(MultipartFile file, String bucketName, String folder) {
        String url = null;
        try {
            OSSClient ossClient = getOSSClient();
            //以输入流的形式上传文件
            InputStream is = file.getInputStream();
            //文件名
            String fileName = file.getOriginalFilename();
            //文件大小
            Long fileSize = file.getSize();
            //创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            //上传的文件的长度
            metadata.setContentLength(is.available());
            //指定该Object被下载时的网页的缓存行为
            metadata.setCacheControl("no-cache");
            //指定该Object下设置Header
            metadata.setHeader("Pragma", "no-cache");
            //指定该Object被下载时的内容编码格式
            metadata.setContentEncoding("utf-8");
            //文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
            //如果没有扩展名则填默认值application/octet-stream
            metadata.setContentType(getContentType(fileName));
            //指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
            String suffix = System.currentTimeMillis()+"." + fileName.substring(fileName.lastIndexOf(".") + 1);
            metadata.setContentDisposition("filename/filesize=" + suffix + "/" + fileSize + "Byte.");
            //上传文件   (上传文件流的形式)
            PutObjectResult putResult = ossClient.putObject(bucketName, folder + suffix, is, metadata);
            //解析结果
            String resultStr = putResult.getETag();
            logger.info(JSONObject.toJSONString(resultStr));
            url = FILE_URL + folder + suffix;

            logger.info(url);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("上传阿里云OSS服务器异常." + e.getMessage(), e);
        }
        return url;
    }

    /**
     * 下载文件01
     * @param request
     * @param response
     * @param keyUrl
     * @return
     */
    public static HttpServletResponse downloadFile(HttpServletRequest request, HttpServletResponse response, String keyUrl){

        try {
            logger.info("【BillCommonFileServiceImpl】 download ==begin "+keyUrl);
            //访问oss上文件的http://*。。。。aliyuncs.com/需要替换掉  直接是
            keyUrl = keyUrl.replaceAll(FILE_URL,"");
            String[] split = keyUrl.split("/");
            //阿里的key值  utf8
            String fileName= URLDecoder.decode(split[split.length-1],"UTF-8");
            // 从阿里云进行下载
            //bucketName需要自己设置
            OSSClient client = getOSSClient();
            OSSObject ossObject = client.getObject(BACKET_NAME, keyUrl);
            // 已缓冲的方式从字符输入流中读取文本，缓冲各个字符，从而提供字符、数组和行的高效读取
            BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));

            InputStream inputStream = ossObject.getObjectContent();

            //缓冲文件输出流
            BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            //通知浏览器以附件形式下载
            // 为防止 文件名出现乱码
            response.setContentType("application/octet-stream");
            final String userAgent = request.getHeader("USER-AGENT");
            if(StringUtils.contains(userAgent, "MSIE")){
                //IE浏览器
                fileName = URLEncoder.encode(fileName,"UTF-8");
            }else if(StringUtils.contains(userAgent, "Mozilla")){
                //google,火狐浏览器
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            }else{
                //其他浏览器
                fileName = URLEncoder.encode(fileName,"UTF-8");
            }
            //这里设置一下让浏览器弹出下载提示框，而不是直接在浏览器中打开
            response.addHeader("Content-Disposition", "attachment;filename=" +fileName);
            logger.info("【BillCommonFileServiceImpl】 download ==end "+keyUrl);

            byte[] car = new byte[1024];
            int L;
            while((L = inputStream.read(car)) != -1){
                if (car.length!=0){
                    outputStream.write(car, 0,L);
                }
            }
            if(outputStream!=null){
                outputStream.flush();
                outputStream.close();
            }
            if (client !=null){
                client.shutdown();
            }

        } catch (IOException e) {
            logger.error("【BillCommonFileServiceImpl】 download ==IOException "+e.getMessage());
            e.printStackTrace();

        } catch (OSSException e){
            logger.error("【BillCommonFileServiceImpl】 download ==OSSException "+e.getMessage());
        }
        return response;
    }

    /**
     * 下载文件02
     * @param request
     * @param response
     * @param url
     */
    public static void downFile02(HttpServletRequest request,HttpServletResponse response, String url){
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            String keyurl = url.replaceAll(FILE_URL,"");
            String[] split = keyurl.split("/");
            String fileName= URLDecoder.decode(split[split.length-1],"UTF-8");
            //如果不存在乱码的情况可以忽略，由于请求参数文件名为中文，到后台会乱码，考虑操作系统和客户端浏览器默认编码
            //判断服务器操作系统，本地开发使用windows
            String os = System.getProperty("os.name");
            if(os.toLowerCase().indexOf("windows") != -1){
                fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
            }else{
                //判断浏览器
                String userAgent = request.getHeader("User-Agent").toLowerCase();
                if(userAgent.indexOf("msie") > 0){
                    fileName = URLEncoder.encode(fileName, "ISO-8859-1");
                }
            }
            //响应二进制流
            response.setContentType("application/octet-stream");
            response.reset();//清除response中的缓存
            //根据网络文件地址创建URL
            URL filrurl = new URL(url);
            //获取此路径的连接
            URLConnection conn = filrurl.openConnection();
            //获取文件大小
            Long fileLength = conn.getContentLengthLong();
            //设置reponse响应头，真实文件名重命名，就是在这里设置，设置编码
            response.setHeader("Content-Disposition","attachment; filename=" + fileName);
            response.setHeader("Content-Length", String.valueOf(fileLength));

            //构造读取流
            bis = new BufferedInputStream(conn.getInputStream());
            //构造输出流
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[1024];
            int bytesRead;
            //每次读取缓存大小的流，写到输出流
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            response.flushBuffer();//将所有的读取的流返回给客户端
        } catch (IOException e) {
            logger.error("文件下载失败！", e);
            throw new RuntimeException("文件下载失败！");
        }finally {
            try {
                if (null != bis) {
                    bis.close();
                }
                if (null != bos) {
                    bos.close();
                }
            } catch (IOException e) {
                logger.error("文件下载失败！", e);
                throw new RuntimeException("文件下载失败！");
            }
        }
    }

}
