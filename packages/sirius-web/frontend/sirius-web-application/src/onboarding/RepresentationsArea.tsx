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
import { IconOverlay, useSelection } from '@eclipse-sirius/sirius-components-core';
import ArrowBack from '@mui/icons-material/ArrowBack';
import ArrowForward from '@mui/icons-material/ArrowForward';
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import IconButton from '@mui/material/IconButton';
import List from '@mui/material/List';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Typography from '@mui/material/Typography';
import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { makeStyles } from 'tss-react/mui';
import { RepresentationAreaProps, RepresentationAreaState } from './RepresentationsArea.types';
import { useRepresentationMetadata } from './useRepresentationMetadata';
import { GQLRepresentationMetadata } from './useRepresentationMetadata.types';

const useRepresentationAreaStyles = makeStyles()((theme) => ({
  cardContent: {
    overflowY: 'auto',
    maxHeight: theme.spacing(50),
  },
  item: {
    padding: 0,
  },
}));

export const RepresentationsArea = ({ editingContextId }: RepresentationAreaProps) => {
  const [state, setState] = useState<RepresentationAreaState>({
    pageSize: 10,
    startCursor: null,
    endCursor: null,
  });

  const { classes } = useRepresentationAreaStyles();
  const { t } = useTranslation('sirius-web-application', { keyPrefix: 'project.edit' });
  const { setSelection } = useSelection();

  const { data } = useRepresentationMetadata(editingContextId, state.startCursor, state.endCursor, state.pageSize);
  const representations: GQLRepresentationMetadata[] =
    data?.viewer.editingContext?.representations.edges.map((edge) => edge.node) ?? [];
  const hasPreviousPage = data?.viewer.editingContext?.representations.pageInfo.hasPreviousPage ?? false;
  const hasNextPage = data?.viewer.editingContext?.representations.pageInfo.hasNextPage ?? false;

  const onPrevious = () => {
    if (data.viewer.editingContext) {
      const endCursor = data.viewer.editingContext.representations.pageInfo.startCursor;
      setState((prevState) => ({ ...prevState, startCursor: null, endCursor }));
    }
  };

  const onNext = () => {
    if (data.viewer.editingContext) {
      const startCursor = data.viewer.editingContext.representations.pageInfo.endCursor;
      setState((prevState) => ({ ...prevState, startCursor, endCursor: null }));
    }
  };

  return (
    <Card>
      <CardContent className={classes.cardContent}>
        <Typography variant="h6">{t('openRepresentation')}</Typography>
        <Typography color="textSecondary">{t('selectRepresentationToOpen')}</Typography>
        <List dense={true}>
          {representations.map((representation) => {
            return (
              <ListItemButton
                className={classes.item}
                dense
                disableGutters
                key={representation.id}
                data-testid={`onboard-open-${representation.label}`}
                onClick={() =>
                  setSelection({
                    entries: [{ id: representation.id }],
                  })
                }>
                <ListItemIcon>
                  <IconOverlay iconURLs={representation.iconURLs} alt="representation icon" />
                </ListItemIcon>
                <ListItemText primary={representation.label} />
              </ListItemButton>
            );
          })}
        </List>
      </CardContent>
      <CardActions>
        <Box sx={(theme) => ({ display: 'flex', flexDirection: 'row', alignItems: 'center', gap: theme.spacing(1) })}>
          <IconButton onClick={onPrevious} disabled={!hasPreviousPage} data-testid="pagination-prev">
            <ArrowBack />
          </IconButton>
          <IconButton onClick={onNext} disabled={!hasNextPage} data-testid="pagination-next">
            <ArrowForward />
          </IconButton>
        </Box>
      </CardActions>
    </Card>
  );
};
