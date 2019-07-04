package com.hzrcht.seaofflowers.module.login.bean;

public class LoginBean {

    /**
     * code : 0
     * msg : SUCCESS
     * time : 1562200107
     * data : {"token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOjI3LCJpYXQiOjE1NjIyMDAxMDcsImV4cCI6MTU2MjIwNzMwNywibmJmIjoxNTYyMjAwMTA3LCJzdWIiOiJ3d3cuYWRtaW4uY29tIiwianRpIjoiNjRhODAxNDJmYzg5ZTkzMjY0YzNjMWQyMTY1YTdkM2IiLCJkYXRhIjp7ImlkIjoyNywic2V4IjowfX0.J8NAO77niemk247fdtfWhwjsYLwqatYzTg84E42fbS4","info":{"id":27,"sex":0}}
     */

    public int code;
    public String msg;
    public int time;
    public DataBean data;

    public static class DataBean {
        /**
         * token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOjI3LCJpYXQiOjE1NjIyMDAxMDcsImV4cCI6MTU2MjIwNzMwNywibmJmIjoxNTYyMjAwMTA3LCJzdWIiOiJ3d3cuYWRtaW4uY29tIiwianRpIjoiNjRhODAxNDJmYzg5ZTkzMjY0YzNjMWQyMTY1YTdkM2IiLCJkYXRhIjp7ImlkIjoyNywic2V4IjowfX0.J8NAO77niemk247fdtfWhwjsYLwqatYzTg84E42fbS4
         * info : {"id":27,"sex":0}
         */

        public String token;
        public InfoBean info;

        public static class InfoBean {
            /**
             * id : 27
             * sex : 0
             */

            public int id;
            public int sex;
        }
    }
}
