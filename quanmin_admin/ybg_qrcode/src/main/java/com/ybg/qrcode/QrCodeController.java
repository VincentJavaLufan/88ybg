package com.ybg.qrcode;
import java.io.ByteArrayOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;

@Controller
@RequestMapping("/common/qrcode/")
@Api(tags = "二维码接口")
public class QrCodeController {
	
	@RequestMapping("index.do")
	public String index() {
		return "/qrcode/qrcode";
	}
	
	@RequestMapping(value = "gen.do", produces = "image/jpeg;charset=UTF-8")
	public void gen(String url, HttpServletResponse response, Integer width, Integer height) {
		try {
			int iWidth = (width == null ? 200 : width);
			int iHeight = (height == null ? 200 : height);
			MatrixToImageWriter.createRqCode(url, iWidth, iHeight, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
