package day2;

import java.util.ArrayList;
import java.util.List;

public class Equals {


    // = vs == vs .equals() 의 차이



    // 1. = 대입연산자
    // 변수에 값을 할당하거나 대입할때만 사용
    // int a = 123;
    // 문자열 객체의 참조(주소) test 에 문자열 "test"값을 대입
    // String test = "test";
    // = 는 오직 “할당”을 의미하며, 비교 연산의 기능이 전혀 없음

    // 2. == 동등연산자
    // 자바에서 ==의 동작은 피연산자가 기본형(primitive type)이냐
    // 참조형(reference type)이냐에 따라서 달라진다

    // 기본형(primitive type)인 경우
    // int a = 3;
    // int b = 3;
    // double x = 3.123;
    // double y = 3.123;
    // 예시로 들면
    // a == b 두 정수값이 같은지 확인
    // x == y 두 실수값이 같은지 확인

    // 참조형(reference type)인 경우
    // q1 -> 힙 영역에 새로운 String 객체를 만들고 그 참조(주소)를 q1에 대입한다
    // String q1 = new String("Hello");

    // q2 -> 같은 방식으로 힙 영역에 String 객체를 만들고 그 참조(주소)를 q2에 대입한다
    // String q2 = new String("Hello");

    // 문자열 리터럴 "Hello"는 자바 컴파일 시점(혹은 클래스 로딩 시점)에 String Pool(상수 풀)안에 저장된다
    // 이떄 q3은 String Pool내에 "Hello"객체를 가리키게 된다 (이건 별도의 new가 아님)
    // String q3 = "Hello";

    // "Hello" 리터럴을 상수풀에서 찾아서 이미 존재하는 같은 풀 내 "Hello" 객체를 가리키게 된다
    // String q4 = "Hello";

    // System.out.println(q1 == q2);
    // 서로 다른 객체를 생성한것임 힙 영역에서 각각 별도의 메모리 공간을 차지하며 참조(주소)가 다름

    // System.out.println(q1 == q3);
    // q1은 new로 생성된 객체이고 q3는 String Pool에 있는 객체를 참조하므로 다름

    // System.out.println(q4 == q3);
    // q4, q3둘다 상수풀에 있는 객체를 참조하므로 같음

    // 그럼 String Pool이란?
    // String Pool은 자바가 문자 리터럴을 효울적으로 관리하기 위해 존재하는 특별한 캐시 영역
    // Java8 이상부터는 String Pool이 Heap 영역으로 옮겨짐 이전 버전에서는 (Permanent Generation영역에 있었음)
    // 효과: 중복 리터럴 생성 방지
    // ex: String a = "test" / String b = "test" 라고 했을때
    // 동일한 리터럴이 여러 곳에서 사용되더라도 하나의 문자열 객체만 생성하여 공유한다
    // 예를들어  String s1 = "Bye" 이 코드를 만나는 순간 컴파일된 .class파일의 상수풀에 "Bye"정보가 기록되고 클래스 로딩 시점에 String Pool에 적재된다

    // String.intern(); 임의의 String 객체를 직접 String Pool에 추가할 수 있도록 해줌
    // String internTest = "internTest";
    // String interned = internTest.intern();
    // String internTest1 = new String("internTest");


    // System.out.println(internTest == interned);

    // internTest1은 힙 영역의 새로운 객체를 가리키고 interned는 상수풀의 문자열 객체를 가리켜서 서로 다름
    // System.out.println(internTest1 == interned);


    // .equals() 객체 동등성 비교 매서드
    // 모든 클래스의 최상위 부모인 Object에 기본적으로 선언되있음
    // 필요에 따라 오버라이드도 가능
    // 객체가 실제로 어떠한 조건을 만족했을때 두 객체는 서로 같다 라고 판단할지 정의할수 있음
    // 기본적인 Object.equals()의 코드


    //public boolean equals(Object obj) {
    //        return (this == obj);
    //    }

