/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
import {
  GQLDeletionPolicy,
  GQLDiagramDescription,
  GQLEdgeDescription,
  GQLNodeDescription,
} from 'diagram/DiagramWebSocketContainer.types';
import { ContextualPaletteProps } from 'diagram/palette/ContextualPalette.types';
import { ToolSection } from 'diagram/palette/tool-section/ToolSection';
import { ToolSeparator } from 'diagram/palette/tool-separator/ToolSeparator';
import { Tool } from 'diagram/palette/tool/Tool';
import { Node } from 'diagram/sprotty/Diagram.types';
import { isContextualTool } from 'diagram/toolServices';
import { GQLSynchronizationPolicy } from 'index';
import React from 'react';
import { SEdge, SLabel, SModelElement } from 'sprotty';
import styles from './ContextualPalette.module.css';
import closeImagePath from './icons/close.svg';
import connectorImagePath from './icons/connector.svg';
import editImagePath from './icons/edit.svg';
import graphicalDeleteImagePath from './icons/graphicalDelete.svg';
import semanticDeleteImagePath from './icons/semanticDelete.svg';

const connectorTool = {
  id: 'connector',
  type: 'connector',
  imageURL: connectorImagePath,
  label: 'Connector',
};
const editTool = {
  id: 'edit',
  type: 'edit',
  imageURL: editImagePath,
  label: 'Edit',
};
const semanticDeleteTool = {
  id: 'semantic-delete',
  type: 'delete',
  imageURL: semanticDeleteImagePath,
  label: 'Delete from model',
};
const graphicalDeleteTool = {
  id: 'graphical-delete',
  type: 'delete',
  imageURL: graphicalDeleteImagePath,
  label: 'Delete from diagram',
};
const closeTool = {
  id: 'close',
  type: 'close',
  imageURL: closeImagePath,
  label: 'Close',
};

const isSynchronized = (diagramDescription: GQLDiagramDescription, element: SModelElement): boolean => {
  let elementWithTarget = element;
  if (elementWithTarget instanceof SLabel) {
    elementWithTarget = elementWithTarget.parent;
  }

  if (element instanceof Node) {
    const descriptionId = element.descriptionId;
    return (
      findNodeDescription(diagramDescription.nodeDescriptions, [], descriptionId).synchronizationPolicy ===
      GQLSynchronizationPolicy.SYNCHRONIZED
    );
  } else if (element instanceof SEdge) {
    const descriptionId = (element as any).descriptionId; // To remove when the typing will be improved in the Dependency injection
    return (
      findEdgeDescription(diagramDescription, descriptionId).synchronizationPolicy ===
      GQLSynchronizationPolicy.SYNCHRONIZED
    );
  }
  return false;
};

const findNodeDescription = (
  childNodeDescriptions: GQLNodeDescription[],
  borderNodeDescriptions: GQLNodeDescription[],
  nodeDescriptionId: string
): GQLNodeDescription | null => {
  let result: GQLNodeDescription | null = null;

  let childNodeIndex = 0;
  while (childNodeIndex < childNodeDescriptions.length && !result) {
    const childNodeDescription = childNodeDescriptions[childNodeIndex];
    if (childNodeDescription.id === nodeDescriptionId) {
      result = childNodeDescription;
    } else {
      result = findNodeDescription(
        childNodeDescription.childNodeDescriptions ?? [],
        childNodeDescription.borderNodeDescriptions ?? [],
        nodeDescriptionId
      );
    }

    childNodeIndex++;
  }

  let borderNodeIndex = 0;
  while (borderNodeIndex < borderNodeDescriptions.length && !result) {
    const borderNodeDescription = borderNodeDescriptions[borderNodeIndex];
    if (borderNodeDescription.id === nodeDescriptionId) {
      result = borderNodeDescription;
    } else {
      result = findNodeDescription(
        borderNodeDescription.childNodeDescriptions ?? [],
        borderNodeDescription.borderNodeDescriptions ?? [],
        nodeDescriptionId
      );
    }

    borderNodeIndex++;
  }
  return result;
};

