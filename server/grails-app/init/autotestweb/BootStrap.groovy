package autotestweb

class BootStrap {

    def init = { servletContext ->
        new User(userName: 'admin',passWord: 'admin',email: '11@qq.com',phone: 17717512443,address: '中国上海').save()
    }
    def destroy = {
    }
}
