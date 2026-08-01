@echo off
rem -------------------------------------------------
rem  DatLichKhamBenh – Khởi chạy với MySQL Workbench
rem -------------------------------------------------

rem Chuyển tới thư mục backend
cd /d "%~dp0backend"

rem Profile dev bật dữ liệu mẫu; production không tự tạo tài khoản mặc định.
mvn clean spring-boot:run -Dspring-boot.run.profiles=dev
