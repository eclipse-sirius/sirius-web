/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import { useEffect, useState } from 'react';
import { Diagram } from '../DiagramRenderer.types';
import { cleanLayoutArea, layout, performDefaultAutoLayout, prepareLayoutArea } from './layout';
import { UseLayoutState, UseLayoutValue } from './useLayout.types';

const initialState: UseLayoutState = {
  currentStep: 'INITIAL_STEP',
  hiddenContainer: null,
  previousDiagram: null,
  diagramToLayout: null,
  laidoutDiagram: null,
  onLaidoutDiagram: () => {},
};

export const useLayout = (): UseLayoutValue => {
  const [state, setState] = useState<UseLayoutState>(initialState);

  const layoutAreaPrepared = () => {
    const currentStep = 'LAYOUT';
    setState((prevState) => ({ ...prevState, currentStep }));
  };

  const layoutDiagram = (
    previousLaidoutDiagram: Diagram | null,
    diagramToLayout: Diagram,
    callback: (laidoutDiagram: Diagram) => void
  ) => {
    if (state.currentStep === 'INITIAL_STEP') {
      setState((prevState) => ({
        ...prevState,
        currentStep: 'BEFORE_LAYOUT',
        previousDiagram: previousLaidoutDiagram,
        diagramToLayout,
        onLaidoutDiagram: callback,
      }));
    }
  };

  useEffect(() => {
    if (state.currentStep === 'BEFORE_LAYOUT' && !state.hiddenContainer && state.diagramToLayout) {
      const layoutArea = prepareLayoutArea(state.diagramToLayout, layoutAreaPrepared);
      setState((prevState) => ({
        ...prevState,
        hiddenContainer: layoutArea,
      }));
    } else if (state.currentStep === 'LAYOUT' && state.hiddenContainer && state.diagramToLayout) {
      const laidoutDiagram = layout(state.previousDiagram, state.diagramToLayout);
      setState((prevState) => ({
        ...prevState,
        diagramToLayout: null,
        laidoutDiagram: laidoutDiagram,
        currentStep: 'AFTER_LAYOUT',
      }));
    } else if (state.currentStep === 'AFTER_LAYOUT' && state.hiddenContainer && state.laidoutDiagram) {
      cleanLayoutArea(state.hiddenContainer);
      state.onLaidoutDiagram(state.laidoutDiagram);
      setState(() => initialState);
    }
  }, [state.currentStep, state.hiddenContainer]);

  return {
    layout: layoutDiagram,
    autoLayout: performDefaultAutoLayout,
  };
};
