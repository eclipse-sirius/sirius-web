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
import React, { CSSProperties } from 'react';
import PropTypes from 'prop-types';
import { EditLabelAction } from 'sprotty';
import { INVOKE_CONTEXTUAL_TOOL_ACTION } from 'diagram/sprotty/Actions';
import { ContextualPalette } from 'diagram/palette/ContextualPalette';

const toolType = PropTypes.shape({
  id: PropTypes.string.isRequired,
  label: PropTypes.string.isRequired,
  imageURL: PropTypes.string,
});
const propTypes = {
  contextualPalette: PropTypes.object,
  modelSource: PropTypes.object,
  toolSections: PropTypes.arrayOf(
    PropTypes.shape({
      label: PropTypes.string.isRequired,
      tools: PropTypes.arrayOf(toolType).isRequired,
      defaultTool: toolType.isRequired,
    })
  ).isRequired,
  deleteElements: PropTypes.func.isRequired,
  setDefaultTool: PropTypes.func.isRequired,
  close: PropTypes.func.isRequired,
};

export const ContextualPaletteContainer = ({
  contextualPalette,
  modelSource,
  toolSections,
  deleteElements,
  setDefaultTool,
  close,
}) => {
  let contextualPaletteContent = <></>;
  if (contextualPalette) {
    const { element, canvasBounds, origin, renameable, deletable } = contextualPalette;
    const { x, y } = origin;
    const style: CSSProperties = {
      left: canvasBounds.x + 'px',
      top: canvasBounds.y + 'px',
      position: 'absolute',
    };
    let invokeLabelEditFromContextualPalette;
    if (renameable && modelSource) {
      invokeLabelEditFromContextualPalette = () =>
        modelSource.actionDispatcher.dispatch(new EditLabelAction(element.id + '_label'));
    }
    let invokeDeleteFromContextualPalette;
    if (deletable) {
      invokeDeleteFromContextualPalette = () => deleteElements([element]);
    }
    const invokeToolFromContextualPalette = (tool) => {
      if (modelSource) {
        modelSource.actionDispatcher.dispatch({ kind: INVOKE_CONTEXTUAL_TOOL_ACTION, tool, element, x, y });
      }
      setDefaultTool(tool);
      close();
    };

    contextualPaletteContent = (
      <div style={style}>
        <ContextualPalette
          toolSections={toolSections}
          targetElement={element}
          invokeTool={invokeToolFromContextualPalette}
          invokeLabelEdit={invokeLabelEditFromContextualPalette}
          invokeDelete={invokeDeleteFromContextualPalette}
          invokeClose={close}></ContextualPalette>
      </div>
    );
  }

  return contextualPaletteContent;
};
ContextualPaletteContainer.propTypes = propTypes;
