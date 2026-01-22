# PC_CAFE_KIOSK

## 📌 프로젝트 개요
PC방 사용자를 위한 **먹거리 주문 키오스크**와  
관리자를 위한 **실시간 좌석·주문 관리 시스템**을 제공하는 프로젝트입니다.  

Spring Boot와 React를 활용하여  
현대적인 PC방 운영 환경을 구축하는 것을 목표로 합니다.

> This project provides a food ordering kiosk for PC users and  
> a real-time seat and order management system for administrators.  
> It aims to build a modern PC cafe operation environment using  
> Spring Boot and React.

---

## 🛠 Tech Stack (기술 스택)

### 🔙 Backend
- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA** (ORM)
- **MySQL** (Relational Database)
- **Lombok** (Code Simplification)

### 🔜 Frontend
- **React.js**
- **Axios** (API Communication)
- **React Router** (Navigation & Routing)
- **CSS3** (Custom Dark-themed UI)

---

## ✨ Key Features (주요 기능)

### ✅ Current Progress (구현된 기능)

#### 🔐 User Authentication (사용자 인증)
- Login / Join 기능
- Role 기반 접근 제어 (Admin / User)

#### 💺 Dynamic Seat Occupancy (실시간 좌석 점유)
- 특정 좌석에서 로그인 시 좌석 상태 자동 변경
- 좌석 상태: `EMPTY` / `USING`

#### 🍜 Food Ordering System (먹거리 주문 시스템)
- 카테고리 기반 메뉴 조회
- 장바구니 담기 및 총 금액 계산
- 좌석 번호와 연동된 주문 처리

#### 🗄 Database Integration (DB 연동)
- Users, Seats, Categories, Products, Orders 간 관계 매핑
- JPA 기반 엔티티 설계 및 연관관계 구성

---

### 🚧 Planned Features (개발 예정)

#### 🧑‍💼 Admin Dashboard (관리자 대시보드)
- 실시간 주문 현황 확인
- 좌석 상태 모니터링

#### 💳 Payment Integration (결제 시스템)
- 먹거리 주문 결제
- 좌석 이용 시간 충전

#### 📦 Inventory Management (재고 관리)
- 주문 완료 시 재고 자동 차감

---

## 🗂 Database Schema (데이터베이스 구조)

- **Users**
  - 회원 정보
  - 잔여 이용 시간

- **Seats**
  - 좌석 상태 (`EMPTY / USING`)
  - 좌석-사용자 매핑

- **Categories / Products**
  - 메뉴 카테고리 관리
  - 상품 정보 관리

- **Orders / OrderItems**
  - 좌석별 주문 관리
  - 주문 상세 항목 관리

---

## 🚀 Project Goal
- PC방 운영의 자동화 및 효율성 향상
- 실시간 데이터 기반 좌석·주문 관리
- 확장 가능한 키오스크/관리자 시스템 구현
