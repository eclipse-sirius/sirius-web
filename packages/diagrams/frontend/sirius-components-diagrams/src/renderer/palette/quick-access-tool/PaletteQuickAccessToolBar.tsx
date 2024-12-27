/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import { Edge, Node, useStoreApi } from '@xyflow/react';
import { makeStyles } from 'tss-react/mui';
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { diagramPaletteToolExtensionPoint } from '../extensions/DiagramPaletteToolExtensionPoints';
import { DiagramPaletteToolContributionProps } from './../extensions/DiagramPaletteToolContribution.types';
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
    paddingLeft: theme.spacing(0.5),
    overflowX: 'auto',
  },
}));

export const PaletteQuickAccessToolBar = ({
  diagramElementId,
  quickAccessTools,
  onToolClick,
  x,
  y,
}: PaletteQuickAccessToolBarProps) => {
  const { classes } = useStyle();

  const { nodeLookup, edgeLookup } = useStoreApi<Node<NodeData>, Edge<EdgeData>>().getState();
  let diagramElement = edgeLookup.get(diagramElementId) || nodeLookup.get(diagramElementId);

  const quickAccessToolComponents: JSX.Element[] = [];
  quickAccessTools.forEach((tool) =>
    quickAccessToolComponents.push(<Tool tool={tool} onClick={onToolClick} key={'tool_' + tool.id} />)
  );

  const paletteToolData: DataExtension<DiagramPaletteToolContributionProps[]> = useData(
    diagramPaletteToolExtensionPoint
  );

  paletteToolData.data
    .filter((data) => data.canHandle(diagramElement ?? null))
    .map((data) => data.component)
    .forEach((PaletteToolComponent, index) =>
      quickAccessToolComponents.push(
        <PaletteToolComponent
          x={x}
          y={y}
          diagramElementId={diagramElementId}
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
