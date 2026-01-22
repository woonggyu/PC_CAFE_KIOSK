# PC_CAFE_KIOSK
#프로젝트 개요
# 프로젝트는 PC방 사용자를 위한 먹거리 주문 키오스크와 관리자를 위한 실시간 좌석 및 주문 관리 시스템을 제공합니다. 스프링 부트와 리액트를 활용하여 현대적인 PC방 운영 환경을 구축하는 것을 목표로 합니다.
#This project provides a food ordering kiosk for PC users and a real-time seat/order management system for administrators. It aims to build a modern PC cafe operation environment using Spring Boot and React.

#Tech Stack (기술 스택)
#Backend
#Java 17 / Spring Boot 3.x
#Spring Data JPA: Database ORM
#MySQL: Relational Database
#Lombok: Code simplification

Frontend
#React.js
#Axios: API Communication
#React Router: Navigation/Routing
#CSS3: Custom Dark-themed UI


# Key Features (주요 기능)
Current Progress (구현된 기능)
User Authentication (사용자 인증): Login/Join with role-based access control (Admin/User).
Dynamic Seat Occupancy (실시간 좌석 점유): Automatically updates seat status to 'USING' upon login from a specific seat.
Food Ordering System (먹거리 주문 시스템):
category-based menu browsing.
Add to cart and total price calculation.
Submitting orders linked to a specific seat number.
Database Integration (DB 연동): Relational mapping between Users, Seats, Categories, Products, and Orders.

#Planned Features (개발 예정)
Admin Dashboard (관리자 대시보드): Real-time monitoring of incoming orders and seat status.
Payment Integration (결제 시스템): Integration with payment APIs for food and seat time charging.
Inventory Management (재고 관리): Automatic stock deduction upon order completion.

＃Database Schema (데이터베이스 구조)
＃Users: Member information and remaining time.
＃Seats: Real-time seat status (EMPTY/USING) and seat-to-user mapping.
＃Products & Categories: Menu management with categorical organization.
＃Orders & OrderItems: Tracking orders from specific seats and detailed item lists.
