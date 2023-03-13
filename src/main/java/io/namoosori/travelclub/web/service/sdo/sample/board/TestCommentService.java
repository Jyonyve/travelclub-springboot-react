package io.namoosori.travelclub.web.service.sdo.sample.board;

import io.namoosori.travelclub.web.aggregate.sample.board.TestComment;

import java.util.List;

public interface TestCommentService {

    TestComment register(String postingId, TestCommentCdo commentCdo);
    TestComment findById(String commentId);
    List<TestComment> findAllByPostingId(String postingId);
    void modify(String commentId, TestComment comment);
    void remove(String commentId);

}
