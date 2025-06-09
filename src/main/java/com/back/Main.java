package com.back;

public class Main {
    public static void main(String[] args) {
        testRq1();
//        App app = new App();
//        app.run();
    }

    private static void testRq1(){
        Rq rq = new Rq("목록?searchKeywordType=content&searchKeyword=자바"); //목록?keywordType=author&page=2&keyword=작자
        String searchKeywordType = rq.getParam("searchKeywordType", ""); // "자바"가 출력됨
        String searchKeyword = rq.getParam("searchKeyword", ""); // "자바"가 출력됨

        System.out.println("actionName = " + rq.getActionName()); // "목록"이 출력됨
        System.out.println("param searchKeywordType = " + searchKeywordType);
        System.out.println("param searchKeyword = " + searchKeyword);
    }
}
