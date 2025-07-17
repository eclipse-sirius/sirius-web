/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
import MoreVertIcon from '@mui/icons-material/MoreVert';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import { emphasize } from '@mui/material/styles';
import React, { useEffect, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { NavigationBar } from '../../../navigationBar/NavigationBar';
import { useCurrentProject } from '../../../project/useCurrentProject';
import { EditProjectNavbarState } from './EditProjectNavbar.types';
import { EditProjectNavbarContextMenu } from './context-menu/EditProjectNavbarContextMenu';
import { useProjectSubscription } from './useProjectSubscription';
import { GQLProjectEventPayload, GQLProjectRenamedEventPayload } from './useProjectSubscription.types';

const useEditProjectViewNavbarStyles = makeStyles()((theme) => ({
  center: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
  title: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
  },
  titleLabel: {
    marginRight: theme.spacing(2),
    overflow: 'hidden',
    textOverflow: 'ellipsis',
    whiteSpace: 'nowrap',
    maxWidth: '100ch',
  },
  onDarkBackground: {
    '&:hover': {
      backgroundColor: emphasize(theme.palette.secondary.main, 0.08),
    },
  },
}));

const isProjectRenamedEventPayload = (payload: GQLProjectEventPayload): payload is GQLProjectRenamedEventPayload =>
  payload.__typename === 'ProjectRenamedEventPayload';

export const EditProjectNavbar = () => {
  const { project } = useCurrentProject();
  const [state, setState] = useState<EditProjectNavbarState>({
    anchorEl: null,
    projectName: project.name,
  });

  const { classes } = useEditProjectViewNavbarStyles();

  const { payload } = useProjectSubscription(project.id);
  useEffect(() => {
    if (payload && isProjectRenamedEventPayload(payload)) {
      setState((prevState) => ({
        ...prevState,
        projectName: payload.newName,
      }));
    }
  }, [payload]);

  const onMoreClick = (event: React.MouseEvent<HTMLElement>) =>
    setState((prevState) => ({
      ...prevState,
      anchorEl: event.currentTarget,
    }));

  const onCloseContextMenu = () =>
    setState((prevState) => ({
      ...prevState,
      anchorEl: null,
    }));

  return (
    <>
      <NavigationBar>
        <div className={classes.center}>
          <div className={classes.title}>
            <Typography variant="h6" noWrap className={classes.titleLabel} data-testid={`navbar-${state.projectName}`}>
              {state.projectName}
            </Typography>
            <IconButton
              className={classes.onDarkBackground}
              edge="start"
              size="small" // Per #3591 it should remain "small" to keep vertical space for a potential subtitle
              aria-label="more"
              aria-controls="more-menu"
              aria-haspopup="true"
              onClick={onMoreClick}
              color="inherit"
              data-testid="more">
              <MoreVertIcon />
            </IconButton>
          </div>
        </div>
      </NavigationBar>
      {state.anchorEl ? <EditProjectNavbarContextMenu anchorEl={state.anchorEl} onClose={onCloseContextMenu} /> : null}
    </>
  );
};
