package com.tyx.security.config.githubAuthentication;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tyx.security.pojo.User;
import com.tyx.security.util.HttpClientUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Create By C  2019-09-10 11:50
 */
public class GithubAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public GithubAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }


    String tokenUrl="https://github.com/login/oauth/access_token?client_id=%s&client_secret=%s&code=%s";
    String infoUrl="https://api.github.com/user?access_token=";

    String client_id="7c6591e3f3909b920e1f";
    String  client_secret="93654b56f01e53b3f7539e67bd4db34cba726c1a";

    private HttpClientUtil httpClientUtil=new HttpClientUtil();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String code = request.getParameter("code");
        String getToken = String.format(tokenUrl, client_id, client_secret, code);

        // 通过code请求token
        String tokenResponse =httpClientUtil.sendGetRequest(getToken);
        int start = tokenResponse.indexOf("=") + 1;
        int end = tokenResponse.indexOf("&");
        String token = tokenResponse.substring(start, end);
        // 通过token请求用户信息
        String infoResponse =httpClientUtil.sendGetRequest(infoUrl + token);
        JSONObject jsonObject = JSON.parseObject(infoResponse);

        try {
            User user = new User();
            user.setGithubId(Integer.parseInt(jsonObject.get("id").toString()));
            user.setGithubAvatar(jsonObject.get("avatar_url").toString());
            user.setGithubUrl(jsonObject.get("html_url").toString());

            Authentication authenticationToken =
                    new UsernamePasswordAuthenticationToken(user, null, null);

            return this.getAuthenticationManager().authenticate(authenticationToken);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
