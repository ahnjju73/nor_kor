package com.example.north_defector.service;

import com.example.north_defector.domain.Board;
import com.example.north_defector.domain.Comment;
import com.example.north_defector.domain.User;
import com.example.north_defector.domain.types.BoardTypes;
import com.example.north_defector.object.BoardDto;
import com.example.north_defector.object.FetchBoardRequest;
import com.example.north_defector.object.request.CommentRequest;
import com.example.north_defector.object.response.BoardDetailResponse;
import com.example.north_defector.object.response.ResponseListDto;
import com.example.north_defector.repository.BoardLikeRepository;
import com.example.north_defector.repository.BoardRepository;
import com.example.north_defector.repository.CommentRepository;
import com.example.north_defector.service.internal.Workspace;
import com.example.north_defector.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService extends Workspace {
    private final BoardRepository boardRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void postBoard(BoardDto boardDto, User author){
        Board board = new Board();
        board.setAuthorNo(author.getUserNo());
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        String email = author.getEmail();
//        board.setSchool(Utils.extractSchoolFromEmail(email));
//        board.setIsForSchool(boardDto.getIsForSchool());
//        board.setBoardTypes(BoardTypes.getType(boardDto.getBoardType()));
        board.setCommDisabled(boardDto.getCommentDisabled());
        String content = boardDto.getContent();
        if(content.indexOf("image") >= 0){
            int start = content.indexOf("image") + 8;
            int end = content.indexOf("\"", content.indexOf("image:") + 3) - 1;
//[{"insert":"Feawfewaefawe"},{"insert":{"image":"https://nearu-test-storage-queue.s3.ap-northeast-2.amazonaws.com/ahnjju73%40gmail.com/ac5364af-768f-4add-82b5-560892d033d4image_picker_E0BBD170-9507-4827-92F9-EAC55D443A11-47362-00000326715FB1CD.jpg"}},{"insert":"\n"}]
            board.setThumbnail(content.substring(start, end));
        }
        boardRepository.save(board);
    }

    @Transactional
    public void editBoard(BoardDto boardDto, User user){
        Board board = boardRepository.findByBoardNo(boardDto.getBoardNo());
        if(board.getAuthorNo() != user.getUserNo()){
            withException("301-001");
        }
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
//        board.setIsForSchool(boardDto.getIsForSchool());
//        board.setBoardTypes(BoardTypes.getType(boardDto.getBoardType()));
        board.setCommDisabled(boardDto.getCommentDisabled());
        board.setUpdatedAt(LocalDateTime.now());
        boardRepository.save(board);
    }

    public ResponseListDto fetchBoardList(FetchBoardRequest boardRequest){
        boardRequest.setSchoolName(Utils.extractSchoolFromEmail(boardRequest.getEmail()));
        boardRequest.setBoardType(BoardTypes.getType(boardRequest.getBoardType()).getBoardType());
        ResponseListDto responseListDto = fetchItemListByNextToken(boardRequest, "board.board.fetchBoardList", "board.board.countAllBoard", "board_no");
        return responseListDto;
    }


    public BoardDetailResponse getBoard(Integer boardNo){
        BoardDetailResponse response = new BoardDetailResponse();
        Board board = boardRepository.findByBoardNo(boardNo);
        List<Comment> comments = getComments(boardNo);
        Integer numLikes = boardLikeRepository.countAllByBoardNo(boardNo);
        response.setBoard(board);
        response.setComments(comments);
        response.setNumLike(numLikes);
        return response;
    }

    public ResponseListDto fetchMyBoardList(FetchBoardRequest boardRequest){
        ResponseListDto responseListDto = fetchItemListByNextToken(boardRequest, "board.board.fetchMyBoardList", "board.board.countMyBoard", "board_no");
        return responseListDto;
    }

    public List<Comment> getComments(Integer boardNo){
        List<Comment> comments = new ArrayList<>();
        List<Comment> commentsNoUpComm = commentRepository.findAllByBoardNoAndUpCommNoIsNullOrderByCreatedAtDesc(boardNo);
        for (int i = 0; i < commentsNoUpComm.size(); i++) {
            List<Comment> comms = commentRepository.findAllByUpCommNoOrderByCreatedAtDesc(commentsNoUpComm.get(i).getCommentNo());
            if(comms != null && comms.size() > 0){
                comments.addAll(comms);
            }
            comments.add(commentsNoUpComm.get(i));
        }
        return comments;
    }

    @Transactional
    public void postComment(CommentRequest request, User session){
        Comment comment = new Comment();
        comment.setComment(request.getComment());
        comment.setBoardNo(request.getBoardNo());
        comment.setAuthorNo(session.getUserNo());
        comment.setCreatedAt(LocalDateTime.now());
        commentRepository.save(comment);
    }

}
