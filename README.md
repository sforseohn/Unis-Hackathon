# 📱 문어할매상담소 - AI 기반 심리 상담 기록 서비스

### 2023년 여름 UNIS 해커톤에서 문어할매손녀팀이 개발한 프로젝트입니다.

---

## 프로젝트 소개

**문어할매상담소**는 주체적인 문제 해결 능력 향상을 위해 AI 기반 심리 상담 기록 서비스를 제공하는 웹 애플리케이션입니다. 사용자는 AI 챗봇을 통해 고민 상담을 받고, 맞춤형 컨텐츠를 추천받으며, 성장 기록을 통해 자신만의 데이터를 구축할 수 있습니다.

---

### 주요 기능

- **AI 챗봇 상담**: 언제 어디서나 AI를 활용해 합리적인 비용으로 심리 상담이 가능합니다.
- **해결 중심 상담 기법**: 사용자가 스스로 문제의 답을 찾아가도록 돕는 질문지 제공.
- **AI 기반 분석결과**: 감정 및 상황 분석, 대안 제시 등을 통해 문제 해결 의지를 향상시킵니다.
- **성장 기록**: 상담 후에도 실천 과정과 해결 과정을 기록할 수 있는 기능 제공.
- **맞춤형 콘텐츠 추천**: AI가 추천하는 책, 유튜브, 음악 등을 통해 다양한 해결책을 모색할 수 있습니다.

---

### 프로젝트 상태

프로젝트는 **완료**되었으며, 최종 결과물은 아래의 PDF에서 확인할 수 있습니다.

[문어할매상담소_문어할매손녀팀(최종).pdf](https://github.com/user-attachments/files/16322949/_.pdf)

---

### 🛠️ 기술 스택

- **프론트엔드**: ![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black) ![Vanilla JS](https://img.shields.io/badge/Vanilla%20JS-000000?style=for-the-badge&logo=javascript&logoColor=white) ![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white) ![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white)
- **백엔드**: ![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white) ![Lombok](https://img.shields.io/badge/Lombok-DC382D?style=for-the-badge&logo=lombok&logoColor=white)
- **데이터베이스**: ![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
- **디자인**: ![Figma](https://img.shields.io/badge/Figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white)
- **AI 서비스**: ![ChatGPT](https://img.shields.io/badge/ChatGPT-00A67E?style=for-the-badge&logo=openai&logoColor=white)
- **API**: ![YouTube API](https://img.shields.io/badge/YouTube%20API-FF0000?style=for-the-badge&logo=youtube&logoColor=white)
- **개발 도구**: ![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-000000?style=for-the-badge&logo=intellij-idea&logoColor=white)

---

### User Flow

![Main Function 1](https://github.com/sforseohn/Unis-Hackathon/blob/main/images/main%20function1.png)
![Main Function 2](https://github.com/sforseohn/Unis-Hackathon/blob/main/images/main%20function2.png)
![Main Function 3](https://github.com/sforseohn/Unis-Hackathon/blob/main/images/main%20function3.png)
![Main Function 4](https://github.com/sforseohn/Unis-Hackathon/blob/main/images/main%20function4.png)
![Main Function 5](https://github.com/sforseohn/Unis-Hackathon/blob/main/images/main%20function5.png)
![Competitiveness Analysis](https://github.com/sforseohn/Unis-Hackathon/blob/main/images/%EA%B2%BD%EC%9F%81%EB%A0%A5%ED%99%95%EB%B3%B4%EB%B0%A9%EC%95%88.png)
![Market Analysis](https://github.com/sforseohn/Unis-Hackathon/blob/main/images/%EC%8B%9C%EC%9E%A5%EB%B6%84%EC%84%9D.png)

---

### 프로젝트 구조

```bash
├── main
│   ├── java/com/ttt/InsightAI
│   │   ├── controller
│   │   │   ├── DiaryAnalysisController.java
│   │   │   ├── PresentController.java
│   │   │   ├── UserController.java
│   │   │   └── ViewController.java
│   │   ├── domain
│   │   │   ├── Analysis.java
│   │   │   ├── AnswerRequest.java
│   │   │   ├── Diary.java
│   │   │   ├── Present.java
│   │   │   └── User.java
│   │   ├── repository
│   │   │   ├── AnalysisRepository.java
│   │   │   ├── DiaryRepository.java
│   │   │   ├── PresentRepository.java
│   │   │   └── UserRepository.java
│   │   ├── service
│   │   │   ├── OpenAiService.java
│   │   │   ├── PresentService.java
│   │   │   ├── YoutubeService.java
│   │   │   └── InsightAiApplication.java
│   └── resources
│       ├── static
│       ├── templates
│       └── application.properties
├── test/java/com/ttt/InsightAI
│   └── InsightAiApplicationTests.java
├── .gitignore
├── README.md
├── build.gradle
├── gradlew
├── gradlew.bat
└── settings.gradle
```

---

### 📝 **기여 방법**

현재 이 프로젝트는 해커톤을 위한 단기 프로젝트로 외부 기여는 받지 않습니다. 프로젝트에 대한 피드백은 언제든지 환영합니다!


### 📋 **라이선스**
이 프로젝트는 해커톤을 위해 제작된 비공개 프로젝트입니다.
