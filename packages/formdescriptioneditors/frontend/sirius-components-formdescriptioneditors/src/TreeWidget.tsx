/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import HelpOutlineOutlined from '@mui/icons-material/HelpOutlineOutlined';
import Typography from '@mui/material/Typography';
import { SimpleTreeView } from '@mui/x-tree-view/SimpleTreeView';
import { TreeItem } from '@mui/x-tree-view/TreeItem';
import { useEffect, useRef, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { makeStyles } from 'tss-react/mui';
import { TreeWidgetProps } from './WidgetEntry.types';

const useStyles = makeStyles()((theme) => ({
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
  const { classes } = useStyles();
  const { t } = useTranslation('sirius-components-formdescriptioneditors', { keyPrefix: 'treeWidget' });

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
        <SimpleTreeView
          aria-label={t('modelBrowser')}
          slots={{ collapseIcon: ExpandMoreIcon, expandIcon: ChevronRightIcon }}
          expandedItems={['5']}>
          <TreeItem itemId="1" label={t('item1')}>
            <TreeItem itemId="2" label={t('item1.1')} />
          </TreeItem>
          <TreeItem itemId="5" label={t('item2')}>
            <TreeItem itemId="10" label={t('item2.1')} />
            <TreeItem itemId="6" label={t('item2.2')}></TreeItem>
          </TreeItem>
        </SimpleTreeView>
      </div>
    </>
  );
};
