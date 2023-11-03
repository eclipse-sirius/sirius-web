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

import { ServerContext, ServerContextValue } from '@eclipse-sirius/sirius-components-core';
import { useCallback, useContext, useEffect, useState } from 'react';
import { XYPosition, useReactFlow } from 'reactflow';
import { Diagram, EdgeData, NodeData } from '../DiagramRenderer.types';
import { LayoutContext } from './LayoutContext';
import { LayoutContextContextValue } from './LayoutContext.types';
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
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);
  const [state, setState] = useState<UseLayoutState>(initialState);

  const reactFlowInstance = useReactFlow<NodeData, EdgeData>();
  const { referencePosition, setReferencePosition, resetReferencePosition } =
    useContext<LayoutContextContextValue>(LayoutContext);

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
      const layoutArea = prepareLayoutArea(state.diagramToLayout, layoutAreaPrepared, httpOrigin);
      setState((prevState) => ({
        ...prevState,
        hiddenContainer: layoutArea,
      }));
    } else if (state.currentStep === 'LAYOUT' && state.hiddenContainer && state.diagramToLayout) {
      const laidoutDiagram = layout(state.previousDiagram, state.diagramToLayout, referencePosition);
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
  }, [state.currentStep, state.hiddenContainer, referencePosition]);

  const onReferencePositionSet = useCallback((referencePosition: XYPosition, parentId: string | null | undefined) => {
    let position = { ...referencePosition };
    let parentNode = reactFlowInstance.getNode(parentId ?? '');
    while (parentNode) {
      position = {
        x: position.x - parentNode.position.x,
        y: position.y - parentNode.position.y,
      };
      parentNode = reactFlowInstance.getNode(parentNode.parentNode ?? '');
    }

    // Add an offset to put the node under the mouse for the next refresh
    setReferencePosition({
      position: {
        x: position.x - 10 > 0 || parentId === '' || !parentId ? position.x - 10 : 0,
        y: position.y - 10 > 0 || parentId === '' || !parentId ? position.y - 10 : 0,
      },
      parentId,
    });
  }, []);

  return {
    layout: layoutDiagram,
    autoLayout: performDefaultAutoLayout,
    setReferencePosition: onReferencePositionSet,
    resetReferencePosition,
  };
};
