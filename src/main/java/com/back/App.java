package com.back;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    // 사용자 입력을 받기 위한 Scanner
    Scanner scanner = new Scanner(System.in);

    // 명언 고유 번호 관리용 변수
    int lastId = 0;

    // 명언들을 저장하는 리스트 (동적 크기 관리 가능)
    List<WiseSaying> wiseSayings = new ArrayList<>();

    // 프로그램 실행 진입점
    void run() {
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");
            String cmd = scanner.nextLine().trim();

            if (cmd.equals("종료")) {
                break;  // "종료" 입력 시 프로그램 종료
            } else if (cmd.equals("목록")) {
                actionList();  // 명언 목록 출력
            } else if (cmd.equals("등록")) {
                actionWrite(); // 명언 등록
            } else if (cmd.startsWith("삭제")) {
                actionDelete(cmd); // 명언 삭제
            } else if (cmd.startsWith("수정")) {
                actionModify(cmd); // 명언 수정
            }
        }

        // 프로그램 종료 시 Scanner 자원 해제
        scanner.close();
    }

    // 명언 목록을 최신순으로 출력하는 기능
    void actionList() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        // 최신순으로 정렬된 명언 리스트 가져오기
        List<WiseSaying> forListWiseSayings = findForList();

        // 리스트 내 명언 하나씩 출력
        for (WiseSaying wiseSaying : forListWiseSayings) {
            System.out.printf("%d / %s / %s\n", wiseSaying.id,
                    wiseSaying.author, wiseSaying.content);
        }
    }

    // 명언을 최신순으로 정렬해서 복사한 리스트 반환
    List<WiseSaying> findForList() {
        List<WiseSaying> copy = new ArrayList<>();
        // 뒤에서부터 앞으로 읽어서 최신순 정렬된 리스트 생성
        for (int i = wiseSayings.size() - 1; i >= 0; i--) {
            copy.add(wiseSayings.get(i));
        }
        return copy;
    }



    // 명언 등록 기능
    void actionWrite() {
        System.out.print("명언 : ");
        String content = scanner.nextLine().trim();

        System.out.print("작가 : ");
        String author = scanner.nextLine().trim();

        // 새로운 명언 객체 생성 및 리스트에 추가
        WiseSaying wiseSaying = write(content, author);

        // 등록 완료 메시지 출력
        System.out.println("%d번 명언이 등록되었습니다.".formatted(wiseSaying.id));
    }

    // 명언 등록: 새로운 객체 생성 후 리스트에 추가
    WiseSaying write(String content, String author) {
        WiseSaying wiseSaying = new WiseSaying();
        wiseSaying.id = ++lastId;  // id 증가 및 할당
        wiseSaying.content = content;
        wiseSaying.author = author;

        wiseSayings.add(wiseSaying);
        return wiseSaying;
    }



    // 명언 삭제 기능
    void actionDelete(String cmd) {
        // "삭제=번호" 형태에서 번호 부분 추출
        String[] cmdBits = cmd.split("=", 2);

        // 번호가 없으면 안내 후 종료
        if (cmdBits.length < 2 || cmdBits[1].isEmpty()) {
            System.out.println("id를 입력해주세요.");
            return;
        }

        // 문자열로 된 번호를 숫자(int)로 변환
        int id = Integer.parseInt(cmdBits[1]);

        // 삭제 시도 후 성공 여부 받기
        boolean deleted = delete(id);

        if (!deleted) {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
        } else {
            System.out.println("%d번 명언이 삭제되었습니다.".formatted(id));
        }
    }

    // 명언 삭제: 리스트에서 ID가 일치하는 항목 제거
    boolean delete(int id) {
        for (int i = 0; i < wiseSayings.size(); i++) {
            if (wiseSayings.get(i).id == id) {
                wiseSayings.remove(i);
                return true;  // 삭제 성공
            }
        }
        return false;  // 삭제 실패 (존재하지 않음)
    }


    // 명언 수정 기능
    void actionModify(String cmd) {
        // "수정=번호"에서 번호 추출
        String[] cmdBits = cmd.split("=", 2);

        // 번호 없으면 안내 후 종료
        if (cmdBits.length < 2 || cmdBits[1].isEmpty()) {
            System.out.println("id를 입력해주세요.");
            return;
        }

        int id = Integer.parseInt(cmdBits[1]);

        // 해당 ID의 명언 찾기
        WiseSaying wiseSaying = findById(id);

        if (wiseSaying == null) {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
            return;
        }

        // 기존 명언 내용 보여주고 수정 입력받기
        System.out.printf("명언(기존) : %s\n", wiseSaying.content);
        System.out.print("명언 : ");
        String content = scanner.nextLine().trim();

        System.out.printf("작가(기존) : %s\n", wiseSaying.author);
        System.out.print("작가 : ");
        String author = scanner.nextLine().trim();

        // 수정 내용 적용
        modify(wiseSaying, content, author);
    }




    // ID로 명언 찾기
    //"wiseSayings 리스트 안에 있는 명언 하나하나를 차례대로 꺼내서, wiseSaying이라는 이름으로 사용할게!"
    WiseSaying findById(int id) {
        for (WiseSaying ws : wiseSayings) {
            if (ws.id == id) return ws;
        }
        return null;  // 못 찾으면 null 반환
    }

    // 명언 수정 적용
    void modify(WiseSaying wiseSaying, String content, String author) {
        wiseSaying.content = content;
        wiseSaying.author = author;
    }
//수정할 대상 명언 객체(wiseSaying)와
//새로운 내용(content), **새로운 작가(author)**를 함께 넘겨주는 거예요.

}