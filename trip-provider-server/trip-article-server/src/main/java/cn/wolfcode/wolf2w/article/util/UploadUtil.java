package cn.wolfcode.wolf2w.article.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件上传工具
 */
public class UploadUtil {
	//阿里域名
	private static final String ALI_DOMAIN = "https://xuanxuan-wolf2w.oss-cn-guangzhou.aliyuncs.com/";
	private static final String ENDPOINT = "http://oss-cn-guangzhou.aliyuncs.com";
	private static final String BUCKET_NAME = "xuanxuan-wolf2w";

	private static final String ACCESS_KEY_ID = "LTAI5tDP9Qj9kWy51AwRqKR9";
	private static final String ACCESS_KEY_SECRET = "yi0tUZeKoOGke5G7cUOFOt3JDVy0KJ";


	//MultipartFile 对象
	public static String uploadAli(MultipartFile file) throws Exception {
		//生成文件名称
		String uuid = UUID.randomUUID().toString();
		String orgFileName = file.getOriginalFilename();//获取真实文件名称 xxx.jpg
		String ext= "." + FilenameUtils.getExtension(orgFileName);//获取拓展名字.jpg
		String fileName =uuid + ext;//xxxxxsfsasa.jpg
		// 创建OSSClient实例。
		OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID,ACCESS_KEY_SECRET);
		// 上传文件流。
		ossClient.putObject(BUCKET_NAME, fileName, file.getInputStream());
		// 关闭OSSClient。
		ossClient.shutdown();
		return ALI_DOMAIN + fileName;
	}

	//Base64字符串图片
	public static String uploadAliBase64(String base64Pic){
		String uuid = UUID.randomUUID().toString();
		String fileName =uuid + "." + getImageExt(base64Pic);
		Base64.Decoder decoder = Base64.getDecoder();
		base64Pic = base64Pic.replaceAll("data:image/.*;base64,", "");
		byte[] content = decoder.decode(base64Pic);
		OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID,ACCESS_KEY_SECRET);
		ossClient.putObject(BUCKET_NAME, fileName, new ByteArrayInputStream(content));
		return ALI_DOMAIN + fileName;
	}
	//从base64字符串中解析出图片后缀名
	public static String getImageExt(String base64Pic) {
		Pattern compile = Pattern.compile("data:image/.*;base64,");
		Matcher matcher = compile.matcher(base64Pic);
		while (matcher.find()){
			String ext = matcher.group().replaceAll("data:image/", "").replaceAll(";base64,", "");
			return ext;
		}
		return "jpg";
	}
	
	public static boolean isBase64Pic(String base64Pic){
		Pattern compile = Pattern.compile("data:image/.*;base64,");
		Matcher matcher = compile.matcher(base64Pic);
		return matcher.find();
	}
}
























