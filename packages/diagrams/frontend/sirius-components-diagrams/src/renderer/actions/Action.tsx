/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { IconOverlay } from '@eclipse-sirius/sirius-components-core';
import IconButton from '@mui/material/IconButton';
import { makeStyles } from 'tss-react/mui';

import { ActionProps } from './Action.types';
import { useInvokeAction } from './useInvokeAction';

const useStyles = makeStyles()((theme) => ({
  actionIcon: {
    '&:hover': {
      backgroundColor: theme.palette.action.selected,
    },
  },
}));

export const Action = ({ action, diagramElementId, contribution }: ActionProps) => {
  const { classes } = useStyles();

  const { invokeAction } = useInvokeAction(diagramElementId);

  if (contribution) {
    const ActionContributionComponent = contribution.component;
    const invokeRemoteAction = action.remoteExecution ? () => invokeAction(action) : null;
    return (
      <ActionContributionComponent diagramElementId={diagramElementId} invokeRemoteAction={invokeRemoteAction}>
        <IconButton className={classes.actionIcon} size="small">
          <IconOverlay iconURL={action.iconURLs} title={action.tooltip} alt={action.tooltip} />
        </IconButton>
      </ActionContributionComponent>
    );
  } else {
    return (
      <IconButton className={classes.actionIcon} size="small" onClick={() => invokeAction(action)}>
        <IconOverlay iconURL={action.iconURLs} title={action.tooltip} alt={action.tooltip} />
      </IconButton>
    );
  }
};
