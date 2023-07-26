![tc3](https://github.com/god-kao-talk/.github/assets/54833128/c0ccb62e-4940-40e9-9ffd-60326cce5a89)

# this.code 👩‍💻
### 대규모 트래픽 처리가 가능한 실시간 채팅 서비스 💬
- 현대 사회에 필수불가결한 메신저 어플의 대용량 데이터 발생과 이로 인한 부하를 견뎌내기 위해 필요한 대책을 직접 리서치 및 적용
- 매번 업그레이드 한 버전 별로 테스트를 진행하고 결과를 수치화, 시각화하여 개선 과정 신뢰도 향상

---
## 프로젝트 목표
### 1. 초당 5000건의 동시 트래픽을 감당하는 채팅 서비스 ⚡
- 채팅 메세지 전송/수신 max 1000ms
- 채팅 메세지 영구 저장
- 실시간 서버 모니터링
- 스케일 아웃 가능한 서버

### 2. 스파이크 테스트로 안정성 있는 트래픽 관리 📈
- 단계별로 아래의 테스트 조건들을 상향시키면서 에러율과 최대지연시간의 결과와 원인을 분석하며 성능 개선
    - 동시간대 접속 유저 : 100~5000명
    - 초당 보내는 채팅 수 : 100~5000개
    - 분당 보내는 총 채팅 수 : 6000~300,000개
    - 응답 시간 제한 : 1000ms, 2000ms

### 3. 실시간으로 누적되는 데이터 처리 💾
- 분당 최대 300000건의 데이터들을 수용하기 위한 DB 성능 개선
- 경제적이고 효율적인 DB선정을 위한 데이터베이스 시스템 분석
    - MySQL
    - MongoDB
    - Cassandra

### 4. 채팅 검색 기능 🔎
- 검색 성능 개선

---
## 영상 📽️
- [최종 발표 영상](https://www.youtube.com/watch?v=T1Iw6dhlZkQ)
- [간단 홍보 영상](https://www.youtube.com/watch?v=yDGTc6K40o4)
- 핵심 기능 시연 영상
  
|채팅방 추가|채팅 기능|
|---|---|
|<img src="https://github.com/god-kao-talk/.github/assets/54833128/1805e65e-8f72-44de-87ae-0511d7631989" alt="채팅방 추가" width="450" height="450">|<img src="https://github.com/god-kao-talk/.github/assets/54833128/9e418882-f6d9-479c-9769-1dc782e96fe3" alt="채팅 내용" width="450" height="450">|

- 부하 테스트 시연 영상
  
|1. 도커 스웜을 통한 클러스터 환경 구축|2. JMeter를 통한 부하 테스트 시작|
|---|---|
|<img src="https://github.com/god-kao-talk/.github/assets/54833128/bde608c9-9f2b-4ab7-bcbb-50c5bc921539" alt="도커 스웜" width="450" height="450">|<img src="https://github.com/god-kao-talk/.github/assets/54833128/ba7db99e-0165-48f8-b777-a730f1461a70" alt="부하 테스트 시작" width="450" height="450">|
|3. 도커 컨테이너 리소스 사용률 및 서버 로그 확인|4. 그라파나를 통한 서버 모니터링|
|<img src="https://github.com/god-kao-talk/.github/assets/54833128/a6a10f6d-6ddd-478f-bbfe-240958e4b59e" alt="서버 로그" width="450" height="450">|<img src="https://github.com/god-kao-talk/.github/assets/54833128/3f2903c2-53f6-4032-8045-a16a6461b3bc" alt="서버 모니터링" width="450" height="450">|
|5. db 모니터링|6. 테스트를 통해 나온 지표 확인 및 분석|
|<img src="https://github.com/god-kao-talk/.github/assets/54833128/3f23b817-f016-4f59-a5fc-74f9dd4f1f74" alt="db 모니터링" width="450" height="450">|<img src="https://github.com/god-kao-talk/.github/assets/54833128/6c86c920-8a0a-4e45-9199-e11858703373" alt="테스트 결과 확인" width="450" height="450">|


---
## 서비스 아키텍처 ⚙️
![서비스 아키텍처](https://github.com/god-kao-talk/.github/assets/54833128/930aa88d-07ea-47e7-9ec3-9602f52ae4fc)
### 활용 기술 / 기술적 의사 결정 ⚒️

|요구사항|선택지|기술 선택 이유|
|---|---|---|
|🛢️ 데이터 베이스|ver1. MySQL, <br> ver2. MongoDB, <br> ver3. Cassandra|버전 별 성능테스트 결과와 IOPS와 Billing 측면에서 우위를 가진 MongoDB 최종 선택|
|📈 부하 테스트|Jmeter, Ngrinder|소켓 통신 테스트를 위한 시나리오 작성 가능|
|📊 모니터링|Grafana, Prometheus, kibana|- Grafana : 시스템 관점에서 CPU 메모리, 디스크 IO 사용율과 같은 지표를 시각화 하는데 특화 <br> - Kibana : 엘라스틱 위에서 쿼리 로그 분석에 특화 <br> → 채팅 시스템에서 트래픽 지표를 분석하기 위해 Grafana 선택|
|🛠️ 데이터 파이프라인|Kafka, Redis|- Redis : 휘발성 <br> - kafka : 트랜잭션을 줄이고 비동기적으로 데이터베이스에 저장할 수 있고 정합성을 보장 <br> → 휘발성이 있는 Redis는 신뢰도가 중요한 채팅 서비스와 적합하지 않다고 판단, kafka 선택|
|🗂️ 클러스터링|Docker Swarm, Kubernetes|컨테이너 클러스터링, 로드밸런싱 기능에 집중 <br> - 중소 규모의 클러스터에서 컨테이너 기반 애플리케이션 구동을 제어하기에 충분한 기능을 제공 <br> - 도커 엔진이 설치된 환경이라면 별도의 구축 비용 없이 컨테이너 오케스트레이션 환경 구축 가능 <br> - Kubernetes의 경우 master node의 최소 요구 사양이 CPU 2, RAM 2GB, 현 프로젝트에 오버 스펙이라고 판단 <br> → Docker Swarm 선택|
|🔍 검색 성능 개선|Elasticsearch, <br> MongoDB Index, <br> QueryDSL|대용량의 데이터 속에서 채팅 메시지를 찾아야 함에 집중 <br> - 역 인덱스를 이용해 데이터를 관리하기 때문에 모든 데이터를 탐색하지 않고도 결과를 찾을 수 있음 <br> - 데이터의 규모가 커질수록 찾고자 하는 메시지의 데이터 위치를 알고 있는 것은 성능 최적화를 가능케 함|
|⚙️ CI/CD|Github Action, Jenkins|Jenkins: 별도의 서버를 구축해야하며, 계정과 트리거에 기반하고 있으며 GitHub 이벤트를 처리할 수없다. <br> Git Action: 클라우드에서 동작하므로 어떤 설치도 필요 없다. 모든 GitHub 이벤트에 대해 GitHub Actions를 제공하고 있다. GitHub에 push, PR 이벤트가 발생할 때 자동 테스트, 배포가 쉽게 이루어지기 때문에 개발에 몰두할 수 있음 <br> -> Github Action 선택|
|🚀 소켓 통신|Web socket|- 서버가 클라이언트에게 비동기 메시지를 보낼 때 가장 널리 사용하는 기술 <br> - 양방향 메시지 전송까지 가능|


---
## ERD, 유저 플로우 🏄
<details>
    <summary>ERD 펼쳐보기</summary>
    <img src="https://github.com/god-kao-talk/.github/assets/54833128/56a5afca-7256-47c2-9ad4-0e300ff74426" alt="erd">
</details>

<details>
    <summary>유저 플로우 펼쳐보기</summary>
    <img src="https://github.com/god-kao-talk/.github/assets/54833128/3feef34e-e2e5-48b0-b689-0a7e6224c4b0" alt="user flow">
</details>

---
## 부하 테스트 및 성능 개선 🔥
- [🐬version 0.1](https://www.notion.so/version-0-1-a5d33fa6a17247498c25f3d79f8d02f2)
- [🐒version 0.2](https://www.notion.so/version-0-2-b8a2c77900f54824a71378bc704e6445)
- [🐅version 0.3](https://www.notion.so/version-0-3-afadc459105944a9b1a2b13c61cf621a)
- [❌version 0.x](https://www.notion.so/version-0-x-c9983b7397724ff9bba241088714d53d)
- [최종 성능 개선 결과](https://www.notion.so/dca6e10439e84264b390f12abbda9d93)
    - [부하 테스트 기록](https://docs.google.com/spreadsheets/d/1K3fgQ_T9y2-cGr0WNEFuMYYJ845qjKn5BfrGWD9_tHo/edit#gid=1540611111)

---
## 팀원 👨‍👩‍👦‍👦
|역할|이름|담당|github|
|---|---|---|---|
|공통| |- BE 채팅 서비스 구현 <br> - 부하 테스트 결과 분석 <br> - 아키텍처 및 데이터 플로우 개선 <br> - 서비스 문제점 파악| |
|팀장|김건|- FE 채팅 서비스 구현 및 개선 방향 제시 <br> - 코드 리팩토링 <br> - Docker 분석 및 환경 테스트 <br> - kafka를 통한 채팅 데이터 플로우 방향 제시 <br> - elasticsearch 기능 구현, 한글 형태소 분석기 추가 <br> - ES sink connector 구현|<a href="https://github.com/kimgun95"> <br> <img src="https://avatars.githubusercontent.com/u/54833128?v=4" alt="프로필 사진" width="100" height="100"> <br> </a>
|팀원|박권재|- JUnit 테스트 코드 구현 <br> - Jmeter 시나리오 구현 및 테스트 <br> - kafka 연결 구현 <br> - kafka connet 구현, mongoDB와 연결 <br> - cassandra 구현|<a href="https://github.com/rnjswo9578"> <br> <img src="https://avatars.githubusercontent.com/u/80087131?v=4" alt="프로필 사진1" width="100" height="100"> <br> </a>|
|팀원|이상언|- 코드 리팩토링 <br> - Jmeter 시나리오 구현 및 테스트 <br> - Grafana 구현 <br> - Prometheus, Spring actuator 구현 <br> - QueryDSL 구현|<a href="https://github.com/rnjswo9578"> <br> <img src="https://avatars.githubusercontent.com/u/87771474?v=4" alt="프로필 사진2" width="100" height="100"> <br> </a>|
|팀원|이태경|- kafka 구현 <br> - NoSQL분석, MongoDB 연결 <br> - Docker Swarm 구현 <br> - GitAction CI/CD 구현 <br> - Grafana, Prometheus 구현|<a href="https://github.com/taekk1a2a3a"> <br> <img src="https://avatars.githubusercontent.com/u/111736036?v=4" alt="프로필 사진3" width="100" height="100"> <br> </a>|

