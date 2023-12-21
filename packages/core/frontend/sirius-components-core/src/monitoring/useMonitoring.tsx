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

import { useContext, useEffect } from 'react';
import { MonitoringContext } from './MonitoringContext';
import { MonitoringContextValue } from './MonitoringContext.types';
import { useMonitoringValue } from './useMonitoring.types';

export const useMonitoring = (): useMonitoringValue => {
  const { logMeasurements, currentSpan, startSpan, endSpan, addMeasurement, endMeasurement } =
    useContext<MonitoringContextValue>(MonitoringContext);

  useEffect(() => {
    if (!currentSpan) {
      logMeasurements();
    }
    return () => {
      logMeasurements();
    };
  }, [currentSpan]);

  return {
    currentSpan,
    startSpan,
    addMeasurement,
    endMeasurement,
    logMeasurements,
    endSpan,
  };
};
