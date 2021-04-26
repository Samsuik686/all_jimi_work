package cn.concox.vo.user;

import java.util.Objects;

public class UserMail {
    private String userId;
    private String mail;
    private String uName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserMail userMail = (UserMail) o;
        return Objects.equals(userId, userMail.userId) &&
                Objects.equals(mail, userMail.mail) &&
                Objects.equals(uName, userMail.uName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, mail, uName);
    }
}
