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
import { Span } from './useMonitoring.types';

export interface MonitoringContextValue {
  currentSpan: Span;
  endSpan: () => void;
  addMeasurement: (name: string, ...tags: string[]) => number;
  endMeasurement: (index: number, ...tags: string[]) => void;
  startSpan: (id: string) => void;
  logMeasurements: () => void;
}

export interface MonitoringContextProviderProps {
  children: React.ReactNode;
}

export interface MonitoringContextProviderState {
  currentSpan: Span;
}
