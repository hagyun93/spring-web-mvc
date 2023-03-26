# spring-web-mvc

## DispatcherServlet 동작 원리
### DispatcherServlet 초기화
- 다음의 특별한 타입의 빈들을 찾거나, 기본 전력에 해당하는 빈들을 등록한다.
  - HandlerMapping
  - HandlerAdapter
  - HandlerExceptionResolver
  - ViewResolver
  - ...

### DispatcherServlet 동작 순서
1. 요청을 분석한다. (로케일, 테마, 멀티파트 등)
2. (핸들러 맵핑에게 위임하여) 요청을 처리할 핸들러를 찾는다.
3. (등록되어 있는 핸들러 어댑터 중에) 해당 핸들러를 실행할 수 있는 "핸들러 어댑터"를 찾는다.
4. 찾아낸 "핸들러 어댑터"를 사용해서 핸들러의 응답을 처리한다.
5. (부가적으로) 예외가 발생했다면, 예외 처리 핸들러에 요청 처리를 위임한다.
   - 핸들러의 리턴값을 보고 어떻게 처리할지 판단한다.
     - 뷰 이름에 해당하는 뷰를 찾어서 모델 데이터를 랜더링한다.
     - @ResponseEntity 가 있다면 Converter 를 사용해서 응답 본문을 만들고,
6. 최종적으로 응답을 보낸다.


### 스프링 MVC 구성 요소

DispatcherServlet 의 기본 전략
  - DispatcherServlet.properties

MultipartResolver
  - 파일 업로드 요청 처리에 필요한 인터페이스
  - HttpServletRequest 를 MultipartHttpServletRequest 로 변환해주어 요청이 담고 있는 File 을 꺼낼 수 있는 API 제공

LocaleResolver
  - 클라이언트의 위치(Locale) 정보를 파악하는 인터페이스
  - 기본 전략은 요청의 accept-language Header 를 보고 판단.

ThemeResolver
  - 애플리케이션에 설정된 테마를 파악하고 변경할 수 있는 인터페이스
  - 참고 : https://memorynotfound.com/spring-mvc-theme-switcher-example/

HandlerMapping
  - 요청을 처리할 핸드러를 찾는 인터페이스
  - 보통 annotation 기반 RequestMappingHandlerMapping 사용 DispatcherServlet 의 default 전략에 있음.

HandlerAdapter
  - HandlerMapping 이 찾아낸 "핸들러"를 처리하는 인터페이스
  - 스프링 MVC 확장력의 핵심
  - 보통 annotation 기반 RequestMappingHandlerAdapter 사용 DispatcherServlet 의 default 전략에 있음.

HandlerExceptionResolvers
  - 요청 처리 중에 발생한 에러 처리하는 인터페이스
  - 보통 @ExceptionHandler 를 통해 에러 처리

RequestToViewNameTranslator
  - 핸들러에서 뷰 이름을 명시적으로 리턴하지 않은 경우, 요청을 기반으로 뷰 이름을 판단하는 인터페이스
  - 뷰 이름이 없는 경우... 요청을 기반으로 판단
    - /sample 로 들어왔으니 sample 이겠거니..

ViewResolver
  - 뷰 이름(string)에 해당하는 뷰를 찾아내는 인터페이스

FlashMapManager
  - FlashMap 인스턴스를 가져오고 저장하는 인터페이스
  - FlashMap 은 주로 리다이렉션을 사용할 때 요청 매개변수를 사용하지 않고 데이터를 전달하고 정리할 때 사용한다.
    - 보통 redirect:/events?id=100 파라미터로 데이터를 전달
    - Spring FlashMap -> redirect:/events 매개변수 사용하지 않는다.

  
### 스프링 MVC 동작원리 정리
결국엔 (굉장히 복잡한) Servlet
= DispatcherServlet

DispatcherServlet 초기화
  1. 특정 타입에 해당하는 빈을 찾는다.
  2. 없으면 기본 전략을 사용한다. ( DispatcherServelt.properties)

스프링 부트 사용하지 않는 스프링 MVC
  - 서블릿 컨테이너(ex, tomcat) 에 등록한 웹 애플리케이션(WAR)에 DispatcherServlet을 등록한다.
    - web.xml 에 서블릿 등록
    - 혹은 WebApplicationInitializer 에 자바 코드로 서블릿 등록 ( 스프링 3.1+, 서블릿 3.0+)
  - 세부 구성 요소는 빈 설정하기 나름.

스프링 부트로 사용하는 스프링 MVC
  - 자바 애플리케이션에 내장 톰캣을 만들고 그 안에 DispatcherServlet 을 등록한다.
    - 스프링 부트 자동 설정이 자동으로 해줌
  - 스프링 부트의 주관에 따라 여러 인터페이스 구현체를 빈으로 등록한다.


