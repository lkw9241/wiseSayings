//8단계
package com.back;
import com.back.WiseSaying;

import java.util.Scanner;

// 명언 앱의 메인 클래스
public class App{

    // 사용자 입력을 받기 위한 Scanner
    Scanner scanner = new Scanner(System.in);

    // 명언마다 부여되는 고유 번호
    int lastId = 0;

    // 명언을 저장할 배열 (최대 100개까지 저장 가능)
    WiseSaying[] wiseSayings = new WiseSaying[100];

    // 현재 저장된 명언의 마지막 인덱스 (처음엔 -1 = 비어 있음)
    int wiseSayingsLastIndex = -1;

    // 프로그램 시작 지점 (진입점)
    void run() {
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");
            String cmd = scanner.nextLine().trim(); // 명령어 입력받기

            if (cmd.equals("종료")) {
                break; // 프로그램 종료
            } else if (cmd.equals("목록")) {
                actionList(); // 명언 목록 보기
            } else if (cmd.equals("등록")) {
                actionWrite(); // 명언 등록
            } else if (cmd.startsWith("삭제")) {
                actionDelete(cmd); // 명언 삭제
            } else if (cmd.startsWith("수정")) {
                actionModify(cmd); // 명언 수정
            }
        }

        scanner.close(); // 프로그램 종료 시 스캐너 닫기
    }

    // 저장된 명언을 최신순으로 출력
    void actionList() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        // 최신순 정렬된 명언 배열 가져오기
        WiseSaying[] forListWiseSayings = findForList();

        // 각 명언 출력
        for (WiseSaying wiseSaying : forListWiseSayings) {
            System.out.printf("%d / %s / %s\n", wiseSaying.id, wiseSaying.author, wiseSaying.content);
        }
    }

    // 명언 등록 기능
    void actionWrite() {
        System.out.print("명언 : ");
        String content = scanner.nextLine().trim();

        System.out.print("작가 : ");
        String author = scanner.nextLine().trim();

        // 명언 등록
        WiseSaying wiseSaying = write(content, author);

        // 등록 완료 메시지
        System.out.println("%d번 명언이 등록되었습니다.".formatted(wiseSaying.id));
    }

    // 명언 삭제 기능
    void actionDelete(String cmd) {
        // 명령어에서 ID 추출
        String[] cmdBits = cmd.split("=", 2);

        if (cmdBits.length < 2 || cmdBits[1].isEmpty()) {
            System.out.println("id를 입력해주세요.");
            return;
        }
        //문자열로 되어 있는 숫자를 → 진짜 숫자(int)로 바꾸는 코드입니다.
        int id = Integer.parseInt(cmdBits[1]);
        int deletedIndex = delete(id);

        if (deletedIndex == -1) {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
            return;
            //return;은 **"지금 이 메서드를 여기서 끝내고, 나가라"**는 뜻이에요.
        }

        System.out.println("%d번 명언이 삭제되었습니다.".formatted(id));
    }

    // 명언 수정 기능
    void actionModify(String cmd) {
        // 명령어에서 ID 추출
        String[] cmdBits = cmd.split("=", 2);

        if (cmdBits.length < 2 || cmdBits[1].isEmpty()) {
            System.out.println("id를 입력해주세요.");
            return;
        }

        int id = Integer.parseInt(cmdBits[1]);
        WiseSaying wiseSaying = findById(id); // ID에 해당하는 명언 찾기

        if (wiseSaying == null) {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
            return;
        }

        // 기존 내용 보여주고 수정 입력받기
        System.out.printf("명언(기존) : %s\n", wiseSaying.content);
        System.out.print("명언 : ");
        String content = scanner.nextLine().trim();

        System.out.printf("작가(기존) : %s\n", wiseSaying.author);
        System.out.print("작가 : ");
        String author = scanner.nextLine().trim();

        modify(wiseSaying, content, author); // 수정 적용
    }

    // 저장된 명언 개수 반환
    int getSize() {
        return wiseSayingsLastIndex + 1;
    }

    // 최신 등록 순으로 명언 배열 반환
    WiseSaying[] findForList() {
        WiseSaying[] forListWiseSayings = new WiseSaying[getSize()];
        int forListWiseSayingsIndex = -1;

        // 뒤에서부터 앞으로 복사 → 최신순
        for (int i = wiseSayingsLastIndex; i >= 0; i--) {
            forListWiseSayings[++forListWiseSayingsIndex] = wiseSayings[i];
        }

        return forListWiseSayings;
    }

    // 명언 저장
    WiseSaying write(String content, String author) {
        WiseSaying wiseSaying = new WiseSaying();
        wiseSaying.id = ++lastId; // id 증가
        wiseSaying.content = content;
        wiseSaying.author = author;

        wiseSayings[++wiseSayingsLastIndex] = wiseSaying; // 배열에 저장

        return wiseSaying;
    }

    // id로 명언의 배열 인덱스를 찾기
    int findIndexById(int id) {
        for (int i = 0; i <= wiseSayingsLastIndex; i++) {
            if (wiseSayings[i].id == id) {
                return i;
            }
        }
        return -1; // 못 찾았으면 -1
    }

    // id로 명언 객체 자체를 찾기
    WiseSaying findById(int id) {
        int index = findIndexById(id);

        if (index == -1) return null;

        return wiseSayings[index];
    }

    // 명언 수정
    void modify(WiseSaying wiseSaying, String content, String author) {
        wiseSaying.content = content;
        wiseSaying.author = author;
    }

    // 명언 삭제
    int delete(int id) {
        int deleteIndex = findIndexById(id);

        if (deleteIndex == -1) return deleteIndex;

        // 뒷 요소들을 한 칸 앞으로 당기기
        for (int i = deleteIndex + 1; i <= wiseSayingsLastIndex; i++) {
            wiseSayings[i - 1] = wiseSayings[i];
        }

        // 마지막 요소 제거
        wiseSayings[wiseSayingsLastIndex] = null;
        wiseSayingsLastIndex--;

        return deleteIndex;
    }
}