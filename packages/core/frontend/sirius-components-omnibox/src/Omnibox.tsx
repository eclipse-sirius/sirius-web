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
import { GQLOmniboxCommand } from './useWorkbenchOmniboxCommands.types';
import { useTranslation } from 'react-i18next';

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

export const Omnibox = ({ open, loading, commands, onQuery, onCommandClick, onClose }: OmniboxProps) => {
  const { t } = useTranslation('sirius-components-core', { keyPrefix: 'omnibox' });
  const [state, setState] = useState<OmniboxState>({
    queryHasChanged: true,
    mode: 'Command',
  });

  useEffect(() => {
    onQuery('', 'Command');
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
    onQuery(query, state.mode);
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

  const onModeChanged = (mode: OmniboxMode) => {
    setState((prevState) => ({
      ...prevState,
      mode,
      queryHasChanged: true,
    }));
    if (inputRef.current) {
      inputRef.current.value = '';
    }
    inputRef.current?.focus();
  };

  const handleCommandClick = (command: GQLOmniboxCommand) => {
    onCommandClick(command, state.mode);
  };

  const { classes } = useOmniboxStyles();

  const dialogContent = (
    <>
      <DialogTitle component="div" className={classes.omniboxInputArea}>
        <ChevronRightIcon className={classes.omniboxIcon} />
        <FormControl variant="standard" className={classes.omniboxFormControl}>
          <Input
            inputRef={inputRef}
            onChange={() => onChange()}
            onKeyDown={handleKeyDown}
            placeholder={state.mode === 'Search' ? t('searchElement') : t('searchCommand')}
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
        <IconButton
          onClick={onSubmitQuery}
          data-testid="submit-query-button"
          color="primary"
          disabled={!state.queryHasChanged}>
          {loading ? <CircularProgress size="24px" color="inherit" /> : <SubdirectoryArrowLeftIcon color="inherit" />}
        </IconButton>
      </DialogTitle>
      <DialogContent dividers={state.mode === 'Command' && commands !== null} className={classes.omniboxResultArea}>
        <OmniboxCommandList
          loading={loading}
          commands={commands}
          onClose={onClose}
          onModeChanged={onModeChanged}
          onCommandClick={handleCommandClick}
          ref={listRef}
        />
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
