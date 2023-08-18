# searchplace
1. 프로젝트 설명
1) searchplace(https://github.com/sohyung123/searchplace.git)
: 장소 검색 api
사용자가 호출하는 api는 모두 searchplace 프로젝트 안에 있습니다. 검색 키워드 목록 api도 실제 계산은 keyword 프로젝트 내에서 수행하지만, 사용자가 접근가능한 api는 searchplace로 통일하였습니다.

2) keyword(https://github.com/sohyung123/keyword.git)
: 검색 키워드 목록 api
searchplace 프로젝트에서 호출하는 api가 작성되어 있어, 해당 프로젝트도 로컬 테스트를 위해 8081 포트에서 실행합니다.
검색 키워드 목록 탑 10을 추출하기 위해, kakao나 naver의 검색 api에 키워드 count와 관련된 response값이 없어, 
장소 검색 api에서 호출한 keyword들을 H2 DB에 저장후, 해당 DB에서 상위 10개 리스트를 추출하는 방식으로 검색 키워드 목록 api를 구성하였습니다.

프로젝트 설정은 아래와 같습니다. 원활한 테스트를 위해 두개의 서버가 모두 기동되어야 하며, keyword 서버는 8081포트에서 실행되도록 application.yml파일에 명시하였습니다.
- springboot 버전 : 3.0.9
- jdk : 17
- maven 사용

2. 테스트 전 사전 설정은두개의 프로젝트 모두 인메모리인 H2 DB를 사용하고 있으므로, 아래와 같은 사전 설정이 되어 있어야 테스트가 가능합니다.
1) H2 DB 다운로드(https://www.h2database.com/html/main.html) 

2) H2 Console 앱 실행

3) JDBC URL : jdbc:h2:tcp://localhost/~/test

4) keyword 테이블 생성
create table keyword (id bigint generated by default as identity, keyword_name varchar(50), count integer, crt_tm timestamp, chg_tm timestamp);

5) 검색 미워드 목록 api 작동 확인을 위한 샘플 데이터 insert
insert into keyword(keyword_name,count,crt_tm,chg_tm) values ('동작구',20,'2023-08-15 12:00:00','2023-08-15 12:00:00'); 
insert into keyword(keyword_name,count,crt_tm,chg_tm) values ('강남구',1000,'2023-08-15 12:00:00','2023-08-15 12:00:00'); 
insert into keyword(keyword_name,count,crt_tm,chg_tm) values ('수서동',55,'2023-08-15 12:00:00','2023-08-15 12:00:00'); 
insert into keyword(keyword_name,count,crt_tm,chg_tm) values ('일원동',66,'2023-08-15 12:00:00','2023-08-15 12:00:00'); 
insert into keyword(keyword_name,count,crt_tm,chg_tm) values ('영등포',35,'2023-08-15 12:00:00','2023-08-15 12:00:00'); 
insert into keyword(keyword_name,count,crt_tm,chg_tm) values ('잠실역',30,'2023-08-15 12:00:00','2023-08-15 12:00:00'); 
insert into keyword(keyword_name,count,crt_tm,chg_tm) values ('롯데타워',100,'2023-08-15 12:00:00','2023-08-15 12:00:00'); 
insert into keyword(keyword_name,count,crt_tm,chg_tm) values ('신사동',10,'2023-08-15 12:00:00','2023-08-15 12:00:00'); 
insert into keyword(keyword_name,count,crt_tm,chg_tm) values ('논현동',10,'2023-08-15 12:00:00','2023-08-15 12:00:00'); 
insert into keyword(keyword_name,count,crt_tm,chg_tm) values ('은행',400,'2023-08-15 12:00:00','2023-08-15 12:00:00'); 
insert into keyword(keyword_name,count,crt_tm,chg_tm) values ('곱창',200,'2023-08-15 12:00:00','2023-08-15 12:00:00'); 
insert into keyword(keyword_name,count,crt_tm,chg_tm) values ('맛집',5000,'2023-08-15 12:00:00','2023-08-15 12:00:00'); 
insert into keyword(keyword_name,count,crt_tm,chg_tm) values ('황소곱창',10,'2023-08-15 12:00:00','2023-08-15 12:00:00'); 
insert into keyword(keyword_name,count,crt_tm,chg_tm) values ('돼지고기',90,'2023-08-15 12:00:00','2023-08-15 12:00:00'); 
insert into keyword(keyword_name,count,crt_tm,chg_tm) values ('횟집',10,'2023-08-15 12:00:00','2023-08-15 12:00:00'); 
insert into keyword(keyword_name,count,crt_tm,chg_tm) values ('소고기',400,'2023-08-15 12:00:00','2023-08-15 12:00:00'); 
insert into keyword(keyword_name,count,crt_tm,chg_tm) values ('치킨',10000,'2023-08-15 12:00:00','2023-08-15 12:00:00'); 
insert into keyword(keyword_name,count,crt_tm,chg_tm) values ('공원',89,'2023-08-15 12:00:00','2023-08-15 12:10:00'); 
insert into keyword(keyword_name,count,crt_tm,chg_tm) values ('한강공원',500,'2023-08-15 12:00:00','2023-08-15 12:00:00'); 
insert into keyword(keyword_name,count,crt_tm,chg_tm) values ('주차장',1000,'2023-08-15 12:00:00','2023-08-15 12:00:00');
commit;

