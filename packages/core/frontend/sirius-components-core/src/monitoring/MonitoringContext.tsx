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

import React, { useState } from 'react';
import {
  MonitoringContextProviderProps,
  MonitoringContextProviderState,
  MonitoringContextValue,
} from './MonitoringContext.types';
import { Measurement, Span } from './useMonitoring.types';

const defaultValue: MonitoringContextValue = {
  currentSpan: {
    id: null,
    measurements: [],
    startedOn: null,
  },
  endSpan: () => {},
  addMeasurement: () => 0,
  endMeasurement: () => {},
  startSpan: () => {},
  logMeasurements: () => {},
};

export const MonitoringContext = React.createContext<MonitoringContextValue>(defaultValue);

export const MonitoringContextProvider = ({ children }: MonitoringContextProviderProps) => {
  const [state, setState] = useState<MonitoringContextProviderState>({
    currentSpan: {
      id: null,
      measurements: [],
      startedOn: null,
    },
  });

  const startSpan = (id: string) => {
    const span: Span = {
      id,
      startedOn: Date.now(),
      measurements: [],
    };

    setState((prevState) => ({ ...prevState, currentSpan: span }));
  };

  const endSpan = () => {
    setState((prevState) => ({
      ...prevState,
      currentSpan: {
        ...prevState.currentSpan,
        id: null,
        measurements: [],
        startedOn: null,
      },
    }));
  };

  const addMeasurement = (name: string, ...tags: string[]) => {
    const measurement: Measurement = {
      name: name,
      measurement: Date.now(),
      unit: 'ms',
      tags: tags.join(' - '),
    };

    setState((prevState) => ({
      ...prevState,
      currentSpan: {
        ...prevState.currentSpan,
        measurements: [...prevState.currentSpan.measurements, measurement],
      },
    }));

    return state.currentSpan.measurements.length > 0 ? state.currentSpan.measurements.length - 1 : 0;
  };

  const endMeasurement = (index: number, ...tags: string[]) => {
    setState((prevState) => {
      const lastMeasurement = state.currentSpan?.measurements.at(index);
      if (lastMeasurement) {
        const measurement: Measurement = {
          ...lastMeasurement,
          measurement: Date.now() - lastMeasurement.measurement,
          tags: lastMeasurement.tags.concat(tags.join('-')),
        };
        const newArray = [...prevState.currentSpan.measurements];
        newArray[index] = measurement;
        return {
          ...prevState,
          currentSpan: {
            ...prevState.currentSpan,
            measurements: newArray,
          },
        };
      }
      return prevState;
    });
  };

  const logMeasurements = () => console.table(state.currentSpan.measurements);

  return (
    <MonitoringContext.Provider
      value={{
        currentSpan: state.currentSpan,
        startSpan,
        addMeasurement,
        endMeasurement,
        endSpan,
        logMeasurements,
      }}>
      {children}
    </MonitoringContext.Provider>
  );
};
