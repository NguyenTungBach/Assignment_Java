package util;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionMongoDB {
    private static final String DATABASE_URL = "mongodb+srv://";
    private static final String DATABASE_USER = "test";
    private static final String DATABASE_PWD = "123";
    private static final String DATABASE_NAME = "Project_Led_Lights";
    private static ConnectionString cnn;

    public static ConnectionString getConnection(){
        if (cnn == null ){
            cnn = new ConnectionString(DATABASE_URL +
                    DATABASE_USER +
                    ":"+
                    DATABASE_PWD +
                    "@cluster0.eecvk.mongodb.net/"+
                    DATABASE_NAME+
                    "?retryWrites=true&w=majority");
        }

        return cnn;
    }
}
