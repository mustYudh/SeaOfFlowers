package com.hzrcht.seaofflowers.action;


import android.text.TextUtils;

/**
 * @author chenwei
 * @date 2017/9/2
 */
public enum ActionEnums {

  /**
   *
   */
  TEST_LOGIN(ActionHeads.TEST_LOGIN),
  HOME_CENTER_TAB_STATUS(ActionHeads.HOME_CENTER_TAB_STATUS);

  private String head;

  private ActionEnums(String head) {
    this.head = head;
  }

  public boolean isMatch(String action) {
    if (TextUtils.isEmpty(action)) {
      return false;
    }
    if (head.equals(action)) {
      return true;
    }
    if (head.endsWith("/") || head.endsWith("?")) {
      return action.startsWith(head);
    } else {
      return action.startsWith(head + "/") || action.startsWith(head + "?");
    }
  }
}
