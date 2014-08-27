package com.xchanging.util;

 
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Logging {

  public static Logger log;
 
  @SuppressWarnings("rawtypes")
  public static Logger getLogger(final Class clazz) {
 //   BasicConfigurator.configure();
	PropertyConfigurator.configure("log4j.properties"); //$NON-NLS-1$
    log = Logger.getLogger(clazz);
  //  log.setLevel(Level.ALL);
    return log;

  }

}
