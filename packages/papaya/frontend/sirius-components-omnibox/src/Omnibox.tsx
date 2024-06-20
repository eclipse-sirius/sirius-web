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
import Breadcrumbs from '@material-ui/core/Breadcrumbs';
import Dialog from '@material-ui/core/Dialog';
import Divider from '@material-ui/core/Divider';
import FormControl from '@material-ui/core/FormControl';
import Input from '@material-ui/core/Input';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';
import { useRef, useState } from 'react';
import { OmniboxProps, OmniboxState } from './Omnibox.types';

const useOmniboxStyles = makeStyles((theme) => ({
  omnibox: {
    display: 'flex',
    flexDirection: 'column',
  },
  omniboxInputArea: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
  },
  omniboxIcon: {
    minWidth: theme.spacing(4),
    fontSize: '2.5rem',
  },
  omniboxFormControl: {
    paddingTop: theme.spacing(1),
    paddingBottom: theme.spacing(1),
    paddingLeft: theme.spacing(0.5),
    paddingRight: theme.spacing(0.5),
  },
}));

export const Omnibox = ({ open, initialContextEntries, onClose }: OmniboxProps) => {
  const [state, setState] = useState<OmniboxState>({
    contextEntries: initialContextEntries,
    query: '',
    actions: [],
  });

  const inputRef = useRef<HTMLInputElement>(null);
  const listRef = useRef<HTMLUListElement>(null);

  const handleChange: React.ChangeEventHandler<HTMLInputElement | HTMLTextAreaElement> = (event) => {
    const {
      target: { value },
    } = event;
    setState((prevState) => ({ ...prevState, query: value }));
  };

  const handleKeyDown: React.KeyboardEventHandler<HTMLInputElement | HTMLTextAreaElement> = () => {};

  const classes = useOmniboxStyles();
  return (
    <Dialog open={open} onClose={onClose} fullWidth className={classes.omnibox}>
      <div className={classes.omniboxInputArea}>
        <ChevronRightIcon className={classes.omniboxIcon} />
        <Breadcrumbs>
          {state.contextEntries.map((contextEntry) => {
            return <Typography key={contextEntry.id}>{contextEntry.label}</Typography>;
          })}
        </Breadcrumbs>
        <FormControl variant="standard" fullWidth className={classes.omniboxFormControl}>
          <Input
            ref={inputRef}
            value={state.query}
            onChange={handleChange}
            onKeyDown={handleKeyDown}
            placeholder="Run commands..."
            disableUnderline
            autoFocus
            fullWidth
            inputProps={{
              style: {
                fontSize: '1.5rem',
              },
            }}
          />
        </FormControl>
      </div>
      <Divider />
      <List ref={listRef} dense disablePadding>
        {state.actions.map((action) => {
          return (
            <ListItem button key={action.id}>
              <ListItemIcon>{action.icon}</ListItemIcon>
              <ListItemText>{action.label}</ListItemText>
            </ListItem>
          );
        })}
      </List>
    </Dialog>
  );
};
