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
import {
  DEFAULT_STYLE,
  GQLStyledString,
  IconOverlay,
  useSelection,
  useSelectionTargets,
} from '@eclipse-sirius/sirius-components-core';
import { DataTree, GQLDataTree, GQLDataTreeNode } from '@eclipse-sirius/sirius-components-datatree';
import Check from '@mui/icons-material/Check';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import YoutubeSearchedForIcon from '@mui/icons-material/YoutubeSearchedFor';
import CircularProgress from '@mui/material/CircularProgress';
import IconButton from '@mui/material/IconButton';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import { SxProps, Theme } from '@mui/material/styles';
import Typography from '@mui/material/Typography';
import { useEffect, useState } from 'react';
import { List as FixedSizeList, type RowComponentProps } from 'react-window';
import { makeStyles } from 'tss-react/mui';
import { SearchResultProps } from './SearchResult.types';
import { GQLObject, GQLSearchMatch } from './useSearch.types';
import { useTranslation } from 'react-i18next';

const useSearchResultStyles = makeStyles()((theme) => ({
  results: {
    display: 'grid',
    gridTemplateRows: 'min-content 1fr', // status, matches
    gridTemplateColumns: '1fr',
    overflow: 'auto',
  },
  statusMessage: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    gap: '10px',
    padding: '10px',
  },
  statusMessageForResults: {
    display: 'flex',
    justifyContent: 'flex-start',
    alignItems: 'center',
    gap: '10px',
    padding: '10px',
  },
  groupByMenu: {
    alignSelf: 'flex-end',
  },
  groupByTitleItem: {
    paddingLeft: theme.spacing(2),
  },
  matches: {
    overflow: 'auto',
    display: 'grid',
    gridTemplateRows: '1fr',
    gridTemplateColumns: '1fr',
  },
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
  const { t } = useTranslation('sirius-web-application', { keyPrefix: 'ageIndicator' });
  const resultsAge = useSecondsSince(timestamp, 60_000);
  const resultsAgeText =
    resultsAge < 60
      ? t('lessThanAMinute')
      : resultsAge < 3_600
      ? t('minutes', { minutes: Math.floor(resultsAge / 60) })
      : resultsAge < 86_400
      ? t('hours', { hours: Math.floor(resultsAge / 3_600) })
      : t('days', { days: Math.floor(resultsAge / 86_400) });
  return (
    <Typography variant="caption" color="secondary">
      {t('performedAgo', { age: resultsAgeText })}
    </Typography>
  );
};

interface SearchResultState {
  anchorEl: HTMLElement | null;
  groupBy: string[];
}

export const SearchResults = ({ loading, query, result, timestamp }: SearchResultProps) => {
  const { classes } = useSearchResultStyles();
  const { t } = useTranslation('sirius-web-application', { keyPrefix: 'searchResults' });

  const [state, setState] = useState<SearchResultState>({
    anchorEl: null,
    groupBy: [],
  });

  const matches = result?.matches || [];

  let status: JSX.Element;
  if (loading) {
    status = (
      <div className={classes.statusMessage}>
        <CircularProgress size="16px" data-testid="search-in-progress" color="secondary" />
        <Typography variant="subtitle2" color="secondary">
          {t('searchInProgress')}
        </Typography>
      </div>
    );
  } else if (result === null) {
    status = (
      <div className={classes.statusMessage}>
        <Typography variant="subtitle2" color="secondary">
          {t('noSearchLaunchedYet')}
        </Typography>
      </div>
    );
  } else {
    const onMoreClick = (event: React.MouseEvent<HTMLElement>) =>
      setState((prevState) => ({
        ...prevState,
        anchorEl: event.currentTarget,
      }));

    const onCloseContextMenu = () =>
      setState((prevState) => ({
        ...prevState,
        anchorEl: null,
      }));

    const onToggleGroupingBy = (groupId: string) => {
      const isGroupEnabled = state.groupBy.includes(groupId);
      setState((prevState) => ({
        ...prevState,
        groupBy: isGroupEnabled ? prevState.groupBy.filter((g) => g !== groupId) : [...prevState.groupBy, groupId],
      }));
    };

    status = (
      <div className={classes.statusMessageForResults}>
        <YoutubeSearchedForIcon color="secondary" />
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'start' }}>
          <Typography variant="subtitle2" color="secondary">
            {t('resultsFor', { count: matches.length, query: query.text })}
          </Typography>
          <AgeIndicator timestamp={timestamp} />
        </div>
        <div style={{ flexGrow: 1 }} />
        <IconButton className={classes.groupByMenu} aria-label="more" size="small" onClick={onMoreClick}>
          <MoreVertIcon />
        </IconButton>
        {state.anchorEl ? (
          <Menu open anchorEl={state.anchorEl} onClose={onCloseContextMenu}>
            <Typography className={classes.groupByTitleItem}>Group by:</Typography>
            {result.groups.map((group) => {
              return (
                <MenuItem key={group.id} onClick={() => onToggleGroupingBy(group.id)}>
                  {state.groupBy.includes(group.id) ? (
                    <ListItemIcon>
                      <Check />
                    </ListItemIcon>
                  ) : (
                    <ListItemIcon>&nbsp;</ListItemIcon>
                  )}
                  {group.label}
                </MenuItem>
              );
            })}
          </Menu>
        ) : null}
      </div>
    );
  }

  const makeSimpleStyledString = (text: string): GQLStyledString => {
    return {
      styledStringFragments: [
        {
          text,
          styledStringFragmentStyle: DEFAULT_STYLE,
        },
      ],
    };
  };

  const { setSelection } = useSelection();
  const { selectionTargets } = useSelectionTargets();

  const onNodeClick = (node: GQLDataTreeNode) => {
    const newSelection = { entries: [{ id: node.id }] };
    setSelection(newSelection);
    selectionTargets.forEach((target) => target.applySelection(newSelection));
  };

  let dataTree: GQLDataTree | null = null;
  if (state.groupBy.length > 0 && result?.groups) {
    const groupId = state.groupBy[0];
    const group = result.groups.find((group) => group.id === groupId);
    const topLevelNodes: GQLDataTreeNode[] = group.sections.map((section) => {
      return {
        id: section.id,
        parentId: null,
        label: makeSimpleStyledString(section.label),
        iconURLs: [section.iconURL],
        endIconsURLs: [],
      };
    });
    topLevelNodes.push({
      id: '<none>',
      parentId: null,
      label: makeSimpleStyledString('None'),
      iconURLs: [],
      endIconsURLs: [],
    });
    const objectNodes = result.matches.map((match) => {
      return {
        id: match.object.id,
        parentId:
          match.memberships
            .filter((membership) => membership.startsWith(groupId + ':'))
            .map((membership) => membership.substring(membership.indexOf(':') + 1))[0] || '<none>',
        label: makeSimpleStyledString(match.object.label),
        iconURLs: match.object.iconURLs,
        endIconsURLs: [],
        onNodeClick,
      };
    });

    dataTree = {
      id: 'search-results-tree',
      label: 'Search Results',
      iconURLs: [],
      nodes: [...topLevelNodes, ...objectNodes],
    };
  }

  return (
    <div className={classes.results} data-role="result">
      {status}
      {dataTree ? (
        <div style={{ overflow: 'auto' }} data-testid="search-results-datatree">
          <DataTree dataTree={dataTree} onNodeClick={onNodeClick} />
        </div>
      ) : (
        <List dense disablePadding className={classes.matches} data-testid="search-matches">
          {matches.length > 0 ? (
            <FixedSizeList rowComponent={MatchRow} rowProps={{ matches }} rowCount={matches.length} rowHeight={34} />
          ) : null}
        </List>
      )}
    </div>
  );
};

