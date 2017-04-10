package com.marsspiders.ukwa.util;

import com.marsspiders.ukwa.LocaleConfiguration;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static com.marsspiders.ukwa.controllers.HomeController.PROJECT_NAME;

public class UrlUtil {
    public static String getRootPathWithLang(HttpServletRequest request, boolean setProtocolToHttps) throws MalformedURLException, URISyntaxException {
        URL url = new URL(request.getRequestURL().toString());
        String host = url.getHost();
        String userInfo = url.getUserInfo();
        String scheme = url.getProtocol();
        if(setProtocolToHttps && !StringUtils.isBlank(scheme)){
            scheme = scheme.replace("http", "https");
        }

        int port = url.getPort();
        String path = (String) request.getAttribute("javax.servlet.forward.request_uri");

        String langPart = "";
        for (String possibleLocale : LocaleConfiguration.AVAILABLE_LOCALES) {
            if (path != null && path.startsWith("/" + possibleLocale)) {
                langPart = "/" + possibleLocale;
                break;
            }
        }

        String newPath = langPart + "/" + PROJECT_NAME + "/";
        URI uri = new URI(scheme, userInfo, host, port, newPath, null, null);
        return uri.toString();
    }
}