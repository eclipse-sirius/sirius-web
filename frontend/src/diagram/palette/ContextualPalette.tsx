/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
import { makeStyles } from '@material-ui/core/styles';
import {
  GQLDeletionPolicy,
  GQLDiagramDescription,
  GQLEdgeDescription,
  GQLNodeDescription,
  ToolSection as ToolSectionType,
} from 'diagram/DiagramWebSocketContainer.types';
import { ContextualPaletteProps } from 'diagram/palette/ContextualPalette.types';
import { ToolSection } from 'diagram/palette/tool-section/ToolSection';
import { Tool } from 'diagram/palette/tool/Tool';
import { Node } from 'diagram/sprotty/Diagram.types';
import { isContextualTool } from 'diagram/toolServices';
import { GQLSynchronizationPolicy } from 'index';
import React from 'react';
import { SEdge, SLabel, SModelElement } from 'sprotty';
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

const useContextualPaletteStyle = makeStyles((theme) => ({
  toolbar: {
    background: theme.palette.background.paper,
    border: '1px solid #d1dadb',
    boxShadow: '0px 2px 5px #002b3c40',
    borderRadius: '2px',
    display: 'grid',
    gridTemplateRows: '8px 1fr 8px',
    gridTemplateColumns: '8px 1fr 8px',
    position: 'fixed',
    zIndex: 2,
  },
  toolEntries: {
    gridColumnStart: 2,
    gridRowStart: 2,
    display: 'grid',
    gridTemplateColumns: (props: { itemsPerLine: number }) => `repeat(${props.itemsPerLine}, 1fr)`,
    gridTemplateRows: '1fr',
  },
  toolLine: {
    display: 'flex',
  },
  toolEntry: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    padding: '0 0px 0 8px',
  },
  toolSectionEntry: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
  },
  toolbarClose: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
  },
}));

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
  return diagramDescription.edgeDescriptions.filter((edgeDescription) => edgeDescription.id === edgeDescriptionId)[0];
};

const TOOL_PER_LINE = 15;

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
  const isTargetElementSynchronized = isSynchronized(diagramDescription, targetElement);
  const contextualToolSections: ToolSectionType[] = [];
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

  const atLeastOneEdgeTool = contextualToolSections.some((toolSection) =>
    toolSection.tools.some((tool) => tool.__typename === 'CreateEdgeTool')
  );

  // Find how many items should be display per line.
  let itemsPerLine = getNumberOfColumns(
    contextualToolSections,
    !!invokeLabelEdit,
    !!invokeDelete,
    isTargetElementSynchronized,
    atLeastOneEdgeTool
  );
  const props = { itemsPerLine };
  const classes = useContextualPaletteStyle(props);

  let connectorToolContent: JSX.Element;
  if (atLeastOneEdgeTool) {
    connectorToolContent = (
      <div className={classes.toolEntry}>
        <Tool
          tool={connectorTool}
          thumbnail={true}
          onClick={() => {
            invokeConnectorTool();
          }}
        />
      </div>
    );
  }

  const toolSectionsContent: JSX.Element[] = contextualToolSections.map((toolSection) => {
    return (
      <div className={classes.toolSectionEntry} key={targetElement.id + toolSection.id}>
        <ToolSection toolSection={toolSection} onToolClick={invokeTool} />
      </div>
    );
  });

  let renameEntry;
  if (invokeLabelEdit) {
    renameEntry = (
      <div className={classes.toolEntry}>
        <Tool tool={editTool} thumbnail={true} onClick={() => invokeLabelEdit()} />
      </div>
    );
  }
  let deleteEntry;
  if (invokeDelete) {
    if (isTargetElementSynchronized) {
      deleteEntry = (
        <div className={classes.toolEntry}>
          <Tool tool={semanticDeleteTool} thumbnail={true} onClick={() => invokeDelete(GQLDeletionPolicy.SEMANTIC)} />
        </div>
      );
    } else {
      deleteEntry = (
        <>
          <div className={classes.toolEntry}>
            <Tool
              tool={graphicalDeleteTool}
              thumbnail={true}
              onClick={() => invokeDelete(GQLDeletionPolicy.GRAPHICAL)}
            />
          </div>
          <div className={classes.toolEntry}>
            <Tool tool={semanticDeleteTool} thumbnail={true} onClick={() => invokeDelete(GQLDeletionPolicy.SEMANTIC)} />
          </div>
        </>
      );
    }
  }
  const closeEntry = (
    <div className={classes.toolEntry}>
      <Tool tool={closeTool} thumbnail={true} onClick={() => invokeClose()} />
    </div>
  );

  /*
   * The palette first line is composed of 15 elements:
   *   - the connector tool first, if at leat one edge tool is defined.
   *   - in the last position of the line are in order, the direct edit tool if it exists,
   * the delete tool if it exists and the close button.
   *   - The remaing elements are tool sections
   *
   * All other lines are composed of remaining tool sections.
   */
  const toolPaletteContent: JSX.Element[] = [];
  if ((!!toolSectionsContent && toolSectionsContent.length > 0) || invokeLabelEdit || invokeDelete) {
    // Fill the first line
    const firstLine: JSX.Element[] = [];
    let firstlineToolElementToTake = TOOL_PER_LINE - 1;
    if (!!invokeLabelEdit) {
      firstlineToolElementToTake--;
    }
    if (!!invokeDelete) {
      firstlineToolElementToTake--;
      if (!isTargetElementSynchronized) {
        firstlineToolElementToTake--;
      }
    }
    if (!!connectorToolContent) {
      firstlineToolElementToTake--;
      firstLine.push(connectorToolContent);
    }

    if (!!toolSectionsContent && toolSectionsContent.length > 0) {
      const firstElements = toolSectionsContent.splice(0, firstlineToolElementToTake);
      firstLine.push(...firstElements);
    }

    if (!!invokeLabelEdit) {
      firstLine.push(renameEntry);
    }
    if (!!invokeDelete) {
      firstLine.push(deleteEntry);
    }
    firstLine.push(closeEntry);
    toolPaletteContent.push(<>{firstLine}</>);

    // Fill other lines
    while (!!toolSectionsContent && toolSectionsContent.length > 0) {
      const line = toolSectionsContent.splice(0, TOOL_PER_LINE);
      toolPaletteContent.push(<>{line}</>);
    }
  }

  let result = <></>;
  if (toolPaletteContent.length > 0) {
    result = (
      <>
        <div className={classes.toolbar} data-testid={`PopupToolbar`}>
          <div className={classes.toolEntries}>{toolPaletteContent}</div>
        </div>
      </>
    );
  }
  return result;
};

const getNumberOfColumns = (
  toolSections: ToolSectionType[],
  hasDirectEdit: boolean,
  hasDeleteTool: boolean,
  isTargetElementSynchronized: boolean,
  hasAtLeastOneConnector: boolean
): number => {
  let numberOfColumns = toolSections.length + 1;

  if (hasDirectEdit) {
    numberOfColumns++;
  }
  if (hasDeleteTool) {
    numberOfColumns++;
    if (!isTargetElementSynchronized) {
      numberOfColumns++;
    }
  }
  if (hasAtLeastOneConnector) {
    numberOfColumns++;
  }

  if (numberOfColumns > TOOL_PER_LINE) {
    numberOfColumns = TOOL_PER_LINE;
  }

  return numberOfColumns;
};
