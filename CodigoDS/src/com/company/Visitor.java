package com.company;

public interface Visitor {
  void visitInterval(Interval interval);

  void visitTask(Task task);

  void visitProject(Project project);

  void searchTaskTag(Task task);

  void searchProjectTag(Project project);
}