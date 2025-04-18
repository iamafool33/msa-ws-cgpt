// ✅ 1. Board 도메인 클래스
package com.example.board.domain;

public class Board {
    private Long id;
    private String title;
    private String content;
    private String writer;

    // Getter/Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getWriter() { return writer; }
    public void setWriter(String writer) { this.writer = writer; }
}
