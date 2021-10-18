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

export function isContextualTool(tool, element) {
  let result = false;
  if (tool.__typename === 'CreateNodeTool') {
    result = canInvokeTool(tool, null, element);
  } else if (tool.__typename === 'CreateEdgeTool') {
    result = tool.edgeCandidates.some((edgeCandidate) =>
      edgeCandidate.sources.some((source) => source.id === element.descriptionId)
    );
  }
  return result;
}

export function canInvokeTool(tool, sourceElement, targetElement) {
  let result = false;
  if (tool.__typename === 'CreateNodeTool') {
    result =
      (tool.appliesToDiagramRoot && targetElement.kind === 'Diagram') ||
      tool.targetDescriptions.some((targetDescription) => targetDescription.id === targetElement.descriptionId);
  } else if (tool.__typename === 'CreateEdgeTool') {
    result = tool.edgeCandidates.some(
      (edgeCandidate) =>
        edgeCandidate.sources.some((source) => source.id === sourceElement.descriptionId) &&
        edgeCandidate.targets.some((target) => target.id === targetElement.descriptionId)
    );
  }
  return result;
}

export function atLeastOneCanInvokeEdgeTool(tools, sourceElement, targetElement) {
  return tools.some((tool) =>
    tool.edgeCandidates.some(
      (edgeCandidate) =>
        edgeCandidate.sources.some((source) => source.id === sourceElement.descriptionId) &&
        edgeCandidate.targets.some((target) => target.id === targetElement.descriptionId)
    )
  );
}
