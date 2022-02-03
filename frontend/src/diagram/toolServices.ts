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

import { CreateEdgeTool, CreateNodeTool, Tool } from './DiagramWebSocketContainer.types';
import { SourceElement } from './sprotty/DiagramServer.types';

const isCreateNodeTool = (tool: Tool): tool is CreateNodeTool => tool.__typename === 'CreateNodeTool';
const isCreateEdgeTool = (tool: Tool): tool is CreateEdgeTool => tool.__typename === 'CreateEdgeTool';

export const canInvokeTool = (tool: Tool, sourceElement: SourceElement, targetElement) => {
  let result = false;
  if (isCreateNodeTool(tool)) {
    result =
      (tool.appliesToDiagramRoot && targetElement.kind === 'siriusComponents://representation?type=Diagram') ||
      tool.targetDescriptions.some((targetDescription) => targetDescription.id === targetElement.descriptionId);
  } else if (isCreateEdgeTool(tool)) {
    result = tool.edgeCandidates.some(
      (edgeCandidate) =>
        edgeCandidate.sources.some((source) => source.id === sourceElement.element.descriptionId) &&
        edgeCandidate.targets.some((target) => target.id === targetElement.descriptionId)
    );
  }
  return result;
};

export const atLeastOneCanInvokeEdgeTool = (tools: CreateEdgeTool[], sourceElement: SourceElement, targetElement) => {
  return tools.some((tool) =>
    tool.edgeCandidates.some(
      (edgeCandidate) =>
        edgeCandidate.sources.some((source) => source.id === sourceElement.element.descriptionId) &&
        edgeCandidate.targets.some((target) => target.id === targetElement.descriptionId)
    )
  );
};
