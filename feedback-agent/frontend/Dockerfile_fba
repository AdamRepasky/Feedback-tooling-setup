FROM node:14-alpine
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
COPY . .

RUN npm run build

CMD npm run serve -- --port 8096
