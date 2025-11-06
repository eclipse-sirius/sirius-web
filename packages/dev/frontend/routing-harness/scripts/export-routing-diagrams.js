#!/usr/bin/env node

/**
 * Headless exporter for the routing harness diagrams.
 *
 * Usage:
 *   npm run export:diagrams -- --out ./tmp/diagrams
 *
 * Options:
 *   --out <dir>        Target directory for the PNG files (default: ./routing-diagram-exports)
 *   --url <url>        Use an already running harness instead of starting a local preview server
 *   --port <number>    Port for the preview server when --url is not provided (default: 5180)
 *   --skip-build       Skip running `npm run build` before launching the preview server
 *   --timeout <ms>     Max time to wait for all downloads (default: 90000)
 *   --limit <count>    Restrict export to the first <count> fixtures (useful for debugging)
 *   --verbose          Log browser console output and page errors
 *   --batch-index <n>  Zero-based index of the batch to export (requires --batch-size)
 *   --batch-size <n>   Number of fixtures per batch when using --batch-index
 *   -h, --help         Show this help message
 */

import { chromium } from '@playwright/test';
import { spawn } from 'node:child_process';
import { mkdir } from 'node:fs/promises';
import { join, dirname, resolve } from 'node:path';
import { fileURLToPath } from 'node:url';
import process from 'node:process';
import { setTimeout as delay } from 'node:timers/promises';

const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);
const harnessDir = resolve(__dirname, '..');

const defaultOptions = {
  outDir: 'routing-diagram-exports',
  port: 5180,
  skipBuild: false,
  timeoutMs: 90_000,
  url: null,
  limit: null,
  verbose: false,
  batchIndex: null,
  batchSize: null,
};

const helpText = `Routing Harness Exporter

Options:
  --out <dir>        Target directory for the PNG files (default: ${defaultOptions.outDir})
  --url <url>        Use an already running harness instead of starting a local preview server
  --port <number>    Port for the preview server when --url is not provided (default: ${defaultOptions.port})
  --skip-build       Skip running "npm run build" before launching the preview server
  --timeout <ms>     Max time (in ms) to wait for all downloads (default: ${defaultOptions.timeoutMs})
  --limit <count>    Restrict export to the first <count> fixtures
  --verbose          Log browser console messages and page errors
  --batch-index <n>  Zero-based index of the batch to export (requires --batch-size)
  --batch-size <n>   Number of fixtures per batch when using --batch-index
  -h, --help         Show this help message
`;

const parseArgs = (argv) => {
  const options = { ...defaultOptions };
  for (let index = 0; index < argv.length; index += 1) {
    const arg = argv[index];
    switch (arg) {
      case '--out':
        options.outDir = argv[++index] ?? options.outDir;
        break;
      case '--url':
        options.url = argv[++index] ?? null;
        break;
      case '--port': {
        const value = Number(argv[++index]);
        if (!Number.isFinite(value) || value <= 0) {
          throw new Error(`Invalid port value: "${argv[index]}"`);
        }
        options.port = value;
        break;
      }
      case '--skip-build':
        options.skipBuild = true;
        break;
      case '--timeout': {
        const value = Number(argv[++index]);
        if (!Number.isFinite(value) || value <= 0) {
          throw new Error(`Invalid timeout value: "${argv[index]}"`);
        }
        options.timeoutMs = value;
        break;
      }
      case '--limit': {
        const value = Number(argv[++index]);
        if (!Number.isFinite(value) || value <= 0) {
          throw new Error(`Invalid limit value: "${argv[index]}"`);
        }
        options.limit = Math.floor(value);
        break;
      }
      case '--verbose':
        options.verbose = true;
        break;
      case '--batch-index': {
        const value = Number(argv[++index]);
        if (!Number.isInteger(value) || value < 0) {
          throw new Error(`Invalid batch index: "${argv[index]}"`);
        }
        options.batchIndex = value;
        break;
      }
      case '--batch-size': {
        const value = Number(argv[++index]);
        if (!Number.isInteger(value) || value <= 0) {
          throw new Error(`Invalid batch size: "${argv[index]}"`);
        }
        options.batchSize = value;
        break;
      }
      case '-h':
      case '--help':
        console.log(helpText);
        process.exit(0);
        break;
      default:
        throw new Error(`Unknown argument: "${arg}"`);
    }
  }
  return options;
};

const runCommand = (command, args, options) =>
  new Promise((resolve, reject) => {
    const child = spawn(command, args, { stdio: 'inherit', ...options });
    child.on('error', reject);
    child.on('exit', (code) => {
      if (code === 0) {
        resolve();
      } else {
        reject(new Error(`${command} ${args.join(' ')} exited with code ${code}`));
      }
    });
  });

const waitForServer = async (url, timeoutMs) => {
  const deadline = Date.now() + timeoutMs;
  while (Date.now() < deadline) {
    try {
      const response = await fetch(url, { method: 'GET' });
      if (response.ok) {
        return;
      }
    } catch (error) {
      // Retry until timeout
    }
    await delay(500);
  }
  throw new Error(`Timed out waiting for ${url} to become available.`);
};

const killProcess = async (child) => {
  if (!child || child.killed) {
    return;
  }
  await new Promise((resolve) => {
    child.once('exit', resolve);
    child.kill('SIGTERM');
    setTimeout(resolve, 2000);
  });
};

