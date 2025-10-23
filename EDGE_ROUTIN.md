# Rectilinear Edge Routing – Current Implementation

## Runtime Wiring
- **Entry point**: `SmoothStepEdgeWrapper.tsx` (`packages/diagrams/frontend/sirius-components-diagrams/src/renderer/edge/SmoothStepEdgeWrapper.tsx`) wraps the React Flow `SmoothStepEdge`. It resolves source/target handle positions with `getHandleCoordinatesByPosition`, nudges them to sit on the node border (marker‑aware), and decides whether a path already exists (`edge.data.bendingPoints`) or if one must be generated on the fly.
- **Fallback path generation**: When no persisted bend points are provided, the wrapper calls `getSmoothStepPath`. The returned quadratic Bézier segments are parsed (`svg-path-parser`) and converted into orthogonal points so that every segment alternates strictly horizontal/vertical, maintaining rectilinearity even for automatically routed edges.
- **Rendering**: The wrapper renders `MultiLabelRectilinearEditableEdge` with the computed coordinates, passing `customEdge` to indicate whether the bend points came from persisted layout data.
- **Store integration**: Both the wrapper and the editable edge hook into the shared diagram store (`useStore`). This keeps React Flow state, palette, connectors, and synchronization with the backend coherent.

## Edge Rendering & Interaction
- **Component**: `MultiLabelRectilinearEditableEdge.tsx` renders the actual SVG path via `BaseEdge` and surfaces editing affordances (palette, bend points, temporary lines, draggable labels).
- **Path string**: It sorts local bend points by `pathOrder` and interpolates `M/L` commands (`M source L p0 L p1 ... L target`) to guarantee axis-aligned segments.
- **Edge metadata**: A `useEffect` writes the latest `edgePath` back into the store so labels, reconnection logic, and other consumers use up‑to‑date geometry data.
- **Interactive handles**:
  - Circles (`<BendPoint/>`) on each explicit bend point.
  - Rectangular “temporary moving lines” between consecutive points to enable dragging entire segments and inserting new points.
  - Both read the zoom level from `useViewport` and disappear in read-only diagrams.
- **Auto pathing**: When an edge has no persisted bend points, `SmoothStepEdgeWrapper.tsx` now tries `getSmartEdge` to obtain a collision-free Manhattan route. The resulting intermediate points are passed to the rectilinear editor, and the legacy `getSmoothStepPath` fallback still kicks in when the smart solver cannot find a viable path.
- **Bendpoint cleanup**: Right before rendering, `SmoothStepEdgeWrapper.tsx` strips any redundant colinear bend points from auto-generated paths so we only keep knees that actually change direction.
- **Last-resort detour spacing**: The emergency “dent” built by `tryBuildDetourAroundCollision` now reuses the parallel-edge index computed for approach offsets so each detouring wire keeps its transverse gap. When several edges wiggle around the same obstacle they receive staggered top/bottom (or left/right) offsets, and both the horizontal plateau and its entry/exit ladders slide just enough to preserve edge ordering and avoid unnecessary crossings.

## Rectilinear Constraints Helpers
- `RectilinearEdgeCalculation.ts` keeps geometry orthogonal:
  - `determineSegmentAxis` classifies a segment as horizontal (`'x'`) or vertical (`'y'`).
  - `cleanBendPoint` collapses nearly co-located points (≤10 px apart) and realigns neighbouring segments to prevent zigzags.
  - `isOutOfLines` detects when a drag leaves the rectangle defined by a node’s bounds; `generateNewHandlePoint` and `generateNewBendPointOnSegment` then add extra points so the path routes around the node perimeter while staying rectilinear.
  - `getNewPointToGoAroundNode` (also used by reconnection code) offsets segments by `edgeBendPointOffset` to avoid clipping corners.

## Editing Hooks
- **`useBendingPoints.ts`**:
  - Maintains local state for bend points (`pathOrder` preserved) and metadata about which segment is being edited.
  - While dragging, it updates adjacent points to keep each segment axis-aligned. For the first/last bend point it also recalculates the source/target anchor to follow the node edges.
  - When the user drags beyond the node bounds, it injects a new bend point so the wire wraps around the rectangle instead of cutting through it.
  - On drag stop, it calls `cleanBendPoint`, updates node connection handles via `getNodesUpdatedWithHandles`, and persists changes through `synchronizeEdgeLayoutData`.
  - For `customEdge` paths, the hook reacts to source/target node motion and forces the first/last segments to remain perpendicular to the handles, preventing accidental diagonal segments.
- **`useTemporaryLines.ts`**:
  - Computes mid‑segment draggable helpers with direction and length metadata (`computeMiddlePoints`).
  - Dragging a temporary line moves both adjacent points simultaneously, effectively shifting an entire segment. If the move exits node bounds, the same “new bend point” logic as above kicks in to insert extra waypoints.
  - Updates node handles and persists the new geometry on drag stop. Uses `useUpdateNodeInternals` to refresh React Flow internals after handle positions change.

## Persistence & Synchronization
- `useEditableEdgePath.ts` exposes `synchronizeEdgeLayoutData`, which packages nodes + edges into a `RawDiagram` and calls `synchronizeLayoutData`. Both `useBendingPoints` and `useTemporaryLines` invoke this after edits so the backend captures the updated rectilinear path.
- The `removeEdgeLayoutData` helper clears `edge.data.bendingPoints` when resetting to auto-layout.

