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
import { IconOverlay, useSelection } from '@eclipse-sirius/sirius-components-core';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import SubdirectoryArrowLeftIcon from '@mui/icons-material/SubdirectoryArrowLeft';
import Button from '@mui/material/Button';
import CircularProgress from '@mui/material/CircularProgress';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import FormControl from '@mui/material/FormControl';
import Input from '@mui/material/Input';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import { useRef, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { OmniboxAction, OmniboxProps, OmniboxState } from './Omnibox.types';
import { useOmniboxCommands } from './useOmniboxCommands';
import { GQLGetOmniboxCommandsQueryVariables } from './useOmniboxCommands.types';

const useOmniboxStyles = makeStyles()((theme) => ({
  omnibox: {
    display: 'flex',
    flexDirection: 'column',
  },
  omniboxPaper: {
    position: 'fixed',
    top: '10%',
  },
  omniboxInputArea: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    paddingTop: theme.spacing(0),
    paddingBottom: theme.spacing(0),
    paddingLeft: theme.spacing(0),
    paddingRight: theme.spacing(1),
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
    flexGrow: 1,
  },
  omniboxResultArea: {
    maxHeight: '330px',
    padding: theme.spacing(0),
  },
}));

export const Omnibox = ({ open, initialContextEntries, onClose }: OmniboxProps) => {
  const [state, setState] = useState<OmniboxState>({
    contextEntries: initialContextEntries,
    queryHasChanged: true,
  });

  const { setSelection } = useSelection();
  const { getOmniboxCommands, loading, data } = useOmniboxCommands();

  const inputRef = useRef<HTMLInputElement>(null);
  const listRef = useRef<HTMLUListElement>(null);

  const onChange = () => {
    if (!state.queryHasChanged) {
      setState((prevState) => ({ ...prevState, queryHasChanged: true }));
    }
  };

  const sendQuery = (query: string) => {
    setState((prevState) => ({ ...prevState, queryHasChanged: false }));
    const variables: GQLGetOmniboxCommandsQueryVariables = {
      contextEntries: state.contextEntries.map((entry) => ({ id: entry.id, kind: entry.kind })),
      query,
    };
    getOmniboxCommands({ variables });
  };

  const handleKeyDown: React.KeyboardEventHandler<HTMLInputElement | HTMLTextAreaElement> = (event) => {
    if (event.key === 'Enter' && state.queryHasChanged) {
      sendQuery(event.currentTarget.value);
    } else if (event.key === 'ArrowDown' && listRef.current) {
      const firstListItem = listRef.current.childNodes[0];
      if (firstListItem instanceof HTMLElement) {
        firstListItem.focus();
      }
    }
  };

  const onSubmitQuery = () => {
    if (state.queryHasChanged) {
      sendQuery(inputRef?.current?.value ?? '');
      if (inputRef?.current) {
        inputRef.current.focus();
      }
    }
  };

  const handleListItemKeyDown: React.KeyboardEventHandler<HTMLDivElement> = (event) => {
    if (event.key === 'ArrowDown') {
      const nextListItemButton = event.currentTarget.nextSibling;
      if (nextListItemButton instanceof HTMLElement) {
        nextListItemButton.focus();
      }
    } else if (event.key === 'ArrowUp') {
      const previousListItemButton = event.currentTarget.previousSibling;
      if (previousListItemButton instanceof HTMLElement) {
        previousListItemButton.focus();
      }
    } else {
      inputRef.current?.focus();
    }
  };

  const handleOnActionClick = (action: OmniboxAction) => {
    setSelection({ entries: [{ id: action.id, kind: action.kind }] });
    onClose();
  };

  let listItems: JSX.Element[] = [];
  if (loading) {
    listItems = [
      <ListItem key={'loading-action-key'}>
        <ListItemText sx={{ color: (theme) => theme.palette.text.disabled }} data-testid="fetch-omnibox-result">
          Fetching result...
        </ListItemText>
      </ListItem>,
    ];
  }
  if (!loading && data) {
    listItems = [
      <ListItem key={'no-result-key'}>
        <ListItemText data-testid="omnibox-no-result">No result</ListItemText>
      </ListItem>,
    ];
    const omniboxCommands = data.viewer.omniboxCommands.edges.map((edge) => edge.node);
    if (omniboxCommands.length > 0) {
      listItems = omniboxCommands
        .map((node) => {
          return {
            id: node.id,
            icon: <IconOverlay iconURL={node.iconURLs} alt={node.kind} />,
            kind: node.kind,
            label: node.label,
          };
        })
        .map((action) => {
          return (
            <ListItemButton
              key={action.id}
              data-testid={action.label}
              onClick={() => handleOnActionClick(action)}
              onKeyDown={handleListItemKeyDown}>
              <ListItemIcon>{action.icon}</ListItemIcon>
              <ListItemText>{action.label}</ListItemText>
            </ListItemButton>
          );
        });
    }
  }

  const { classes } = useOmniboxStyles();
  return (
    <Dialog
      open={open}
      onClose={onClose}
      fullWidth
      PaperProps={{
        className: classes.omniboxPaper,
      }}
      className={classes.omnibox}
      scroll="paper"
      data-testid="omnibox">
      <DialogTitle component="div" className={classes.omniboxInputArea}>
        <ChevronRightIcon className={classes.omniboxIcon} />
        <FormControl variant="standard" className={classes.omniboxFormControl}>
          <Input
            inputRef={inputRef}
            onChange={() => onChange()}
            onKeyDown={handleKeyDown}
            placeholder="Hit 'Enter' to run the command..."
            disableUnderline
            autoFocus
            fullWidth
            slotProps={{
              input: {
                style: {
                  fontSize: '1.5rem',
                },
              },
            }}
          />
        </FormControl>
        <Button
          type="submit"
          variant="contained"
          color="primary"
          disabled={!state.queryHasChanged}
          onClick={onSubmitQuery}
          startIcon={
            loading ? <CircularProgress size="24px" color="inherit" /> : <SubdirectoryArrowLeftIcon color="inherit" />
          }
          data-testid="submit-query-button">
          Submit
        </Button>
      </DialogTitle>
      <DialogContent dividers={listItems.length > 0} className={classes.omniboxResultArea}>
        <List ref={listRef} dense disablePadding>
          {listItems}
        </List>
      </DialogContent>
    </Dialog>
  );
};