3. api 테스트 방법
1) 장소 검색 api
curl -X GET -i localhost:8080/v1/place?query=%EB%8F%99%EC%9E%91%EA%B5%AC

2) 검색 키워드 목록 api
curl -X GET -i localhost:8080/v1/keyword

*** 그 외에 Circuit 관련 api들은 아래 url에서 테스트 하실수 있습니다.
localhost:8080/swagger-ui/index.html


4. searchplace 프로젝트 코드 설명
1) EventListenerAsyncConfig : 
- SaveKeywordService의 saveKeywordData 메서드에서 사용하기 위한 비동기 설정입니다.
- SearchPlaceService의 keywordSearch 메서드가 장소 검색 결과를 return하는 핵심 기능인데, 일시적인 DB 장애 상황을 가정하여
DB에 특정 키워드의 count를 늘리는 작업이 실패하더라도, 장소 검색 결과는 정상적으로 사용자에게 return할 수 있도록, ApplicationEventPublisher를 통해 event를 발생시켜,
비동기로 SaveKeywordService의 saveKeywordData 메서드를 실행할수 있도록 설계하였습니다.

2) FeignConfig :
- kakao, naver의 검색 api 장애상황에 대응하기 위한 config 파일입니다.
- retryer를 통해 feign으로 http통신이 실패했을경우에 retry를 보낼 수 있으며, 5xx 서버 에러한정에서만 retry를 보낼수 있도록 CustomServerErrorDecoder라는 파일에 별도로 추가 설정했습니다.
- circuitBreakerNameResolver를 통해 circuit의 네이밍룰을 지정하였습니다. circuit은 feign으로 http 통신이 실패했을 경우에, searchplace 프로젝트 전체에 장애가 퍼지는 것을 막기 위함입니다.
circuit 관련 config 설정들은 application.yml의 resilience4j 하단에 명시하였습니다.

3) JasyptConfig :
- application.yml에 kakao, naver 검색 api를 사용할수 있도록 url, 헤더 정보들이 설정되어 있는데, 이 중에 헤더와 관련된 개인정보들을 암호화하는 config입니다.

4) CustomServerErrorDecoder :
- 5xx번대 서버 에러만 인지하여, Retry를 보낼수 있도록 설정하는 파일입니다.

5) RecordFailurePredicate :
- feign circuit 기능 사용시, 어떤 Exception 유형이 발생했을때 circuit을 자동으로 open(http요청 차단)할 것인지를 정의하는 파일입니다.

6) KakaoFeignClient : 
- kakao 검색 api data를 가져오는 Feignclient 입니다.
- naver 검색 api 장애시, kakao의 결과만이라도 return하기 위하여 Fallback 클래스를 내부에 구현하여 null값을 return하도록 구현했습니다.