## Related Behaviours
- `EdgeLayout.ts` utilities are shared between rectilinear routing and reconnection/auto-layout. Notably, `getNodesUpdatedWithHandles` stores anchored handle positions back on the node data, and `getEdgeParameters` decides which side (top/bottom/left/right) a handle should attach to when no bend points exist.
- `useReconnectEdge.tsx` leverages `getNewPointToGoAroundNode` to keep rerouted edges rectilinear when reconnecting to a new target or source.

## Smart Edge Routing
- **Activation**: Edge type `smartManhattan` in `edgeTypes.ts:17` maps to `SmartStepEdgeWrapper`. Diagrams choose this type when they want automatic obstacle avoidance instead of manual bend-point editing.
- **Wrapper flow**: `SmartStepEdgeWrapper.tsx:63` retrieves source/target handle positions, snaps them to a 10 px grid, and determines which nodes matter by walking from each endpoint up to their lowest common ancestor while keeping siblings near that container (`SmartStepEdgeWrapper.tsx:116`).
- **Grid construction**: The filtered nodes become padded rectangles in a `pathfinding` grid (`getSmartEdge.ts:126`). `createGrid` converts the entire subgraph into walkable/non-walkable cells and forces a short walkable corridor out of the source/target handles so the path exits perpendicular to the node edges (`getSmartEdge.ts:82`).
- **Pathfinding**: `getSmartEdge.ts:68` runs Jump Point Search with diagonals disabled, guaranteeing orthogonal routes around any node whose bounding box was included. If the grid solver fails, the wrapper falls back to `getSmoothStepPath` so the edge still renders (`SmartStepEdgeWrapper.tsx:233`).
- **Result**: Successful runs return an SVG `M/L` polyline plus a midpoint for label positioning (`getSmartEdge.ts:256`). Unlike the rectilinear editor, smart edges do not expose bend points; they re-solve on layout changes, preserving clearance with included nodes.

## Tests & Documentation
- No dedicated unit or integration tests for the rectilinear-edge hooks/components were found in the repository (search for `rectilinear-edge`, `useBendingPoints`, and `useTemporaryLines` returned no `.test` files). Behaviour is currently covered only indirectly via manual testing and documentation (see `doc/iterations/2025.4/force_custom_edges_to_stay_rectilinear.adoc`).
- The lack of automated coverage means regressions in bend-point handling, insertion, or synchronization would only be caught through UX testing.

## Key Takeaways for Improvements
- The system is heavily imperative: mutations to arrays of bend points and direct store updates happen during drag. This is efficient but fragile without tests.
- Rectilinearity is enforced locally (by constraining adjacent segments) rather than via a global path solver; large reroutes might still require additional logic to avoid overlaps with intermediate nodes.
- Synchronization relies on firing `synchronizeLayoutData` at the end of each interaction, so batching edits or throttling might be valuable to reduce traffic for long drags.

## 2025 Updates – Manhattan Auto Routing
- **Side-aware spacing**: `SmoothStepEdgeWrapper` now computes offsets only among edges entering the same node side. Handles are considered when several edges share the same handle, otherwise all side edges participate so “one-handle-per-edge” diagrams still fan out. (See `computeTargetOffset`.)
- **Crossing reduction**: For edges arriving from the right or bottom, offsets are applied in reverse order. The outermost edge stays closest to the node, drastically reducing crossings with edges coming from the opposite side.
- **Perpendicular spread**: The last two points before the node are shifted by up to 12 px increments (capped inside the 50 px auto-layout corridor). This keeps parallel edges visually separated while honoring the node handle entry point.
- **Straight-edge cleanup**: When the automatically generated path is almost horizontal or vertical, bendpoints are dropped altogether and the handles are re-aligned to the exact axis. SmoothStep’s tiny detours no longer produce small triangles or zig-zags on straight edges.
- **Anchor-ready generation**: `buildAutoBendingPoints` now accepts an `anchor` decision (`'source'`, `'target'`, or `'midpoint'`). Currently we always anchor near the target, but the code is prepared to flip the L-shape near the source—or centre it in the middle of the edge span—without refactoring the routing again.
- **Collision-aware anchor fallback**: When auto-routing tries its anchor preferences, it now checks each candidate bendpoint against existing node bounding boxes (ignoring the source, target, and their ancestors). If any auto-generated point would land inside an existing node, we fall back to the next anchor option to keep the path obstacle free.
- **Accurate nested-node bounds**: Overlap detection now computes node rectangles in diagram coordinates. We reuse React Flow’s `positionAbsolute` when available and otherwise walk up the parent chain to accumulate offsets, so child nodes that store relative positions no longer trigger false positives.
- **Segment-based collision checks**: Candidate paths are evaluated segment by segment (source → bends → target). This catches cases where a node sits between two bendpoints even though neither waypoint lies inside the node itself, reducing accidental reroutes.
- **Balanced source anchoring**: When the auto-router anchors near the source handle, it now leaves the same clearance corridor as the target-anchored case before turning, so the first segment no longer hugs the node edge.
- **Last-resort detours**: If every anchor strategy still collides, the router carves rectangular “dents” (four extra bendpoints) around each blocking node using the `edgeBendPointOffset` corridor, repeating until the path clears every obstacle or the fallback is exhausted.
