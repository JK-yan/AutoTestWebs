package autotestweb

class User {
    String username
    String password
//    String email
//    String phone
//    String address

    static constraints = {
        username(size: 2..10,blank: false,unique: true,)
        password(size: 2..10,blank: false,passWord:true)
//        email(email: true,unique: true,blank: false)
//        phone(matches: /\d{7,11}/,blank: false,unique: true,)
//        address(maxSize: 200,blank: false)
    }

}
