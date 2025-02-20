# 📰 simple-sns

---

## 📚 Project Overview

Spring Boot와 JPA를 활용하여 간단한 SNS 기능을 구현한 프로젝트입니다.

- **목적**:
    - 데이터베이스 스키마 설계를 통해 전체 구조를 진행할 수 있다.
    - JPA를 이용하여 데이터베이스와 연동하고, CRUD 작업을 수행할 수 있다.
    - 사용자 인증과 인가의 기본 원리와 차이점을 이해하고 진행할 수 있다.
    - 기능에 맞는 REST API 설계를 하고, Spring Boot를 이용하여 REST API를 구현할 수 있다.
    - Git을 사용하여 소스 코드 버전 관리를 하고, Git branch를 이용하여 브랜치 관리 및 원화한 협업을 수행할 수 있다.
    - Pull Request와 코드 리뷰 과정에 대해 이해하고 적용할 수 있다.

---

## 📝 Table of Contents

1. [Project Structure](#-project-structure)
2. [ERD](#-erd)
3. [API Documentation](#-api-documentation)
4. [Wireframe](#-wireframe)
5. [Features](#-features)
6. [Tech Stack](#-tech-stack)
7. [Contact](#-contact)

---

## 🛠 Project Structure

```
src
└── main
    ├── java
    │   └── com
    │       └── example
    │           └── simplens
    │               ├── common
    │               │   ├── config
    │               │   ├── consts
    │               │   ├── dto
    │               │   └── filter
    │               ├── domain
    │               │   ├── comment
    │               │   ├── friend
    │               │   ├── post
    │               │   └── user
    │               └── exception
    │                   ├── code
    │                   ├── custom
    │                   ├── dto
    │                   └── handler
    ├── resources
    │   └── application.properties
    └── SimpleSnsApplication.java

build.gradle  
README.md
```

---

## 🖇️ ERD

![Image](https://github.com/user-attachments/assets/48a4b78f-51c0-4d24-821c-b39d238f3020)

---

## 📝 API Documentation

[🔗 API 명세서 바로가기](https://www.notion.so/teamsparta/API-1a02dc3ef514802ba1c2c3d73057e85b)

---

## 🖼️ Wireframe

![Image](https://github.com/user-attachments/assets/2703ab2e-009e-43d0-a615-48a453c80f1f)

![Image](https://github.com/user-attachments/assets/9c3dae7a-9483-4a37-8fd7-02d874f343e3)

![Image](https://github.com/user-attachments/assets/72b05c74-624f-4aff-86ec-978bee23e5c6)

![Image](https://github.com/user-attachments/assets/1a356e75-559c-4b72-9d43-447ea5c860fb)

![Image](https://github.com/user-attachments/assets/25816a78-e784-4691-a139-16ac2efcf2fa)

![Image](https://github.com/user-attachments/assets/e46511b7-f134-4916-b614-af7bfd1f740d)

![Image](https://github.com/user-attachments/assets/8a7c9a5a-80c4-4ef8-8add-6717e4c58cdd)

![Image](https://github.com/user-attachments/assets/385b1e70-5539-479b-8c19-aca0ead08fe3)

---

## ✨ Features

### **1. 프로필 관리**

- **프로필 조회 기능**
    - 다른 사용자의 프로필을 조회할 수 있습니다.
    - 조회 시 민감한 정보(`password`)는 표시되지 않습니다.
    - 회원 목록을 조회할 수 있습니다. (Pagination)
- **프로필 수정 기능**
    - 로그인한 사용자가 본인의 프로필을 수정할 수 있습니다.
- **비밀번호 수정 기능**
    - 로그인한 사용자 본인의 비밀번호만 수정할 수 있습니다.
    - 비밀번호 수정 시, 본인 확인을 위해 현재 비밀번호를 입력하여 올바른 경우에만 수정할 수 있습니다.
    - 현재 비밀번호와 동일한 비밀번호로는 변경할 수 없습니다.

### **2. 뉴스피드 게시물 관리**

- **게시물 작성, 조회, 수정, 삭제 기능**
    - 게시물 수정, 삭제는 작성자 본인만 처리할 수 있습니다.
- **뉴스피드 목록 조회 기능**
    - 기본 정렬은 생성일자 기준으로 내림차순 정렬합니다.
    - 10개씩 페이지네이션하여, 각 페이지 당 뉴스피드 데이터가 10개씩 나오게 합니다.

### **3. 사용자 인증**

- **회원가입 기능**
    - 사용자 아이디
        - 사용자 아이디는 이메일 형식이어야 합니다.
        - 이메일이 중복되면 가입할 수 없습니다.
    - 비밀번호
        - `Bcrypt`로 인코딩합니다.
        - 비밀번호는 최소 5글자 이상, 15글자 이하입니다.
- **회원탈퇴 기능**
    - 탈퇴 처리 시 비밀번호를 확인한 후 일치할 때 탈퇴 처리합니다.
    - 탈퇴한 사용자의 아이디는 재사용할 수 없습니다.
    - 회원 탈퇴시 유저 데이터는 논리삭제(Soft Delete)를 적용합니다.
- **로그인/로그아웃 기능**
    - 사용자 이메일과 비밀번호로 로그인합니다.

### **4. 친구 관리**

- **친구 요청/삭제 기능**
    - 특정 사용자를 친구로 요청/삭제할 수 있습니다.
- **친구 목록 조회 기능**
    - 친구 요청을 받은 사용자가 친구 요청 목록을 조회할 수 있습니다.
    - 친구 요청을 수락하면, 친구 목록 조회에 친구가 된 사용자를 조회할 수 있습니다.
- **친구 뉴스피드 게시물 조회 기능**
    - 친구의 최신 게시물들을 최신순으로 볼 수 있습니다. (Pagination)

### **5. 댓글**

- **댓글 작성, 조회, 수정, 삭제 기능**
    - 사용자는 게시물에 댓글을 작성할 수 있고, 본인의 댓글은 수정 및 삭제를 할 수 있습니다.
- **댓글 목록 조회 기능**
    - 특정 게시물에 달린 댓글 목록을 조회할 수 있습니다. (Pagination)

### **6. 좋아요**

- **게시물 및 댓글 좋아요 / 좋아요 취소 기능**
    - 사용자가 게시물이나 댓글에 좋아요를 남기거나 취소할 수 있습니다.
    - 본인이 작성한 게시물과 댓글에 좋아요를 남길 수 없습니다.
    - 같은 게시물에는 사용자당 한 번만 좋아요가 가능합니다.

---

## 🧑‍💻 Tech Stack

- **Language**: Java
- **Framework**: Spring Boot
- **Database**: MySQL
- **ORM**: JPA
- **Version Control**: GitHub

---

## 📞 Contact
Created by <br>
[@daylikezero](https://github.com/daylikezero)<br>
[@mannaKim](https://github.com/mannaKim)<br>
[@2024122601](https://github.com/2024122601)<br>
[@Park-EJ](https://github.com/Park-EJ)<br>
[@wjdgus2319](https://github.com/wjdgus2319)
