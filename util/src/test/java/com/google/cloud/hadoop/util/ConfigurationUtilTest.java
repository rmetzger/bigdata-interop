/**
 * Copyright 2014 Google Inc. All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.hadoop.util;

import com.google.common.collect.Lists;

import org.apache.hadoop.conf.Configuration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit tests for ConfigurationUtil class.
 */
@RunWith(JUnit4.class)
public class ConfigurationUtilTest {
  private static final String KEY_ONE = "test";
  private static final String VALUE_ONE = "test";
  private static final String KEY_TWO = "test2";
  private static final String VALUE_TWO = "test2";

  /**
   * Verifies getMandatoryConfig method for single strings.
   */
  @Test
  public void testSingleStringGetMandatoryConfig() throws IOException {
    // Test null value.
    Configuration config = new Configuration();
    try {
      ConfigurationUtil.getMandatoryConfig(config, KEY_ONE);
      Assert.fail();
    } catch (IOException e) {
      // Expected.
    }

    // Test empty string.
    config.set(KEY_ONE, "");
    try {
      ConfigurationUtil.getMandatoryConfig(config, KEY_ONE);
      Assert.fail();
    } catch (IOException e) {
      // Expected.
    }

    // Test proper setting.
    config.set(KEY_ONE, VALUE_ONE);
    Assert.assertEquals(VALUE_ONE, ConfigurationUtil.getMandatoryConfig(config, KEY_ONE));
  }

  /**
   * Verifies getMandatoryConfig method for a list of strings.
   */
  @Test
  public void testListGetMandatoryConfig() throws IOException {
    // Test one null value.
    Configuration config = new Configuration();
    try {
      config.set(KEY_ONE, VALUE_ONE);
      ConfigurationUtil.getMandatoryConfig(config, Lists.newArrayList(KEY_ONE, KEY_TWO));
      Assert.fail();
    } catch (IOException e) {
      // Expected.
    }

    // Test one empty string.
    config.set(KEY_TWO, "");
    try {
      ConfigurationUtil.getMandatoryConfig(config, Lists.newArrayList(KEY_ONE, KEY_TWO));
      Assert.fail();
    } catch (IOException e) {
      // Expected.
    }

    // Test proper setting.
    config.set(KEY_TWO, VALUE_TWO);
    Map<String, String> expectedMap = new HashMap<>();
    expectedMap.put(KEY_ONE, VALUE_ONE);
    expectedMap.put(KEY_TWO, VALUE_TWO);

    Assert.assertEquals(expectedMap,
        ConfigurationUtil.getMandatoryConfig(config, Lists.newArrayList(KEY_ONE, KEY_TWO)));
  }
}
