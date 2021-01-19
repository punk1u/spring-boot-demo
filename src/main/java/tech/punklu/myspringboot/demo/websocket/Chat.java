package tech.punklu.myspringboot.demo.websocket;

import java.io.Serializable;

public class Chat implements Serializable {

    private String to;

    private String from;

    private String content;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Chat{");
        sb.append("to='").append(to).append('\'');
        sb.append(", from='").append(from).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