    // 동일한 객체인지(참조인지) == 연산자를 통해 동작을 수행한다
    // 그래서 Object 에 equals()를 그대로 사용하면 객체의 내용을 비교하는것이 아닌
    // 참조만 동일한지 확인하게 된다

    // .equals()가 오버라이드 되는 이유
    // 논리적 동등성(Logical Equality) 정의
    // 문자열,컬렉션,사용자 정의 클래스 등은 내부 data 가 같으면 같은 객체로 보고 싶을떄가 많다
    // String Integer List 등 표준 라이브러리 클래스들은 내부적으로
    // equals()를 오버라이드 하여 객체의 내용이 같은지 비교하도록 구현이 되어있음
    // ex: new String("test").equals("test") -> true
    // == 연산으로 비교했으면 힙 영역에 새롭게 생성된 객체와 상수풀에 있는 객체를 비교하게 되므로 false임
    // String 클래스 안에 equals()를 재정의하여 비교했기 때문에 true

    // String 클래스에 구현되있는 equals 함수
    // public boolean equals(Object anObject) {
    //        if (this == anObject) {
    //            return true;
    //        }
    //        if (anObject instanceof String) {
    //            String aString = (String)anObject;
    //            if (coder() == aString.coder()) {
    //                return isLatin1() ? StringLatin1.equals(value, aString.value)
    //                                  : StringUTF16.equals(value, aString.value);
    //            }
    //        }
    //        return false;
    //    }
    // 기본적인 Object 에 있는 ==연산 비교와 String 클래스인지 비교 후 String 클래스면 인코딩도 확인하여 동일한 내용인지 확인한다

    // .equals()의 일반 규약
    // 공식문서에 의한 equals()의 규약은 총 5가지다
    // 1. 반사성(reflexive)
    // 어떤 객체 x에 대해 x.equals(x) 는 항상 true 다
    // 2. 대칭성(symmetric)
    // 두 객체 x,y가 있을때 x.equals(y)가 true 면 그 역인 y.equals(x)도 true 여야한다
    // 3. 추이성(transitive)
    // x.equals(y)가 true 고 y.equals(z)가 true 면 x.equals(z)도 true 여야 한다
    // 4. 일관성(consistent)
    // 여러번 호출해도 같은 조건이 변하지 않았으면 언제 호출하든 x.equals(y)는 항상 true 여야한다
    // 객체가 바뀌지 않는 상황이라는 전제 하에
    // 5. null 에 대한 비교
    // 어떤 객체 x도 x.equals(null)은 false 여야 한다
    // 이 규약들을 지키면서 오버라이드 하지 않으면 컬렉션 프레임워크등에서 오류나 예상치 못한 동작이 발생할 수 있음

    // 추가적으로 알아둘 것
    // equals 와 hashCode의 관계
    // 둘은 생각보다 긴밀히 연관되어 있다 특히 해시 기반 컬렉션(HashSet, hashMap, HashTable 등)은
    // hashCode()를 사용하여 버킷(bucket)에 분류하고 그 버킷 내에서 최종적으로 equals()로 최종 비교를 수행한다
    // equals를 오버라이드 했는데 hashCode를 올바르게 구현하지 않으면 동등한 객체여도 다른 해시값을 가져서 같다고 판단 못할 수 도 있다

    // 이후 부분은 물어보기 (너무 시간을 지체함)


    public static void main(String[] args) {
        String q1 = new String("Hello");
        String q2 = new String("Hello");
        String q3 = "Hello";
        String q4 = "Hello";
        System.out.println(q1 == q2);
        System.out.println(q1 == q3);
        System.out.println(q4 == q3);
        String internTest = "internTest";
        String internTest1 = new String("internTest");
        String interned = internTest.intern();
        System.out.println(internTest == interned);
        System.out.println(internTest1 == interned);
        List<Integer> test123 = new ArrayList<>();




    }

}
