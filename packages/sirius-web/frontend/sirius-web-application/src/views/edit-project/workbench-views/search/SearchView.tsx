/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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
import {
  IconOverlay,
  useSelection,
  useSelectionTargets,
  WorkbenchViewComponentProps,
  WorkbenchViewHandle,
} from '@eclipse-sirius/sirius-components-core';
import SearchOutlinedIcon from '@mui/icons-material/SearchOutlined';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import InputAdornment from '@mui/material/InputAdornment';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import { Theme, useTheme } from '@mui/material/styles';
import TextField from '@mui/material/TextField';
import { ForwardedRef, forwardRef, useEffect, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { useSearch } from './useSearch';
import { GQLObject, GQLSearchPayload, GQLSearchSuccessPayload } from './useSearch.types';
import { useSearchViewHandle } from './useSearchViewHandle';

const useDetailsViewStyles = makeStyles()((theme) => ({
  idle: {
    padding: theme.spacing(1),
  },
  view: {
    display: 'grid',
    gridTemplateColumns: 'auto',
    gridTemplateRows: 'auto 1fr',
    justifyItems: 'stretch',
    overflow: 'auto',
  },
  toolbar: {
    display: 'flex',
    flexDirection: 'row',
    overflow: 'hidden',
    height: theme.spacing(4),
    paddingLeft: theme.spacing(1),
    paddingRight: theme.spacing(1),
    borderBottomWidth: '1px',
    borderBottomStyle: 'solid',
    justifyContent: 'right',
    alignItems: 'center',
    borderBottomColor: theme.palette.divider,
  },
  content: {
    overflow: 'auto',
  },
}));

const isSearchSuccessPayload = (payload: GQLSearchPayload): payload is GQLSearchSuccessPayload =>
  payload && payload.__typename === 'SearchSuccessPayload';

export const SearchView = forwardRef<WorkbenchViewHandle, WorkbenchViewComponentProps>(
  ({ editingContextId, id }: WorkbenchViewComponentProps, ref: ForwardedRef<WorkbenchViewHandle>) => {
    const [query, setQuery] = useState<string>('');
    const [matches, setMatches] = useState<GQLObject[] | null>(null);
    const { setSelection } = useSelection();
    const { selectionTargets } = useSelectionTargets();
    const theme: Theme = useTheme();

    const { launchSearch, loading, data } = useSearch();
    useEffect(() => {
      if (loading) {
        setMatches(null);
      } else if (isSearchSuccessPayload(data?.viewer?.editingContext?.search)) {
        setMatches(data?.viewer?.editingContext?.search.result.matches);
      }
    }, [loading, data]);

    useSearchViewHandle(id, ref);

    const { classes } = useDetailsViewStyles();

    let result: JSX.Element | string = '';
    if (loading) {
      result = 'Searching...';
    } else if (matches) {
      result = (
        <List dense disablePadding>
          {matches.map((matchedObject, index) => (
            <ListItem
              key={index}
              onClick={() => {
                const newSelection = { entries: [{ id: matchedObject.id }] };
                setSelection(newSelection);
                selectionTargets.forEach((target) => {
                  if (target.applySelection) {
                    target.applySelection(newSelection);
                  }
                });
              }}>
              <ListItemIcon>
                <IconOverlay iconURL={matchedObject.iconURLs} alt="Icon of the object" />
              </ListItemIcon>
              <ListItemText primary={matchedObject.label} />
            </ListItem>
          ))}
        </List>
      );
    }

    const contents = (
      <Box
        sx={{
          display: 'grid',
          gridTemplateRows: 'min-content 1fr',
          gridTemplateColumns: '1fr',
        }}
        data-representation-kind="search-view">
        <Box
          sx={{
            padding: theme.spacing(1),
            display: 'flex',
            flexDirection: 'row',
            gap: theme.spacing(1),
          }}>
          <TextField
            value={query}
            onChange={(event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
              setQuery(event.target.value);
            }}
            onKeyDown={(event) => {
              if ('Enter' === event.key && (event.ctrlKey || event.metaKey)) {
                launchSearch(editingContextId, query);
              }
            }}
            spellCheck={false}
            variant="outlined"
            size="small"
            fullWidth
            autoFocus
            slotProps={{
              input: {
                startAdornment: (
                  <InputAdornment position="start">
                    <SearchOutlinedIcon />
                  </InputAdornment>
                ),
              },
            }}
          />
          <Button
            data-testid="launch-search"
            onClick={() => {
              launchSearch(editingContextId, query);
            }}
            variant="outlined"
            color="primary"
            autoFocus>
            Search
          </Button>
        </Box>
        <Box sx={{ overflow: 'auto' }}>{result}</Box>
      </Box>
    );

    return (
      <div className={classes.view}>
        <div className={classes.content}>{contents}</div>
      </div>
    );
  }
);
