/\*!

- CrossFade Tunnels
-
- This document explains how the crossing detection system works, why it is
- implemented the way it is, and how to extend/debug it.
  \*/

# CrossFade Tunnels

The `edge/crossings` folder contains the logic that identifies when a polyline
segment should appear to pass _under_ another edge. The visual effect itself is
implemented in `MultiLabelEdge.tsx` and the rectilinear editor, but the data
driving those fades originates here.

## What Users See

Whenever two rectilinear edges truly cross, the lower-priority edge fades out
for a short distance so it looks like the edge disappears into a tunnel and
emerges on the other side. The fade follows the actual rendered path, ensuring
blank segments stay glued to the edge even when users drag nodes or the layout
changes.

## Goals

1. Detect crossings straight from the rendered SVG path so there is no
   discrepancy between what React Flow draws and what we inspect.
2. Only fade true underpasses: no gaps near a node, near a handle, or when
   edges merely touch at endpoints.
3. Keep the behavior deterministic to avoid flicker when users drag nodes.

## High-Level Flow

1. **Path capture** – each edge writes its SVG path string (`edge.data.edgePath`)
   back into the store immediately after rendering.
2. **Hook** – `useEdgeCrossingFades()` pulls the current `edges`/`nodes`
   snapshot from the store and calls `detectEdgeCrossings(edges, nodes)`.
   It updates `edge.data.crossingGaps` only when the computed windows differ
   from what the edge already stores, keeping React renders cheap.
3. **Renderer** – the edge components convert each `[startRatio, endRatio]`
   window into a `strokeDasharray` gap, which gives the illusion that the edge
   disappears under the crossing.

## `detectEdgeCrossings` Internals

1. **Segment extraction** – `parseSegments()` walks the SVG `M/L/Q…` commands
   produced by React Flow (via `svg-path-parser`), converts them into straight
   segments, and records cumulative lengths along the path. The detector never
   looks at node geometry until very late in the process.
2. **Intersection math** – every pair of segments is fed to
   `getSegmentIntersection()`, which solves the 2×2 linear system for two
   parametric segments. Degenerate (parallel/colinear) cases are discarded.
3. **Endpoint/window filtering**
   - Intersections must fall strictly inside both segments
     (`ratio` ∈ (0, 1)); touching corners or overlapping colinear runs are
     ignored.
   - The detector drops any intersection that lies within 18 px of either edge’s
     _path_ endpoints. This avoids cutting entries/exits where multiple edges
     share a port.
   - A node-based filter still exists, but only as a fallback to keep fades away
     from the node rectangles (±12 px) when the path itself runs very close to a
     node.
4. **Under-edge selection** – edges inherit React Flow’s `zIndex`. If both edges
   have the same `zIndex`, the detector falls back to deterministic ID ordering
   so the same edge always gets the fade when diagrams change subtly.
5. **Window emission** – every crossing produces a `[startRatio, endRatio]`
   window spanning `±fadeWindowLength / 2` along the under-edge. Windows are
   deduplicated via a `Set` so multiple segments hitting the same coordinates do
   not create redundant dash entries.

## Why Use Path Coordinates?

Earlier attempts inferred crossings by checking which node rectangles an edge
passed near. That approach failed for rectilinear routing (especially fan-in /
fan-out scenarios) because parallel segments that happen to be adjacent inside
the same corridor would be interpreted as overlapping even when they do not
cross. By working directly with the rendered polyline we avoid all ambiguity:
if the SVG commands do not intersect, no fade is emitted.

## Extensibility

- **Bezier curves** – the current implementation converts quadratic curves into
  straight segments by sampling the parsed coordinates. If future routing modes
  emit full Bézier curves, the segment extraction step can be upgraded to
  approximate the curve with enough line segments before we run the intersection
  math.
- **Performance** – the detector is currently `O(E²)` in the number of edges,
  which is acceptable for small diagrams. The hook intentionally skips crossing
  detection while the user drags nodes so the heavy pass only runs once the
  mouse is released. If we need to scale to hundreds of edges, consider spatial
  bucketing (grid or R-tree) before walking every segment pair.
- **Priority rules** – to expose user-controlled priorities, extend
  `MultiLabelEdgeData` with an explicit crossing priority and tweak
  `selectUnderEdge()` accordingly. Rendering changes would stay the same because
  the dash windows already encode “this edge is underneath”.

## Debugging Tips

- Temporarily lower `PATH_ENDPOINT_PADDING` or adjust the `fadeWindowLength`
  argument in `useEdgeCrossingFades()` to explore how sensitive the cuts are.
- To understand a specific crossing, log the `segmentA`/`segmentB` coordinates
  inside `detectEdgeCrossings` and compare them to the harness’ displayed path.

## Running Unit Tests

Execute the Vitest suite dedicated to the crossing helpers from the repository root with:

```bash
npm run test --workspace @eclipse-sirius/sirius-components-diagrams -- src/renderer/edge/crossings/__tests__
```
