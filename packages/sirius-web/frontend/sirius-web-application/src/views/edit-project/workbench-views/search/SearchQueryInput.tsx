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
import { useState } from 'react';
import { SearchQueryInputProps, SearchQueryInputState } from './SearchQueryInput.types';

const initialState: SearchQueryInputState = {
  text: '',
  matchCase: false,
  matchWholeWord: false,
  useRegularExpression: false,
  searchInAttributes: false,
};

export const SearchQueryInput = ({ onLaunchSearch }: SearchQueryInputProps) => {
  const [state, setState] = useState<SearchQueryInputState>(initialState);
  const theme: Theme = useTheme();

  const onToggleMatchCase = () => {
    setState((prevState) => ({ ...prevState, matchCase: !prevState.matchCase }));
  };

  const onToggleMatchWholeWord = () => {
    setState((prevState) => ({ ...prevState, matchWholeWord: !prevState.matchWholeWord }));
  };

  const onToggleUseRegularExpression = () => {
    setState((prevState) => ({ ...prevState, useRegularExpression: !prevState.useRegularExpression }));
  };

  const onClearText = () => {
    setState((prevState) => ({
      ...prevState,
      text: '',
    }));
  };

  const onClearAll = () => {
    setState(initialState);
  };

  const onToggleSearchInAttributes = () => {
    setState((prevState) => ({ ...prevState, searchInAttributes: !prevState.searchInAttributes }));
  };

  return (
    <Box>
      <Box sx={{ padding: theme.spacing(1) }}>
        <TextField
          value={state.text}
          onChange={(event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
            setState((prevState) => ({ ...prevState, text: event.target.value }));
          }}
          onKeyDown={(event) => {
            if ('Enter' === event.key && (event.ctrlKey || event.metaKey)) {
              const { text, matchCase, matchWholeWord, useRegularExpression, searchInAttributes } = state;
              onLaunchSearch({ text, matchCase, matchWholeWord, useRegularExpression, searchInAttributes });
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
              sx: { paddingRight: theme.spacing(1) },
              endAdornment: (
                <InputAdornment position="end">
                  <Tooltip title="Match Case">
                    <IconButton
                      size="small"
                      onClick={onToggleMatchCase}
                      color={state.matchCase ? 'primary' : 'default'}>
                      <TextFieldsIcon />
                    </IconButton>
                  </Tooltip>
                  <Tooltip title="Match Whole Word">
                    <IconButton
                      size="small"
                      onClick={onToggleMatchWholeWord}
                      color={state.matchWholeWord ? 'primary' : 'default'}>
                      <TextFormatIcon />
                    </IconButton>
                  </Tooltip>
                  <Tooltip title="Use Regular Expression">
                    <IconButton
                      size="small"
                      onClick={onToggleUseRegularExpression}
                      color={state.useRegularExpression ? 'primary' : 'default'}>
                      <Typography>.*</Typography>
                    </IconButton>
                  </Tooltip>
                  <Tooltip title="Clear text">
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
        <Typography variant="body1">Search in attributes</Typography>
        <Switch color="secondary" checked={state.searchInAttributes} onClick={onToggleSearchInAttributes} />
      </Box>
      <Box
        sx={{
          padding: theme.spacing(1),
          display: 'flex',
          gap: theme.spacing(1),
        }}>
        <Button
          data-testid="launch-search"
          disabled={state.text.trim() === ''}
          onClick={() => {
            const { text, matchCase, matchWholeWord, useRegularExpression, searchInAttributes } = state;
            onLaunchSearch({ text, matchCase, matchWholeWord, useRegularExpression, searchInAttributes });
          }}
          variant="contained"
          color="secondary"
          sx={{ flexGrow: 1 }}>
          Search
        </Button>
        <Button
          data-testid="clear-search"
          onClick={onClearAll}
          variant="outlined"
          color="secondary"
          sx={{ flexGrow: 0 }}>
          Clear All
        </Button>
      </Box>
    </Box>
  );
};