const MatchRow = ({ matches, index, style }: RowComponentProps<{ matches: GQLSearchMatch[] }>) => {
  const { setSelection } = useSelection();
  const { selectionTargets } = useSelectionTargets();
  const { t } = useTranslation('sirius-web-application', { keyPrefix: 'matchRow' });

  const onMatchSelected = (matchedObject: GQLObject) => {
    const newSelection = { entries: [{ id: matchedObject.id }] };
    setSelection(newSelection);
    selectionTargets.forEach((target) => target.applySelection(newSelection));
  };

  const matchItemStyle: SxProps<Theme> = (theme) => ({
    cursor: 'pointer',
    '&:hover': {
      backgroundColor: theme.palette.action.hover,
    },
  });

  const matchedObject = matches[index].object;
  return (
    <ListItem key={index} style={style} sx={matchItemStyle} onClick={() => onMatchSelected(matchedObject)}>
      <ListItemIcon sx={{ minWidth: '24px' }}>
        <IconOverlay iconURLs={matchedObject.iconURLs} alt={t('iconOverlayAlt')} />
      </ListItemIcon>
      <ListItemText
        primary={matchedObject.label}
        sx={{
          overflow: 'hidden',
          textOverflow: 'ellipsis',
          whiteSpace: 'nowrap',
          maxWidth: '100ch',
        }}
      />
    </ListItem>
  );
};
