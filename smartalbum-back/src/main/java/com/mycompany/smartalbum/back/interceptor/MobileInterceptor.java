package com.mycompany.smartalbum.back.interceptor;

/**
 * Copyright (c) 2010 Mark S. Kolich
 * http://mark.koli.ch
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MobileInterceptor extends HandlerInterceptorAdapter {

  /**
   * The name of the mobile view that the viewer is re-directed to
   * in the event that a mobile device is detected.
   */
  private static final String MOBILE_VIEWER_VIEW_NAME = "mobile";
  private static final String DEVICE_IS_MOBILE_VAR = "DEVICE_IS_MOBILE";

  /**
   * The User-Agent Http header.
   */
  private static final String USER_AGENT_HEADER = "User-Agent";

  private List<String> mobileAgents_;
  private List<Pattern> uaPatterns_;

  public void init() {
    uaPatterns_ = new ArrayList<Pattern>();
    // Pre-compile the user-agent patterns as specified in mvc.xml
    for(final String ua : mobileAgents_) {
      try { 
        uaPatterns_.add(Pattern.compile(ua, Pattern.CASE_INSENSITIVE));
      } catch (PatternSyntaxException e) {
        // Ignore the pattern, if it failed to compile
        // for whatever reason.
      }
    }
  }

  @Override
  public void postHandle(HttpServletRequest request,
    HttpServletResponse response, Object handler,
    ModelAndView mav) throws Exception {
    final String userAgent = request.getHeader(USER_AGENT_HEADER);
    if(userAgent != null) {
      // If the User-Agent matches a mobile device, then we set
      // the view name to the mobile view JSP so that a mobile
      // JSP is rendered instead of a normal view.
      if(isMobile(userAgent)) {
        //final String view = mav.getViewName();
        // If the incoming view was "homepage" then this interceptor
        // changes the view name to "homepage-mobile" which, depending
        // on your Spring configuration would probably resolve to
        // "homepage-mobile.jsp" to render a mobile version of your
        // site.
        //mav.setViewName(view + "-" + MOBILE_VIEWER_VIEW_NAME);
        
        request.setAttribute(DEVICE_IS_MOBILE_VAR, 
                true);
      }
      else
      {
    	  request.setAttribute(DEVICE_IS_MOBILE_VAR, 
                  false);
      }
    }
  }

  /**
   * Returns true of the given User-Agent string matches a suspected
   * mobile device.
   * @param agent
   * @return
   */
  private final boolean isMobile(final String agent) {
    boolean mobile = false;
    for(final Pattern p : uaPatterns_) {
      final Matcher m = p.matcher(agent);
      if(m.find()) {
        mobile = true;
        break;
      }
    }
    return mobile;
  }

  public void setMobileUserAgents(List<String> agents) {
    mobileAgents_ = agents;
  }

}
