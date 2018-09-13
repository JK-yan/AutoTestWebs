package auth
import grails.plugin.springsecurity.SpringSecurityUtils
import org.springframework.security.access.annotation.Secured
import org.springframework.security.web.RedirectStrategy

@Secured('permitAll')
class LogoutController {

    /** 依赖注入RedirectStrategy. */
    RedirectStrategy redirectStrategy

    /**
     * 注销方法
     */
    def index() {

//        if (!request.post && SpringSecurityUtils.getSecurityConfig().logout.postOnly) {
//            response.sendError HttpServletResponse.SC_METHOD_NOT_ALLOWED // 405
//            return
//        }

        // TODO put any pre-logout code here
        redirectStrategy.sendRedirect request, response, SpringSecurityUtils.securityConfig.logout.filterProcessesUrl // '/logoff'
        response.flushBuffer()
    }
}