/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import { useCurrentProject } from '@eclipse-sirius/sirius-web-application';
import Paper from '@mui/material/Paper';
import Typography from '@mui/material/Typography';
import { Panel, ReactFlowState, useStore } from '@xyflow/react';
import { memo } from 'react';
import { makeStyles } from 'tss-react/mui';

const usePapayaDiagramLegendPanelStyles = makeStyles()((theme) => ({
  papayaDiagramLegendPanel: {
    display: 'flex',
    flexDirection: 'column',
    padding: theme.spacing(1),
    minWidth: '100px',
  },
}));

const nodesLengthSelector = (state: ReactFlowState) => Array.from(state.nodeLookup.values()).length || 0;
const edgesLengthSelector = (state: ReactFlowState) => Array.from(state.edges.values()).length || 0;

export const PapayaDiagramLegendPanel = memo(() => {
  const { classes } = usePapayaDiagramLegendPanelStyles();

  const nodesLength = useStore(nodesLengthSelector);
  const edgesLength = useStore(edgesLengthSelector);

  const { project } = useCurrentProject();
  if (project.natures.filter((nature) => nature.name === 'siriusComponents://nature?kind=papaya').length === 0) {
    return null;
  }
  return (
    <Panel position="bottom-left">
      <Paper className={classes.papayaDiagramLegendPanel}>
        <Typography variant="subtitle2" gutterBottom>
          Legend
        </Typography>
        <Typography variant="body2">{nodesLength} nodes</Typography>
        <Typography variant="body2">{edgesLength} edges</Typography>
      </Paper>
    </Panel>
  );
});
