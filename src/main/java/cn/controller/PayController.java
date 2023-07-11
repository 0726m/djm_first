package cn.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


@Controller
@RequestMapping("/pay")
@Slf4j
public class PayController {
    private final String APP_ID = "2021000121686490";
    private final String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCxQSbh7wmDiLjUdKP42ELhsu67jfQeOMKvHRTVGh9OagheSfk77+JmAtEBLTtZVEzGSR8s/Bu7GEQchHyWvtQwGms1oEkwTfksJkIkUbeYr6/KUwcNrAV+N5Bt8xtRAE1eKTrUBARdJ1r+NaaFmoqA6oGXqJBeognuWbyPogmDm8oZTHJhjE1WhDe14U4rQ+/zPmIjb8bHP7JHJ8mpeZho3JIAWfHKuOa20uqvlRHZESjjgNMHywLGCPQC63vYoQHQ0GMtmPoerOPPh5kLtM8NcmJgATSCzA1gUn3srnmcJptyemYKBcTKid7e6Zv3v2EnnPgoCkguvJe+vXIzSVOrAgMBAAECggEAFRikAA/rGfSTyvV27o8Opr4dG8VxYIdw5FaZvCM3xqVoCZDUUQXj9DAxn+o6Pg+Cc7cQNtJqmoHw2VQLYAWmS3NZfzBshYtCNLb4+EmhiFKgoXvwQvhOTw9d6Tgd8C4RBzvw/Hrd6hhdBBRulUgnY+CFO/+b7ydp9yxG/PFUII2fcuusyMcaJx8u4fAkKyzR40dZ3pKU8y9BEPDW2b29daReuVeEVdBQL1C3QFE1eEvhJUHucDuMCv6WbBWtwTrxIM3HYOmSk6755aL75AYdYRHNNkgaxiVxwF5Bbdhr6lZvVUMz1nGU8maN+Omuz7QGpEN8nnSUKcvPVAjOGW6igQKBgQDWedoZPeAmcN/uAoJTXT/ild45oA0U1+IH0Q1V7NIlLrR0tdQ8F80jegxe2D3Or9xe+2urEler30yYyKv5zTL2DYlGicRScuxBogb2MlGAHPS4HQKQH7CMTFXX6DM78mW/VZmMYfAz4YjOBIpyxRDQXZfwbGW2x3xL+LOSAu7r2QKBgQDTknp/U9tROmf2sZCqnSV+htHxListrQ/OsFR8UR1eHG1JvVIPlVx35Ml7RAb9iDXKqC23P22vbuuSRz3fSmwhbsScjTgNX47TY2403gxahN3R5/kX+QY9aIhGDAj3uwFlT/6/FWVGNhVFYiDxOfDJOx1rcY96FqUVbYzTP/OdIwKBgCuiLKnX+cM4UYZlgkgdf4LEhKpqkl68MOLwN+tUO8iOwO3BlEUJfpWKHfwpuCFVi9rTAauTFr0Dt9yZbstqYMTIKjZsjsFLWh9cLFhoEkajHXJbjIrvt4ZDfW9ocQvwG50aeBukTS7w4lkveU8yImKRt/iUxPiziukV1sqMyDi5AoGBALhzi7hWM26trNW2GJmmR3n3sDDEP4Ci0JUxxXZcgW36+oy/MTvEy8j6/W0p8a43gi4cUT7hXY8E9PuigJ6bE4pSN7cEp0mBddN1bC9LJxoDnB/UTaZIxNfkumnXNDpGHwFkrIOK87AylnjXBEFKclH957g0WRHnjCUSTSQfLRRhAoGAc+y6m1katkHxaLyF175E4HMOgvfGt8urj6YOP6qZXBWEJRUepW/K1fl2igN2SY1Qfp4wXQYpDca/IGzRUHjq5yL8Y1IMpB6+bACoA8qaAwdRFQwrqVADqDyVSCt+VAeQ+rBSAV5nX75cCUFpDbaPSi3qWDf9CQ+AmlPrDzn3Ba0=";
    private final String CHARSET = "UTF-8";
    private final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAk+Ho292hdbeUMNvLyncbeG6QLzi+3vfWylcQwPIHQFm2HhUBSO+La8Ygav54hmWt6YPs52P8a47N9cDz6hISbFT71ZJFB6S/0xnMrZWxPvBcUreG7bOr7XCxHB8F/4I35iSOo7xVXU02ao5BId12naQIOrVTuRT6xj3BB+Q1r4GdCZgALToE/OoAEFjeeGjl4HcjcARjx3ExADs9HKjL6h2r5sO3ja1AvWDfNXnq9bg8gkTPYECIx+hRatXL72siH6J/Lv5GPMykQBN4eSAi4Ju5Rp6Vp1wMjt3/xVEbyGKQjxDobJg+O6bqyyaCIx3+Z3UfG1HFC0w+seEzpT3+2QIDAQAB";
    //这是沙箱接口路径,正式路径为https://openapi.alipay.com/gateway.do
    private final String GATEWAY_URL = "https://openapi.alipaydev.com/gateway.do";
    private final String FORMAT = "JSON";
    //签名方式
    private final String SIGN_TYPE = "RSA2";
    //支付宝异步通知路径,付款完毕后会异步调用本项目的方法,必须为公网地址
    private final String NOTIFY_URL = "http://localhost:8080/films/pay/returnUrl";
    //支付宝同步通知路径,也就是当付款完毕后跳转本项目的页面,可以不是公网地址
    private final String RETURN_URL = "http://localhost:8080/films/orders/index";

    @RequestMapping("alipay")
    public void alipay(Integer orderId, HttpServletResponse httpResponse) throws IOException {
        //实例化客户端,填入所需参数
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //在公共参数中设置回跳和通知地址
        request.setReturnUrl(RETURN_URL);
        request.setNotifyUrl(NOTIFY_URL);
        //根据订单编号,查询订单相关信息
//        Order order = IPayService.selectById(orderId);
        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = orderId.toString();
        //付款金额，必填
        String total_amount = "30";
        //订单名称，必填
        String subject = "订单测试";
        //商品描述，可空
        String body = "";
        request.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }

    @RequestMapping(value = "/returnUrl", method = RequestMethod.GET)
    public String returnUrl(HttpServletRequest request, HttpServletResponse response)
            throws IOException, AlipayApiException {
        System.out.println("=================================同步回调=====================================");
        // 获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("utf-8"), "utf-8");
            params.put(name, valueStr);
        }

        System.out.println(params);//查看参数都有哪些
        boolean signVerified = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET, SIGN_TYPE); // 调用SDK验证签名
//验证签名通过
        if(signVerified){
            // 商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

            System.out.println("商户订单号="+out_trade_no);
            System.out.println("支付宝交易号="+trade_no);
            System.out.println("付款金额="+total_amount);

            //支付成功，修复支付状态
            // payService.updateById(Integer.valueOf(out_trade_no));
            return "ok";//跳转付款成功页面
        }else{
            return "no";//跳转付款失败页面
        }
    }
}