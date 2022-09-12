/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
import { CodeNode } from '@lexical/code';
import { LinkNode } from '@lexical/link';
import { ListItemNode, ListNode } from '@lexical/list';
import { $convertFromMarkdownString, $convertToMarkdownString, TRANSFORMERS } from '@lexical/markdown';
import { LexicalComposer } from '@lexical/react/LexicalComposer';
import { useLexicalComposerContext } from '@lexical/react/LexicalComposerContext';
import LexicalErrorBoundary from '@lexical/react/LexicalErrorBoundary';
import { HorizontalRuleNode } from '@lexical/react/LexicalHorizontalRuleNode';
import { MarkdownShortcutPlugin } from '@lexical/react/LexicalMarkdownShortcutPlugin';
import { OnChangePlugin } from '@lexical/react/LexicalOnChangePlugin';
import { RichTextPlugin } from '@lexical/react/LexicalRichTextPlugin';
import { HeadingNode, QuoteNode } from '@lexical/rich-text';
import Button from '@material-ui/core/Button';
import { makeStyles } from '@material-ui/core/styles';
import { TextNode } from 'lexical';
import { useCallback, useEffect, useState } from 'react';
import { ListPlugin } from './ListPlugin';
import { RichTextEditorProps } from './RichTextEditor.types';
import { ToolbarPlugin } from './ToolbarPlugin';

interface ContentEditableProps {
  onFocus: () => void;
  readOnly: boolean;
  edited: boolean;
  onSave: (newValue: string) => void;
}

const useContentEditableStyles = makeStyles((theme) => ({
  message: {
    position: 'absolute',
    textAlign: 'right',
    padding: theme.spacing(1),
    bottom: 0,
    right: 0,
  },
}));

/**
 * A content-editable div managed by lexical, but which also invokes our onFocus callback.
 */
const ContentEditable = ({ onFocus, readOnly, edited, onSave }: ContentEditableProps): JSX.Element => {
  const [editor] = useLexicalComposerContext();
  const classes = useContentEditableStyles();
  const ref = useCallback(
    (rootElement: null | HTMLElement) => {
      editor.setRootElement(rootElement);
    },
    [editor]
  );
  let message = null;
  if (edited) {
    message = (
      <div className={classes.message}>
        <Button
          color="primary"
          variant="contained"
          size="small"
          onClick={() => {
            editor.getEditorState().read(() => {
              const markdown = $convertToMarkdownString(TRANSFORMERS);
              onSave(markdown);
            });
          }}>
          Save changes
        </Button>
      </div>
    );
  }
  return (
    <div>
      <div ref={ref} contentEditable={!readOnly} spellCheck={false} onFocus={onFocus}></div>
      {message}
    </div>
  );
};

/**
 * Updates the editor's content when we get a new value for the widget's text.
 */
const ValueUpdater = ({
  value,
  setEdited,
}: {
  value: string;
  setEdited: (edited: boolean) => void;
}): JSX.Element | null => {
  const [editor] = useLexicalComposerContext();
  useEffect(() => {
    editor.update(() => {
      $convertFromMarkdownString(value, TRANSFORMERS);
      setEdited(null);
    });
  }, [editor, value]);
  return null;
};

const useRichTextEditorStyles = makeStyles((theme) => ({
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
  editorListitem: {
    margin: `${theme.spacing(2)} ${theme.spacing(8)} ${theme.spacing(2)} ${theme.spacing(8)}`,
  },
  editorNestedListitem: {
    listStyleType: 'none',
  },
}));

export const RichTextEditor = ({ value, placeholder, readOnly, onFocus, onSave }: RichTextEditorProps) => {
  const [edited, setEdited] = useState<boolean | null>(null);

  const classes = useRichTextEditorStyles();
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
      listitem: classes.editorListitem,
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
    namespace: 'RichTextEditor',
    onError: console.error,
    theme,
    nodes: [HeadingNode, ListNode, ListItemNode, QuoteNode, HorizontalRuleNode, TextNode, CodeNode, LinkNode],
    editorState: () => $convertFromMarkdownString(value, TRANSFORMERS),
  };
  const save = (newValue: string) => {
    onSave(newValue);
    setEdited(false);
  };
  return (
    <LexicalComposer initialConfig={initialConfig}>
      <ToolbarPlugin
        readOnly={readOnly}
        pristine={!edited}
        onEdited={() => {
          setEdited(true);
        }}
        onSave={save}
      />
      <div className={classes.editorContainer}>
        <RichTextPlugin
          contentEditable={<ContentEditable onFocus={onFocus} readOnly={readOnly} edited={edited} onSave={save} />}
          placeholder={<div className={classes.editorPlaceholder}>{placeholder}</div>}
          ErrorBoundary={LexicalErrorBoundary}
        />
        <ValueUpdater value={value} setEdited={setEdited} />
        <ListPlugin />
        <OnChangePlugin
          ignoreSelectionChange={true}
          onChange={() => {
            if (edited === null) {
              // this is the initial render, not a real "change"
              setEdited(false);
            } else {
              setEdited(true);
            }
          }}
        />
        <MarkdownShortcutPlugin transformers={TRANSFORMERS} />
      </div>
    </LexicalComposer>
  );
};
