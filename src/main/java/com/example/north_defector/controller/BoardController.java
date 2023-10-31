package com.example.north_defector.controller;

import com.example.north_defector.config.flows.SessionMapper;
import com.example.north_defector.object.*;
import com.example.north_defector.object.request.CommentRequest;
import com.example.north_defector.object.response.BoardDetailResponse;
import com.example.north_defector.object.response.ResponseListDto;
import com.example.north_defector.service.BoardService;
import com.example.north_defector.service.internal.Workspace;
import com.example.north_defector.utils.AmazonUtils;
import com.example.north_defector.utils.keys.ENV;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class BoardController extends Workspace {

    private final BoardService boardService;

    @SessionMapper
    @PostMapping("/board")
    public void post(SessionRequest request){
        BoardDto boardDto = map(request.getParam(), BoardDto.class);
        boardService.postBoard(boardDto, request.getSession());
    }

    @SessionMapper
    @PutMapping("/board")
    public void edit(SessionRequest request){
        BoardDto boardDto = map(request.getParam(), BoardDto.class);
        boardService.editBoard(boardDto, request.getSession());
    }

    @SessionMapper
    @Transactional
    @DeleteMapping("/board")
    public void delete(SessionRequest request){

    }

    @SessionMapper(checkSession = false)
    @GetMapping("/board")
    public BoardDetailResponse boardDetail(SessionRequest request){
        BoardDto boardDto = map(request.getParam(), BoardDto.class);
        return boardService.getBoard(boardDto.getBoardNo());
    }

    @SessionMapper
    @Transactional
    @PutMapping("/board-list")
    public ResponseListDto getBoard(SessionRequest request){
        FetchBoardRequest fetchBoardRequest = map(request.getParam(), FetchBoardRequest.class);
        fetchBoardRequest.setEmail(request.getSession().getEmail());
        ResponseListDto responseListDto = boardService.fetchBoardList(fetchBoardRequest);
        return responseListDto;
    }

    @SessionMapper(checkSession = true)
    @PostMapping("/generate-presigned")
    public PreSignedURLVo generatePresignedUrl(SessionRequest request){
        PreSignedURLVo presigned = map(request.getParam(), PreSignedURLVo.class);
        String uuid = UUID.randomUUID().toString();
        PreSignedURLVo presignedURLVo = new PreSignedURLVo();
        presignedURLVo.setBucket(ENV.AWS_S3_QUEUE_BUCKET);
        presignedURLVo.setFileKey(request.getSession().getEmail() + "/" + uuid + presigned.getFilename());
        presignedURLVo.setFilename(presigned.getFilename());
        presignedURLVo.setUrl(AmazonUtils.AWSGeneratePresignedURL(presignedURLVo));
        return presignedURLVo;
    }

    @SessionMapper
    @GetMapping("/my-board-list")
    public ResponseListDto getMyBoards(SessionRequest request){
        FetchBoardRequest fetchBoardRequest = new FetchBoardRequest();
        fetchBoardRequest.setUserNo(request.getSession().getUserNo());
        ResponseListDto responseListDto = boardService.fetchMyBoardList(fetchBoardRequest);
        return responseListDto;
    }

    @SessionMapper
    @PostMapping("/comment")
    public void postComment(SessionRequest request){
        CommentRequest commentRequest = map(request.getParam(), CommentRequest.class);
        boardService.postComment(commentRequest, request.getSession());
    }


}