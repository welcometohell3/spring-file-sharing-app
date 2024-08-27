# Используем node.js образ для сборки
FROM node:18-alpine as build

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем package.json и package-lock.json
COPY package*.json ./

# Устанавливаем зависимости
RUN npm install

# Копируем весь проект
COPY . .

# Сборка фронтенда
RUN npm run build

# Используем nginx для хостинга приложения
FROM nginx:alpine
COPY --from=build /app/build /usr/share/nginx/html

# Экспонируем порт
EXPOSE 80

# Запуск nginx
CMD ["nginx", "-g", "daemon off;"]
