/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
import { Edge, Node, useReactFlow } from '@xyflow/react';
import { useContext, useEffect, useState } from 'react';
import { NodeTypeContext } from '../../contexts/NodeContext';
import { NodeTypeContextValue } from '../../contexts/NodeContext.types';
import { useDiagramDescription } from '../../contexts/useDiagramDescription';
import { GQLReferencePosition } from '../../graphql/subscription/diagramEventSubscription.types';
import { GQLArrangeLayoutDirection } from '../../representation/DiagramRepresentation.types';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { useLayoutConfigurations } from './arrange-all/useLayoutConfigurations';
import { useElkLayout } from './elk/useElkLayout';
import { cleanLayoutArea, layout, prepareLayoutArea, prepareLayoutLabels } from './layout';
import { RawDiagram } from './layout.types';
import { UseLayoutState, UseLayoutValue } from './useLayout.types';

const initialState: UseLayoutState = {
  currentStep: 'INITIAL_STEP',
  hiddenContainer: null,
  previousDiagram: null,
  diagramToLayout: null,
  laidoutDiagram: null,
  referencePosition: null,
  layoutDirection: 'UNDEFINED',
  root: null,
  onLaidoutDiagram: () => {},
};

const isHandleReferencePosition = (causedBy: string) => causedBy === 'InvokeSingleClickOnTwoDiagramElementsToolInput';

export const useLayout = (): UseLayoutValue => {
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);
  const { nodeLayoutHandlers } = useContext<NodeTypeContextValue>(NodeTypeContext);
  const [state, setState] = useState<UseLayoutState>(initialState);
  const { elkLayout } = useElkLayout();
  const { layoutConfigurations } = useLayoutConfigurations();
  const { diagramDescription } = useDiagramDescription();

  const reactFlowInstance = useReactFlow<Node<NodeData>, Edge<EdgeData>>();

  const layoutAreaPrepared = () => {
    const currentStep = 'LAYOUT';
    setState((prevState) => ({ ...prevState, currentStep }));
  };

  const layoutDiagram = (
    previousLaidoutDiagram: RawDiagram | null,
    diagramToLayout: RawDiagram,
    referencePosition: GQLReferencePosition | null,
    layoutDirection: GQLArrangeLayoutDirection,
    callback: (laidoutDiagram: RawDiagram) => void
  ) => {
    if (state.currentStep === 'INITIAL_STEP') {
      let processedReferencePosition: GQLReferencePosition | null = referencePosition;
      if (processedReferencePosition && !isHandleReferencePosition(processedReferencePosition.causedBy)) {
        let parentNode = reactFlowInstance.getNode(processedReferencePosition.parentId ?? '');
        while (parentNode) {
          processedReferencePosition.positions.forEach((position) => {
            position.x -= parentNode?.position.x ?? 0;
            position.y -= parentNode?.position.y ?? 0;
          });
          parentNode = reactFlowInstance.getNode(parentNode.parentId ?? '');
        }
      }

      setState((prevState) => ({
        ...prevState,
        currentStep: 'BEFORE_LAYOUT',
        previousDiagram: previousLaidoutDiagram,
        diagramToLayout,
        referencePosition: processedReferencePosition,
        layoutDirection,
        onLaidoutDiagram: callback,
      }));
    }
  };

  useEffect(() => {
    if (state.currentStep === 'BEFORE_LAYOUT' && !state.hiddenContainer && state.diagramToLayout) {
      const { hiddenContainer, root } = prepareLayoutArea(state.diagramToLayout, layoutAreaPrepared, httpOrigin);
      setState((prevState) => ({
        ...prevState,
        hiddenContainer: hiddenContainer,
        root: root,
      }));
    } else if (state.currentStep === 'LAYOUT' && state.hiddenContainer && state.diagramToLayout) {
      prepareLayoutLabels(state.previousDiagram, state.diagramToLayout);
      if (diagramDescription?.layoutOption === 'AUTO_LAYOUT' && layoutConfigurations[0]) {
        elkLayout(state.diagramToLayout.nodes, state.diagramToLayout.edges, layoutConfigurations[0].layoutOptions).then(
          (layoutNodes) => {
            const updatedLayoutNodes = layoutNodes.map((n) => {
              return {
                ...n,
                data: {
                  ...n.data,
                  isNew: false,
                },
              };
            });
            const laidoutDiagram = layout(
              { nodes: updatedLayoutNodes, edges: state.previousDiagram?.edges ?? [] },
              { nodes: updatedLayoutNodes, edges: state.diagramToLayout?.edges ?? [] },
              state.referencePosition,
              state.layoutDirection,
              nodeLayoutHandlers,
              true
            );
            setState((prevState) => ({
              ...prevState,
              diagramToLayout: null,
              laidoutDiagram: laidoutDiagram,
              currentStep: 'AFTER_LAYOUT',
            }));
          }
        );
      } else {
        const laidoutDiagram = layout(
          state.previousDiagram,
          state.diagramToLayout,
          state.referencePosition,
          state.layoutDirection,
          nodeLayoutHandlers,
          false
        );
        setState((prevState) => ({
          ...prevState,
          diagramToLayout: null,
          laidoutDiagram: laidoutDiagram,
          currentStep: 'AFTER_LAYOUT',
        }));
      }
    } else if (state.currentStep === 'AFTER_LAYOUT' && state.hiddenContainer && state.laidoutDiagram) {
      cleanLayoutArea(state.hiddenContainer, state.root);
      state.onLaidoutDiagram(state.laidoutDiagram);
      setState(() => initialState);
    }
  }, [state.currentStep, state.hiddenContainer, state.referencePosition, state.layoutDirection]);

  return {
    layout: layoutDiagram,
  };
};
