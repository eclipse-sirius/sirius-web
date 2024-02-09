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

import { useSelection } from '@eclipse-sirius/sirius-components-core';
import Typography from '@material-ui/core/Typography';
import { Theme, makeStyles } from '@material-ui/core/styles';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import HelpOutlineOutlined from '@material-ui/icons/HelpOutlineOutlined';
import { TreeItem } from '@material-ui/lab';
import TreeView from '@material-ui/lab/TreeView';
import { useEffect, useRef, useState } from 'react';
import { TreeWidgetProps } from './WidgetEntry.types';

const useStyles = makeStyles<Theme>((theme) => ({
  selected: {
    color: theme.palette.primary.main,
  },
  propertySectionLabel: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
  },
}));

export const TreeWidget = ({ widget }: TreeWidgetProps) => {
  const classes = useStyles();

  const [selected, setSelected] = useState<Boolean>(false);
  const { selection } = useSelection();
  const ref = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    if (ref.current && selection.entries.find((entry) => entry.id === widget.id)) {
      ref.current.focus();
      setSelected(true);
    } else {
      setSelected(false);
    }
  }, [widget, selection]);

  return (
    <>
      <div onFocus={() => setSelected(true)} onBlur={() => setSelected(false)} ref={ref} tabIndex={0}>
        <div className={classes.propertySectionLabel}>
          <Typography variant="subtitle2" className={selected ? classes.selected : ''}>
            {widget.label}
          </Typography>
          {widget.hasHelpText ? (
            <HelpOutlineOutlined color="secondary" style={{ marginLeft: 8, fontSize: 16 }} />
          ) : null}
        </div>
      </div>
      <div>
        <TreeView
          aria-label="Model browser"
          defaultCollapseIcon={<ExpandMoreIcon />}
          defaultExpandIcon={<ChevronRightIcon />}
          expanded={['5']}>
          <TreeItem nodeId="1" label="Item1">
            <TreeItem nodeId="2" label="Item1.1" />
          </TreeItem>
          <TreeItem nodeId="5" label="Item2">
            <TreeItem nodeId="10" label="Item2.1" />
            <TreeItem nodeId="6" label="Item2.2"></TreeItem>
          </TreeItem>
        </TreeView>
      </div>
    </>
  );
};
