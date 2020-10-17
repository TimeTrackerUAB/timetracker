package com.company;

public interface Visitor {
  void visitInterval(Interval interval);
  void visitActivity(Activity activity);

}