const findEdgeDescription = (
  diagramDescription: GQLDiagramDescription,
  edgeDescriptionId: string
): GQLEdgeDescription | null => {
  return diagramDescription.edgdDescriptions.filter((edgeDescription) => edgeDescription.id === edgeDescriptionId)[0];
};

/**
 * The component used to display a Contextual Palette.
 *
 * @hmarchadour
 */
export const ContextualPalette = ({
  diagramDescription,
  toolSections,
  targetElement,
  invokeTool,
  invokeConnectorTool,
  invokeLabelEdit,
  invokeDelete,
  invokeClose,
}: ContextualPaletteProps) => {
  let connectorToolContent;
  let connectorToolSeparator;
  let toolSectionsContent;

  const isTargetElementSynchronized = isSynchronized(diagramDescription, targetElement);

  const contextualToolSections = [];
  // Keep only create node/edge tools
  toolSections.forEach((toolSection) => {
    const filteredTools = toolSection.tools.filter((tool) => isContextualTool(tool, targetElement));
    if (filteredTools.length > 0) {
      // copy toolSection and replace filtered tools.
      const filteredToolSection = Object.assign({}, toolSection);
      filteredToolSection.tools = filteredTools;
      if (!filteredTools.some((filteredTool) => filteredTool.id === toolSection.defaultTool.id)) {
        filteredToolSection.defaultTool = filteredTools[0];
      }
      contextualToolSections.push(filteredToolSection);
    }
  });

  if (contextualToolSections.length > 0) {
    const atLeastOneEdgeTool = contextualToolSections.some((toolSection) =>
      toolSection.tools.some((tool) => tool.__typename === 'CreateEdgeTool')
    );
    if (atLeastOneEdgeTool) {
      connectorToolContent = (
        <div className={styles.toolEntry}>
          <Tool
            tool={connectorTool}
            thumbnail={true}
            onClick={() => {
              invokeConnectorTool();
            }}
          />
        </div>
      );
      connectorToolSeparator = <ToolSeparator />;
    }
    toolSectionsContent = contextualToolSections.map((toolSection) => {
      return (
        <div className={styles.toolSectionEntry} key={targetElement.id + toolSection.id}>
          <ToolSection toolSection={toolSection} onToolClick={invokeTool} />
        </div>
      );
    });
  }
  let renameEntry;
  if (invokeLabelEdit) {
    renameEntry = (
      <div className={styles.toolEntry}>
        <Tool tool={editTool} thumbnail={true} onClick={() => invokeLabelEdit()} />
      </div>
    );
  }
  let deleteEntry;
  if (invokeDelete) {
    if (isTargetElementSynchronized) {
      deleteEntry = (
        <div className={styles.toolEntry}>
          <Tool tool={semanticDeleteTool} thumbnail={true} onClick={() => invokeDelete(GQLDeletionPolicy.SEMANTIC)} />
        </div>
      );
    } else {
      deleteEntry = (
        <>
          <div className={styles.toolEntry}>
            <Tool
              tool={graphicalDeleteTool}
              thumbnail={true}
              onClick={() => invokeDelete(GQLDeletionPolicy.GRAPHICAL)}
            />
          </div>
          <div className={styles.toolEntry}>
            <Tool tool={semanticDeleteTool} thumbnail={true} onClick={() => invokeDelete(GQLDeletionPolicy.SEMANTIC)} />
          </div>
        </>
      );
    }
  }
  let separator;
  if (toolSectionsContent && (invokeLabelEdit || invokeDelete)) {
    separator = <ToolSeparator />;
  }
  const closeEntry = (
    <div className={styles.toolEntry}>
      <Tool tool={closeTool} thumbnail={true} onClick={() => invokeClose()} />
    </div>
  );
  let result = <></>;
  if (toolSectionsContent || invokeLabelEdit || invokeDelete) {
    result = (
      <>
        <div className={styles.toolbar} data-testid={`PopupToolbar`}>
          <div className={styles.toolEntries}>
            {connectorToolContent}
            {connectorToolSeparator}
            {toolSectionsContent}
            {separator}
            {renameEntry}
            {deleteEntry}
            <ToolSeparator />
            {closeEntry}
          </div>
        </div>
      </>
    );
  }
  return result;
};
