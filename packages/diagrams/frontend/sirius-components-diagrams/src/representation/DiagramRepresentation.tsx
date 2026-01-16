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

import { gql, useQuery } from '@apollo/client';
import {
  RepresentationComponentProps,
  Selection,
  useMultiToast,
  WorkbenchMainRepresentationHandle,
} from '@eclipse-sirius/sirius-components-core';
import { ReactFlowProvider } from '@xyflow/react';
import { ForwardedRef, forwardRef, memo, useEffect, useImperativeHandle, useState } from 'react';
import { DiagramContext } from '../contexts/DiagramContext';
import { DiagramDescriptionContext } from '../contexts/DiagramDescriptionContext';
import { DialogContextProvider } from '../dialog/DialogContext';
import { ManageVisibilityContextProvider } from '../renderer/actions/visibility/ManageVisibilityContextProvider';
import { ConnectorContextProvider } from '../renderer/connector/ConnectorContext';
import { DiagramDirectEditContextProvider } from '../renderer/direct-edit/DiagramDirectEditContext';
import { DropNodeContextProvider } from '../renderer/dropNode/DropNodeContext';
import { MarkerDefinitions } from '../renderer/edge/MarkerDefinitions';
import { FullscreenContextProvider } from '../renderer/fullscreen/FullscreenContext';
import { NodeContextProvider } from '../renderer/node/NodeContext';
import { DiagramPaletteContextProvider } from '../renderer/palette/contexts/DiagramPaletteContext';
import { useApplySelection } from '../renderer/selection/useApplySelection';
import {
  DiagramRepresentationState,
  GQLDiagramDescription,
  GQLDiagramDescriptionData,
  GQLDiagramDescriptionVariables,
} from './DiagramRepresentation.types';
import { DiagramSubscriptionProvider } from './DiagramSubscriptionProvider';

export const getDiagramDescription = gql`
  query getDiagramDescription($editingContextId: ID!, $representationId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $representationId) {
          description {
            ... on DiagramDescription {
              id
              nodeDescriptions {
                id
                userResizable
                keepAspectRatio
                childNodeDescriptionIds
                borderNodeDescriptionIds
              }
              childNodeDescriptionIds
              dropNodeCompatibility {
                droppedNodeDescriptionId
                droppableOnDiagram
                droppableOnNodeTypes
              }
              debug
              arrangeLayoutDirection
              autoLayout
            }
          }
        }
      }
    }
  }
`;

/**
 * This is needed because the DiagramRepresentation component, which receives the `ref`, can not use it itself:
 * the `applySelection` it needs to publish with `useImperativeHandle` can only be implemented from inside
 * the React Flow context (where `useApplySelection()` can be invoked).
 */
const ApplySelectionWrapper = forwardRef(
  (
    props: { representationId: string; children: React.ReactNode },
    ref: ForwardedRef<WorkbenchMainRepresentationHandle>
  ) => {
    const { applySelection: applyAndRevealSelection } = useApplySelection();
    useImperativeHandle(
      ref,
      () => {
        return {
          id: props.representationId,
          applySelection: (selection: Selection) => {
            applyAndRevealSelection(selection, true);
          },
        };
      },
      []
    );
    return <>{props.children}</>;
  }
);

export const DiagramRepresentation = memo(
  forwardRef<WorkbenchMainRepresentationHandle, RepresentationComponentProps>(
    (
      { editingContextId, representationId, readOnly }: RepresentationComponentProps,
      ref: ForwardedRef<WorkbenchMainRepresentationHandle>
    ) => {
      const [state, setState] = useState<DiagramRepresentationState>({
        id: crypto.randomUUID(),
        message: null,
        toolSelections: new Map<string, Selection>(),
      });
      const { addErrorMessage } = useMultiToast();

      const registerPostToolSelection = (id: string, selection: Selection) => {
        setState((prevState) => {
          const newToolSelections = new Map(prevState.toolSelections);
          newToolSelections.set(id, selection);
          return { ...prevState, toolSelections: newToolSelections };
        });
      };

      const consumePostToolSelection = (id: string): Selection | null => {
        const newToolSelections = new Map(state.toolSelections);
        const selection = newToolSelections.get(id);
        if (selection) {
          newToolSelections.delete(id);
          setState((prevState) => {
            return { ...prevState, toolSelections: newToolSelections };
          });
          return selection;
        }
        return null;
      };

      const {
        loading: diagramDescriptionLoading,
        data: diagramDescriptionData,
        error: diagramDescriptionError,
      } = useQuery<GQLDiagramDescriptionData, GQLDiagramDescriptionVariables>(getDiagramDescription, {
        variables: {
          editingContextId,
          representationId,
        },
      });

      useEffect(() => {
        if (!diagramDescriptionLoading) {
          setState((prevState) => ({
            ...prevState,
            diagramDescription: diagramDescriptionData?.viewer.editingContext.representation.description,
          }));
        }
        if (diagramDescriptionError) {
          const { message } = diagramDescriptionError;
          addErrorMessage(message);
        }
      }, [diagramDescriptionLoading, diagramDescriptionData, diagramDescriptionError]);

      const diagramDescription: GQLDiagramDescription | undefined =
        diagramDescriptionData?.viewer.editingContext.representation.description;

      if (state.message) {
        return <div>{state.message}</div>;
      }

      if (!diagramDescription) {
        return <div></div>;
      }

      return (
        <ReactFlowProvider>
          <DiagramDirectEditContextProvider>
            <DiagramPaletteContextProvider>
              <ConnectorContextProvider>
                <DropNodeContextProvider>
                  <NodeContextProvider>
                    <MarkerDefinitions />
                    <FullscreenContextProvider>
                      <DiagramDescriptionContext.Provider value={{ diagramDescription }}>
                        <DiagramContext.Provider
                          value={{
                            editingContextId,
                            diagramId: representationId,
                            readOnly,
                            registerPostToolSelection,
                            consumePostToolSelection,
                            toolSelections: state.toolSelections,
                          }}>
                          <ApplySelectionWrapper representationId={representationId} ref={ref}>
                            <ManageVisibilityContextProvider>
                              <DialogContextProvider>
                                <DiagramSubscriptionProvider
                                  diagramId={representationId}
                                  editingContextId={editingContextId}
                                  readOnly={readOnly}
                                />
                              </DialogContextProvider>
                            </ManageVisibilityContextProvider>
                          </ApplySelectionWrapper>
                        </DiagramContext.Provider>
                      </DiagramDescriptionContext.Provider>
                    </FullscreenContextProvider>
                  </NodeContextProvider>
                </DropNodeContextProvider>
              </ConnectorContextProvider>
            </DiagramPaletteContextProvider>
          </DiagramDirectEditContextProvider>
        </ReactFlowProvider>
      );
    }
  )
);
