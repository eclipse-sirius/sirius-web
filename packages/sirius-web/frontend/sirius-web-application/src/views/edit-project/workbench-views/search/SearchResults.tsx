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
import { IconOverlay, useSelection, useSelectionTargets } from '@eclipse-sirius/sirius-components-core';
import YoutubeSearchedForIcon from '@mui/icons-material/YoutubeSearchedFor';
import CircularProgress from '@mui/material/CircularProgress';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Typography from '@mui/material/Typography';
import { useEffect, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { SearchResultProps } from './SearchResult.types';
import { GQLObject } from './useSearch.types';

const useSearchResultStyles = makeStyles()((theme) => ({
  results: {
    display: 'grid',
    gridTemplateRows: 'min-content 1fr', // status, matches
    gridTemplateColumns: '1fr',
    overflow: 'auto',
  },
  statusMessage: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    gap: '10px',
    padding: '10px',
  },
  matches: { overflow: 'auto' },
  matchItem: {
    cursor: 'pointer',
    '&:hover': {
      backgroundColor: theme.palette.action.hover,
    },
  },
}));

const useSecondsSince = (startTime: number, refreshDelay: number = 1000): number => {
  const [secondsSince, setSecondsSince] = useState<number>(0);

  useEffect(() => {
    if (startTime) {
      const timeout = setInterval(() => {
        setSecondsSince(Math.floor((Date.now() - startTime) / 1000));
      }, refreshDelay);
      return () => clearInterval(timeout);
    } else {
      return undefined;
    }
  }, [startTime, refreshDelay]);

  return secondsSince;
};

const AgeIndicator = ({ timestamp }: { timestamp: number }) => {
  const resultsAge = useSecondsSince(timestamp, 60_000);
  const resultsAgeText =
    resultsAge < 60
      ? `less than a minute`
      : resultsAge < 3_600
      ? `${Math.floor(resultsAge / 60)}m`
      : resultsAge < 86_400
      ? `${Math.floor(resultsAge / 3_600)}h`
      : `${Math.floor(resultsAge / 86_400)}d`;
  return (
    <Typography variant="caption" color="secondary">
      {`Performed ${resultsAgeText} ago`}
    </Typography>
  );
};

export const SearchResults = ({ loading, query, result, timestamp }: SearchResultProps) => {
  const { classes } = useSearchResultStyles();
  const { setSelection } = useSelection();
  const { selectionTargets } = useSelectionTargets();

  const matches = result?.matches || [];

  let status: JSX.Element;
  if (loading) {
    status = (
      <>
        <CircularProgress size="16px" data-testid="search-in-progress" color="secondary" />
        <Typography variant="subtitle2" color="secondary">
          Search in progress...
        </Typography>
      </>
    );
  } else if (result === null) {
    status = (
      <Typography variant="subtitle2" color="secondary">
        No search launched yet.
      </Typography>
    );
  } else {
    status = (
      <>
        <YoutubeSearchedForIcon color="secondary" />
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'start' }}>
          <Typography variant="subtitle2" color="secondary">
            {`${matches.length} result${matches.length > 1 ? 's' : ''} for '${query.text}'`}
          </Typography>
          <AgeIndicator timestamp={timestamp} />
        </div>
      </>
    );
  }

  const onMatchSelected = (match: GQLObject) => {
    const newSelection = { entries: [{ id: match.id }] };
    setSelection(newSelection);
    selectionTargets.forEach((target) => {
      if (target.applySelection) {
        target.applySelection(newSelection);
      }
    });
  };

  return (
    <div className={classes.results}>
      <div className={classes.statusMessage}>{status}</div>
      {matches ? (
        <List dense disablePadding className={classes.matches} data-testid="search-matches">
          {matches.map((matchedObject, index) => (
            <ListItem key={index} className={classes.matchItem} onClick={() => onMatchSelected(matchedObject)}>
              <ListItemIcon>
                <IconOverlay iconURLs={matchedObject.iconURLs} alt="Icon of the object" />
              </ListItemIcon>
              <ListItemText primary={matchedObject.label} />
            </ListItem>
          ))}
        </List>
      ) : null}
    </div>
  );
};
