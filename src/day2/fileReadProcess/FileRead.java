package day2.fileReadProcess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileRead {


    // 절대 경로로 파일을 읽어오는 예시 코드
    public static void main(String[] args) {


        String filePath = "C:\\Users\\qoqhs\\read.txt"; // 파일 경로

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line); // 파일의 각 줄을 출력
            }
        } catch (IOException e) {
            e.printStackTrace(); // 예외 처리
        }
    }

    // 코드 흐름 파악
    // 1. JVM 실행
    // 자바 명령 java FileRead 로 JVM이 실행되면서 FileRead.class(바이트 코드)가 JVM에 의해 로드된다

    // 2. 클래스 로더
    // JVM의 클래스 로더가 FileRead를 메모리에 로드하고 초기화 한다
    // 정적 영역(클래스 변수 등)이 초기화되고, main 메서드를 실행할 준비를 한다

    // 3. 메인 메서드 실행
    // 클래스 로더가 준비를 마치면 public static void main(String[] args)메서드가 호출되면서 시작한다

    // 4. new FileReader(filePath)
    // FileReader 객체 생성
    // FileReader 는 “문자(char) 단위로 읽기”에 특화된 클래스이며, 내부적으로 FileInputStream 을 사용
    // FileReader 는 단순히 “파일 경로”를 인자로 받아, 기본 문자 인코딩(플랫폼 디폴트 인코딩)을 사용하는 InputStreamReader 를 구성하는 역할을 한다
    // 실제 “파일 열기” 동작은 FileInputStream 을 통해 수행된다
    // 내부적으로 super(new FileInputStream(filePath))(등가의 호출) 형태로 동작
    // 결국 FileInputStream 생성자를 통해 파일이 열림
    // super(new FileInputStream(fileName));
    // 내부에서 자바 네이티브 메서드(JNI)(open0)를 호출해서 OS에 파일 열기를 요청
    // open0()에서 C/C++ 구현부가 실행 → OS 시스템 콜(open(), CreateFile(), etc.)을 통해 실제 파일 핸들/디스크립터를 획득
    // 성공 시 “디스크립터(fd)”나 “핸들(handle)”을 자바가 얻음 → FileDescriptor 객체에 저장
    // 이후 FileReader 에게 스트림을 넘겨주고, FileReader 도 최종 초기화 완료
    // fileOpen이 자바 쪽에 있는 상자(필드)에 네이티브 핸들을 집어넣는다고 생각하면 된다
    // 그리고 자바 코드는 그 상자(필드)를 열어 핸들을 확인하거나, 다른 함수에 넘겨서 실제 I/O 작업을 진행한다

    // 5. BufferedReader br = new BufferedReader(new FileReader(filePath))
    // BufferedReader 는 “버퍼링” 기능을 제공해, 디스크로부터 데이터를 조금씩 여러 번 읽는 대신, 상대적으로 큰 덩어리로 한번에 읽어 들이는 방식을 쓴다
    // 기본 버퍼 크기는 보통 8KB(8192 bytes) 정도인데, 이는 파일 입출력을 더 효율적이고 빠르게 만들어준다
    // 즉, 내부 버퍼를 채울 때마다 FileReader(더 정확히는 내부 FileInputStream)를 통해 OS 에서 데이터를 끌어오고, 사용자가 read()를 호출할 때마다 버퍼에서 꺼내주는 형식이다
    // BufferedReader 의 대표적인 메서드 readLine()을 통해 텍스트 파일을 “한 줄씩” 가져옴
    // 버퍼에 남아있는 데이터가 있는지 확인함
    // 버퍼가 비어 있거나 부족하면, OS에 더 많은 데이터를 읽어 달라고 요청
    // 이때, OS는 디스크(또는 SSD)에서 파일 데이터를 가져와 자바의 버퍼에 채워줌
    // 내부적으로는 JNI 를 통해 read() 시스템 콜이 수행된다
    // 버퍼에 담긴 데이터 중 줄바꿈 문자(\n 혹은 \r\n)를 찾는다.
    // 줄바꿈 문자를 만날 때까지가 한 줄이 되고
    // 해당 줄을 String 객체로 만들어 반환함
    // readLine()이 읽어온 문자열을 line 변수에 넣고.
    // 만약 더 이상 읽을 내용이 없으면(EOF에 도달하면), readLine()은 null을 반환.
    // while 문에서 line 이 null 이 아닌 동안 계속 반복해서 파일의 각 줄을 읽어서 출력한다

    // JNI 에서 실제 OS에 어떻게 요청을 보내는지
    // 1. 자바 클래스(FileInputStream.java)에 native 선언된 open0(String name) →
    // 2. FileInputStream.c 에 있는 Java_java_io_FileInputStream_open0 JNI 함수가 매핑 →
    // 이 과정은 언제?
    // JVM 구동 시(또는 특정 시점에), 이 라이브러리들이 자동으로 로드되고, registerNatives 등으로 자바 메서드 <-> C 함수 매핑이 설정됨
    // 3. 내부에서 fileOpen(...) 같은 공용 함수 호출 →
    // 4. io_util_md.c 등의 소스에서 OS API(open(), CreateFile())를 최종 호출 →
    // 5. 성공 시 OS가 돌려준 파일 디스크립터/핸들을 FileDescriptor 객체에 저장
    // 6. 즉, open0() 자체는 비교적 얇은 래퍼(Wrapper) 역할을 하며, 실질적인 OS 파일 열기 로직은 플랫폼별 소스(Windows, Linux, macOS 등)에서 담당하고 있음
    // 자바가 “이름 규칙”과 “동적 라이브러리 로딩”을 통해 C 함수를 찾아 호출하는 과정

    // 실제 FileInputStream.c에 있는 JNI의 open0
    // JNIEXPORT void JNICALL
    // Java_java_io_FileInputStream_open0(JNIEnv *env, jobject this, jstring path) {
    //    fileOpen(env, this, path, fis_fd, O_RDONLY);
    // }



    // 파일이 텍스트만 있는것이 아닌데 그럼 다른 경우는?
    // 흐름 자체는 같지만 자바에서 그 파일을 어떻게 해석하고 다루는가에 차이는 있음
    // 텍스트 vs 바이너리


    // 정리
    // 1. FileReader 생성 시:
    // 네이티브 메서드를 통해 OS에 “이 파일을 열겠다”는 요청.
    // 성공 시 OS는 파일 디스크립터를 반환하고, 이를 바탕으로 FileReader가 만들어짐.

    // 2. BufferedReader 생성 시:
    // FileReader 의 스트림을 효율적으로 다루기 위해 버퍼를 덧씌운 객체 생성.
    // OS 요청은 없음. 다만 FileReader 가 이미 오픈한 파일 디스크립터를 내부적으로 사용.

    // 3.readLine() 호출 시:
    // BufferedReader 내부 버퍼에 데이터가 부족하면 OS 에게 “파일에서 데이터를 읽어와 달라”는 요청을 보냄.
    // OS는 디스크에서 데이터를 읽어 메모리에 로드해주고, Java 는 이 버퍼에서 한 줄 단위로 끊어서 가져옴.
    // 리소스 닫기:
    // 여기 예시에는 try-with-resources 구문으로 close 를 자동실행
    // 오직 AutoCloseable 구현체만 try-with-resources 구문에 넣을 수 있는 객체는 AutoClosable 인터페이스를 구현해야 함
    // close() 호출 시, OS 에게 “사용 중이던 파일 핸들을 해제해 달라” 요청.
    // OS가 디스크립터를 닫고 자원을 반환.
    // 예외 처리:
    //
    // 디스크에 파일이 없거나 권한이 없으면 OS가 거부 응답.
    // IOException 발생.
    //

    // 자세한 설명은 아래 pdf 링크 참조
    // file:///C:/Users/qoqhs/Downloads/%ED%8C%8C%EC%9D%BC%ED%9D%90%EB%A6%84%EB%8F%84.drawio%20(2).pdf
}