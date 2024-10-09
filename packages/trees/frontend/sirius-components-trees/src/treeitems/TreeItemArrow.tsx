/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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

import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { makeStyles } from 'tss-react/mui';
import { TreeItemArrowProps } from './TreeItemArrow.types';

const useTreeItemArrowStyle = makeStyles()(() => ({
  arrow: {
    cursor: 'pointer',
  },
  noChildren: {
    paddingLeft: '20px',
  },
}));

export const TreeItemArrow = ({ item, depth, onExpand, 'data-testid': dataTestid }: TreeItemArrowProps) => {
  const { classes } = useTreeItemArrowStyle();
  if (item.hasChildren) {
    const onClick: React.MouseEventHandler = (event) => {
      event.stopPropagation();
      onExpand(item.id, depth);
    };
    if (item.expanded) {
      return (
        <ExpandMoreIcon className={classes.arrow} style={{ fontSize: 20 }} onClick={onClick} data-testid={dataTestid} />
      );
    } else {
      return (
        <ChevronRightIcon
          className={classes.arrow}
          style={{ fontSize: 20 }}
          onClick={onClick}
          data-testid={dataTestid}
        />
      );
    }
  }
  return <div className={classes.noChildren} />;
};
