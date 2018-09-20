package com.example.abhishekbaari.schoolian3;

public class AdaptorForNotificationRecyclerView {
    private String Head;
    private String Decp;

    public AdaptorForNotificationRecyclerView(String head, String decp) {
        this.Head = head;
        this.Decp = decp;
    }

    public String getHead() {
        return Head;
    }

    public String getDecp() {
        return Decp;
    }
}
