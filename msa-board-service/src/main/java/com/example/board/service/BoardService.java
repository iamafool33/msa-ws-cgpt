// ✅ 4. BoardService 클래스
package com.example.board.service;

import com.example.board.domain.Board;
import com.example.board.mapper.BoardMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final BoardMapper boardMapper;

    public BoardService(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    public List<Board> getAllBoards() {
        return boardMapper.findAll();
    }

    public Board getBoardById(Long id) {
        return boardMapper.findById(id);
    }

    public void createBoard(Board board) {
        boardMapper.insert(board);
    }

    public void updateBoard(Board board) {
        boardMapper.update(board);
    }

    public void deleteBoard(Long id) {
        boardMapper.delete(id);
    }
}