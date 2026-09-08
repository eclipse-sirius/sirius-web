/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { $toggleLink } from '@lexical/link';
import { useLexicalComposerContext } from '@lexical/react/LexicalComposerContext';
import CheckIcon from '@mui/icons-material/Check';
import CloseIcon from '@mui/icons-material/Close';
import IconButton from '@mui/material/IconButton';
import Paper from '@mui/material/Paper';
import Popper from '@mui/material/Popper';
import TextField from '@mui/material/TextField';
import {
  $getSelection,
  $isRangeSelection,
  $setSelection,
  BaseSelection,
  COMMAND_PRIORITY_LOW,
  createCommand,
} from 'lexical';
import { FocusEvent, FormEvent, KeyboardEvent, useCallback, useEffect, useRef, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { LinkAnchor, LinkEditorPluginState } from './LinkEditorPlugin.types';

export const OPEN_LINK_EDITOR_COMMAND = createCommand<void>('OPEN_LINK_EDITOR_COMMAND');
export const CLOSE_LINK_EDITOR_COMMAND = createCommand<void>('CLOSE_LINK_EDITOR_COMMAND');

const initialState: LinkEditorPluginState = { anchor: null, mode: 'closed', url: 'https://' };

const useLinkEditorStyles = makeStyles()((theme) => ({
  popper: { zIndex: theme.zIndex.modal },
  paper: { border: `1px solid ${theme.palette.divider}`, padding: theme.spacing(1) },
  form: { alignItems: 'center', display: 'flex', gap: theme.spacing(0.5) },
  input: { width: theme.spacing(32) },
}));

/** Provides MUI controls for inserting editor links. */
export const LinkEditorPlugin = (): JSX.Element => {
  const [editor] = useLexicalComposerContext();
  const { classes } = useLinkEditorStyles();
  const inputRef = useRef<HTMLInputElement>(null);
  const selectionRef = useRef<BaseSelection | null>(null);
  const [state, setState] = useState<LinkEditorPluginState>(initialState);

  const close = useCallback((): void => {
    selectionRef.current = null;
    setState(initialState);
  }, []);

  const getSelectionAnchor = (): LinkAnchor | null => {
    const rootElement = editor.getRootElement();
    const domSelection = window.getSelection();
    if (rootElement === null || domSelection === null || domSelection.rangeCount === 0) return rootElement;
    const range = domSelection.getRangeAt(0);
    if (!rootElement.contains(range.commonAncestorContainer)) return rootElement;
    const rect = range.getBoundingClientRect();
    return { contextElement: rootElement, getBoundingClientRect: () => rect };
  };

  useEffect(
    () =>
      editor.registerCommand(
        OPEN_LINK_EDITOR_COMMAND,
        () => {
          const selection = $getSelection();
          if (!$isRangeSelection(selection) || selection.isCollapsed()) return false;
          selectionRef.current = selection.clone();
          setState({ anchor: getSelectionAnchor(), mode: 'insert', url: 'https://' });
          return true;
        },
        COMMAND_PRIORITY_LOW
      ),
    [editor]
  );

  useEffect(
    () =>
      editor.registerCommand(
        CLOSE_LINK_EDITOR_COMMAND,
        () => {
          close();
          return false;
        },
        COMMAND_PRIORITY_LOW
      ),
    [close, editor]
  );

  useEffect(() => {
    if (state.mode === 'insert') inputRef.current?.focus();
  }, [state.mode]);

  const submit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (state.url && selectionRef.current !== null) {
      editor.update(() => {
        $setSelection(selectionRef.current);
        $toggleLink(state.url);
      });
    }
    close();
  };

  const onKeyDown = (event: KeyboardEvent<HTMLInputElement>) => {
    if (event.key === 'Escape') {
      event.preventDefault();
      close();
    }
  };

  const onLinkEditorBlur = (event: FocusEvent<HTMLDivElement, Element>): void => {
    if (!event.currentTarget.contains(event.relatedTarget)) close();
  };

  return (
    <Popper anchorEl={state.anchor} className={classes.popper} open={state.anchor !== null} placement="top-start">
      <Paper data-testid="link-editor" elevation={4} onBlur={onLinkEditorBlur} className={classes.paper}>
        <form className={classes.form} onSubmit={submit}>
          <TextField
            autoComplete="off"
            className={classes.input}
            inputRef={inputRef}
            label="Link URL"
            onChange={(event) => setState((currentState) => ({ ...currentState, url: event.target.value }))}
            onKeyDown={onKeyDown}
            size="small"
            value={state.url}
          />
          <IconButton aria-label="Confirm link" size="small" type="submit">
            <CheckIcon fontSize="small" />
          </IconButton>
          <IconButton aria-label="Cancel link" size="small" onClick={close}>
            <CloseIcon fontSize="small" />
          </IconButton>
        </form>
      </Paper>
    </Popper>
  );
};
