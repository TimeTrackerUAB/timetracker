package com.webserver;

import com.company.*;

public class GenerateId {

  private static volatile GenerateId generator = new GenerateId();
  private int id;

  private GenerateId() {
    id = 0;
  }

  public static GenerateId getInstance() {
    return generator;
  }

  public int generateId() {
    return id++;
  }
}
