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
import { useCallback, useContext } from 'react';
import { useEdges, useNodes } from 'reactflow';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { DiagramDirectEditContext } from './DiagramDirectEditContext';
import { DiagramDirectEditContextValue } from './DiagramDirectEditContext.types';
import { UseDiagramDirectEditValue } from './useDiagramDirectEdit.types';

const directEditActivationValidCharacters = /[\w&é§èàùçÔØÁÛÊË"«»’”„´$¥€£\\¿?!=+-,;:%/{}[\]–#@*.]/;

export const useDiagramDirectEdit = (): UseDiagramDirectEditValue => {
  const { currentlyEditedLabelId, editingKey, setCurrentlyEditedLabelId, resetDirectEdit } =
    useContext<DiagramDirectEditContextValue>(DiagramDirectEditContext);
  const nodes = useNodes<NodeData>();
  const edges = useEdges<EdgeData>();

  const onDirectEdit = useCallback(
    (event: React.KeyboardEvent<Element>) => {
      const { key } = event;
      /*If a modifier key is hit alone, do nothing*/
      const isTextField = event.target instanceof HTMLInputElement || event.target instanceof HTMLTextAreaElement;
      if (((event.altKey || event.shiftKey) && event.getModifierState(key)) || isTextField) {
        return;
      }

      event.preventDefault();
      const validFirstInputChar =
        !event.metaKey && !event.ctrlKey && key.length === 1 && directEditActivationValidCharacters.test(key);
      let currentlyEditedLabelId: string | undefined | null = nodes.find((node) => node.selected)?.data.label?.id;
      let isLabelEditable: boolean | undefined = nodes.find((node) => node.selected)?.data.labelEditable;
      if (!currentlyEditedLabelId) {
        currentlyEditedLabelId = edges.find((edge) => edge.selected)?.data?.label?.id;
      }

      if (currentlyEditedLabelId && isLabelEditable) {
        if (validFirstInputChar) {
          setCurrentlyEditedLabelId('keyDown', currentlyEditedLabelId, key);
        } else if (key === 'F2') {
          setCurrentlyEditedLabelId('F2', currentlyEditedLabelId, null);
        }
      }
    },
    [setCurrentlyEditedLabelId, nodes]
  );

  return {
    currentlyEditedLabelId,
    editingKey,
    setCurrentlyEditedLabelId,
    resetDirectEdit,
    onDirectEdit,
  };
};
