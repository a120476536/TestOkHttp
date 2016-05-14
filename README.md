# TestOkHttp

OKHttp 封装

使用方法:
  /** 需在全局初始化 OKHttp  */
  OKHttpRequestHelper.getInstance.init();
  
  /** POST 提交 */
  
  RequestParams params = new RequestParams();
  params.put("time" , "20160427000000");
  OKHttpRequestHelper.getInstance.post(url, params, new StringRequestCallbackHelper() {
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
