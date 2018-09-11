package autotestweb

import ch.qos.logback.classic.Logger
import grails.validation.ValidationException
import groovy.util.logging.Slf4j

import static org.springframework.http.HttpStatus.*
@Slf4j
class UserController {

//    UserService userService

//    static responseFormats = ['json', 'xml']
//    static allowedMethods = [authenticate: "POST", update: "PUT", delete: "DELETE"]
//
//    def index(Integer max) {
//        params.max = Math.min(max ?: 10, 100)
//        respond userService.list(params), model:[userCount: userService.count()]
//    }
//
//    def show(Long id) {
//        respond userService.get(id)
//    }
//
//    def save(User user) {
//        if (user == null) {
//            render status: NOT_FOUND
//            return
//        }
//
//        try {
//            userService.save(user)
//        } catch (ValidationException e) {
//            respond user.errors, view:'create'
//            return
//        }
//
//        respond user, [status: CREATED, view:"show"]
//    }
//
//    def update(User user) {
//        if (user == null) {
//            render status: NOT_FOUND
//            return
//        }
//
//        try {
//            userService.save(user)
//        } catch (ValidationException e) {
//            respond user.errors, view:'edit'
//            return
//        }
//
//        respond user, [status: OK, view:"show"]
//    }
//
//    def delete(Long id) {
//        if (id == null) {
//            render status: NOT_FOUND
//            return
//        }
//
//        userService.delete(id)
//
//        render status: NO_CONTENT
//    }
//
    def scaffold = User

    def login = {
//        if (request.authenticate())
    }

    def authenticate = {
        def user = User.findByUsernameAndPassword(params.username, params.password)
        Logger
        if(user){
            session.user = user
            flash.message = "Hello ${user.username}!"
            render("登陆成功${session}")
//            redirect(controller:"entry", action:"list")
        }else{
            flash.message = "Sorry, ${params.login}. Please try again."
            redirect(action:"login")
        }
    }

    def logout = {
        flash.message = "Goodbye ${session.user.username}"
        session.user = null
        redirect(controller:"entry", action:"list")
    }
}
