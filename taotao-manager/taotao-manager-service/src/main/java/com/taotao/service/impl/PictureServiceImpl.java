package com.taotao.service.impl;

import java.io.ByteArrayInputStream;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.commom.utils.FtpUtil;
import com.taotao.commom.utils.IDUtils;
import com.taotao.common.pojo.PictureResult;
import com.taotao.service.PictureService;
@Service
public class PictureServiceImpl implements PictureService {
	@Value("${FTP_ADDRESS}")
	private String FTP_ADDRESS;
	@Value("${FTP_PORT}")
	private Integer FTP_PORT;
	@Value("${FTP_USERNAME}")
	private String FTP_USERNAME;
	@Value("${FTP_PASSWORD}")
	private String FTP_PASSWORD;
	@Value("${FILI_UPLOAD_PATH}")
	private String FILI_UPLOAD_PATH;
	@Value("${IMAGE_BASE_URL}")
	private String IMAGE_BASE_URL;

	
	public  PictureResult uploadFile(byte[] bytes, String name) {
		//获取图片的名称
		PictureResult pictureResult = new PictureResult();
		try {
			//重新修改的图片名称 随机名称+后缀名
			String newName = IDUtils.genImageName() + name.substring(name.lastIndexOf("."));
			//以当前的日期作为文件夹存储图片
			String filepath = new DateTime().toString("yyyy/MM/dd");
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, FILI_UPLOAD_PATH, filepath, newName, bis);
			pictureResult.setError(0);
			//http://192.168.25.133/images/2018/06/26/ 很正的图片路径
			pictureResult.setUrl(IMAGE_BASE_URL+"/"+filepath+"/"+newName);
			return pictureResult;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pictureResult.setError(1);
		pictureResult.setMessage("上传错误");
		return pictureResult;
	}

}