7) NaverFeignClient : 
- naver 검색 api data를 가져오는 Feignclient 입니다.
- kakao 검색 api 장애시, naver의 결과만이라도 return하기 위하여 Fallback 클래스를 내부에 구현하여 null값을 return하도록 구현했습니다.

8) KeywordRepository : H2 DB / springdata jpa 사용을 위한 Repository 설정입니다.

9) Keyword : H2 DB / springdata jpa 사용을 위한 Entity 파일입니다.

10) Dto 중 일부 설명
- 실제 api 호출시 사용하는 Dto는 ParameterDto 하나뿐입니다.
- kakao 검색 api의 Response는 KakaoResponseDto
- Naver 검색 api의 Response는 NaverResponseDto
- 그 외에 나머지 Dto는 내부에서 데이터 전달을 위해 사용하는 Dto들입니다.

11) SearchPlaceController : 
- searchPlace : 장소 검색 api
- getKeywordList : 검색 키워드 목록 api
- closeCircuit: Circuit 수동 close api
- openCircuit : Circuit 수동 open api
- circuitStatus : 특정 Circuit의 상태 조회 api
- allCircuit : 전체 Circuit의 상태를 조회하는 api
- 그 외에 FeignException 처리를 위한 @ExceptionHandler가 구현되어있습니다.

12) KakaoDataService : 
- KakaoFeignClient를 호출하여 데이터를 가져오는 서비스입니다. 반응성 향상을 위해 캐시처리 되어있습니다.

13) NaverDataService : 
- NaverFeignClient를 호출하여 데이터를 가져오는 서비스입니다. 반응성 향상을 위해 캐시처리 되어있습니다.

14) KeywordListService : 
- KeywordListFeignClient를 호출하여, keyword 프로젝트(8081포트)의 api를 사용해서 keywordList를 가져온느 서비스입니다.

15) SaveKeywordService : 
- SearchPlaceService 내에서 보낸 event를 전달받아 수행하며, 특정 키워드가 호출될때마다 count를 1씩 증가하여 db에 조회,저장합니다.

16) SearchPlaceService :
- kakao, naver 검색 api 데이터를 가져와서 중복값 처리하는 로직이 들어가있는 메인 service입니다.
- kakao 장애시 naver의 결과만이라도 return하며, 반대로 naver 장애시 kakao의 결과만이라도 return합니다.
- Feign 관련 캐시를 1시간마다 스케쥴로 삭제하는 deleteCache() 메서드도 구현되어 있습니다.

5. keyword 프로젝트 코드 설명 
1) Keyword(entity), KeywordRepository, KeywordListDto 모두 searchplace 프로젝트와 동일하게, 키워드 리스트틑 db에서 가져오고자 하는 용도의 코드입니다.

2) KeywordListController :
- searchplace에서 KeywordListService 서비스를 통해 키워드 리스트를 가져가는 api입니다.

3) KeywordListService :
- 최대 10개의 리스트를 count가 큰 순서대로 뽑도록 로직이 구성되어 있습니다.
- 동시성을 고려하여 CopyOnWriteArrayList, AtomicInteger를 사용하였습니다.


6. 구현시 고려한 기술사항
- 프로그램의 지속적 유지 보수 및 확장에 용이한 아키텍처에 대한 설계
: searchplace 프로젝트에서, 각 서비스별로 하나의 메인 기능을 담당하게끔, FeignClient와 Service들을 각각 분리하여 구현하였습니다.
이를 통해 코드 유지 보수및 코드 확장시 타 서비스에 영향을 최소화하도록 구현했습니다. 

- 동시성 이슈가 발생할 수 있는 부분을 염두에 둔 설계 및 구현 (예시. 키워드 별로 검색된 횟수)
: 키워드 별로 검색된 횟수를 계산할때, 멀티 스레드 환경에 따른 동시성 이슈를 고려하여, java.util.concurrent 패키지 내의 자료구조(CopyOnWriteArrayList)와,
count 계산시 AtomicInteger를 사용하여, 별도의 synchronized를 사용하지 않아도 thread-safe하도록 환경을 구성했습니다.

