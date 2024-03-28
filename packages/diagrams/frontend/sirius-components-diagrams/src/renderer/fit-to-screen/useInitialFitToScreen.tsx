/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import { Edge, Node, useReactFlow } from '@xyflow/react';
import { useEffect, useState } from 'react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { UseInitialFitToScreenState, UseInitialFitToScreenValue } from './useInitialFitToScreen.types';

export const useInitialFitToScreen = (): UseInitialFitToScreenValue => {
  const reactFlowInstance = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const [state, setState] = useState<UseInitialFitToScreenState>({
    initialFitToScreenPerformed: false,
    shouldPerformFitToScreen: false,
  });

  const fitToScreen = () => {
    if (!state.initialFitToScreenPerformed) {
      setState((prevState) => ({ ...prevState, shouldPerformFitToScreen: true }));
    }
  };

  // We cannot perform the fit to screen directly but instead need to wait for the next render in order to retrieve the updated nodes and edges in the react flow instance
  useEffect(() => {
    if (state.shouldPerformFitToScreen) {
      reactFlowInstance.fitView({ duration: 200 });
      setState((prevState) => ({ ...prevState, initialFitToScreenPerformed: true, shouldPerformFitToScreen: false }));
    }
  }, [state.shouldPerformFitToScreen]);

  return { fitToScreen };
};
