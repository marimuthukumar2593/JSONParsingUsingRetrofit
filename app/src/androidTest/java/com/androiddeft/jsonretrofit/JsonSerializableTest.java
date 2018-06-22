package com.androiddeft.jsonretrofit;

import android.test.InstrumentationTestCase;

import net.javacrumbs.jsonunit.JsonAssert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by vverbytskyy on 1/28/16.
 */
public class JsonSerializableTest extends InstrumentationTestCase {

    private Utils utils;

    private static final String JSON_READING_EXPECTED ="{\"page\":1,\"per_page\":3,\"total\":12,\"total_pages\":4,\"data\":[{\"id\":1,\"first_name\":\"George\",\"last_name\":\"Bluth\",\"avatar\":\"https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg\"},{\"id\":2,\"first_name\":\"Janet\",\"last_name\":\"Weaver\",\"avatar\":\"https://s3.amazonaws.com/uifaces/faces/twitter/josephstein/128.jpg\"},{\"id\":3,\"first_name\":\"Emma\",\"last_name\":\"Wong\",\"avatar\":\"https://s3.amazonaws.com/uifaces/faces/twitter/olegpogodaev/128.jpg\"}]}";

    @Override
    public void setUp() throws Exception {
        super.setUp();

        utils = new Utils(getInstrumentation().getContext());
    }

    @Override
    public void tearDown() throws Exception {
        utils = null;

        super.tearDown();
    }


    public void testJsonReadingFromAssets() {

        String actualJson = utils.loadJsonFromAsset("user_profile_array.json");

        JsonAssert.assertJsonStructureEquals(JSON_READING_EXPECTED, actualJson);
        JsonAssert.assertJsonEquals(JSON_READING_EXPECTED, actualJson);
    }

    public void testJsonNonEqual() {

        String actualJson = utils.loadJsonFromAsset("user_profile_wrong.json");
        assertEquals("Non Equal", JSON_READING_EXPECTED , actualJson);
//        JsonAssert.assertJsonStructureEquals(JSON_READING_EXPECTED, actualJson);
//        JsonAssert.assertJsonEquals(JSON_READING_EXPECTED, actualJson);
    }
}
