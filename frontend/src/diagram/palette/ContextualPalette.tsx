/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
import { ContextualPaletteProps } from 'diagram/palette/ContextualPalette.types';
import { ToolSection } from 'diagram/palette/tool-section/ToolSection';
import { ToolSeparator } from 'diagram/palette/tool-separator/ToolSeparator';
import { Tool } from 'diagram/palette/tool/Tool';
import { isContextualTool, isDeleteTool } from 'diagram/toolServices';
import React from 'react';
import styles from './ContextualPalette.module.css';
import closeImagePath from './icons/close.svg';
import deleteFromModelImagePath from './icons/delete.svg';
import deleteFromDiagramImagePath from './icons/deleteFromDiagram.svg';
import editImagePath from './icons/edit.svg';

const editTool = {
  id: 'edit',
  type: 'edit',
  imageURL: editImagePath,
  label: 'Edit',
};
const deleteFromModelTool = {
  id: 'delete',
  type: 'delete',
  imageURL: deleteFromModelImagePath,
  label: 'Delete',
};
const deleteFromDiagramTool = {
  id: 'deleteFromDiagram',
  type: 'delete',
  imageURL: deleteFromDiagramImagePath,
  label: 'Delete From Diagram',
};
const closeTool = {
  id: 'close',
  type: 'close',
  imageURL: closeImagePath,
  label: 'Close',
};

/**
 * The component used to display a Contextual Palette.
 *
 * @hmarchadour
 */
export const ContextualPalette = ({
  toolSections,
  targetElement,
  invokeTool,
  invokeLabelEdit,
  invokeCustomDeleteTool,
  invokeDeleteFromModel,
  invokeDeleteFromDiagram,
  invokeClose,
}: ContextualPaletteProps) => {
  let toolSectionsContent;

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
  let customDeleteTool;
  toolSections.forEach((toolSection) => {
    const deleteTools = toolSection.tools.filter((tool) => isDeleteTool(tool, targetElement));
    if (deleteTools.length > 0) {
      customDeleteTool = deleteTools[0];
    }
  });
  let deleteSection;
  if (customDeleteTool) {
    deleteSection = (
      <div className={styles.toolEntry}>
        <Tool tool={customDeleteTool} thumbnail={true} onClick={invokeCustomDeleteTool} />
      </div>
    );
  } else if (invokeDeleteFromModel && invokeDeleteFromDiagram) {
    const deleteToolsSection = {
      label: 'Deletion Tools',
      defaultTool: deleteFromModelTool,
      tools: [deleteFromModelTool, deleteFromDiagramTool],
    };
    const invokeDeleteTool = (tool) => {
      if (tool.id === 'deleteFromDiagram') {
        invokeDeleteFromDiagram();
      } else {
        invokeDeleteFromModel();
      }
    };
    deleteSection = (
      <div className={styles.toolSectionEntry} key={targetElement.id + '-delete-section'}>
        <ToolSection toolSection={deleteToolsSection} onToolClick={(tool) => invokeDeleteTool(tool)} />
      </div>
    );
  } else if (invokeDeleteFromModel) {
    deleteSection = (
      <div className={styles.toolEntry}>
        <Tool tool={deleteFromModelTool} thumbnail={true} onClick={() => invokeDeleteFromModel()} />
      </div>
    );
  }
  let separator;
  if (
    toolSectionsContent &&
    (invokeLabelEdit || customDeleteTool || invokeDeleteFromModel || invokeDeleteFromDiagram)
  ) {
    separator = <ToolSeparator />;
  }
  const closeEntry = (
    <div className={styles.toolEntry}>
      <Tool tool={closeTool} thumbnail={true} onClick={() => invokeClose()} />
    </div>
  );
  let result = <></>;
  if (toolSectionsContent || invokeLabelEdit || invokeDeleteFromModel) {
    result = (
      <>
        <div className={styles.toolbar} data-testid={`PopupToolbar`}>
          <div className={styles.toolEntries}>
            {toolSectionsContent}
            {separator}
            {renameEntry}
            {deleteSection}
            <ToolSeparator />
            {closeEntry}
          </div>
        </div>
      </>
    );
  }
  return result;
};
