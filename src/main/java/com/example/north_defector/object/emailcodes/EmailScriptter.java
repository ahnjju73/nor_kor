package com.example.north_defector.object.emailcodes;


import com.example.north_defector.service.internal.Workspace;
import com.example.north_defector.utils.SendMails;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class EmailScriptter extends Workspace {

    public EmailScriptter(String to, String title){
        this.to = to;
        this.title = title;
    }

    protected String to;
    protected String title;

    interface EmailHtml{
        String writeHtml(String str);
    }

    protected void sender(String emailHtml){
        SendMails.getInstance()
                .setEMAIL(to)
                .setCONTENT((s) -> emailHtml)
                .setTITLE(title).send();
    }

}
