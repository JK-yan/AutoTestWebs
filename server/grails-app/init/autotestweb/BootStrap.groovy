package autotestweb

class BootStrap {

    def init = { servletContext ->
        final String BACK_ADMIN='admin'
        if(!User.findByUsername(BACK_ADMIN)){
            new User(username: BACK_ADMIN,password: BACK_ADMIN).save()
        }
    }
    def destroy = {
    }
}
