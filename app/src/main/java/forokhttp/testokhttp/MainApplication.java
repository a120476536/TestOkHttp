package forokhttp.testokhttp;

import android.app.Application;

import com.forokhttp.request.helper.OKHttpRequestHelper;

/**
 * Created by huangyaping on 16/4/29.
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        OKHttpRequestHelper.getInstance().init();
    }
}
