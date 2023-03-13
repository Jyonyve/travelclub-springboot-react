package io.namoosori.travelclub.web.service.sdo.sample.board;

import io.namoosori.travelclub.web.aggregate.sample.board.TestPosting;

import java.util.List;


public interface TestPostingService {
    //
    String register(String boardId, TestPostingCdo postingCdo);
    TestPosting findById(String postingId);
    List<TestPosting> findByTestBoardId(String boardId);
    void modify(String postingId, TestPosting posting);
    void remove(String postingId);
}
