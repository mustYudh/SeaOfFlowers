package com.hzrcht.seaofflowers.module.login.bean;

import java.io.Serializable;

public class LoginBean implements Serializable {


    /**
     * token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOjMwLCJpYXQiOjE1NjI4MzA4NjUsImV4cCI6MTU2MzY5NDg2NSwibmJmIjoxNTYyODMwODY1LCJzdWIiOiJ3d3cuYWRtaW4uY29tIiwianRpIjoiMDczZjMxYmFmODVjZmI5N2M2NjAwYjUxODdkNTM0NjAiLCJkYXRhIjp7ImlkIjozMCwic2V4IjoxLCJ0eXBlIjowLCJuaWNrX25hbWUiOiIiLCJ1c2VyU2lnIjoiZUp4TmpsdFBnekFZaHY5TGJ6V3V0QVdaaVJlVkxGTXowWERZb2xuU3RLVnNEWEt3RlBFUS03dVZTUFQyZWQ3dmU5OVBrRzNTTXk1bE96U1cyZmRPZ1FzQXdlbUVkYUVhcTB1dGpJTjRwcnpyZE1HNFpkZ1UtOEo5VWJGSk9lWVJDQkVpRUpGZnFkNDZiUlRqcFoxKmhZRUx6SWV2eXZTNmJSeEcwUE05aENIOGsxYlhQNHM4UDBBaGhtSGd6MjM2NFBEZEtvOXVLTWsyTnFiSDJ5ei1JQU5aVnJpbmNoay0yT2d4RldwOGFyZGI4cksqV3BkaVItV0tEalVmMi0zaU9hbEVVcC1reDFGbDJGeEh5WDRoSTRIa0xuU08zNmZuV09pNE9WeUNyMi1EeUZiQiJ9fQ.V482W4XsMuIb3kR73MBOcSo-IijVP_UuB11TzP4QvxI
     * info : {"id":30,"sex":1,"type":0,"nick_name":"","userSig":"eJxNjltPgzAYhv9LbzWutAWZiReVLFMz0XDYolnStKVsDXKwFPEQ-7uVSPT2ed7ve99PkG3SMy5lOzSW2fdOgQsAwemEdaEaq0utjIN4przrdMG4ZdgU-8J9UbFJOeYRCBEiEJFfqd46bRTjpZ1*hYELzIevyvS6bRxG0PM9hCH8k1bXP4s8P0AhhmHgz2364PDdKo9uKMk2NqbH2yz-IANZVrinchk-2OgxFWp8ardb8rK*WpdiR-WKDjUf2-3iOalEUp-kx1Fl2FxHyX4hI4HkLnSO36fnWOi4OVyCr2-DyFbB"}
     * userSig : eJxNjltPgzAYhv9LbzWutAWZiReVLFMz0XDYolnStKVsDXKwFPEQ-7uVSPT2ed7ve99PkG3SMy5lOzSW2fdOgQsAwemEdaEaq0utjIN4przrdMG4ZdgU-8J9UbFJOeYRCBEiEJFfqd46bRTjpZ1*hYELzIevyvS6bRxG0PM9hCH8k1bXP4s8P0AhhmHgz2364PDdKo9uKMk2NqbH2yz-IANZVrinchk-2OgxFWp8ardb8rK*WpdiR-WKDjUf2-3iOalEUp-kx1Fl2FxHyX4hI4HkLnSO36fnWOi4OVyCr2-DyFbB
     */

    public String token;
    public InfoBean info;
    public String userSig;

    public static class InfoBean {
        /**
         * id : 30
         * sex : 1
         * type : 0
         * nick_name :
         * userSig : eJxNjltPgzAYhv9LbzWutAWZiReVLFMz0XDYolnStKVsDXKwFPEQ-7uVSPT2ed7ve99PkG3SMy5lOzSW2fdOgQsAwemEdaEaq0utjIN4przrdMG4ZdgU-8J9UbFJOeYRCBEiEJFfqd46bRTjpZ1*hYELzIevyvS6bRxG0PM9hCH8k1bXP4s8P0AhhmHgz2364PDdKo9uKMk2NqbH2yz-IANZVrinchk-2OgxFWp8ardb8rK*WpdiR-WKDjUf2-3iOalEUp-kx1Fl2FxHyX4hI4HkLnSO36fnWOi4OVyCr2-DyFbB
         */

        public int id;
        public int sex;
        public int type;
        public String nick_name;
        public String userSig;
    }
}
