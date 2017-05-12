package com.pipi.common.upload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


/**
 * 重写上传解析器 实现进度条功能
 * Created by yahto on 07/05/2017.
 */
public class CommonsMultipartResolverExt extends CommonsMultipartResolver {

	private HttpServletRequest request;

	@Override
	protected FileUpload newFileUpload(FileItemFactory fileItemFactory) {
		ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
		upload.setSizeMax(-1);
		if (request != null) {
			/**注入监听*/
			final HttpSession hs = request.getSession();
			upload.setProgressListener(new ProgressListener() {

				/**
				 * pBytesRead代表已上传字节数 pContentLength代表总大小 pItems代表总长度
				 */
				@Override
				public void update(long pBytesRead, long pContentLength,
						int pItems) {
					ProcessInfo pri = new ProcessInfo();
					pri.itemNum = pItems;
					pri.readSize = pBytesRead;
					pri.totalSize = pContentLength;
					pri.show = pBytesRead + "/" + pContentLength + " byte";
					pri.rate = Math.round(new Float(pBytesRead)
							/ new Float(pContentLength) * 100);
					hs.setAttribute("proInfo", pri);
				}
			});
		}
		return upload;
	}

	@Override
	public MultipartHttpServletRequest resolveMultipart(
			HttpServletRequest request) throws MultipartException {
		this.request = request;// 获取到request,要用到session
		return super.resolveMultipart(request);
	}
}