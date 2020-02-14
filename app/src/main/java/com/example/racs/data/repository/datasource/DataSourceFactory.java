package com.example.racs.data.repository.datasource;

import com.example.racs.data.api.AccessImpl;
import com.example.racs.data.api.AuthorizationImpl;
import com.example.racs.data.api.LockImpl;
import com.example.racs.data.api.UserImpl;

public class DataSourceFactory {

    private static LocalApiDataSource localApiDataSource;

    public static DataSource getDataSource() {
        if (localApiDataSource == null) {
            AccessImpl accessImpl = new AccessImpl();
            LockImpl lockImpl = new LockImpl();
            UserImpl userImpl = new UserImpl();
            AuthorizationImpl authImpl = new AuthorizationImpl();
            localApiDataSource = new LocalApiDataSource(accessImpl, authImpl, lockImpl, userImpl);
        }
        return localApiDataSource;
    }
}
