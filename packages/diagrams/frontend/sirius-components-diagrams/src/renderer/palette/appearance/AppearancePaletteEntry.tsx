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
import { PaletteExtensionEntryComponentProps } from '@eclipse-sirius/sirius-components-palette';
import NavigateNextIcon from '@mui/icons-material/NavigateNext';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import Tooltip from '@mui/material/Tooltip';
import { useTranslation } from 'react-i18next';
import { makeStyles } from 'tss-react/mui';

const useStyle = makeStyles()((theme) => ({
  listItemText: {
    '& .MuiListItemText-primary': {
      whiteSpace: 'nowrap',
      overflow: 'hidden',
      textOverflow: 'ellipsis',
    },
  },
  listItemButton: {
    paddingTop: 0,
    paddingBottom: 0,
  },
  listItemIcon: {
    minWidth: 0,
    marginRight: theme.spacing(2),
  },
}));

export const AppearancePaletteEntry = ({ onToolSectionClick }: PaletteExtensionEntryComponentProps) => {
  const { classes } = useStyle();
  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'diagramPalette' });

  const title = t('appearance');

  return (
    <Tooltip key={`tooltip_appearance`} title={title} placement="right">
      <ListItemButton
        className={classes.listItemButton}
        onClick={(event) => onToolSectionClick(event, 'appearance')}
        data-testid={`toolSection-${title}`}>
        <ListItemText primary={title} className={classes.listItemText} />
        <NavigateNextIcon />
      </ListItemButton>
    </Tooltip>
  );
};
