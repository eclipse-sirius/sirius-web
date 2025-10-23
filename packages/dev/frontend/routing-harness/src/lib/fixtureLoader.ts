import type { DiagramFixture, FixtureManifestEntry } from '../types';

let manifestCache: FixtureManifestEntry[] | null = null;
const fixtureCache = new Map<string, DiagramFixture>();

export const loadManifest = async (): Promise<FixtureManifestEntry[]> => {
  if (manifestCache) {
    return manifestCache;
  }

  const response = await fetch('/fixtures/manifest.json');
  if (!response.ok) {
    throw new Error(`Unable to load fixtures manifest (${response.status})`);
  }
  const data = (await response.json()) as FixtureManifestEntry[];
  manifestCache = data.slice().sort((a, b) => a.name.localeCompare(b.name));
  return manifestCache;
};

export const loadFixture = async (fixtureId: string): Promise<DiagramFixture> => {
  if (fixtureCache.has(fixtureId)) {
    return fixtureCache.get(fixtureId)!;
  }

  const response = await fetch(`/fixtures/${fixtureId}.json`);
  if (!response.ok) {
    throw new Error(`Unable to load fixture "${fixtureId}" (${response.status})`);
  }
  const data = (await response.json()) as DiagramFixture;
  fixtureCache.set(fixtureId, data);
  return data;
};
