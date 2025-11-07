import { createContext, PropsWithChildren, useContext } from 'react';
import type { XYPosition } from '@xyflow/react';

export type RoutingTracePhase =
  | 'initial'
  | 'fan-out'
  | 'fan-in'
  | 'simplify'
  | 'straighten'
  | 'detour'
  | 'parallel-spacing'
  | 'final';

export type RoutingTraceEvent = {
  edgeId: string;
  phase: RoutingTracePhase;
  message: string;
  polyline?: XYPosition[];
  metadata?: Record<string, unknown>;
};

export type RoutingTraceCollector = (event: RoutingTraceEvent) => void;

export const ROUTING_TRACE_NOOP_COLLECTOR: RoutingTraceCollector = () => {};

const RoutingTraceContext = createContext<RoutingTraceCollector>(ROUTING_TRACE_NOOP_COLLECTOR);

type RoutingTraceProviderProps = PropsWithChildren<{
  collector?: RoutingTraceCollector | null;
}>;

export const RoutingTraceProvider = ({ collector, children }: RoutingTraceProviderProps) => (
  <RoutingTraceContext.Provider value={collector ?? ROUTING_TRACE_NOOP_COLLECTOR}>{children}</RoutingTraceContext.Provider>
);

export const useRoutingTraceCollector = (): RoutingTraceCollector => useContext(RoutingTraceContext);

