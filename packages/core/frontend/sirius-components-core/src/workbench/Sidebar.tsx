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

import IconButton from '@mui/material/IconButton';
import Tooltip from '@mui/material/Tooltip';
import { makeStyles } from 'tss-react/mui';
import { SidebarProps } from './Sidebar.types';

const useSidebarStyles = makeStyles()((theme) => ({
  viewSelectorLeft: {
    display: 'flex',
    flexDirection: 'column',
    background: theme.palette.navigation.leftBackground,
  },
  viewSelectorRight: {
    display: 'flex',
    flexDirection: 'column',
    background: theme.palette.navigation.rightBackground,
  },
  viewSelectorIconLeft: {
    color: theme.palette.text.disabled,
    borderLeftStyle: 'solid',
    borderLeftSize: '2px',
    borderColor: theme.palette.navigation.leftBackground,
    borderRadius: 0,
  },
  viewSelectorIconRight: {
    color: theme.palette.text.disabled,
    borderRightStyle: 'solid',
    borderRightSize: '2px',
    borderRightColor: theme.palette.navigation.rightBackground,
    borderRadius: 0,
  },
  viewSelectorIconSelectedLeft: {
    color: theme.palette.primary.main,
    borderLeft: 'solid 2px',
    borderRadius: 0,
  },
  viewSelectorIconSelectedRight: {
    color: theme.palette.primary.main,
    borderRight: 'solid 2px',
    borderRadius: 0,
  },
  viewIcon: {
    padding: theme.spacing(1),
  },
}));

export const Sidebar = ({ side, contributions, onContributionClick, selectedContributionIds }: SidebarProps) => {
  const { classes } = useSidebarStyles();

  return (
    <div
      className={side === 'left' ? classes.viewSelectorLeft : classes.viewSelectorRight}
      data-testid={`sidebar-${side}`}>
      {contributions.map((contribution) => {
        const { id, title, icon } = contribution;

        let iconClassName = side === 'left' ? classes.viewSelectorIconLeft : classes.viewSelectorIconRight;
        if (selectedContributionIds.includes(id)) {
          iconClassName =
            side === 'left' ? classes.viewSelectorIconSelectedLeft : classes.viewSelectorIconSelectedRight;
        }
        return (
          <Tooltip
            enterDelay={250}
            title={title}
            placement={side === 'left' ? 'right' : 'left'}
            key={id}
            className={classes.viewIcon}>
            <IconButton
              className={iconClassName}
              aria-label={title}
              data-testid={`viewselector-${title}`}
              onClick={() => onContributionClick(id)}>
              {icon}
            </IconButton>
          </Tooltip>
        );
      })}
    </div>
  );
};
