package com.hzrcht.seaofflowers.module.mine.activity;

/**
 * @author yudneghao
 * @date 2019-07-25
 */
public class CurrentChatMember {
  private static final CurrentChatMember ourInstance = new CurrentChatMember();

  public static CurrentChatMember getInstance() {
    return ourInstance;
  }

  private CurrentChatMember() {
  }

  private String userId = "";

  public String gerUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public boolean isOpenChat(String userId) {
    return userId.equals(this.userId);
  }
}
