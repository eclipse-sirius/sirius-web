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
import React, { useEffect, useCallback } from 'react';
import PropTypes from 'prop-types';
import { HOVER_ACTION } from 'diagram/sprotty/Actions';
import { canDrop } from 'diagram/toolServices';
import { useMutation } from 'common/GraphQLHooks';
import { useProject } from 'project/ProjectProvider';
import { invokeDropToolOnDiagramMutation } from './operations';

const DATA_DIAGRAM_ID = 'data-diagramid';

const DATA_DESCRIPTION_ID = 'data-descriptionid';

const DATA_NODE_ID = 'data-nodeid';

const SVG_NAMESPACE = 'http://www.w3.org/2000/svg';

const toolType = PropTypes.shape({
  id: PropTypes.string.isRequired,
  label: PropTypes.string.isRequired,
  imageURL: PropTypes.string,
});
const propTypes = {
  representationId: PropTypes.string.isRequired,
  modelSource: PropTypes.object,
  toolSections: PropTypes.arrayOf(
    PropTypes.shape({
      label: PropTypes.string.isRequired,
      tools: PropTypes.arrayOf(toolType).isRequired,
      defaultTool: toolType.isRequired,
    })
  ).isRequired,
  setError: PropTypes.func.isRequired,
  children: PropTypes.node.isRequired,
};

export const DropArea = ({ representationId, modelSource, toolSections, setError, children }) => {
  const { id } = useProject();

  const [invokeDropToolMutation, invokeDropToolResult] = useMutation(
    invokeDropToolOnDiagramMutation,
    {},
    'invokeDropToolOnDiagram'
  );

  useEffect(() => {
    if (!invokeDropToolResult?.loading && invokeDropToolResult?.data?.data) {
      const { invokeDropToolOnDiagram } = invokeDropToolResult.data.data;
      if (invokeDropToolOnDiagram.__typename === 'ErrorPayload') {
        setError(invokeDropToolOnDiagram.message);
      }
    }
  }, [invokeDropToolResult, setError]);

  const dropElements = useCallback(
    (tool: any, objectIds: string[], diagramElementId?: string) => {
      const { id: toolId } = tool;
      const input = {
        projectId: id,
        representationId,
        objectIds,
        toolId,
      };
      if (diagramElementId) {
        input['diagramElementId'] = diagramElementId;
      }

      invokeDropToolMutation({ input });
    },
    [id, representationId, invokeDropToolMutation]
  );

  const searchId = useCallback((dom) => {
    if (dom.hasAttribute(DATA_DIAGRAM_ID)) {
      return dom.getAttribute(DATA_DIAGRAM_ID);
    } else if (dom.hasAttribute(DATA_NODE_ID)) {
      return dom.getAttribute(DATA_NODE_ID);
    } else if (dom.parentElement) {
      const parentDom = dom.parentElement;
      if (parentDom.namespaceURI === SVG_NAMESPACE) {
        return searchId(parentDom);
      }
    }
    return null;
  }, []);

  const searchDescriptionId = useCallback((dom) => {
    if (dom.hasAttribute(DATA_DESCRIPTION_ID)) {
      return dom.getAttribute(DATA_DESCRIPTION_ID);
    } else if (dom.parentElement) {
      const parentDom = dom.parentElement;
      if (parentDom.namespaceURI === SVG_NAMESPACE) {
        return searchDescriptionId(parentDom);
      }
    }
    return null;
  }, []);

  const searchDropTool = useCallback(
    (targetId, targetDescriptionId) => {
      let dropTool;
      for (let toolSection of toolSections) {
        const dropTools = toolSection.tools.filter((tool) =>
          canDrop(tool, targetId, targetDescriptionId, representationId)
        );
        if (dropTools && dropTools.length > 0) {
          dropTool = dropTools[0];
          break;
        }
      }
      return dropTool;
    },
    [representationId, toolSections]
  );

  const handleDragOver = useCallback(
    (e) => {
      e.preventDefault();
      if (modelSource) {
        const id = searchId(e.target);
        modelSource.actionDispatcher.dispatch({ kind: HOVER_ACTION, id });
        const descriptionId = searchDescriptionId(e.target);
        const tool = searchDropTool(id, descriptionId);
        if (tool) {
          e.dataTransfer.dropEffect = 'link';
        }
      }
    },
    [modelSource, searchId, searchDescriptionId, searchDropTool]
  );

  const handleDragLeave = useCallback(
    (e) => {
      e.preventDefault();
      const dom = e.target;
      if (modelSource && dom.hasAttribute(DATA_DIAGRAM_ID)) {
        modelSource.actionDispatcher.dispatch({ kind: HOVER_ACTION });
      }
    },
    [modelSource]
  );

  const handleDrop = useCallback(
    (e) => {
      e.preventDefault();
      const objectIds = JSON.parse(e.dataTransfer.getData('ids'));

      const id = searchId(e.target);
      const descriptionId = searchDescriptionId(e.target);
      const tool = searchDropTool(id, descriptionId);

      if (tool && objectIds && objectIds.length > 0) {
        const diagramElementId = searchId(e.target);
        if (diagramElementId) {
          dropElements(tool, objectIds, diagramElementId);
        } else {
          dropElements(tool, objectIds);
        }
      }
    },
    [searchDropTool, dropElements, searchDescriptionId, searchId]
  );

  const style = { display: 'grid', gridTemplateColumns: '1fr', gridTemplateRows: '1fr' };

  return (
    <div
      style={style}
      onDrop={(e) => handleDrop(e)}
      onDragOver={(e) => handleDragOver(e)}
      onDragLeave={(e) => handleDragLeave(e)}>
      {children}
    </div>
  );
};
DropArea.propTypes = propTypes;
