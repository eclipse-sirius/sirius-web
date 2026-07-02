/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import { DataExtension, useData } from '@eclipse-sirius/sirius-components-core';
import Divider from '@mui/material/Divider';
import { PaletteToolContributionProps } from '../extensions/PaletteToolContribution.types';
import { paletteToolExtensionPoint } from '../extensions/PaletteToolExtensionPoints';
import { PaletteToolOverriddenContributionProps } from '../extensions/PaletteToolOverrideContribution.types';
import { paletteToolOverrideExtensionPoint } from '../extensions/PaletteToolOverrideExtensionPoints';
import { isTool, isToolSection } from '../Palette';
import { GQLPalette, GQLPaletteEntry, GQLTool } from '../Palette.types';
import { ToolListItem } from '../tool-list-item/ToolListItem';
import { usePalette } from '../usePalette';
import { useToolValue } from './useTool.types';

export const useTool = (): useToolValue => {
  const { setLastToolInvokedId } = usePalette();
  const paletteToolData: DataExtension<PaletteToolContributionProps[]> = useData(paletteToolExtensionPoint);
  const paletteToolOverriddenData: DataExtension<PaletteToolOverriddenContributionProps[]> = useData(
    paletteToolOverrideExtensionPoint
  );

  const getRenderedGQLTool = (
    palette: GQLPalette,
    tool: GQLTool,
    representationElementIds: string[],
    representationKind: string,
    onToolClick: (tool: GQLTool) => void
  ): JSX.Element => {
    const overriddenTool = paletteToolOverriddenData.data.find((contributedTool) =>
      contributedTool.canHandle(representationKind, tool)
    );
    if (!overriddenTool) {
      return (
        <ToolListItem
          onToolClick={onToolClick}
          tool={tool}
          disabled={false}
          key={'toolItem_' + tool.id}
          data-testid={`paletteEntry-${tool.label}`}
        />
      );
    } else {
      const OverriddenComponent = overriddenTool.component;
      return (
        <OverriddenComponent
          representationElementIds={representationElementIds}
          onInvoked={() => setLastToolInvokedId(palette.id, tool.id)}
          tool={tool}></OverriddenComponent>
      );
    }
  };

  const getRenderedTool = (
    palette: GQLPalette,
    representationElementIds: string[],
    representationKind: string,
    onToolClick: (tool: GQLTool) => void,
    toolId: string | null
  ): JSX.Element | null => {
    if (!toolId) {
      return null;
    }

    const paletteEntry: GQLPaletteEntry | undefined =
      palette.paletteEntries.find((entry) => entry.id === toolId) ||
      palette.paletteEntries
        .filter(isToolSection)
        .flatMap((entry) => entry.tools)
        .find((entry) => entry.id === toolId);

    if (paletteEntry && isTool(paletteEntry)) {
      return (
        <>
          {getRenderedGQLTool(palette, paletteEntry, representationElementIds, representationKind, onToolClick)}
          <Divider />
        </>
      );
    }

    const contributedTool = paletteToolData.data.find((toolData) => toolData.id === toolId);
    if (contributedTool) {
      const ContributedComponent = contributedTool.component;
      return (
        <>
          <ContributedComponent
            representationElementIds={representationElementIds}
            onInvoked={() => setLastToolInvokedId(palette.id, contributedTool.id)}></ContributedComponent>
          <Divider />
        </>
      );
    }
    return null;
  };

  return {
    getRenderedTool,
    getRenderedGQLTool,
  };
};
