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
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Divider from '@mui/material/Divider';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import ListSubheader from '@mui/material/ListSubheader';
import Menu from '@mui/material/Menu';
import Typography from '@mui/material/Typography';
import { useTranslation } from 'react-i18next';
import { makeStyles } from 'tss-react/mui';
import { LaneContextMenuProps } from './LaneContextMenu.types';
import { useCardVisibility } from './useCardVisibility';

const useStyle = makeStyles()((theme) => ({
  listItemText: {
    fontSize: theme.typography.fontSize,
    color: theme.palette.text.primary,
  },
  listItemIconVisible: {
    color: theme.palette.text.primary,
  },
  listItemIconHidden: {
    color: theme.palette.text.disabled,
  },
  buttonContainer: {
    display: 'flex',
  },
}));

export const LaneContextMenu = ({ menuAnchor, onClose, onChangesApplied, cards }: LaneContextMenuProps) => {
  const { handleVisibilityChanged, selectedCardsIds } = useCardVisibility(cards);
  const { t } = useTranslation('sirius-components-deck', { keyPrefix: 'laneContextMenu' });
  const onApplyClicked = () => {
    onChangesApplied(selectedCardsIds);
  };

  const { classes } = useStyle();
  return (
    <>
      <Menu
        id="lane-contextmenu"
        anchorEl={menuAnchor}
        keepMounted
        open
        onClose={onClose}
        data-testid="lane-contextmenu"
        disableRestoreFocus={true}
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}>
        <List
          dense={true}
          component="nav"
          aria-labelledby="nested-list-subheader"
          subheader={
            <ListSubheader className={classes.listItemText} inset={false}>
              <Typography variant="subtitle2">{t('visibleCards')}</Typography>
            </ListSubheader>
          }>
          <Divider />
          {cards.map((card) => (
            <ListItem
              dense={true}
              key={card.id}
              component={'button'}
              onClick={() => handleVisibilityChanged(card.id)}
              data-testid={`hide-reveal-card-${card.title}`}>
              <ListItemIcon className={classes.listItemText}>
                {selectedCardsIds.includes(card.id) ? (
                  <Visibility className={classes.listItemIconVisible} fontSize="small" data-testid="visibility-on" />
                ) : (
                  <VisibilityOff className={classes.listItemIconHidden} fontSize="small" data-testid="visibility-off" />
                )}
              </ListItemIcon>
              <ListItemText className={classes.listItemText} primary={card.title} />
            </ListItem>
          ))}
        </List>
        <Box sx={{ display: 'flex', paddingX: '10px', justifyContent: 'flex-end' }}>
          <Button
            size="small"
            data-testid="apply-card-visibility"
            color="primary"
            onClick={onApplyClicked}
            variant="contained">
            {t('apply')}
          </Button>
        </Box>
      </Menu>
    </>
  );
};
