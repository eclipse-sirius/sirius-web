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
import Box from '@material-ui/core/Box';
import Button from '@material-ui/core/Button';
import Divider from '@material-ui/core/Divider';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import ListSubheader from '@material-ui/core/ListSubheader';
import Menu from '@material-ui/core/Menu';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Visibility from '@material-ui/icons/Visibility';
import VisibilityOff from '@material-ui/icons/VisibilityOff';
import { LaneContextMenuProps } from './LaneContextMenu.types';
import { useCardVisibility } from './useCardVisibility';

const useStyle = makeStyles((theme) => ({
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
  const onApplyClicked = () => {
    onChangesApplied(selectedCardsIds);
  };

  const classes = useStyle();
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
        getContentAnchorEl={null}
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
              <Typography variant="subtitle2">Visible Cards</Typography>
            </ListSubheader>
          }>
          <Divider />
          {cards.map((card) => (
            <ListItem
              button
              dense={true}
              key={card.id}
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
            Apply
          </Button>
        </Box>
      </Menu>
    </>
  );
};
