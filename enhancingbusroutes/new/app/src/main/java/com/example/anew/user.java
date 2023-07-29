package com.example.anew;

import com.google.firebase.firestore.Exclude;

public class user {


    public String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String no,route,time;
    public user(String id,String busno, String route, String time) {
        this.key = id;
        this.no = busno;
        this.route = route;
        this.time = time;
    }

    public user() {

    }

    public String getNo() {
            return no;
        }

        public String getRoute() {
            return route;
        }

        public String getTime() {
            return time;
        }


    public void setNo(String no) {
        this.no = no;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
