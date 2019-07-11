package com.hzrcht.seaofflowers.thrid.oss;

import java.io.Serializable;



public class PersistenceResponse implements Serializable {
  public boolean success;
  public String fileAbsPath;
  public String cloudUrl;
}