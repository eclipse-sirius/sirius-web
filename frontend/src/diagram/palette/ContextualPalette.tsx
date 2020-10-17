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
import React from 'react';
import PropTypes from 'prop-types';
import { Tool } from 'diagram/palette/tool/Tool';
import { ToolSection } from 'diagram/palette/tool-section/ToolSection';
import { ToolSeparator } from 'diagram/palette/tool-separator/ToolSeparator';
import { isContextualTool } from 'diagram/toolServices';
import styles from './ContextualPalette.module.css';
import editImagePath from './icons/edit.svg';
import deleteImagePath from './icons/delete.svg';
import closeImagePath from './icons/close.svg';

const editTool = {
  id: 'edit',
  type: 'edit',
  imageURL: editImagePath,
  label: 'Edit',
};
const deleteTool = {
  id: 'delete',
  type: 'delete',
  imageURL: deleteImagePath,
  label: 'Delete',
};
const closeTool = {
  id: 'close',
  type: 'close',
  imageURL: closeImagePath,
  label: 'Close',
};

const toolType = PropTypes.shape({
  id: PropTypes.string.isRequired,
  label: PropTypes.string.isRequired,
  imageURL: PropTypes.string,
});
const propTypes = {
  toolSections: PropTypes.arrayOf(
    PropTypes.shape({
      label: PropTypes.string.isRequired,
      tools: PropTypes.arrayOf(toolType).isRequired,
      defaultTool: toolType.isRequired,
    })
  ).isRequired,
  targetElement: PropTypes.object.isRequired,
  invokeTool: PropTypes.func.isRequired,
  invokeLabelEdit: PropTypes.func,
  invokeDelete: PropTypes.func,
  invokeClose: PropTypes.func.isRequired,
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
  invokeDelete,
  invokeClose,
}) => {
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
        <div className={styles.toolSectionEntry} key={toolSection.id}>
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
    deleteEntry = (
      <div className={styles.toolEntry}>
        <Tool tool={deleteTool} thumbnail={true} onClick={() => invokeDelete()} />
      </div>
    );
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
ContextualPalette.propTypes = propTypes;
