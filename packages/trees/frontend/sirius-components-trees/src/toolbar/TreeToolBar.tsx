/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import IconButton from '@material-ui/core/IconButton';
import { makeStyles } from '@material-ui/core/styles';
import { SwapHoriz as SwapHorizIcon } from '@material-ui/icons';
import React from 'react';
import { TreeToolBarProps } from './TreeToolBar.types';
import { TreeToolBarContributionComponentProps } from './TreeToolBarContribution.types';

const useTreeToolbarStyles = makeStyles((theme) => ({
  toolbar: {
    display: 'flex',
    flexDirection: 'row',
    overflow: 'hidden',
    height: theme.spacing(4),
    paddingLeft: theme.spacing(1),
    paddingRight: theme.spacing(1),
    borderBottomWidth: '1px',
    borderBottomStyle: 'solid',
    justifyContent: 'right',
    borderBottomColor: theme.palette.divider,
  },
}));

export const TreeToolBar = ({
  editingContextId,
  onSynchronizedClick,
  synchronized,
  treeToolBarContributionComponents,
  readOnly,
}: TreeToolBarProps) => {
  const classes = useTreeToolbarStyles();

  const preferenceButtonSynchroniseTitle = synchronized
    ? 'Disable synchronisation with representation'
    : 'Enable synchronisation with representation';
  return (
    <>
      <div className={classes.toolbar}>
        {treeToolBarContributionComponents.map((component, index) => {
          const props: TreeToolBarContributionComponentProps = {
            editingContextId: editingContextId,
            disabled: readOnly,
            key: index.toString(),
          };
          const element = React.createElement(component, props);
          return element;
        })}
        <IconButton
          color="inherit"
          size="small"
          aria-label={preferenceButtonSynchroniseTitle}
          title={preferenceButtonSynchroniseTitle}
          onClick={onSynchronizedClick}
          data-testid="tree-synchronise">
          <SwapHorizIcon color={synchronized ? 'inherit' : 'disabled'} />
        </IconButton>
      </div>
    </>
  );
};
