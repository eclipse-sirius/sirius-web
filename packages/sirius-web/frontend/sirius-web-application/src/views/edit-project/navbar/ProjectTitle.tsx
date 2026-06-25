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
import MoreVertIcon from '@mui/icons-material/MoreVert';
import Box from '@mui/material/Box';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import { useState } from 'react';
import { EditProjectNavbarContextMenu } from './context-menu/EditProjectNavbarContextMenu';
import { ProjectTitleProps, ProjectTitleState } from './ProjectTitle.types';

export const ProjectTitle = ({ name, workbenchHandle }: ProjectTitleProps) => {
  const [state, setState] = useState<ProjectTitleState>({
    anchorEl: null,
  });

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
      <Box
        sx={() => ({
          display: 'flex',
          flexDirection: 'row',
        })}>
        <Typography
          variant="h6"
          noWrap
          sx={{
            maxWidth: '100ch',
            overflow: 'hidden',
            whiteSpace: 'nowrap',
            textOverflow: 'ellipsis',
          }}
          data-testid="navbar-title">
          {name}
        </Typography>
        <Box>
          <IconButton data-testid="more" onClick={onMoreClick} sx={(theme) => ({ padding: theme.spacing(0.25) })}>
            <MoreVertIcon
              sx={(theme) => ({
                color: theme.palette.common.white,
                fontSize: theme.spacing(1.75),
              })}
            />
          </IconButton>
        </Box>
      </Box>
      {state.anchorEl ? (
        <EditProjectNavbarContextMenu
          anchorEl={state.anchorEl}
          projectName={name}
          onClose={onCloseContextMenu}
          workbenchHandle={workbenchHandle}
        />
      ) : null}
    </>
  );
};
