# Edge Routing Helpers

This folder contains the “drop‑in” building blocks that power our rectilinear edge rendering.  
Every function is written so reviewers can reason about the geometry without browsing the rest of
the renderer.

## Modules

- `geometry.ts`: translates XYFlow nodes into absolute rectangles, walks ancestor chains, and
  performs light‑weight overlap detection. Nothing in this file is React specific; consumers provide
  the node maps and receive plain coordinates back.
- `postProcessing.ts`: orchestrates the three routing phases that run on every edge:
  1. **Detours** – detect collisions against the rectangles provided by `geometry.ts` and insert
     perimeter traces that remain orthogonal.
  2. **Parallel spacing** – group segments that share the same axis/coordinate and offset them so
     multiple connections exiting a port do not overlap.
  3. **Straightening** – snap almost‑axis‑aligned paths to a shared X/Y coordinate when that keeps
     the drawing visually simpler.
  The file exports `buildDetouredPolyline`, `buildSpacedPolylines` (plus the single‑edge helper
  `buildSpacedPolyline`), and `straightenAlmostStraightPolyline`. SmoothStepEdgeWrapper is their only
  consumer today, which is why the three stages live together and share the same types.

## Tests

All routing tests now live under `__tests__` next to the production code:

| File | Focus |
| --- | --- |
| `postProcessEdgeDetours.test.ts` | Harness‑based regression tests that walk fixtures and assert that the detour builder stays orthogonal. |
| `postProcessEdgeParallelism.test.ts` | Unit checks for the spacing algorithm (vertical and horizontal overlaps, degenerate cases). |
| `postProcessEdgeStraighten.test.ts` | Unit checks for the straightening heuristics (axis preference, deviation threshold). |
| `SmoothStepEdgeWrapper.overlaps.test.tsx` | End‑to‑end harness that renders SmoothStep edges and inspects the polylines captured from the mocked rectilinear renderer. |

Each suite exercises the public exports through the same surface that the production renderer uses,
so refactors remain safe.

## Reviewing Tips

- When you need node rectangles, flow through `computeAbsoluteNodeRects` – it handles nested nodes,
  hidden nodes, and label exclusions consistently.
- `buildDetouredPolyline` can return either a single polyline or every detoured neighbor. The latter
  is needed when we want to apply spacing after detours (so parallel edges stay spread apart even if
  they collided with the same obstacle).
- `buildSpacedPolylines`/`straightenAlmostStraightPolyline` never mutate their inputs. The returned
  maps/arrays are clones so callers can cache or diff them without defensive copies.

Keep this README up to date whenever the file layout or test coverage changes – it is the canonical
place reviewers go to understand the routing pipeline at a glance.
