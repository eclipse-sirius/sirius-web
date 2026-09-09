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
import { $isLinkNode, $toggleLink } from '@lexical/link';
import { useLexicalComposerContext } from '@lexical/react/LexicalComposerContext';
import { $unwrapNode } from '@lexical/utils';
import CheckIcon from '@mui/icons-material/Check';
import CloseIcon from '@mui/icons-material/Close';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import IconButton from '@mui/material/IconButton';
import Paper from '@mui/material/Paper';
import Popper from '@mui/material/Popper';
import TextField from '@mui/material/TextField';
import {
  $getNearestNodeFromDOMNode,
  $getNodeByKey,
  $getSelection,
  $isRangeSelection,
  $setSelection,
  BaseSelection,
  CLICK_COMMAND,
  COMMAND_PRIORITY_LOW,
  createCommand,
  SELECTION_CHANGE_COMMAND,
} from 'lexical';
import { FocusEvent, FormEvent, KeyboardEvent, useCallback, useEffect, useRef, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { LinkAnchor, LinkEditorMode, LinkEditorPluginState } from './LinkEditorPlugin.types';

export const OPEN_LINK_EDITOR_COMMAND = createCommand<void>('OPEN_LINK_EDITOR_COMMAND');
export const CLOSE_LINK_EDITOR_COMMAND = createCommand<void>('CLOSE_LINK_EDITOR_COMMAND');

const initialState: LinkEditorPluginState = {
  anchor: null,
  linkKey: null,
  linkUrl: '',
  mode: 'closed',
  url: 'https://',
};

const useLinkEditorStyles = makeStyles()((theme) => ({
  popper: {
    zIndex: theme.zIndex.modal,
  },
  paper: {
    padding: theme.spacing(0.5),
  },
  editPaper: {
    border: `1px solid ${theme.palette.divider}`,
    padding: theme.spacing(1),
  },
  form: {
    display: 'flex',
    alignItems: 'center',
    gap: theme.spacing(0.5),
  },
  url: {
    maxWidth: theme.spacing(30),
    overflow: 'hidden',
    textOverflow: 'ellipsis',
    whiteSpace: 'nowrap',
  },
  input: {
    width: theme.spacing(32),
  },
}));

/**
 * Provides MUI controls for inserting, editing, and removing editor links.
 */
export const LinkEditorPlugin = (): JSX.Element => {
  const [editor] = useLexicalComposerContext();
  const { classes } = useLinkEditorStyles();
  const inputRef = useRef<HTMLInputElement>(null);
  const modeRef = useRef<LinkEditorMode>('closed');
  const selectionRef = useRef<BaseSelection | null>(null);
  const [state, setState] = useState<LinkEditorPluginState>(initialState);

  const close = useCallback((): void => {
    selectionRef.current = null;
    modeRef.current = 'closed';
    setState(initialState);
  }, []);

  const getSelectionAnchor = (): LinkAnchor | null => {
    const rootElement = editor.getRootElement();
    const domSelection = window.getSelection();
    if (rootElement === null || domSelection === null || domSelection.rangeCount === 0) {
      return rootElement;
    }
    const range = domSelection.getRangeAt(0);
    if (!rootElement.contains(range.commonAncestorContainer)) {
      return rootElement;
    }
    const rect = range.getBoundingClientRect();
    return { contextElement: rootElement, getBoundingClientRect: () => rect };
  };

  const updateLink = (): boolean => {
    const selection = $getSelection();
    if (!$isRangeSelection(selection)) {
      return false;
    }
    selectionRef.current = selection.clone();
    const node = selection.anchor.getNode();
    const linkNode = $isLinkNode(node) ? node : node.getParent();
    if (!$isLinkNode(linkNode)) {
      return false;
    }
    const key = linkNode.getKey();
    const url = linkNode.sanitizeUrl(linkNode.getURL());
    modeRef.current = 'view';
    setState({
      anchor: editor.getElementByKey(key),
      linkKey: key,
      linkUrl: url,
      mode: 'view',
      url,
    });
    return true;
  };

  useEffect(() => {
    return editor.registerCommand(
      OPEN_LINK_EDITOR_COMMAND,
      () => {
        const selection = $getSelection();
        if (!$isRangeSelection(selection) || selection.isCollapsed()) {
          return false;
        }
        selectionRef.current = selection.clone();
        modeRef.current = 'insert';
        setState({ ...initialState, anchor: getSelectionAnchor(), mode: 'insert' });
        return true;
      },
      COMMAND_PRIORITY_LOW
    );
  }, [editor]);

  useEffect(() => {
    return editor.registerCommand(
      SELECTION_CHANGE_COMMAND,
      () => {
        if (modeRef.current === 'insert' || modeRef.current === 'edit') {
          return false;
        }
        if (updateLink()) {
          return false;
        } else {
          close();
        }
        return false;
      },
      COMMAND_PRIORITY_LOW
    );
  }, [editor]);

  useEffect(() => {
    return editor.registerCommand(
      CLICK_COMMAND,
      (event) => {
        if (modeRef.current === 'insert' || modeRef.current === 'edit' || !(event.target instanceof Element)) {
          return false;
        }
        const linkElement = event.target.closest('a');
        if (linkElement === null) {
          return false;
        }
        const node = $getNearestNodeFromDOMNode(linkElement);
        const linkNode = $isLinkNode(node) ? node : node?.getParent();
        if (!$isLinkNode(linkNode)) {
          return false;
        }
        event.preventDefault();
        const selection = $getSelection();
        if ($isRangeSelection(selection)) {
          selectionRef.current = selection.clone();
        }
        const url = linkNode.sanitizeUrl(linkNode.getURL());
        modeRef.current = 'view';
        setState({
          anchor: linkElement,
          linkKey: linkNode.getKey(),
          linkUrl: url,
          mode: 'view',
          url,
        });
        return true;
      },
      COMMAND_PRIORITY_LOW
    );
  }, [editor]);

  useEffect(() => {
    return editor.registerCommand(
      CLOSE_LINK_EDITOR_COMMAND,
      () => {
        close();
        return false;
      },
      COMMAND_PRIORITY_LOW
    );
  }, [close, editor]);

  useEffect(() => {
    if (state.mode === 'insert' || state.mode === 'edit') {
      inputRef.current?.focus();
    }
  }, [state.mode]);

  const submit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (!state.url) {
      return;
    }
    if (state.mode === 'insert' && selectionRef.current !== null) {
      editor.update(() => {
        $setSelection(selectionRef.current);
        $toggleLink(state.url);
      });
    }
    if (state.mode === 'edit' && selectionRef.current !== null) {
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

  const removeLink = () => {
    const linkKey = state.linkKey;
    if (linkKey !== null) {
      editor.update(() => {
        const node = $getNodeByKey(linkKey);
        if ($isLinkNode(node)) {
          $unwrapNode(node);
        }
      });
    }
    close();
  };

  const openEditMode = (): void => {
    modeRef.current = 'edit';
    setState((currentState) => ({ ...currentState, mode: 'edit', url: currentState.linkUrl }));
  };

  const onLinkEditorBlur = (event: FocusEvent<HTMLDivElement, Element>): void => {
    const editorRoot = editor.getRootElement();
    const focusRemainsInEditor = event.relatedTarget instanceof Element && editorRoot?.contains(event.relatedTarget);
    if (!event.currentTarget.contains(event.relatedTarget) && !focusRemainsInEditor) {
      close();
    }
  };

  const open = state.mode !== 'closed' && state.anchor !== null;
  return (
    <Popper
      anchorEl={state.anchor}
      className={classes.popper}
      modifiers={[
        { name: 'flip', options: { fallbackPlacements: ['bottom-start'] } },
        { name: 'preventOverflow', options: { altAxis: true, padding: 8 } },
      ]}
      open={open}
      placement={state.mode === 'insert' ? 'top-start' : 'bottom-start'}>
      <Paper
        data-testid="link-editor"
        elevation={4}
        onBlur={onLinkEditorBlur}
        className={state.mode === 'insert' || state.mode === 'edit' ? classes.editPaper : classes.paper}>
        {state.mode === 'view' ? (
          <div className={classes.form}>
            <a className={classes.url} href={state.linkUrl} rel="noopener noreferrer" target="_blank">
              {state.linkUrl}
            </a>
            <IconButton
              aria-label="Edit link"
              size="small"
              onMouseDown={(event) => event.preventDefault()}
              onClick={openEditMode}>
              <EditIcon fontSize="small" />
            </IconButton>
            <IconButton
              aria-label="Delete link"
              size="small"
              onMouseDown={(event) => event.preventDefault()}
              onClick={removeLink}>
              <DeleteIcon fontSize="small" />
            </IconButton>
          </div>
        ) : (
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
        )}
      </Paper>
    </Popper>
  );
};
