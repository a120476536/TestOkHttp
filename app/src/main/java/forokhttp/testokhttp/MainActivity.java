package forokhttp.testokhttp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.forokhttp.request.HttpRequest;
import com.forokhttp.request.RequestParams;
import com.forokhttp.request.StringRequestCallbackHelper;
import com.forokhttp.request.helper.OKHttpRequestHelper;
import com.forokhttp.request.minterface.HttpContextHelper;
import com.forokhttp.request.minterface.NetWorkDataResultHelper;

public class MainActivity extends AppCompatActivity implements HttpContextHelper{

    public static String BASE_API_URL = "http://172.16.53.16:6001";

    /** 新游接口 */
    public static final String NEW_GAME = BASE_API_URL + "/pushServer/push/receiveMsg.do";

    private TextView textView_main_content_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        textView_main_content_view = (TextView) findViewById(R.id.textView_main_content_view);

//        getNetwork();

        getData();
    }

    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();

    @Override
    public String getOkHttpTask() {
        return HTTP_TASK_KEY;
    }

    public void getData (){
        String url = "http://api.wanmen.org:13132/newCourse";
        RequestParams params = new RequestParams(this);
        params.put("page", 1);
        params.put("limit", 12);

        HttpRequest.post(NEW_GAME, params, new StringRequestCallbackHelper() {
            @Override
            protected void onSuccess(String s) {
                super.onSuccess(s);
                textView_main_content_view.setText(s);
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                textView_main_content_view.setText(msg);
            }
        });

    }

    public void getNetwork (){
        RequestParams params = new RequestParams();
        params.put("time" , "20160427000000");
        HttpRequest.post(NEW_GAME, params, new StringRequestCallbackHelper() {
            @Override
            protected void onSuccess(String s) {
                super.onSuccess(s);
                textView_main_content_view.setText(s);
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                textView_main_content_view.setText(msg + errorCode);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
