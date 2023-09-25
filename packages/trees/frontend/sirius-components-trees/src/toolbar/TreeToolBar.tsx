/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import { makeStyles } from '@material-ui/core/styles';
import React from 'react';
import { TreeFiltersMenu } from '../views/TreeFiltersMenu';
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
  treeFilters,
  onTreeFilterMenuItemClick,
  treeToolBarContributionComponents,
  readOnly,
}: TreeToolBarProps) => {
  const classes = useTreeToolbarStyles();

  let treeFiltersMenu: JSX.Element;
  if (treeFilters.length > 0) {
    treeFiltersMenu = <TreeFiltersMenu filters={treeFilters} onTreeFilterMenuItemClick={onTreeFilterMenuItemClick} />;
  }

  return (
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
      {treeFiltersMenu}
    </div>
  );
};
