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

import { SourceElement } from '../sprotty/DiagramServer.types';
import {
  SingleClickOnDiagramElementTool,
  SingleClickOnTwoDiagramElementsTool,
  Tool,
} from './DiagramRepresentation.types';

const isSingleClickOnDiagramElementTool = (tool: Tool): tool is SingleClickOnDiagramElementTool =>
  tool.__typename === 'SingleClickOnDiagramElementTool';
const isSingleClickOnTwoDiagramElementsTool = (tool: Tool): tool is SingleClickOnTwoDiagramElementsTool =>
  tool.__typename === 'SingleClickOnTwoDiagramElementsTool';

export const canInvokeTool = (tool: Tool, sourceElement: SourceElement, targetElement) => {
  let result = false;
  if (isSingleClickOnDiagramElementTool(tool)) {
    result =
      (tool.appliesToDiagramRoot && targetElement.kind === 'siriusComponents://representation?type=Diagram') ||
      tool.targetDescriptions.some((targetDescription) => targetDescription.id === targetElement.descriptionId);
  } else if (isSingleClickOnTwoDiagramElementsTool(tool)) {
    result = tool.candidates.some(
      (candidate) =>
        candidate.sources.some((source) => source.id === sourceElement.element.descriptionId) &&
        candidate.targets.some((target) => target.id === targetElement.descriptionId)
    );
  }
  return result;
};

export const atLeastOneSingleClickOnTwoDiagramElementsTool = (
  tools: SingleClickOnTwoDiagramElementsTool[],
  sourceElement: SourceElement,
  targetElement
) => {
  return tools.some((tool) =>
    tool.candidates.some(
      (candidate) =>
        candidate.sources.some((source) => source.id === sourceElement.element.descriptionId) &&
        candidate.targets.some((target) => target.id === targetElement.descriptionId)
    )
  );
};
