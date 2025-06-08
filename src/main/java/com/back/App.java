package com.back;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App{
    Scanner scanner = new Scanner(System.in);

    List<WiseSaying> wiseSayings = new ArrayList<>();
    int lastId = 0;

    void run(){
        System.out.println("== 명언앱 ==");
        while(true) {
            System.out.print("명령)");
            String cmd = scanner.nextLine().trim();
            if (cmd.equals("종료")) {
                break;
            } else if (cmd.equals("목록")) {
                actionList();

            } else if (cmd.equals("등록")) {
                actionWrite();

            }else if (cmd.startsWith("삭제")) {
//                actionDelete(cmd);
            } else if (cmd.startsWith("수정")){
//                actionModify(cmd);

            }

        }
        scanner.close();
    }
    void actionList() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("-------------------------");

        List<WiseSaying> forListWiseSayings = findForList();
        for (WiseSaying wiseSaying : forListWiseSayings) {
            System.out.println("%d / %s / %s".formatted(wiseSaying.id,
                    wiseSaying.author, wiseSaying.content));
        }

    }
    List<WiseSaying> findForList(){
        List<WiseSaying> forListWiseSayings= new ArrayList<>();
        for(int i = wiseSayings.size()-1; i>=0; i--){
            forListWiseSayings.add(wiseSayings.get(i));

        }
        return forListWiseSayings;
    }

    void actionWrite(){
        System.out.print("명언 : ");
        String content = scanner.nextLine().trim();
        System.out.print("작가 : ");
        String author = scanner.nextLine().trim();

        WiseSaying wiseSaying=write(content, author);
        System.out.println("%d번 명언이 등록되었습니다.".formatted(wiseSaying.id));

    }
    WiseSaying write(String content, String author){
        WiseSaying wiseSaying = new WiseSaying();
        wiseSaying.id = ++lastId;
        wiseSaying.author = author;
        wiseSaying.content = content;

        wiseSayings.add(wiseSaying);
        return wiseSaying;

    }

    void actionDelete(String cmd) {

    }
    void delete() {

    }

    void actionModify(){

    }
    void modify() {

    }
    void findById(){

        }

}