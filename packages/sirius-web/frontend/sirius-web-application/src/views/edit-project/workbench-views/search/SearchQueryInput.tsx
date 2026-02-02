/*******************************************************************************
 * Copyright (c) 2022, 2026 Obeo.
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
import CancelIcon from '@mui/icons-material/Cancel';
import SearchOutlinedIcon from '@mui/icons-material/SearchOutlined';
import TextFieldsIcon from '@mui/icons-material/TextFields';
import TextFormatIcon from '@mui/icons-material/TextFormat';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import InputAdornment from '@mui/material/InputAdornment';
import { Theme, useTheme } from '@mui/material/styles';
import Switch from '@mui/material/Switch';
import TextField from '@mui/material/TextField';
import Tooltip from '@mui/material/Tooltip';
import Typography from '@mui/material/Typography';
import { ForwardedRef, forwardRef, useImperativeHandle } from 'react';
import { useTranslation } from 'react-i18next';
import { SearchQueryInputProps } from './SearchQueryInput.types';
import { SearchQuery } from './useSearch.types';
import { EMPTY_QUERY, useSearchQuery } from './useSearchQuery';

export const SearchQueryInput = forwardRef<SearchQuery, SearchQueryInputProps>(
  ({ editingContextId, initialQuery, onLaunchSearch }: SearchQueryInputProps, ref: ForwardedRef<SearchQuery>) => {
    const { searchQuery, onSearchQueryChange } = useSearchQuery(editingContextId, initialQuery);

    useImperativeHandle(
      ref,
      () => {
        return searchQuery;
      },
      [searchQuery]
    );

    const theme: Theme = useTheme();
    const { t } = useTranslation('sirius-web-application', { keyPrefix: 'searchQueryInput' });
    const onToggleMatchCase = () => {
      onSearchQueryChange({ ...searchQuery, matchCase: !searchQuery.matchCase });
    };

    const onToggleMatchWholeWord = () => {
      onSearchQueryChange({ ...searchQuery, matchWholeWord: !searchQuery.matchWholeWord });
    };

    const onToggleUseRegularExpression = () => {
      onSearchQueryChange({ ...searchQuery, useRegularExpression: !searchQuery.useRegularExpression });
    };

    const onClearText = () => {
      onSearchQueryChange({ ...searchQuery, text: '' });
    };

    const onClearAll = () => {
      onSearchQueryChange(EMPTY_QUERY);
    };

    const onToggleSearchInAttributes = () => {
      onSearchQueryChange({ ...searchQuery, searchInAttributes: !searchQuery.searchInAttributes });
    };

    const onToggleSearchInLibraries = () => {
      onSearchQueryChange({ ...searchQuery, searchInLibraries: !searchQuery.searchInLibraries });
    };

    return (
      <Box data-role="query">
        <Box sx={{ padding: theme.spacing(1) }}>
          <TextField
            data-testid="search-textfield"
            value={searchQuery.text}
            onChange={(event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
              onSearchQueryChange({ ...searchQuery, text: event.target.value });
            }}
            onKeyDown={(event) => {
              if ('Enter' === event.key && (event.ctrlKey || event.metaKey)) {
                onLaunchSearch(searchQuery);
              }
            }}
            spellCheck={false}
            variant="outlined"
            size="small"
            fullWidth
            autoFocus
            placeholder={t('launchSearchPlaceholder')}
            slotProps={{
              input: {
                startAdornment: (
                  <InputAdornment position="start">
                    <SearchOutlinedIcon />
                  </InputAdornment>
                ),
                sx: { paddingRight: theme.spacing(1) },
                endAdornment: (
                  <InputAdornment position="end">
                    <Tooltip title={t('matchCase')}>
                      <IconButton
                        size="small"
                        onClick={onToggleMatchCase}
                        color={searchQuery.matchCase ? 'primary' : 'default'}>
                        <TextFieldsIcon />
                      </IconButton>
                    </Tooltip>
                    <Tooltip title={t('matchWholeWord')}>
                      <IconButton
                        size="small"
                        onClick={onToggleMatchWholeWord}
                        color={searchQuery.matchWholeWord ? 'primary' : 'default'}>
                        <TextFormatIcon />
                      </IconButton>
                    </Tooltip>
                    <Tooltip title={t('useRegularExpression')}>
                      <IconButton
                        size="small"
                        onClick={onToggleUseRegularExpression}
                        color={searchQuery.useRegularExpression ? 'primary' : 'default'}>
                        <Typography>.*</Typography>
                      </IconButton>
                    </Tooltip>
                    <Tooltip title={t('clearText')}>
                      <IconButton size="small" edge="end" onClick={onClearText}>
                        <CancelIcon />
                      </IconButton>
                    </Tooltip>
                  </InputAdornment>
                ),
              },
            }}
          />
        </Box>
        <Box
          sx={{
            paddingLeft: theme.spacing(1),
            paddingRight: theme.spacing(1),
            display: 'flex',
            flexDirection: 'row',
            alignItems: 'center',
            justifyContent: 'space-between',
          }}>
          <Typography variant="body1">{t('searchInAttributes')}</Typography>
          <Switch
            data-testid="search-in-attributes-toggle"
            color="secondary"
            checked={searchQuery.searchInAttributes}
            onClick={onToggleSearchInAttributes}
          />
        </Box>
        <Box
          sx={{
            paddingLeft: theme.spacing(1),
            paddingRight: theme.spacing(1),
            display: 'flex',
            flexDirection: 'row',
            alignItems: 'center',
            justifyContent: 'space-between',
          }}>
          <Typography variant="body1">{t('searchInLibraries')}</Typography>
          <Switch
            data-testid="search-in-libraries-toggle"
            color="secondary"
            checked={searchQuery.searchInLibraries}
            onClick={onToggleSearchInLibraries}
          />
        </Box>

        <Box
          sx={{
            padding: theme.spacing(1),
            display: 'flex',
            gap: theme.spacing(1),
          }}>
          <Button
            data-testid="launch-search"
            disabled={searchQuery.text.trim() === ''}
            onClick={() => {
              onLaunchSearch(searchQuery);
            }}
            variant="contained"
            color="secondary"
            sx={{ flexGrow: 1 }}>
            {t('search')}
          </Button>
          <Button
            data-testid="clear-search"
            onClick={onClearAll}
            variant="outlined"
            color="secondary"
            sx={{ flexGrow: 0 }}>
            {t('clearAll')}
          </Button>
        </Box>
      </Box>
    );
  }
);
