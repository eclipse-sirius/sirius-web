FROM node:24-slim AS app

# We don't need the standalone Chromium
ENV PUPPETEER_SKIP_CHROMIUM_DOWNLOAD=true

# Install all system dependencies required by Chrome
# See https://pptr.dev/troubleshooting#chrome-doesnt-launch-on-linux
RUN apt update && \
    apt install -y \
      libx11-xcb1 \
      ca-certificates \
      fonts-liberation \
      libasound2 \
      libatk-bridge2.0-0 \
      libatk1.0-0 \
      libc6 \
      libcairo2 \
      libcups2 \
      libdbus-1-3 \
      libexpat1 \
      libfontconfig1 \
      libgbm1 \
      libgcc1 \
      libglib2.0-0 \
      libgtk-3-0 \
      libnspr4 \
      libnss3 \
      libpango-1.0-0 \
      libpangocairo-1.0-0 \
      libstdc++6 \
      libx11-6 \
      libx11-xcb1 \
      libxcb1 \
      libxcomposite1 \
      libxcursor1 \
      libxdamage1 \
      libxext6 \
      libxfixes3 \
      libxi6 \
      libxrandr2 \
      libxrender1 \
      libxss1 \
      libxtst6 \
      lsb-release \
      wget \
      xdg-utils && \
    rm -rf /var/lib/apt/lists/*

RUN useradd exporter
USER exporter
WORKDIR /home/exporter

COPY package.json package-lock.json index.mjs .
RUN npx @puppeteer/browsers install chrome@138 && \
    npm ci

EXPOSE 3000/tcp
ENTRYPOINT ["npm", "run", "start"]
