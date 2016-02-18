package com.ruban.framework.web.image.validation;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * 图形验证码
 * 
 * @author zjg
 * @sinace 20130719
 * 
 */
public class ImageServlet extends GenericServlet {

	private static final long serialVersionUID = 3581488453580539433L;

	public static final String VALIDATION = "_validation_";

	private final static Logger logger = LoggerFactory
			.getLogger(ImageServlet.class);

	private String name = "";

	private int maxRetry = 10;

	private final ValidationImageHandler imageHhandler = new ValidationImageHandler();

	/**
	 * 初始化配置文件
	 */
	public final void init() throws ServletException {
		ServletConfig servletconfig = getServletConfig();
		// 初始化配置文件内容
		if (servletconfig.getInitParameter("name") != null)
			name = servletconfig.getInitParameter("name").intern();
		if (servletconfig.getInitParameter("maxretry") != null)
			try {
				maxRetry = Integer.parseInt(servletconfig
						.getInitParameter("maxretry"));
			} catch (NumberFormatException e) {
				if (logger.isErrorEnabled()) {
					logger.error(
							"Initialization image configuration file error：", e);
				}
				// 设置maxRety默认值
				maxRetry = 100;
			}
	}

	public void service(ServletRequest request, ServletResponse response) {

		int count = 0;
		try {
			HttpSession session = ((HttpServletRequest) request).getSession();
			ValidationChars vchars = (ValidationChars) session
					.getAttribute(VALIDATION);
			// 判断是否超过最大错误次数
			if (vchars != null) {
				count = vchars.getCount();
			}
			if (count >= maxRetry) {
				return;
			}
			// 获得请求路径，判断请求的图片类型
			String pathInfo = ((HttpServletRequest) request).getPathInfo();
			if (StringUtils.isEmpty(pathInfo)) {
				return;
			}
			if (pathInfo.startsWith("/")) {
				pathInfo = pathInfo.substring(1);
			}
			String fileName = null;
			String fileExt = null;
			int index = pathInfo.indexOf(".");
			if (index >= 0) {
				fileName = pathInfo.substring(0, index).intern();
				fileExt = pathInfo.substring(index + 1).intern();
			} else {
				fileName = pathInfo.intern();
				fileExt = "";
			}
			String imageEncode = "GIF";
			if (fileExt.equalsIgnoreCase("jpg")
					|| fileExt.equalsIgnoreCase("jpeg"))
				imageEncode = "JPEG";
			else if (fileExt.equalsIgnoreCase("png"))
				imageEncode = "PNG";

			// 获得验证码处理器
			ValidationImageHandler handler = getHander(fileName);
			// 设置返回包头
			((HttpServletResponse) response).setContentType("image/" + fileExt);
			((HttpServletResponse) response).setHeader("Pragma", "No-cache");
			((HttpServletResponse) response).setHeader("Cache-Control",
					"no-cache");
			((HttpServletResponse) response).setHeader("Expires",
					"Thu, 01 Jan 1970 00:00:00 GMT");
			((HttpServletResponse) response).setDateHeader("Expires", 0);

			// 产生验证码
			if (handler != null) {
				FuzzyImage fuzzy = handler.service();
				session.setAttribute(VALIDATION,
						new ValidationChars(new String(fuzzy.getChars()),
								System.currentTimeMillis(), ++count));
				// 将图片写入输出流
				ServletOutputStream out = response.getOutputStream();
				try {
					if (imageEncode.equalsIgnoreCase("GIF")) {
						if (!ImageIO.write(fuzzy.getImage(), imageEncode, out)) {
							if (logger.isErrorEnabled()) {
								logger.error("ImageIO writer not found for "
										+ pathInfo);
							}
						}
					} else {
						if (!ImageIO.write(fuzzy.getImage(), imageEncode, out)) {
							if (logger.isErrorEnabled()) {
								logger.error("ImageIO writer not found for "
										+ pathInfo);
							}
						}
					}
				} catch (IOException e) {
					if (logger.isErrorEnabled()) {
						logger.error("create validationImage error:", e);
					}

				} finally {
					out.close();
				}
			} else {
				if (logger.isErrorEnabled()) {
					logger.error("No corresponding image processor!");
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * Get the handler instance for image
	 * 
	 * @param fileName
	 * @return if not found,return null;
	 * @throws ServletException
	 */
	protected ValidationImageHandler getHander(String fileName) {
		if (fileName.startsWith(name))
			return imageHhandler;
		return null;
	}

}
