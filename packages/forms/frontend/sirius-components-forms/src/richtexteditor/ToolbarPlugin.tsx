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
import {
  $isListNode,
  INSERT_ORDERED_LIST_COMMAND,
  INSERT_UNORDERED_LIST_COMMAND,
  ListNode,
  REMOVE_LIST_COMMAND,
} from '@lexical/list';
import { useLexicalComposerContext } from '@lexical/react/LexicalComposerContext';
import { $createHeadingNode, $isHeadingNode } from '@lexical/rich-text';
import { $wrapNodes } from '@lexical/selection';
import { $getNearestNodeOfType, mergeRegister } from '@lexical/utils';
import Divider from '@material-ui/core/Divider';
import Paper from '@material-ui/core/Paper';
import { makeStyles, withStyles } from '@material-ui/core/styles';
import CodeIcon from '@material-ui/icons/Code';
import FormatBoldIcon from '@material-ui/icons/FormatBold';
import FormatItalicIcon from '@material-ui/icons/FormatItalic';
import FormatListBulletedIcon from '@material-ui/icons/FormatListBulleted';
import FormatListNumberedIcon from '@material-ui/icons/FormatListNumbered';
import StrikethroughSIcon from '@material-ui/icons/StrikethroughS';
import SubjectIcon from '@material-ui/icons/Subject';
import TitleIcon from '@material-ui/icons/Title';
import ToggleButton from '@material-ui/lab/ToggleButton';
import ToggleButtonGroup from '@material-ui/lab/ToggleButtonGroup';
import {
  $createParagraphNode,
  $getSelection,
  $isRangeSelection,
  COMMAND_PRIORITY_LOW,
  FORMAT_TEXT_COMMAND,
  SELECTION_CHANGE_COMMAND,
} from 'lexical';
import { useCallback, useEffect, useState } from 'react';
import { ToolbarPluginProps } from './RichTextEditor.types';

const useToolbarStyles = makeStyles((theme) => ({
  paper: {
    display: 'flex',
    flexDirection: 'row',
    borderBottom: `1px solid ${theme.palette.divider}`,
    flexWrap: 'wrap',
  },
  divider: {
    margin: theme.spacing(1, 0.5),
  },
  button: {
    color: theme.palette.primary.light,
    '&.Mui-selected': {
      color: theme.palette.primary.main,
    },
  },
}));

const StyledToggleButtonGroup = withStyles((theme) => ({
  grouped: {
    margin: theme.spacing(0.5),
    border: 'none',
    '&:not(:first-child)': {
      borderRadius: theme.shape.borderRadius,
    },
    '&:first-child': {
      borderRadius: theme.shape.borderRadius,
    },
  },
}))(ToggleButtonGroup);

