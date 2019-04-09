package beans;

public class User {
    private String _userId, _psw;
    private boolean valid;

    public User(String userid, String psw) {
        //controlli sull'input

        _userId = userid;
        _psw = psw;
        valid = false;
    }

    public void setValid(boolean validity) {
        valid = validity;
    }

    public boolean isValid() {
        return valid;
    }

    public String getUserId() {
        return _userId;
    }

    public String getPassword() {
        return _psw;
    }

}