package com.rongyuan.mingyida;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.gson.Gson;
import com.rongyuan.mingyida.common.databus.RxBus;
import com.rongyuan.mingyida.common.http.API;
import com.rongyuan.mingyida.common.http.IHttpClient;
import com.rongyuan.mingyida.common.http.IRequest;
import com.rongyuan.mingyida.common.http.IResponse;
import com.rongyuan.mingyida.common.http.impl.BaseRequest;
import com.rongyuan.mingyida.common.http.impl.OkHttpClientImpl;
import com.rongyuan.mingyida.model.RejisterBean;

import org.junit.Test;
import org.junit.runner.RunWith;

import rx.functions.Func1;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.rongyuan.mingyida", appContext.getPackageName());
    }

}
