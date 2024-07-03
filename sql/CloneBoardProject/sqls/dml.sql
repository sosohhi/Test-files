-- 테스트 게시판 등록
INSERT INTO board(seq,title,content,create_date, read_count) 
VALUES (seq_board_no.NEXTVAL, '제목입니다1', '내용입니다1',sysdate,0);

INSERT INTO board(seq,title,content, read_count) 
VALUES (seq_board_no.NEXTVAL, '제목입니다2', '내용입니다2',0);

INSERT INTO board(seq,title,content) 
VALUES (seq_board_no.NEXTVAL, '제목입니다2', '내용입니다2');