- 카카오, 네이버 등 검색 API 제공자의 “다양한” 장애 및 연동 오류 발생 상황에 대한 고려 
: 첫번째로 searchplace 프로젝트에서 FeignConfig 파일과, CustomServerErrorDecoder 파일을 통해, 5xx 서버 장애시 최대 5회 retry 하도록 구현했습니다.
Service 단에서 kakao, nvaer의 검색 api를 호출하는 부분에 장애가 발생한다면, 전체 서비스로 장애가 번질 우려가 있습니다.
이에 두번째로 Circuitbreaker 개념을 도입하여, kakao 혹은 naver의 api 장애시, circuit을 자동으로 open하여 문제가 되는 api를 차단하고, 장소 검색 api는 정상적으로 수행되도록 구현하였습니다.
Circuitbreaker는 FeignConfig, RecordFailurePredicate, application.yml에 설정값을 설정하여, SearchPlaceController상에서도 수동으로 api를 통해 테스트를 할수 있게 구현했습니다.

- 대용량 트래픽 처리를 위한 반응성(Low Latency), 확장성(Scalability), 가용성(Availability)을 높이기 위한 고려
: 먼저 확장성 측면을 고려하여, searchplace와 keyword로 프로젝트 자체를 분리하여, 각각 서버를 기동해야하는 구조로 설계했습니다.
searchplace가 일종의 gateway역할을 하고, 사용자들은 모두 searchplace의 api로만 접근하게 하고, keyword 리스트 조회 필요시에는 keyword 프로젝트로 http통신을 보내는 구조로 설계했습니다.
이와 같은 마이크로서비스아키텍쳐로 구현 후, cloud 환경에 서비스를 구축하게 되면, 확장성을 높일 수 있습니다.
예를 들어 장소검색 api가 검색 키워드 목록 api보다 더 많이 사용된다면, searchplace 프로젝트에 리소스를 더 많이 할당하여 유연하게 확장성을 높일수 있을것입니다.

반응성 측면에서는, kakao와 naver 검색 api를 동일 키워드로 여러번 검색했을시에 값이 크게 달라지지않은 현상을 확인하여, searchplace 프로젝트내에
KakaoDataService와 NaverDataService에 cache 처리를 하여 반응성을 향상시켰습니다.

가용성 측면에서는, kakao와 naver의 검색 api가 동시에 장애가 발생할 확률은 거의 없으므로, CircuitBreaker를 통해 장애가 발생한 api를 차단하여
장소 검색 api는 항상 가용할 수 있도록 설계하여 가용성을 향상시켰습니다.

- 테스트 코드를 통한 프로그램 검증 및 테스트 용이성(Testability)을 높이기 위한 코드 설계
: 핵심 기능별로 모든 서비스를 각각 하나씩 분리하여 구현하였습니다. @SpringbootTest를 통해 통합테스트틀 해야하는 항목도 있지만,
이렇게 기능별로 서비스를 별도로 구현하게 되면 의존성이 크지 않아 단위 테스트가 가능합니다.
예를 들어 searchplace 프로젝트내에 SaveKeywordServiceTest 코드를 참고하면, mockito를 사용하여 단위 테스트를 수행하여 테스트 용이성을 높였습니다. 
- 구글 장소 검색 등 새로운 검색 API 제공자의 추가 시 변경 영역 최소화에 대한 고려
: 검색 api가 추가되면 기존에 kakao와 naver 관련 서비스들은 수정할 코드가 없도록 서비스를 각각 분리하였습니다.
구글 api가 추가되면, dto와 GoogleFeignClient, GoogleDataService만 추가하면 됩니다.
물론 Keyword들의 중복을 뽑아, 중복 키워드를 우선적으로 배치하는 등의 로직이 들어간 SearchPlaceService의 수정은 불가피하지만, 그 외에는 영향도를 최소화했습니다.
Interface 서비스를 두고 kakao, naver, google 각각 구현체를 두는 방식도 고려하였으나,
구글 api 추가시 구글 관련 서비스가 추가되어야 하는 상황은 동일하고, IDE사용시 구현체를 두는 방식보다 직접 Service에 접근하는 방식이 편한것 같아 위와 같이 구성했습니다.
