@echo off
rem -------------------------------------------------
rem  DatLichKhamBenh – Khởi chạy với MySQL Workbench
rem -------------------------------------------------

rem Chuyển tới thư mục backend
cd /d "%~dp0backend"

rem Chạy Spring Boot với profile dev (sử dụng connection trong application-dev.yml)
mvn clean spring-boot:run -Dspring-boot.run.profiles=dev
