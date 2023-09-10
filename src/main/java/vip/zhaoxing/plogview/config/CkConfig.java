package vip.zhaoxing.plogview.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CkConfig {
     static String url;
     static String user;
     static String passwd;
     static String database;
     static String table;

    @Value("${clickHouse.url}")
    void setUrl(String url) {
        CkConfig.url = url;
    }

    @Value("${clickHouse.user}")
    void setUser(String user) {
        CkConfig.user = user;
    }


    @Value("${clickHouse.database}")
    void setDatabase(String database) {
        CkConfig.database = database;
    }


    @Value("${clickHouse.passwd}")
    void setPasswd(String passwd) {
        CkConfig.passwd = passwd;
    }

    @Value("${clickHouse.table}")
    void setTable(String table) {
        CkConfig.table = table;
    }


    public static String getUrl() {
        return url;
    }

    public static String getUser() {
        return user;
    }

    public static String getPasswd() {
        return passwd;
    }

    public static String getDatabase() {
        return database;
    }

    public static String getTable() {
        return table;
    }
}
