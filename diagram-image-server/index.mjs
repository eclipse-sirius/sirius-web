/*******************************************************************************
 * Copyright (c) 2025 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
import cookieParser from "cookie-parser";
import express from "express";
import { parseArgs } from "node:util";
import puppeteer from "puppeteer";

const { values } = parseArgs({
  options: {
    server: {
      type: "string",
      short: "s",
      default: "http://localhost:5173",
    },
    port: {
      type: "string",
      short: "p",
      default: "3000",
    },
    cookie: {
      type: "string",
      short: "c",
      default: ["SESSION", "JSESSIONID"],
      multiple: true,
    },
  },
});

const normalizeServerRoot = (serverRoot) => {
  const url = new URL(serverRoot);
  return `${url.protocol}//${url.host}`;
};

const config = {
  // Our own port
  port: Number(values["port"]) || 3000,
  // The server root where the diagrams are hosted
  serverRoot: normalizeServerRoot(values["server"]),
  // The names of the cookies to forward to the diagram server
  cookiesNames: values["cookie"],
};

const browser = await puppeteer.launch({
  headless: true,
  args: ["--no-sandbox", "--disable-setuid-sandbox"],
});

/**
 * Opens a specific page, waits for a specific selector, and invokes the
 * supplied callback on the loaded page to fetch any data it needs.
 *
 * The page is closed before returning the result fromthe callback.
 *
 * The page is loaded in a new browser context to ensure isolation.
 *
 * If the global configuration provides a cookie value, it is set
 * in the context to allow authenticated access to the page.
 */
const readPageData = async (url, selector, cookies, readData) => {
  const context = await browser.createBrowserContext();

  // Forward all cookies from the configured whitelist
  for (const name of config.cookiesNames) {
    if (cookies[name]) {
      await context.setCookie({
        name,
        value: cookies[name],
        domain: new URL(config.serverRoot).hostname,
      });
    }
  }
  const page = await context.newPage();
  await page.setViewport({ width: 1920, height: 1080 });
  await page.setDefaultTimeout(20000);
  console.log(`Loading page: ${url}`);
  await page.goto(url);
  await page.waitForSelector(selector);
  const result = await readData(page);
  await page.close();
  return result;
};

/**
 * Reads the PNG content from the loaded "PNG Viewer" page.
 * It expects the image to be in a data URL format.
 */
const readPngContent = async (page) => {
  const dataURL = await page.$eval("#png-viewer-image", async (img) => img.src);
  const matches = dataURL.match(/^data:.+\/(.+);base64,(.*)$/);
  if (matches === null || matches.length !== 3) {
    throw new Error("Invalid data URL format");
  }
  const data = matches[2];
  return Buffer.from(data, "base64");
};

/**
 * Reads the SVG content from the loaded "SVG Viewer" page.
 * It expects it to be encoded as a blob URL.
 */
const readSvgContent = async (page) => {
  return await page.$eval("#svg-viewer-image", async (img) => {
    const response = await fetch(img.src);
    if (!response.ok) {
      throw new Error(`Failed to fetch SVG: ${response.statusText}`);
    }
    return await response.text();
  });
};

const app = express();
app.use(cookieParser());

app.get("/api/png-diagram/:editingContext/:diagram", async (req, res) => {
  const { editingContext, diagram } = req.params;

  const pngContent = await readPageData(
    `${config.serverRoot}/projects/${editingContext}/edit/${diagram}?selection=${diagram}&mode=png-viewer`,
    "#png-viewer-image",
    req.cookies,
    readPngContent
  );

  res.setHeader("Content-Type", "image/png");
  res.send(pngContent);
});

app.get("/api/svg-diagram/:editingContext/:diagram", async (req, res) => {
  const { editingContext, diagram } = req.params;

  const svgContent = await readPageData(
    `${config.serverRoot}/projects/${editingContext}/edit/${diagram}?selection=${diagram}&mode=svg-viewer`,
    "#svg-viewer-image",
    req.cookies,
    readSvgContent
  );

  res.setHeader("Content-Type", "image/svg+xml");
  res.send(svgContent);
});

app.get("/", (_req, res) => {
  res.send(`
    <h1>Diagram Image Server</h1>
    <p>Use the following endpoints to retrieve diagrams:</p>
    <ul>
      <li><code>/api/png-diagram/:editingContext/:diagram</code> - Get PNG diagram</li>
      <li><code>/api/svg-diagram/:editingContext/:diagram</code> - Get SVG diagram</li>
    </ul>
    <p>Diagrams will be served from <code>${config.serverRoot}</code></p>
    `);
});

app.listen(config.port, () => {
  console.log(
    `Image server listening on port ${config.port}; diagrams will be served from ${config.serverRoot}`
  );
});
