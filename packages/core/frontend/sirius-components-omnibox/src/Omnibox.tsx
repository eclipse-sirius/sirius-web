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
import SubdirectoryArrowLeftIcon from '@mui/icons-material/SubdirectoryArrowLeft';
import CircularProgress from '@mui/material/CircularProgress';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import FormControl from '@mui/material/FormControl';
import IconButton from '@mui/material/IconButton';
import Input from '@mui/material/Input';
import { useEffect, useRef, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { OmniboxMode, OmniboxProps, OmniboxState } from './Omnibox.types';
import { OmniboxCommandList } from './OmniboxCommandList';
import { OmniboxObjectList } from './OmniboxObjectList';
import { OmniboxSearchList } from './OmniboxSearchList';
import { useOmniboxCommands } from './useOmniboxCommands';
import { GQLGetOmniboxCommandsQueryVariables } from './useOmniboxCommands.types';
import { useOmniboxSearch } from './useOmniboxSearch';
import { GQLGetOmniboxSearchResultsQueryVariables } from './useOmniboxSearch.types';

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

const isLocalStorageAvailable = (): boolean => {
  let available = false;

  if (window.localStorage) {
    try {
      window.localStorage.setItem('local_storage_availability', 'value');
      window.localStorage.getItem('local_storage_availability');
      window.localStorage.removeItem('local_storage_availability');
      available = true;
    } catch {
      available = false;
    }
  }

  return available;
};

const localStorageKey = 'sirius_web_search_history';

const getPreviousSearches = (): string[] => {
  if (!isLocalStorageAvailable()) return [];
  try {
    const data = window.localStorage.getItem(localStorageKey);
    return data ? JSON.parse(data) : [];
  } catch {
    return [];
  }
};

const saveNewSearchQuery = (newSearch: string) => {
  if (isLocalStorageAvailable() && newSearch && newSearch.length > 0) {
    const previousSearches = getPreviousSearches().filter((value) => value !== newSearch);

    const newPreviousSearch =
      previousSearches.length < 10
        ? [newSearch, ...previousSearches]
        : [newSearch, ...previousSearches.slice(0, previousSearches.length - 1)];

    window.localStorage.setItem(localStorageKey, JSON.stringify(newPreviousSearch));
  }
};

export const Omnibox = ({ open, editingContextId, onClose }: OmniboxProps) => {
  const [state, setState] = useState<OmniboxState>({
    queryHasChanged: true,
    mode: 'Command',
    query: '',
  });

  const { getOmniboxCommands, loading: commandLoading, data: commandData } = useOmniboxCommands();
  const { getOmniboxSearchResults, loading: searchResultsLoading, data: searchResultsData } = useOmniboxSearch();

  const { selection } = useSelection();
  const selectedObjectIds: string[] = selection.entries.map((entry) => entry.id);

  useEffect(() => {
    const variables: GQLGetOmniboxCommandsQueryVariables = {
      editingContextId,
      selectedObjectIds,
      query: '',
    };
    getOmniboxCommands({ variables });
  }, []);

  const inputRef = useRef<HTMLInputElement>(null);
  const listRef = useRef<HTMLUListElement>(null);

  const onChange = () => {
    if (!state.queryHasChanged) {
      setState((prevState) => ({ ...prevState, queryHasChanged: true }));
    }
  };

  const sendQuery = (query: string) => {
    setState((prevState) => ({ ...prevState, queryHasChanged: false }));
    if (state.mode === 'Search') {
      const variables: GQLGetOmniboxSearchResultsQueryVariables = {
        editingContextId,
        selectedObjectIds,
        query,
      };
      saveNewSearchQuery(query);
      getOmniboxSearchResults({ variables });
    } else {
      const variables: GQLGetOmniboxCommandsQueryVariables = {
        editingContextId,
        selectedObjectIds,
        query,
      };
      getOmniboxCommands({ variables });
    }
  };

  const handleKeyDown: React.KeyboardEventHandler<HTMLInputElement | HTMLTextAreaElement> = (event) => {
    if (event.key === 'Enter' && state.queryHasChanged) {
      sendQuery(state.query);
    } else if (event.key === 'ArrowDown' && listRef.current) {
      const firstListItem = listRef.current.childNodes[0];
      if (firstListItem instanceof HTMLElement) {
        firstListItem.focus();
      }
    }
  };

  const onSubmitQuery = () => {
    if (state.queryHasChanged) {
      sendQuery(state.query);
      inputRef.current?.focus();
    }
  };

  const onModeChanged = (mode: OmniboxMode) => {
    setState((prevState) => ({
      ...prevState,
      mode,
      queryHasChanged: true,
      query: '',
    }));
    inputRef.current?.focus();
  };

  let omniboxResult: JSX.Element | null = null;
  if (state.mode === 'Command') {
    omniboxResult = (
      <OmniboxCommandList
        loading={commandLoading}
        data={commandData}
        editingContextId={editingContextId}
        onClose={onClose}
        onModeChanged={onModeChanged}
        ref={listRef}
      />
    );
  }
  const previousSearches = getPreviousSearches();
  if (state.mode === 'Search') {
    omniboxResult = searchResultsData ? (
      <OmniboxObjectList loading={searchResultsLoading} data={searchResultsData} onClose={onClose} ref={listRef} />
    ) : (
      <OmniboxSearchList
        previousSearches={previousSearches}
        onPreviousSearchSelected={(item) => {
          setState((prev) => ({
            ...prev,
            query: item,
            queryHasChanged: true,
          }));
          sendQuery(item);
          inputRef.current?.focus();
        }}
        ref={listRef}
      />
    );
  }

  const { classes } = useOmniboxStyles();

  const dialogContent = (
    <>
      <DialogTitle component="div" className={classes.omniboxInputArea}>
        <ChevronRightIcon className={classes.omniboxIcon} />
        <FormControl variant="standard" className={classes.omniboxFormControl}>
          <Input
            inputRef={inputRef}
            onChange={(event) => {
              setState((prevState) => ({
                ...prevState,
                query: event.target.value,
                queryHasChanged: true,
              }));
              onChange();
            }}
            onKeyDown={handleKeyDown}
            placeholder={
              state.mode === 'Search' ? "Hit 'Enter' to search an element..." : "Hit 'Enter' to search a command..."
            }
            disableUnderline
            autoFocus
            fullWidth
            value={state.query}
            slotProps={{
              input: {
                style: {
                  fontSize: '1.5rem',
                },
              },
            }}
          />
        </FormControl>
        <IconButton
          onClick={onSubmitQuery}
          data-testid="submit-query-button"
          color="primary"
          disabled={!state.queryHasChanged}>
          {commandLoading || searchResultsLoading ? (
            <CircularProgress size="24px" color="inherit" />
          ) : (
            <SubdirectoryArrowLeftIcon color="inherit" />
          )}
        </IconButton>
      </DialogTitle>
      <DialogContent
        dividers={
          (state.mode === 'Command' && commandData !== null) ||
          (state.mode === 'Search' && (searchResultsData !== null || previousSearches.length > 0))
        }
        className={classes.omniboxResultArea}>
        {omniboxResult}
      </DialogContent>
    </>
  );

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
      {dialogContent}
    </Dialog>
  );
};