const waitForDownloads = (page, expectedCount, timeoutMs) =>
  new Promise((resolve, reject) => {
    if (expectedCount <= 0) {
      resolve([]);
      return;
    }

    const downloads = [];
    let timer = null;

    const cleanup = () => {
      if (timer) {
        clearTimeout(timer);
      }
      page.off('download', handleDownload);
    };

    const handleDownload = (download) => {
      downloads.push(download);
      if (downloads.length >= expectedCount) {
        cleanup();
        resolve(downloads);
      }
    };

    timer = setTimeout(() => {
      cleanup();
      reject(new Error(`Timed out waiting for ${expectedCount} downloads (received ${downloads.length}).`));
    }, timeoutMs);

    page.on('download', handleDownload);
  });

const main = async () => {
  const options = parseArgs(process.argv.slice(2));
  const outputDir = resolve(process.cwd(), options.outDir);
  const shouldStartServer = !options.url;
  const baseUrl = options.url ?? `http://127.0.0.1:${options.port}`;
  let previewProcess = null;
  let browser = null;

  try {
    if (shouldStartServer) {
      if (!options.skipBuild) {
        console.log('[harness-export] Building harness…');
        await runCommand('npm', ['run', 'build'], { cwd: harnessDir, shell: false });
      }

      console.log('[harness-export] Starting preview server…');
      previewProcess = spawn(
        'npm',
        ['run', 'preview', '--', '--host', '127.0.0.1', '--port', String(options.port)],
        {
          cwd: harnessDir,
          stdio: ['ignore', 'pipe', 'pipe'],
        }
      );

      previewProcess.stdout?.on('data', (chunk) => {
        process.stdout.write(`[preview] ${chunk}`);
      });
      previewProcess.stderr?.on('data', (chunk) => {
        process.stderr.write(`[preview] ${chunk}`);
      });

      await waitForServer(baseUrl, 30_000);
      console.log(`[harness-export] Preview server ready at ${baseUrl}`);
    }

    await mkdir(outputDir, { recursive: true });

    console.log('[harness-export] Launching browser…');
    browser = await chromium.launch({ headless: true });
    const context = await browser.newContext({
      acceptDownloads: true,
      viewport: { width: 1600, height: 1200 },
      deviceScaleFactor: 1,
    });
    const page = await context.newPage();

    if (options.verbose) {
      page.on('console', (msg) => {
        const type = msg.type();
        const text = msg.text();
        console.log(`[browser:${type}] ${text}`);
      });
      page.on('pageerror', (error) => {
        console.error('[browser:error]', error);
      });
    }

    const navigationUrl = new URL(baseUrl);
    if (options.batchIndex !== null && options.batchSize !== null) {
      const start = options.batchIndex * options.batchSize;
      const count =
        options.limit && options.limit > 0 ? Math.min(options.batchSize, options.limit) : options.batchSize;
      navigationUrl.searchParams.set('batchStart', String(start));
      navigationUrl.searchParams.set('batchCount', String(count));
    } else if (options.limit && options.limit > 0) {
      navigationUrl.searchParams.set('batchCount', String(options.limit));
    }

    const visitUrl = navigationUrl.toString();
    console.log(`[harness-export] Navigating to ${visitUrl}`);
    await page.goto(visitUrl, { waitUntil: 'networkidle' });
    const waitTimeout = Math.min(options.timeoutMs, 30_000);
    await Promise.race([
      page.waitForSelector('.fixture', { timeout: waitTimeout }),
      page.waitForFunction(
        () => {
          const status = Array.from(document.querySelectorAll('.app__status')).map((el) => el.textContent ?? '');
          return status.some((text) => text.includes('No fixtures in the selected range.') || text.includes('No fixtures available.'));
        },
        { timeout: waitTimeout }
      ),
    ]);

    const fixturesToExport = await page.evaluate(() =>
      Array.from(document.querySelectorAll('[data-fixture-id]')).map((section) => ({
        id: section.getAttribute('data-fixture-id') ?? '',
        fileName: section.getAttribute('data-fixture-file') ?? '',
      }))
    );

    if (!fixturesToExport || fixturesToExport.length === 0) {
      throw new Error('No fixtures detected on the harness page (check batch parameters).');
    }

    await page.waitForFunction(() => {
      const button = document.querySelector('[data-testid="export-all-button"]');
      return Boolean(button) && !button.hasAttribute('disabled');
    });

    console.log(
      `[harness-export] Found ${fixturesToExport.length} fixture(s) for export…`
    );
    const downloadsPromise = waitForDownloads(page, fixturesToExport.length, options.timeoutMs);
    await page.click('[data-testid="export-all-button"]');
    const downloads = await downloadsPromise;

    if (downloads.length !== fixturesToExport.length) {
      throw new Error(`Expected ${fixturesToExport.length} downloads but received ${downloads.length}.`);
    }

    for (let index = 0; index < fixturesToExport.length; index += 1) {
      const fixture = fixturesToExport[index];
      const download = downloads[index];
      const baseName = (fixture.fileName || fixture.id || 'diagram').replace(/\.json$/i, '');
      const targetPath = join(outputDir, `${baseName}.png`);
      await download.saveAs(targetPath);
      console.log(`[harness-export] Saved ${targetPath}`);
    }

    await page.waitForFunction(() => {
      const button = document.querySelector('[data-testid="export-all-button"]');
      return Boolean(button) && !button.hasAttribute('disabled');
    });

    await browser.close();
    browser = null;

    console.log(`[harness-export] Export completed. Files available in ${outputDir}`);
  } finally {
    if (browser) {
      await browser.close().catch(() => {});
    }
    await killProcess(previewProcess);
  }
};

main().catch((error) => {
  console.error('[harness-export] Export failed:', error);
  process.exitCode = 1;
});
