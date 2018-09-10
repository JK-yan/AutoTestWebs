package autotestweb

class User {
    String userName
    String passWord
    String email
    String phone
    String address

    static constraints = {
        userName(size: 2..10,blank: false,unique: true,)
        passWord(size: 2..10,blank: false)
        email(email: true,unique: true,blank: false)
        phone(matches: /\d{7,11}/,blank: false,unique: true,)
        address(maxSize: 200,blank: false)
    }

}
