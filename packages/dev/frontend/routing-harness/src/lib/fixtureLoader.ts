import type { DiagramFixture, FixtureManifestEntry } from '../types';

const fixtureModules = import.meta.glob('../fixtures/*.json', {
  eager: true,
  import: 'default',
}) as Record<string, DiagramFixture>;

const fixturesById = new Map<string, DiagramFixture>();
const manifestEntries: FixtureManifestEntry[] = [];

Object.entries(fixtureModules).forEach(([, fixture]) => {
  const id = fixture.id ?? '';
  if (!id) {
    console.warn('[routing-harness] Ignoring fixture without id:', fixture);
    return;
  }
  fixturesById.set(id, fixture);
  manifestEntries.push({
    id,
    name: fixture.name ?? id,
    description: fixture.description,
    categories: fixture.categories,
  });
});

manifestEntries.sort((a, b) => a.name.localeCompare(b.name));

export const loadManifest = async (): Promise<FixtureManifestEntry[]> => {
  return manifestEntries.map((entry) => ({
    ...entry,
    categories: entry.categories ? [...entry.categories] : undefined,
  }));
};

export const loadFixture = async (fixtureId: string): Promise<DiagramFixture> => {
  const fixture = fixturesById.get(fixtureId);
  if (!fixture) {
    throw new Error(`Fixture "${fixtureId}" not found`);
  }
  return JSON.parse(JSON.stringify(fixture));
};
