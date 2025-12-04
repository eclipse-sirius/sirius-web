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
import { DataExtension, useData } from '@eclipse-sirius/sirius-components-core';
import { PaletteExtensionSectionComponentProps } from '@eclipse-sirius/sirius-components-palette';
import NavigateBeforeIcon from '@mui/icons-material/NavigateBefore';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import Tooltip from '@mui/material/Tooltip';
import Typography from '@mui/material/Typography';
import React from 'react';
import { useTranslation } from 'react-i18next';
import { makeStyles } from 'tss-react/mui';
import { PaletteAppearanceSectionContributionProps } from './extensions/PaletteAppearanceSectionContribution.types';
import { paletteAppearanceSectionExtensionPoint } from './extensions/PaletteAppearanceSectionExtensionPoints';

const useStyle = makeStyles()((theme) => ({
  toolListItemButton: {
    paddingTop: 0,
    paddingBottom: 0,
  },
  toolList: {
    width: '100%',
    padding: 0,
  },
  listItemText: {
    '& .MuiListItemText-primary': {
      whiteSpace: 'nowrap',
      overflow: 'hidden',
      textOverflow: 'ellipsis',
    },
  },
  sectionTitleListItemText: {
    '& .MuiListItemText-primary': {
      fontWeight: theme.typography.fontWeightBold,
      whiteSpace: 'nowrap',
      overflow: 'hidden',
      textOverflow: 'ellipsis',
    },
  },
}));

export const PaletteAppearanceSection = ({
  diagramElementIds,
  onBackToMainList,
}: PaletteExtensionSectionComponentProps) => {
  const { classes } = useStyle();
  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'paletteAppearanceSection' });

  const handleBackToMainListClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>): void => {
    event.stopPropagation();
    onBackToMainList();
  };

  const paletteAppearanceSectionData: DataExtension<PaletteAppearanceSectionContributionProps[]> = useData(
    paletteAppearanceSectionExtensionPoint
  );

  const paletteAppearanceSectionComponents: JSX.Element[] = paletteAppearanceSectionData.data
    .filter((data) => data.canHandle(diagramElementIds))
    .map((data) => data.component)
    .map((PaletteAppearanceSectionComponent, index) => (
      <PaletteAppearanceSectionComponent
        diagramElementIds={diagramElementIds}
        key={'paletteAppearanceSectionComponents_' + index.toString()}
      />
    ));

  return (
    <List className={classes.toolList} component="nav">
      <Tooltip title={t('appearance')} key="tooltip_appearance" placement="right">
        <ListItemButton
          className={classes.toolListItemButton}
          onClick={handleBackToMainListClick}
          data-testid={`back-Appearance`}
          autoFocus={true}>
          <NavigateBeforeIcon />
          <ListItemText className={classes.sectionTitleListItemText} primary={t('appearance')} />
        </ListItemButton>
      </Tooltip>
      {paletteAppearanceSectionComponents.length > 0 ? (
        paletteAppearanceSectionComponents
      ) : (
        <ListItem>
          <Typography>{t('noAppearanceEditorAvailableForThisStyleOfElement')}</Typography>
        </ListItem>
      )}
    </List>
  );
};
