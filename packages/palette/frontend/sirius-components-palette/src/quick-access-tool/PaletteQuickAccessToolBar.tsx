/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
import Box from '@mui/material/Box';
import Divider from '@mui/material/Divider';
import { Theme } from '@mui/material/styles';
import { makeStyles } from 'tss-react/mui';
import { PaletteQuickToolContributionProps } from '../extensions/PaletteQuickToolContribution.types';
import { paletteQuickToolExtensionPoint } from '../extensions/PaletteQuickToolExtensionPoints';
import { PaletteQuickAccessToolBarProps } from './PaletteQuickAccessToolBar.types';
import { Tool } from './Tool';

const useStyle = makeStyles()((theme: Theme) => ({
  quickAccessTools: {
    display: 'flex',
    flexWrap: 'nowrap',
    flexDirection: 'row',
    justifyContent: 'flex-start',
    alignItems: 'center',
    gap: theme.spacing(0.5),
    padding: theme.spacing(0.5),
    overflowX: 'auto',
  },
}));

export const PaletteQuickAccessToolBar = ({
  diagramElementIds,
  quickAccessTools,
  onToolClick,
}: PaletteQuickAccessToolBarProps) => {
  const { classes } = useStyle();

  const quickAccessToolComponents: JSX.Element[] = [];
  quickAccessTools.forEach((tool) =>
    quickAccessToolComponents.push(<Tool tool={tool} onClick={onToolClick} key={'tool_' + tool.id} />)
  );

  const paletteToolData: DataExtension<PaletteQuickToolContributionProps[]> = useData(paletteQuickToolExtensionPoint);

  paletteToolData.data
    .filter((data) => data.canHandle(diagramElementIds))
    .map((data) => data.component)
    .forEach((PaletteToolComponent, index) =>
      quickAccessToolComponents.push(
        <PaletteToolComponent
          representationElementIds={diagramElementIds}
          key={'paletteToolComponents_' + index.toString()}
        />
      )
    );

  return quickAccessToolComponents.length > 0 ? (
    <>
      <div>
        <Box className={classes.quickAccessTools}>{quickAccessToolComponents}</Box>
      </div>
      <Divider />
    </>
  ) : null;
};
