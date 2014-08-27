package com.xchanging.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ConfigurationManager {
  private static final String         BUNDLE_NAME     = "com.xchanging.util.config"; 

  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
                                                          .getBundle(BUNDLE_NAME);

  public static String getString(final String key) {
    try {
      return RESOURCE_BUNDLE.getString(key);
    } catch (final MissingResourceException e) {
      return '!' + key + '!';
    }
  }

  private ConfigurationManager() {
  }
}
