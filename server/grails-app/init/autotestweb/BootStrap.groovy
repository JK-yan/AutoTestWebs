package autotestweb

import autotestweb.auth.RoleInfo
import autotestweb.auth.UserInfo

class BootStrap {

    def init = { servletContext ->
        //创建角色
        new RoleInfo(authority: "ROLE_ADMIN", remark: "管理员")
                .addToUserInfo(new UserInfo(username: "admin", password: "admin")).save()
        new RoleInfo(authority: "ROLE_SUPSYS", remark: "超级管理员")
                .addToUserInfo(new UserInfo(username: "super", password: "super")).save()
        new RoleInfo(authority: "ROLE_USER", remark: "普通用户")
                .addToUserInfo( new UserInfo(username: "user", password: "user")).save()

    }
    def destroy = {
    }
}
