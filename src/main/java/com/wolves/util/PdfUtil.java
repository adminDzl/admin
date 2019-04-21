package com.wolves.util;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

/**
 * 对pdf模板赋值
 * @author gf
 * @date 2019/4/21
 */
public class PdfUtil {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PdfUtil.class);

    /**
     * pdf读取模板，生成pdf文件
     * @param response
     * @param path 模板oss路径
     * @param filename 生成pdf文件的文件名
     * @param bean 需要给pdf赋值 Java bean
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static void setPdfData(HttpServletResponse response, String path,String filename, Object bean) throws IOException, DocumentException {
        // pdf模板
        URL url = new URL(path);
        PdfReader reader = new PdfReader(url);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        /* 将要生成的目标PDF文件名称 */
        PdfStamper ps = new PdfStamper(reader, bos);
        PdfContentByte under = ps.getUnderContent(1);
        /* 使用中文字体 */
        BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        ArrayList<BaseFont> fontList = new ArrayList<BaseFont>();
        fontList.add(bf);
        /* 取出报表模板中的所有字段 */
        AcroFields fields = ps.getAcroFields();
        fields.setSubstitutionFonts(fontList);

        try {
            Map<String, String> data =  BeanUtils.describe(bean);
            fillData(fields, data);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        /* 必须要调用这个，否则文档不会生成的 */
        ps.setFormFlattening(true);
        ps.close();

        try {
            String fileName = filename+"-"+System.currentTimeMillis()+".pdf";
            response.reset();
            //设置Content-Type头
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setCharacterEncoding("utf-8");
            OutputStream fos = new BufferedOutputStream(response.getOutputStream());
            fos.write(bos.toByteArray());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bos.close();
        }
    }

    private static void fillData(AcroFields fields, Map<String, String> data) throws IOException, DocumentException {
        // 为字段赋值,注意字段名称是区分大小写的
        for (String key : data.keySet()) {
            String value = data.get(key);
            fields.setField(key, value);
        }

    }

}