export const ToolbarPlugin = ({ readOnly }: ToolbarPluginProps) => {
  const [editor] = useLexicalComposerContext();

  const [isBold, setIsBold] = useState<boolean>(false);
  const [isItalic, setIsItalic] = useState<boolean>(false);
  const [isStrikeTrough, setIsStrikeTrough] = useState<boolean>(false);
  const [isCode, setIsCode] = useState<boolean>(false);
  const [blockType, setBlockType] = useState('paragraph');

  const updateButtons = (toggled) => {
    setIsBold(toggled.includes('bold'));
    setIsItalic(toggled.includes('italic'));
    setIsStrikeTrough(toggled.includes('strikethrough'));
    setIsCode(toggled.includes('code'));
    if (toggled.includes('paragraph')) {
      setBlockType('paragraph');
    }
    if (toggled.includes('header1')) {
      setBlockType('header1');
    }
    if (toggled.includes('number-list')) {
      setBlockType('number-list');
    }
    if (toggled.includes('bullet-list')) {
      setBlockType('bullet-list');
    }
  };

  // Update the state of the toolbar's buttons according to the actual style of the current selection
  const updateToolbar = useCallback(() => {
    const selection = $getSelection();
    if ($isRangeSelection(selection)) {
      const anchorNode = selection.anchor.getNode();
      const element = anchorNode.getKey() === 'root' ? anchorNode : anchorNode.getTopLevelElementOrThrow();
      const elementKey = element.getKey();
      const elementDOM = editor.getElementByKey(elementKey);
      if (elementDOM !== null) {
        setIsBold(selection.hasFormat('bold'));
        setIsItalic(selection.hasFormat('italic'));
        setIsStrikeTrough(selection.hasFormat('strikethrough'));
        setIsCode(selection.hasFormat('code'));
        if ($isListNode(element)) {
          const parentList = $getNearestNodeOfType(anchorNode, ListNode);
          const type = parentList ? parentList.getTag() : element.getTag();
          if (type === 'ol') {
            setBlockType('number-list');
          } else if (type === 'ul') {
            setBlockType('bullet-list');
          } else {
            setBlockType(type);
          }
        } else {
          const type = $isHeadingNode(element) ? element.getTag() : element.getType();
          if (type === 'h1') {
            setBlockType('header1');
          } else if (type === 'paragraph') {
            setBlockType('paragraph');
          } else {
            setBlockType(type);
          }
        }
      }
    }
  }, []);

  useEffect(() => {
    return mergeRegister(
      editor.registerUpdateListener(({ editorState }) => {
        editorState.read(() => {
          updateToolbar();
        });
      }),
      editor.registerCommand(
        SELECTION_CHANGE_COMMAND,
        (_payload, _newEditor) => {
          updateToolbar();
          return false;
        },
        COMMAND_PRIORITY_LOW
      )
    );
  }, [editor, updateToolbar]);

  const toggled = [blockType];
  if (isBold) {
    toggled.push('bold');
  }
  if (isItalic) {
    toggled.push('italic');
  }
  if (isStrikeTrough) {
    toggled.push('strikethrough');
  }
  if (isCode) {
    toggled.push('code');
  }

  const classes = useToolbarStyles({});
  return (
    <Paper elevation={0} className={classes.paper}>
      <StyledToggleButtonGroup size="small" value={toggled} onChange={(_, newStyles) => updateButtons(newStyles)}>
        <ToggleButton
          classes={{ root: classes.button }}
          disabled={readOnly}
          value={'paragraph'}
          key={'paragraph'}
          onClick={() => {
            editor.update(() => {
              const selection = $getSelection();
              if ($isRangeSelection(selection)) {
                $wrapNodes(selection, () => $createParagraphNode());
              }
            });
          }}>
          <SubjectIcon fontSize="small" />
        </ToggleButton>
        <ToggleButton
          classes={{ root: classes.button }}
          disabled={readOnly}
          value={'header1'}
          key={'header1'}
          onClick={() => {
            editor.update(() => {
              const selection = $getSelection();
              if ($isRangeSelection(selection)) {
                $wrapNodes(selection, () => $createHeadingNode('h1'));
              }
            });
          }}>
          <TitleIcon fontSize="small" />
        </ToggleButton>
        <ToggleButton
          classes={{ root: classes.button }}
          disabled={readOnly}
          value={'bullet-list'}
          key={'bullet-list'}
          onClick={() => {
            if (blockType !== 'ul') {
              editor.dispatchCommand(INSERT_UNORDERED_LIST_COMMAND, null);
            } else {
              editor.dispatchCommand(REMOVE_LIST_COMMAND, null);
            }
          }}>
          <FormatListBulletedIcon fontSize="small" />
        </ToggleButton>
        <ToggleButton
          classes={{ root: classes.button }}
          disabled={readOnly}
          value={'number-list'}
          key={'number-list'}
          onClick={() => {
            if (blockType !== 'ol') {
              editor.dispatchCommand(INSERT_ORDERED_LIST_COMMAND, null);
            } else {
              editor.dispatchCommand(REMOVE_LIST_COMMAND, null);
            }
          }}>
          <FormatListNumberedIcon fontSize="small" />
        </ToggleButton>
      </StyledToggleButtonGroup>
      <Divider flexItem orientation="vertical" className={classes.divider} />
      <StyledToggleButtonGroup size="small" value={toggled} onChange={(_, newStyles) => updateButtons(newStyles)}>
        <ToggleButton
          classes={{ root: classes.button }}
          disabled={readOnly}
          value={'bold'}
          key={'bold'}
          onClick={() => {
            editor.dispatchCommand(FORMAT_TEXT_COMMAND, 'bold');
          }}>
          <FormatBoldIcon fontSize="small" />
        </ToggleButton>
        <ToggleButton
          classes={{ root: classes.button }}
          value={'italic'}
          key={'italic'}
          onClick={() => {
            editor.dispatchCommand(FORMAT_TEXT_COMMAND, 'italic');
          }}>
          <FormatItalicIcon fontSize="small" />
        </ToggleButton>
        <ToggleButton
          classes={{ root: classes.button }}
          disabled={readOnly}
          value={'code'}
          key={'code'}
          onClick={() => {
            editor.dispatchCommand(FORMAT_TEXT_COMMAND, 'code');
          }}>
          <CodeIcon fontSize="small" />
        </ToggleButton>
        <ToggleButton
          classes={{ root: classes.button }}
          disabled={readOnly}
          value={'strikethrough'}
          key={'strikethrough'}
          onClick={() => {
            editor.dispatchCommand(FORMAT_TEXT_COMMAND, 'strikethrough');
          }}>
          <StrikethroughSIcon fontSize="small" />
        </ToggleButton>
      </StyledToggleButtonGroup>
    </Paper>
  );
};
