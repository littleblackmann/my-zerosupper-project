# ZERO Supper

這是我為了紀念曾經經營過的 ZERO Supper 漢堡店，重新整理與保存的網站。這裡收錄當年的菜單、故事、夥伴與舊店址，也留下那段一起做漢堡、招呼客人和認真生活過的回憶。

原始 Vue 前端已更新為 Vite，後端則以乾淨的新架構重建，讓這個很久以前完成的小作品可以繼續被保存、瀏覽，也能安心地慢慢成長。

## 專案結構

- `frontend/my-zerosupper-frontend` — Vue 3 + Vite
- `backend/my-zerosupper-backend` — Spring Boot 4.1 + Java 17
- 本機預設資料庫 — H2 檔案資料庫（保存在後端 `data/`，不會提交）
- 正式環境資料庫 — PostgreSQL

## 已有功能

- 會員註冊、登入、登出與可撤銷的 Bearer token
- BCrypt 密碼雜湊與後端角色權限
- 菜單與商品查詢
- 購物車、庫存檢查、後端重新計價與建立訂單
- 會員訂單紀錄
- 管理員商品 CRUD、訂單狀態與會員列表
- Flyway 資料庫 migration
- 可選 SMTP 訂單確認信
- Actuator 健康檢查：`/actuator/health`

## 本機啟動

需要 Java 17 與 Node.js 20.19 以上。Gradle 由專案 wrapper 自動下載，不必全域安裝。

第一個 PowerShell 視窗：

```powershell
cd backend\my-zerosupper-backend
.\start-backend.ps1
```

第一次啟動會產生一組隨機管理員密碼，保存在已被 Git 忽略的 `.env.local.ps1`。

第二個 PowerShell 視窗：

```powershell
cd frontend\my-zerosupper-frontend
npm install
npm run serve
```

開啟 <http://localhost:8080>。Vite 會將 `/api` 代理到本機後端的 `9527` port。

## 驗證

```powershell
cd backend\my-zerosupper-backend
.\gradlew.bat test

cd ..\..\frontend\my-zerosupper-frontend
npm audit
npm run lint
npm run build
```

## 正式環境設定

所有敏感資料都從環境變數取得，範例請看 `backend/my-zerosupper-backend/.env.example`。正式環境至少要提供管理員帳密與 PostgreSQL 連線資訊，請勿提交真正的密碼或 SMTP 憑證。
