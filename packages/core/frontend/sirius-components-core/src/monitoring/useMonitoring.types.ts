/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
export interface UseMonitoringState {
  currentSpan: Span;
}

export interface useMonitoringValue {
  currentSpan: Span | null;
  endSpan: () => void;
  startSpan: (id: string) => void;
  addMeasurement: (name: string, ...tags: string[]) => number;
  endMeasurement: (index: number, ...tags: string[]) => void;
  logMeasurements: () => void;
}

export interface Span {
  id: string | null;
  startedOn: number | null;
  measurements: Measurement[];
}

export interface Measurement {
  name: string;
  measurement: number;
  unit: string;
  tags: string;
}
