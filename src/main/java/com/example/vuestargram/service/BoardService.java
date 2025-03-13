package com.example.vuestargram.service;


import com.example.vuestargram.model.Board;
import com.example.vuestargram.model.QBoard;
import com.example.vuestargram.repogitory.BoardRepogitory;
import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepogitory boardRepogitory;
    private final JPAQueryFactory queryFactory;
    public Board test() {
        //Optional<Board> board = boardRepogitory.findById(11L);

        // QueryDSL
        QBoard qBoard = QBoard.board; //qeuryDSL이 자동으로 생성해주는 board엔티티 기반의 클래스

        JPAQuery<Board> query = queryFactory.selectFrom(qBoard) //select랑 from 이랑 같을 때 selectFrom로 한번에
                                             .where(
                                                     qBoard.boardId.eq(11L)
                                             );
        if(true) {
            query.where(
                    qBoard.boardId.eq(11L)
            );
        }
        return query.fetchFirst();
    }
}
