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
import Link from '@material-ui/core/Link';
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import { memo } from 'react';
import { Panel } from 'reactflow';

const usePapayaDiagramInformationPanelStyles = makeStyles((theme) => ({
  papayaDiagramInformationPanel: {
    display: 'flex',
    flexDirection: 'column',
    padding: theme.spacing(1),
    maxWidth: '250px',
  },
  links: {
    display: 'flex',
    flexDirection: 'column',
    listStyle: 'disc',
    listStylePosition: 'inside',
    paddingTop: theme.spacing(1),
  },
}));
export const PapayaDiagramInformationPanel = memo(() => {
  const classes = usePapayaDiagramInformationPanelStyles();

  const { project } = useCurrentProject();

  if (project.natures.filter((nature) => nature.name === 'siriusComponents://nature?kind=papaya').length === 0) {
    return null;
  }
  return (
    <Panel position="bottom-right">
      <Paper className={classes.papayaDiagramInformationPanel}>
        <Typography variant="subtitle2">Learn more</Typography>
        <Typography variant="body2">
          Follow these links to view the code of this Sirius Web internal test project on Github.
        </Typography>
        <ul className={classes.links}>
          <li>
            <Link
              href="https://github.com/eclipse-sirius/sirius-web/tree/master/packages/sirius-web/backend/sirius-web-papaya"
              target="_blank"
              rel="noopener noreferrer">
              Backend code
            </Link>
          </li>
          <li>
            <Link
              href="https://github.com/eclipse-sirius/sirius-web/tree/master/packages/sirius-web/frontend/sirius-web-papaya"
              target="_blank"
              rel="noopener noreferrer">
              Frontend code
            </Link>
          </li>
        </ul>
      </Paper>
    </Panel>
  );
});
