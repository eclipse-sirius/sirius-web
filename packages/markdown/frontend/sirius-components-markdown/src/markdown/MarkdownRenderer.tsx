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
import { CodeNode } from '@lexical/code-core';
import { HorizontalRuleNode } from '@lexical/extension';
import { LinkNode } from '@lexical/link';
import { ListItemNode, ListNode } from '@lexical/list';
import { $convertFromMarkdownString, $convertToMarkdownString } from '@lexical/markdown';
import { LexicalComposer } from '@lexical/react/LexicalComposer';
import { useLexicalComposerContext } from '@lexical/react/LexicalComposerContext';
import { LexicalErrorBoundary } from '@lexical/react/LexicalErrorBoundary';
import { LinkPlugin } from '@lexical/react/LexicalLinkPlugin';
import { MarkdownShortcutPlugin } from '@lexical/react/LexicalMarkdownShortcutPlugin';
import { PlainTextPlugin } from '@lexical/react/LexicalPlainTextPlugin';
import { RichTextPlugin } from '@lexical/react/LexicalRichTextPlugin';
import { TabIndentationPlugin } from '@lexical/react/LexicalTabIndentationPlugin';
import { HeadingNode, QuoteNode } from '@lexical/rich-text';
import { $createParagraphNode, $createTextNode, $getRoot, $setSelection, TextNode } from 'lexical';
import { FocusEvent, useCallback, useEffect, useRef, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { CLOSE_LINK_EDITOR_COMMAND, LinkEditorPlugin } from './LinkEditorPlugin';
import { ListPlugin } from './ListPlugin';
import { markdownTransformers } from './MarkdownTransformers';
import { ContentEditableProps, MarkdownRendererProps } from './MarkdownRenderer.types';
import { ToolbarPlugin } from './ToolbarPlugin';

const ContentEditable = ({ readOnly, label }: ContentEditableProps): React.JSX.Element => {
  const [editor] = useLexicalComposerContext();
  const ref = useCallback(
    (rootElement: null | HTMLElement) => {
      editor.setRootElement(rootElement);
    },
    [editor]
  );
  return (
    <div
      ref={ref}
      role="textbox"
      aria-label={label}
      aria-multiline="true"
      aria-readonly={readOnly}
      contentEditable={!readOnly}
      spellCheck={false}></div>
  );
};

const MarkdownEditor = ({
  value,
  placeholder,
  readOnly,
  onBlur,
  plainTextByDefault = false,
}: MarkdownRendererProps) => {
  const [editor] = useLexicalComposerContext();
  const { classes } = useMarkdownRendererStyles();
  const [plainText, setPlainText] = useState<boolean>(plainTextByDefault);
  const draft = useRef<{ value: string; source: string; serialized: string }>({ value, source: value, serialized: '' });

  const readSource = () =>
    editor.read(() => {
      const serialized = plainText ? $getRoot().getTextContent() : $convertToMarkdownString(markdownTransformers);
      return serialized === draft.current.serialized ? draft.current.source : serialized;
    });

  useEffect(() => {
    editor.setEditable(!readOnly);
  }, [editor, readOnly]);

  useEffect(() => {
    if (value !== draft.current.value) {
      draft.current = { value, source: value, serialized: '' };
    }
    editor.update(() => {
      if (plainText) {
        $getRoot()
          .clear()
          .append($createParagraphNode().append($createTextNode(draft.current.source)));
      } else {
        $convertFromMarkdownString(draft.current.source, markdownTransformers);
      }
      draft.current.serialized = plainText
        ? $getRoot().getTextContent()
        : $convertToMarkdownString(markdownTransformers);
      $setSelection(null);
    });
    editor.dispatchCommand(CLOSE_LINK_EDITOR_COMMAND, undefined);
  }, [editor, value, plainText]);

  const toggleMode = () => {
    draft.current.source = readSource();
    editor.dispatchCommand(CLOSE_LINK_EDITOR_COMMAND, undefined);
    setPlainText(!plainText);
  };
  const TextPlugin = plainText ? PlainTextPlugin : RichTextPlugin;

  return (
    <div
      onBlur={(event: FocusEvent<HTMLDivElement, Element>) => {
        const focusMovedToLinkEditor =
          event.relatedTarget instanceof Element && event.relatedTarget.closest('[data-testid="link-editor"]') !== null;
        if (!event.currentTarget.contains(event.relatedTarget) && !focusMovedToLinkEditor) {
          editor.dispatchCommand(CLOSE_LINK_EDITOR_COMMAND, undefined);
          if (!readOnly) {
            onBlur(readSource());
          }
        }
      }}>
      <ToolbarPlugin readOnly={readOnly} plainText={plainText} onModeChange={toggleMode} />
      <div className={classes.editorContainer}>
        {!plainText ? (
          <>
            <MarkdownShortcutPlugin transformers={markdownTransformers} />
            <LinkPlugin />
            {!readOnly ? <LinkEditorPlugin /> : null}
            <ListPlugin />
            <TabIndentationPlugin />
          </>
        ) : null}
        <TextPlugin
          contentEditable={<ContentEditable readOnly={readOnly} label={placeholder} />}
          placeholder={<div className={classes.editorPlaceholder}>{placeholder}</div>}
          ErrorBoundary={LexicalErrorBoundary}
        />
      </div>
    </div>
  );
};

const useMarkdownRendererStyles = makeStyles()((theme) => ({
  editorContainer: {
    marginTop: theme.spacing(2),
    color: theme.palette.text.primary,
    position: 'relative',
    fontWeight: Number(theme.typography.fontWeightRegular),
    textAlign: 'left',
    borderBottom: `1px solid ${theme.palette.divider}`,
    '&:hover': {
      borderBottom: `2px solid ${theme.palette.divider}`,
    },
    '&:focus-within': {
      transition: 'transform 200ms cubic-bezier(0.0, 0, 0.2, 1) 0ms',
      borderBottom: `2px solid ${theme.palette.primary.main}`,
    },
  },
  editorPlaceholder: {
    color: theme.palette.text.secondary,
    top: 0,
    overflow: 'hidden',
    position: 'absolute',
    textOverflow: 'ellipsis',
    fontSize: theme.typography.fontSize,
    userSelect: 'none',
    display: 'inline-block',
    pointerEvents: 'none',
  },
  editorParagraph: {
    margin: 0,
    marginBottom: theme.spacing(2),
    position: 'relative',
  },
  editorTextBold: {
    fontWeight: 'bold',
  },
  editorTextItalic: {
    fontStyle: 'italic',
  },
  editorTextUnderline: {
    textDecoration: 'underline',
  },
  editorTextStrikethrough: {
    textDecoration: 'line-through',
  },
  editorTextUnderlineStrikethrough: {
    textDecoration: 'underline line-through',
  },
  editorTextCode: {
    backgroundColor: theme.palette.background.default,
    padding: '1px 0.25rem',
    fontFamily: 'Menlo, Consolas, Monaco, monospace',
    fontSize: '94%',
  },
  editorHeading1: {
    fontFamily: theme.typography.h4.fontFamily,
    fontSize: theme.typography.h4.fontSize,
    fontWeight: theme.typography.h4.fontWeight,
    lineHeight: theme.typography.h4.lineHeight,
    letterSpacing: theme.typography.h4.letterSpacing,
    color: theme.palette.text.primary,
    margin: 0,
    marginBottom: theme.spacing(3),
    padding: 0,
  },
  editorHeading2: {
    fontFamily: theme.typography.h5.fontFamily,
    fontSize: theme.typography.h5.fontSize,
    fontWeight: theme.typography.h5.fontWeight,
    lineHeight: theme.typography.h5.lineHeight,
    letterSpacing: theme.typography.h5.letterSpacing,
    color: theme.palette.text.secondary,
    margin: 0,
    marginTop: theme.spacing(2),
    padding: 0,
  },
  editorListOl: {
    padding: 0,
    margin: 0,
    marginLeft: theme.spacing(2),
    listStyle: 'decimal',
  },
  editorListUl: {
    padding: 0,
    margin: 0,
    marginLeft: theme.spacing(2),
    listStyle: 'circle',
  },
  editorChecklist: {
    padding: 0,
    margin: 0,
    marginLeft: theme.spacing(2),
    listStyleType: 'none',
  },
  editorListitem: {
    margin: `${theme.spacing(1)} ${theme.spacing(2)} ${theme.spacing(1)} ${theme.spacing(2)}`,
  },
  editorChecklistItem: {
    position: 'relative',
    listStyleType: 'none',
    paddingLeft: theme.spacing(3),
    '&::before': {
      content: '""',
      position: 'absolute',
      left: 0,
      top: '0.25em',
      width: '1em',
      height: '1em',
      border: `${theme.spacing(0.125)} solid ${theme.palette.text.secondary}`,
      borderRadius: theme.shape.borderRadius,
      backgroundColor: theme.palette.background.paper,
    },
  },
  editorChecklistItemChecked: {
    '&::before': {
      borderColor: theme.palette.primary.main,
      backgroundColor: theme.palette.primary.main,
    },
    '&::after': {
      content: '""',
      position: 'absolute',
      left: '0.40em',
      top: '0.48em',
      width: '0.25em',
      height: '0.5em',
      border: `solid ${theme.palette.primary.contrastText}`,
      borderWidth: `0 ${theme.spacing(0.25)} ${theme.spacing(0.25)} 0`,
      transform: 'rotate(45deg)',
    },
  },
  editorNestedListitem: {
    listStyleType: 'none',
    paddingLeft: 0,
    '&::before, &::after': {
      display: 'none',
    },
  },
}));

export const MarkdownRenderer = (props: MarkdownRendererProps) => {
  const { classes } = useMarkdownRendererStyles();
  const theme = {
    placeholder: classes.editorPlaceholder,
    paragraph: classes.editorParagraph,
    heading: {
      h1: classes.editorHeading1,
      h2: classes.editorHeading2,
    },
    list: {
      nested: {
        listitem: classes.editorNestedListitem,
      },
      ol: classes.editorListOl,
      ul: classes.editorListUl,
      checklist: classes.editorChecklist,
      listitem: classes.editorListitem,
      listitemChecked: `${classes.editorChecklistItem} ${classes.editorChecklistItemChecked}`,
      listitemUnchecked: classes.editorChecklistItem,
    },
    text: {
      bold: classes.editorTextBold,
      italic: classes.editorTextItalic,
      underline: classes.editorTextUnderline,
      strikethrough: classes.editorTextStrikethrough,
      underlineStrikethrough: classes.editorTextUnderlineStrikethrough,
      code: classes.editorTextCode,
    },
  };
  const initialConfig = {
    namespace: 'MarkdownRenderer',
    onError: console.error,
    theme,
    nodes: [HeadingNode, ListNode, ListItemNode, QuoteNode, HorizontalRuleNode, TextNode, CodeNode, LinkNode],
  };
  return (
    <LexicalComposer initialConfig={initialConfig}>
      <MarkdownEditor {...props} />
    </LexicalComposer>
  );
};
