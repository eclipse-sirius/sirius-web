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
import { INVOKE_TOOL_ACTION, ACTIVE_TOOL_ACTION } from 'diagram/sprotty/Actions';
import { ContextualMenu } from 'diagram/palette/ContextualMenu';

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
  close: PropTypes.func.isRequired,
};

export const ContextualMenuContainer = ({ contextualMenu, modelSource, toolSections, close }) => {
  let contextualMenuContent = <></>;
  if (contextualMenu) {
    const { sourceElement, targetElement, canvasBounds } = contextualMenu;
    const style: CSSProperties = {
      position: 'absolute',
      left: canvasBounds.x + 'px',
      top: canvasBounds.y + 'px',
    };
    const edgeTools = [];
    toolSections.forEach((toolSection) => {
      const filteredTools = toolSection.tools
        .filter((tool) => tool.__typename === 'CreateEdgeTool')
        .filter((tool) =>
          tool.edgeCandidates.some(
            (edgeCandidate) =>
              edgeCandidate.sources.some((source) => source.id === sourceElement.descriptionId) &&
              edgeCandidate.targets.some((target) => target.id === targetElement.descriptionId)
          )
        );
      edgeTools.push(...filteredTools);
    });
    if (edgeTools && edgeTools.length > 0) {
      const invokeToolFromContextualMenu = (tool) => {
        if (modelSource) {
          modelSource.actionDispatcher.dispatchAll([
            { kind: ACTIVE_TOOL_ACTION, activeTool: tool },
            { kind: INVOKE_TOOL_ACTION, element: targetElement },
          ]);
        }
      };
      contextualMenuContent = (
        <div style={style}>
          <ContextualMenu
            tools={edgeTools}
            sourceElement={sourceElement}
            targetElement={targetElement}
            invokeTool={invokeToolFromContextualMenu}
            invokeClose={close}></ContextualMenu>
        </div>
      );
    }
  }

  return contextualMenuContent;
};
ContextualMenuContainer.propTypes = propTypes;
